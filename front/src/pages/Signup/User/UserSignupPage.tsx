import { useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

import { ErrorMessage } from '@hookform/error-message';

import { axBase } from '@/apis/api/axiosInstance'
import { inputs, buttons, NavTop } from '@/components';



interface SignupProps {
  email: string;
  password: string;
  passwordConfirm: string;
  nickName: string;
  name: string;
  phone: number;
}

function UserSignupPage() {
  const navigate = useNavigate();
  const {
    control,
    handleSubmit,
    formState: { errors },
    getValues,
    setError,
    watch,
  } = useForm<SignupProps>({});

  // 이메일 & 비밀번호 유효성 검사 형식
  const Regex = {
    email: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g,
    password:
      /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,}$/,
  };

  // 이메일 중복검사
  const [NotEmailDuplicateCheck, setNotEmailDuplicateCheck] = useState(true);
  const emailValidate = () => {
    const email = getValues('email');
    //   이메일 유효성 검사 통과 못하면 리턴
    if (!Regex.email.test(email)) {
      setError('email', {
        type: 'email',
        message: '이메일 형식을 입력해주세요.',
      });
      return;
    } else {
      axBase({
        url: `/members/checkEmail/${email}`,
        data: email,
      })
        .then((res) => {
          const canUseEmail = res.data.data;
          if (canUseEmail) {
            // 중복확인 결과 이용가능한 이메일인 경우
            setError('email', {
              type: 'email',
              message: '사용가능한 이메일입니다.',
            });
            setNotEmailDuplicateCheck(false);
          } else {
            setError('email', {
              type: 'email',
              message: '사용할 수 없는 이메일입니다.',
            });
          }
        })

        .catch((err) => console.log(err.response.data));
    }
  };

  // 닉네임 중복검사
  const [NotnickNameDuplicateCheck, setNotnickNameDuplicateCheck] =
    useState(true);
  const nickNameValidate = () => {
    const nickName = getValues('nickName');
    if (!nickName) {
      setError('nickName', {
        type: 'nickName',
        message: '닉네임을 입력해주세요.',
      });
      return;
    } else {
      axBase({
        method: 'get',
        url: `/members/checkNickname/${nickName}`,
        data: nickName,
      })
        .then((res) => {
          const canUsenickName = res.data.data;
          if (canUsenickName) {
            // 중복확인 결과 이용가능한 닉네임인 경우
            setError('nickName', {
              type: 'pass',
              message: '사용가능한 닉네임입니다.',
            });
            setNotnickNameDuplicateCheck(false);
          } else {
            setError('nickName', {
              type: 'fail',
              message: '사용할 수 없는 닉네임입니다.',
            });
          }
        })

        .catch((err) => console.log(err));
    }
  };

  // 회원가입 버튼 누르면 다음페이지로 이동

  const onSubmit: SubmitHandler<SignupProps> = (data) => {
    // console.log(data.email)
    navigate('/signup/confirm', {
      replace: true,
      state: {
        email: data.email,
        password: data.password,
        name: data.name,
        phone: data.phone,
        nickName: data.nickName,
      },
    });
    axBase({
      method: 'post',
      url: '/members/mail',
      data: {
        email: data.email,
      },
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch(() => {
        setError('email', {
          type: 'email',
          message: '사용할 수 없는 이메일입니다.',
        });
      });
  };
  // form 디자인
  return (
    <div>

      <div className="mt-14">
        <NavTop.NavBack NavContent='회원가입' />
        <p className="text-title1 mb-5">만나서 반가워요!</p>
        <div>
          <form
            className="flex flex-col gap-4 "
            onSubmit={handleSubmit(onSubmit)}
          >
            <inputs.InputFormBtn
              control={control}
              name="email"
              placeholder="이메일"
              type="email"
              button={
                <buttons.BtnSm BtnValue="중복확인" onClick={emailValidate} />
              }
              rules={{
                required: { value: true, message: '이메일을 입력해주세요' },
                pattern: {
                  value: Regex.email,
                  message: '이메일 형식을 입력해주세요',
                },
                validate: {
                  emailvalidate: () =>
                    !NotEmailDuplicateCheck || '이메일 중복확인을 해주세요',
                },
                onChange: () => {
                  setNotEmailDuplicateCheck(true);
                },
              }}
            />
            <ErrorMessage errors={errors} name="email" />

            <inputs.InputForm
              control={control}
              name="password"
              placeholder="비밀번호 (대문자, 특수문자를 포함해 8자 이상)"
              type="password"
              rules={{
                required: { value: true, message: '비밀번호를 입력해주세요' },
                pattern: {
                  value: Regex.password,
                  message: '대문자, 특수문자를 포함해 8자 이상입력해주세요',
                },
              }}

            />

            <ErrorMessage errors={errors} name="password" />

            <inputs.InputForm
              control={control}
              name="passwordConfirm"
              placeholder="비밀번호 확인"
              type="password"
              rules={{
                required: {
                  value: true,
                  message: '비밀번호 확인을 입력해주세요',
                },
                validate: (val: string) => {
                  if (watch('password') != val) {
                    return '비밀번호가 일치하지 않습니다';
                  }
                },
              }}

            />

            <ErrorMessage errors={errors} name="passwordConfirm" />

            <inputs.InputFormBtn
              control={control}
              name="nickName"
              placeholder="닉네임"
              type="text"
              button={
                <buttons.BtnSm BtnValue="중복확인" onClick={nickNameValidate} />
              }
              rules={{
                required: { value: true, message: '닉네임을 입력해주세요' },
                validate: {
                  nickNamevalidate: () =>
                    !NotnickNameDuplicateCheck || '닉네임 중복확인을 해주세요',
                },
                onChange: () => {
                  setNotnickNameDuplicateCheck(true);
                },
              }}
            />

            <ErrorMessage errors={errors} name="nickName" />

            <inputs.InputForm
              control={control}
              name="name"
              placeholder="이름"
              type="name"
              rules={{
                required: { value: true, message: '이름을 입력해주세요' },
              }}
            />
            <ErrorMessage errors={errors} name="name" />

            <inputs.InputForm
              control={control}
              name="phone"
              placeholder="전화번호 ('-'를 빼고 입력하세요)"
              type="number"
              rules={{
                required: { value: true, message: '전화번호을 입력해주세요' },
              }}
            />
            <ErrorMessage errors={errors} name="phone" />

            <buttons.BtnLg BtnValue="회원가입" />
          </form>
        </div>
      </div>
    </div>
  );
}

export default UserSignupPage;
