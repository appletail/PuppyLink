import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { ErrorMessage } from '@hookform/error-message';


import { axBase } from '@/apis/api/axiosInstance'
import { NavTop, inputs, buttons, ChannelTalk } from '@/components';


// typescript이기 때문에 interface를 지정해줘야 한다.
interface LoginProps {
  firstName: string;
  email: string;
  password: string;
}

function LoginPage() {
  
  ChannelTalk.hideChannelButton();
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
        const resData = response.data.data
        navigate('/')
        // Bearer를 제외하고 token을 저장합니다
        const access_token = response.headers.authorization.split(" ")[1];
        const refresh_token = response.headers.refreshtoken.split(" ")[1];

        // session storage에 access token과 refresh token을 저장합니다
        if (access_token) {
          sessionStorage.setItem('access-token', access_token);
          sessionStorage.setItem('refresh-token', refresh_token);

          // local storage에 Login한 user의 정보를 userData로 저장합니다
          const LoginData: Member =
          {
            email: resData.email,
            name: resData.name,
            phone: resData.phone,
            nickName: resData.nickName,
            activated: true,
            authorities: [{ "authorityName": resData.authorities[0].authorityName }],
            joinDate: resData.joinDate,
            role: resData.authorities[0].authorityName
          }
          sessionStorage.setItem('userData', JSON.stringify(LoginData))
          // 챗봇 프로필 정보 삽입
          ChannelTalk.updateUser({
            language: 'ko',
            profile: {
              email: resData.email,
              phone: resData.phone,
              nickName: resData.nickName,
            }
          });
        }
      })
      .catch(() => {
        toast.error('아이디 또는 비밀번호를 잘못 입력했습니다.', {
          autoClose: 3000,
          position: toast.POSITION.BOTTOM_CENTER,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: 'colored',
        })
      });
  };

  return (
    <div className='w-[21.875rem]'>
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
          <div aria-hidden='true' className="text-main-100" onClick={goSignup}>
            회원가입
          </div>
        </div>
      </div>
      <ToastContainer />
    </div>
  );
}

export default LoginPage;
