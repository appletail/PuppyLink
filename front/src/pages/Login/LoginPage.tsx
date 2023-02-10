import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

import { ErrorMessage } from '@hookform/error-message';

import { axBase } from '@/apis/api/axiosInstance'
import { NavTop, inputs, buttons } from '@/components';


// typescript이기 때문에 interface를 지정해줘야 한다.
interface LoginProps {
  firstName: string;
  email: string;
  password: string;
}

function LoginPage() {
  // recoil에서 서버주소 가져오기

  const navigate = useNavigate();
  const goSignup = () => {
    navigate('/signup/userTab');
  };

  // 이메일 유효성 검사 패턴
  const Regex = { email: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g };

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginProps>({});

  const onSubmit: SubmitHandler<LoginProps> = (data) => {
    axBase({
      method: 'post',
      url: 'members/login',
      data: data,
    })
      .then((response) => {
        // console.log(response)
        navigate('/')
        // Bearer를 제외하고 token을 저장합니다
        const access_token = response.headers.authorization.split(" ")[1];
        const refresh_token = response.headers.refreshtoken.split(" ")[1];
        if (access_token) {
          localStorage.setItem('access-token', access_token);
          localStorage.setItem('refresh-token', refresh_token);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div>
      <NavTop.NavBack NavContent='로그인' />
      <p className="text-title1 mt-14 mb-3 ">다시 보니 반갑네요!</p>

      {/* 로그인 form */}
      <form
        className="flex flex-col gap-4 mb-5"
        onSubmit={handleSubmit(onSubmit)}
      >
        {/* 이메일 입력받는 부분 */}
        <inputs.InputForm
          control={control}
          name="email"
          placeholder="이메일"
          type="email"
          rules={{
            required: { value: true, message: '이메일을 입력해주세요' },
            pattern: {
              value: Regex.email,
              message: '이메일 형식을 입력해주세요',
            },
          }}
        />
        <ErrorMessage errors={errors} name="email" />

        {/* 비밀번호 입력받는 부분 */}
        <inputs.InputForm
          control={control}
          name="password"
          placeholder="비밀번호"
          type="password"
          rules={{
            required: { value: true, message: '비밀번호를 입력해주세요' },
          }}
        />
        <ErrorMessage errors={errors} name="password" />
        <buttons.BtnLg BtnValue="로그인" />
      </form>

      {/* 소셜 로그인 */}
      <div className="mb-32">
        <p className="text-body mb-4">소셜로 로그인 하기</p>
        <div></div>
      </div>

      {/* 비밀번호 찾기, 회원가입 */}
      <div className="flex flex-col gap-4">
        <p className="text-body">혹시</p>
        <div className="flex justify-between">
          <p>비밀번호를 잊으셨나요?</p>
          <p className="text-main-100">비밀번호 찾기</p>
        </div>
        <div className="flex justify-between">
          <p>아직 회원이 아니신가요?</p>
          <div className="text-main-100" onClick={goSignup}>
            회원가입
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
