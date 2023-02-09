<<<<<<< HEAD
import  { useEffect } from 'react';
=======
import React, { useEffect } from 'react';
>>>>>>> dev
import { useForm, SubmitHandler } from 'react-hook-form';
import { MdMail } from 'react-icons/md';
import { useLocation, useNavigate } from 'react-router-dom';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';

import { axBase } from '@/apis/api/axiosInstance'
import { NavTop } from '@/components';





type ConfirmProps = {
  // confirmNumber: string;
};

let submitCount = 0;

function SignupConfirmPage() {
  const location = useLocation();
  const state = location.state;
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
  const navigate = useNavigate();

  useEffect(() => {
    if (formState.isValid && !isValidating) {
      // console.log(data.confirmNumber)
      axBase({
        method: 'post',
        url: '/members',
        data: {
          email: state.email,
          password: state.password,
          name: state.name,
          phone: state.phone,
          nickName: state.nickName,
          businessNo: state.businessNo? state.businessNo : "",
          auth: data.confirmNumber,
        },
      })
        .then(() => {
          navigate('/signup/success', {
            replace: true,
            state: {
              nickName: state.nickName,
            },
          });
        })
        .catch((err) => {
          // console.log(err.response.data.message);
          toast.error(err.response.data.message, {
            autoClose: 3000,
            position: toast.POSITION.BOTTOM_CENTER,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: 'light',
          });
        });
    }
  }, [formState, data, isValidating]);

  return (
    <div>
      <NavTop.NavBack NavContent='회원가입' />
      <div className="mt-16 text-center  ">
        {/* 상단 부분 */}
        <div className="flex flex-col items-center">
          <MdMail className="mb-2" size="4rem" />
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
              className="w-full p-4 appearance-none border-none rounded-lg  text-center text-headline-bold focus:border-none bg-select   focus:outline-select focus:shadow  invalid:border-red invalid:text-red"
              type="number"
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
        <ToastContainer />
      </div>
    </div>
  );
}

export default SignupConfirmPage;
