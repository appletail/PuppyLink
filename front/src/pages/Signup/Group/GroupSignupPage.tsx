import { useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

import { ToastContainer } from 'react-toastify';

import { ErrorMessage } from '@hookform/error-message';

import BusinessValidate from './Components/BusinessValidate';

import { axBase } from '@/apis/api/axiosInstance'
import { inputs, buttons, ModalForm, NavTop, ChannelTalk } from '@/components';


interface SignupProps {
  email: string;
  password: string;
  passwordConfirm: string;
  businessName: string;
  name: string;
  phone: number;
  businessNo: number;
}

function GroupSignupPage() {
  ChannelTalk.hideChannelButton();
  const navigate = useNavigate();
  const {
    control,
    handleSubmit,
    formState: { errors },
    getValues,
    setError,
    watch
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

        .catch((err) => console.log(err));
    }
  };

  // 닉네임 중복검사
  const [NotgroupNameDuplicateCheck, setNotgroupNameDuplicateCheck] =
    useState(true);
  const groupNameValidate = () => {
    const businessName = getValues('businessName');
    if (!businessName) {
      setError('businessName', {
        type: 'businessName',
        message: '단체명을 입력해주세요.',
      });
      return;
    } else {
      // console.log(businessName)
      axBase({
        method: 'get',
        url: `/members/checkNickname/${businessName}`,
        data: businessName
      })
        .then((res) => {
          // console.log(res)
          const canUsegroupName = res.data.data;
          if (canUsegroupName) {
            // 중복확인 결과 이용가능한 닉네임인 경우
            setError('businessName', {
              type: 'pass',
              message: '사용가능한 단체명입니다.',
            });
            setNotgroupNameDuplicateCheck(false);
          } else {
            setError('businessName', {
              type: 'fail',
              message: '사용할 수 없는 단체명 입니다.',
            });
          }
        })

        .catch((err) => console.log(err));
    }
  };

  // 사업자 번호 인증
  const [NotbusinessNoValidateCheck, setNotbusinessNoValidateCheck] = useState(true)
  const [openModal, setOpenModal] = useState(false)


  // 회원가입 버튼 누르면 다음페이지로 이동

  const onSubmit: SubmitHandler<SignupProps> = (data) => {
    // console.log(data)
    navigate('/signup/confirm', {
      replace: true,
      state: {
        email: data.email,
        password: data.password,
        name: data.name,
        phone: data.phone,
        businessName: data.businessName,
        businessNo: data.businessNo,
      },
    })
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
      <NavTop.NavBack NavContent='회원가입' />
      <div className="mt-12">
        <p className="text-title1 mb-5">만나서 반가워요!</p>
        <div>
          <form
            className="flex flex-col gap-4 "
            onSubmit={handleSubmit(onSubmit)}
          >
            {/* 이메일 */}
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
            {/* 비밀번호 */}
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

            {/* 비밀번호 확인 */}
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

            {/* 닉네임 */}
            <inputs.InputFormBtn
              control={control}
              name="businessName"
              placeholder="단체명"
              type="text"
              button={
                <buttons.BtnSm BtnValue="중복확인" onClick={groupNameValidate} />
              }
              rules={{
                required: { value: true, message: '단체명을 입력해주세요' },
                validate: {
                  groupNamevalidate: () =>
                    !NotgroupNameDuplicateCheck || '단체명 중복확인을 해주세요',
                },
                onChange: () => {
                  setNotgroupNameDuplicateCheck(true);
                },
              }}
            />
            <ErrorMessage errors={errors} name="businessName" />

            {/* 이름 */}
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

            {/* 전화번호 */}
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

            {/* 사업자번호 */}
            <inputs.InputFormBtn
              control={control}
              name="businessNo"
              placeholder="사업자번호"
              type="number"
              button={
                <buttons.BtnSm BtnValue="인증하기" onClick={() => { setOpenModal(!openModal) }} />
              }
              rules={{
                required: { value: true, message: '사업자 번호를 입력해주세요' },
                validate: {
                  businessNovalidate: () =>
                    !NotbusinessNoValidateCheck || '사업자 번호 인증을 해주세요',
                },
                onChange: () => {
                  setNotbusinessNoValidateCheck(true);
                },
              }}
            />
            <ErrorMessage errors={errors} name="businessNo" />

            {openModal &&
              <ModalForm
                closeModal={() => setOpenModal(!openModal)}
                ModalContent={<BusinessValidate businessNo={getValues('businessNo')}
                  setNotbusinessNoValidateCheck={setNotbusinessNoValidateCheck}
                  setOpenModal={setOpenModal}
                />}
              />}
            <buttons.BtnLg BtnValue="회원가입" />
          </form>
        </div>
      </div>
      <ToastContainer />
    </div>
  );
}

export default GroupSignupPage;
