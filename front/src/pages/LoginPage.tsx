import { inputs, buttons } from '@/components';
import { URL as ServerURL } from '@/states/Server';
import { useRecoilValue } from 'recoil';

import { useForm, SubmitHandler } from 'react-hook-form';
import { ErrorMessage } from '@hookform/error-message';
import axios from 'axios';

// typescript이기 때문에 interface를 지정해줘야 한다.
interface IFormInput {
  firstName: String;
  email: String;
}

function LoginPage() {
  // recoil에서 서버주소 가져오기
  const URL = useRecoilValue(ServerURL);

  // 이메일 유효성 검사 패턴
  const Regex = { email: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g };

  const {
    control,
    handleSubmit,
    formState: { errors, isSubmitted },
  } = useForm<IFormInput>({});

  const onSubmit: SubmitHandler<IFormInput> = (data) => {
    axios({
      method: 'post',
      url: `${URL}/members/login`,
      data: data,
    })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div>
      <p className="text-title1 mt-14 mb-3 ">다시 보니 반갑네요!</p>

      {/* 로그인 form */}
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="flex flex-col gap-4 mb-5"
      >
        {/* 이메일 입력받는 부분 */}
        <inputs.InputForm
          name="email"
          control={control}
          rules={{
            required: { value: true, message: '이메일을 입력해주세요' },
            pattern: {
              value: Regex.email,
              message: '이메일 형식을 입력해주세요',
            },
          }}
          type="email"
          placeholder="이메일"
        />
        <ErrorMessage errors={errors} name="email" />

        {/* 비밀번호 입력받는 부분 */}
        <inputs.InputForm
          name="password"
          control={control}
          rules={{
            required: { value: true, message: '비밀번호를 입력해주세요' },
          }}
          type="password"
          placeholder="비밀번호"
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
          <p className="text-main-100">회원가입</p>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
