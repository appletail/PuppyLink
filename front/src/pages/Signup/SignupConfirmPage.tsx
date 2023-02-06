import React, { useEffect } from 'react';
import { MdMail } from 'react-icons/md';
import { useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { URL as ServerURL } from '@/states/Server';
import axios from 'axios';
import { useForm, SubmitHandler } from 'react-hook-form';

type ConfirmProps = {
  // confirmNumber: string;
};

let submitCount = 0;

function SignupConfirmPage() {
  const URL = useRecoilValue(ServerURL);
  const location = useLocation();
  const state = location.state
  const email = state.email;

  const {
    register,
    handleSubmit,
    watch,
    formState,
    formState: { isValidating },
  } = useForm({
    mode: 'onChange',
  });

  const onSubmit: SubmitHandler<ConfirmProps> = () => {
    submitCount++;
    console.log(submitCount);
  };

  const data = watch();

  useEffect(() => {
    if (formState.isValid && !isValidating) {
      // console.log(data.confirmNumber)
      const sendEmail = axios({
        method: 'post',
        url: `${URL}/members/signup`,
        data: {
          email: state.email,
          password: state.password,
          name: state.name,
          phone: state.phone,
          nickName: state.nickName,
          auth: data.confirmNumber
        },
      })
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    }
  }, [formState, data, isValidating]);

  return (
    <div>
      <div className="mt-16 text-center  ">
        {/* 상단 부분 */}
        <div className="flex flex-col items-center">
          <MdMail size="4rem" className="mb-2" />
          <p className="text-title1-bold pb-1">인증번호를 입력해 주세요</p>
          <div className="text-body p-2">
            <div>회원가입 인증메일이 전송되었습니다.</div>
            <div>{email}을 확인해주세요.</div>
          </div>
        </div>

        {/* 숫자 입력받는 부분 */}
        <div className="p-4">
          <form onSubmit={handleSubmit(onSubmit)}>
            <input
              type="number"
              className="w-full p-4 appearance-none border-none rounded-lg  text-center text-headline-bold focus:border-none bg-select   focus:outline-select focus:shadow  invalid:border-red invalid:text-red"
              {...register('confirmNumber', {
                required: '인증번호를 입력해주세요.',
                minLength: 4,
                maxLength: 4,
                onChange: (e) => {
                  if (e.target.value.length >= 4) {
                    e.target.value = e.target.value.slice(0, 4);
                  }
                },
              })}
            />
          </form>
        </div>

        <div className="text-body p-2">코드를 받지 못하였나요? 재전송하기</div>
      </div>
    </div>
  );
}

export default SignupConfirmPage;
