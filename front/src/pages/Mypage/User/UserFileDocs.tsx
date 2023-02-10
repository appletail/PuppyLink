import React from 'react'
import { useForm } from 'react-hook-form'
import { ToastContainer, toast } from 'react-toastify';

import EticketUpload from './Components/EticketUpload'
import PassportUpload from './Components/PassportUpload'

import { buttons, NavTop } from '@/components'
import "react-toastify/dist/ReactToastify.css";



function UserFileDocs() {

  const { control, handleSubmit, register, formState: { isValid } } = useForm()

  const notify = () => {
    if (!isValid) {
      toast.error('모든 항목을 작성해주세요.', {
        position: "bottom-center",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: 0,
        theme: "colored",
      })
    }
  }

  return (
    <div>
      <NavTop.NavBack NavContent='서류 제출' />
      <div className='mt-12 mb-5'>
        <p className='text-title1-bold px-5'>서류 제출</p>
      </div>
      <hr className='bg-grey border-none h-[0.5px] mb-7 w-screen' />
      <div>
        <form onSubmit={handleSubmit((data) => console.log(data))}>
          <div className='px-5'>
            <p className='text-body-bold mb-4'>여권 사진 제출</p>
            <div className='mb-3 flex gap-4'>
              {/* 여권 사진 넣는 곳 */}
              <PassportUpload control={control} name='image' rules={{ validate: (v: any) => v != undefined && v != '', }} />
            </div>
            <p className='text-footnote text-grey mb-6'>검역 위임을 위해 영문 성함, 생년월일, 사진이 나오게 찍어주세요.</p>
          </div>
          <hr className='bg-grey border-none h-[0.5px] mb-7 w-screen' />
          <div className='px-5 mb-7 flex justify-between'>
            <p className='text-body-bold'>E-Ticket 제출</p>
            <buttons.BtnSm BtnValue='사진으로 입력' />
          </div>
          <div className='px-5 mb-4 flex flex-col gap-4'>
            {/* 입력 폼 넣는 곳 */}
            <EticketUpload control={control} rules={{ validate: (v: any) => v != undefined && v != '', }} />
            <buttons.BtnLg BtnValue='제출하기' onClick={notify} />
          </div>
        </form>
      </div>
      <ToastContainer />
    </div>
  )
}

export default UserFileDocs