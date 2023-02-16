
import { useForm, SubmitHandler } from 'react-hook-form';

import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';

import { ErrorMessage } from '@hookform/error-message';
import { useRecoilValue } from 'recoil';

import { axBase } from '@/apis/api/axiosInstance';
import { NavTop, buttons, inputs } from '@/components'
import { LoginState } from '@/states/LoginState';



import 'react-toastify/dist/ReactToastify.css';

interface PasswordProps {
  rawPassword: string;
  newPassword: string;
  newPasswordConfirm: string;
}


function UserChangePassword() {

  //  비밀번호 유효성 검사 형식
  const Regex = {
    password:
      /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,}$/,
  };

  const {
    control,
    handleSubmit,
    formState: { errors },
    watch
  } = useForm<PasswordProps>({});



  let usernickName = ""
  // recoil에서 로그인 여부를 판단한다.
  const isLoggedIn = useRecoilValue(LoginState)
  if (isLoggedIn) {
    // 로그인 되어있다면 userData를 가져온다
    const userData = sessionStorage.getItem("userData") || ""
    const { nickName } = JSON.parse(userData)
    usernickName = nickName
  }

  const navigate = useNavigate();
  const onSubmit: SubmitHandler<PasswordProps> = (data) => {
    axBase({
      url: `/members/${usernickName}/change`,
      method: "put",
      data: {
        rawPassword: data.rawPassword,
        newPassword: data.newPassword
      }
    }).then((res) => {
      const code = res.data.code
      if (code === 2302) {
        toast.error("현재 비밀번호를 확인해주세요", {
          autoClose: 3000,
          position: toast.POSITION.BOTTOM_CENTER,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: 'colored',
        })
      }
      else {
        alert("비밀번호 변경에 성공했습니다.")
        navigate("/")
      }
    })
      .catch((err) => console.log(err))
  }

  return (
    <div className='w-[21.875rem]'>
      <div><NavTop.NavBack NavContent='비밀번호 변경' /></div>
      <div className="mt-14">
        <p className="text-title1-bold">비밀번호 변경</p>
        <div className="mt-5">
          <form
            className="flex flex-col gap-4 mb-5"
            onSubmit={handleSubmit(onSubmit)}
          >
            <inputs.InputForm control={control}
              name="rawPassword"
              placeholder="현재 비밀번호"
              type="password"
              rules={{
                required: { value: true, message: '현재 비밀번호를 입력해주세요' },
              }} />
            <ErrorMessage errors={errors} name="rawPassword" />

            <inputs.InputForm control={control}
              name="newPassword"
              placeholder="새 비밀번호 (대문자, 특수문자를 포함해 8자 이상)"
              type="password"
              rules={{
                required: { value: true, message: '변경할 비밀번호를 입력해주세요' },
                pattern: {
                  value: Regex.password,
                  message: '대문자, 특수문자를 포함해 8자 이상입력해주세요',
                },
              }} />
            <ErrorMessage errors={errors} name="newPassword" />

            <inputs.InputForm control={control}
              name="newPasswordConfirm"
              placeholder="비밀번호 확인"
              type="password"
              rules={{
                required: {
                  value: true,
                  message: '비밀번호 확인을 입력해주세요',
                },
                validate: (val: string) => {
                  if (watch('newPassword') != val) {
                    return '비밀번호가 일치하지 않습니다';
                  }
                },
              }} />
            <ErrorMessage errors={errors} name="newPasswordConfirm" />

            <buttons.BtnLg BtnValue="변경하기" />
          </form>
        </div>
      </div>
      <ToastContainer />
    </div>
  )
}

export default UserChangePassword