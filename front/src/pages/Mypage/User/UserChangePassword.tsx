
import { useForm, SubmitHandler } from 'react-hook-form';

import { ErrorMessage } from '@hookform/error-message';

import { NavTop, buttons, inputs } from '@/components'

interface PasswordProps {
  currentPassword: string;
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

  const onSubmit: SubmitHandler<PasswordProps> = (data) => { console.log(data) }

  return (
    <div>
      <div><NavTop.NavBack NavContent='비밀번호 변경' /></div>
      <div className="mt-14">
        <p className="text-title1-bold">비밀번호 변경</p>
        <div className="mt-5">
          <form
            className="flex flex-col gap-4 mb-5"
            onSubmit={handleSubmit(onSubmit)}
          >
            <inputs.InputForm control={control}
              name="currentPassword"
              placeholder="현재 비밀번호"
              rules={{}}
              type="password" />
            <ErrorMessage errors={errors} name="currentPassword" />

            <inputs.InputForm control={control}
              name="newPassword"
              placeholder="새 비밀번호 (대문자, 특수문자를 포함해 8자 이상)"
              type="password"
              rules={{
                required: { value: true, message: '비밀번호를 입력해주세요' },
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
    </div>
  )
}

export default UserChangePassword