import { useState } from 'react';
import { FieldValues, useForm } from 'react-hook-form'
import { useLocation } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';

import { useRecoilValue } from 'recoil';

import EticketUpload from './Components/EticketUpload'
import PassportUpload from './Components/PassportUpload'

import { axBase, axAuth } from '@/apis/api/axiosInstance';
import { buttons, NavTop } from '@/components'
import "react-toastify/dist/ReactToastify.css";
import { LoginState } from '@/states/LoginState';



function UserFileDocs() {

  let nickName = ""

  // recoil에서 로그인 여부를 판단한다.
  const isLoggedIn = useRecoilValue(LoginState)
  if (isLoggedIn) {
    // 로그인 되어있다면 userData를 가져온다
    const userData = localStorage.getItem("userData") || ""
    const parsedUserData = JSON.parse(userData)
    nickName = parsedUserData.nickName
  }

  // 로그인이 안되어있으면 로그인 페이지로 리다이렉트 한다.
  const location = useLocation()
  const volunteerNo = location.state.volNo

  const submitDocs = (data: FieldValues) => {
    // 여권사진 전송
    const file = data.image[0]
    const fileDto = {
      nickName,
      ticketType: 'passport',
      volunteerNo,
    }

    const formData = new FormData()
    const blob = new Blob([JSON.stringify(fileDto)], {
      type: 'application/json',
    });
    file && formData.append('multipartFile', file)
    formData.append('fileDto', blob)

    axAuth({
      method: 'post',
      url: '/file/history/',
      data: formData
    })

    // 티켓 정보 제출
    const E_TicketData = { ...data }
    delete E_TicketData.image
    const Tdata = {
      ...E_TicketData,
      volunteerNo
    }
    axAuth({
      method: 'post',
      url: '/volunteer/docs',
      data: Tdata
    })

  }
  const [ocrData, setOcrData] = useState<ocrData>()

  const submitOCR = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files
    const fileDto = {
      nickName,
      ticketType: 'flight',
      volunteerNo,
    }

    const formData = new FormData()
    const blob = new Blob([JSON.stringify(fileDto)], {
      type: 'application/json',
    });
    files && formData.append('multipartFile', files[0])
    formData.append('fileDto', blob)

    if (files) {
      axAuth({
        method: 'post',
        url: '/file/history/',
        data: formData
      })
        .then(() => {
          axBase({ url: `/volunteer/ocr/${volunteerNo}` })
            .then((res) => setOcrData(res.data.data))
        })
    }
  }

  const [imgValue, setImgValue] = useState<FileList | string>('')
  const { control, handleSubmit, formState: { isValid }, } = useForm({
    values: { ...ocrData, image: imgValue }
  })

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
        <form onSubmit={handleSubmit(submitDocs)}>
          <div className='px-5'>
            <p className='text-body-bold mb-4'>여권 사진 제출</p>
            <div className='mb-3 flex gap-4'>
              {/* 여권 사진 넣는 곳 */}
              <PassportUpload
                control={control}
                name='image'
                rules={{ validate: (v: any) => v != undefined && v != '', }}
                setImageData={(img: FileList | string) => setImgValue(img)} />
            </div>
            <p className='text-footnote text-grey mb-6'>검역 위임을 위해 영문 성함, 생년월일, 사진이 나오게 찍어주세요.</p>
          </div>
          <hr className='bg-grey border-none h-[0.5px] mb-7 w-screen' />
          <div className='px-5 mb-7 flex justify-between'>
            <p className='text-body-bold'>E-Ticket 제출</p>
            {/* OCR 제출 */}
            <input accept="image/*" className='hidden' id="submitocr" type="file" onChange={submitOCR} />
            <label htmlFor="submitocr">
              <div className="bg-main-100 text-white rounded-full px-3 h-7 text-footnote flex items-center">사진으로 입력</div>
            </label>
          </div>
          <div className='px-5 mb-4 flex flex-col gap-4'>
            {/* 입력 폼 넣는 곳 */}
            <EticketUpload
              control={control}
              rules={{ validate: (v: any) => v != undefined && v != '', }}
            />
            <buttons.BtnLg BtnValue='제출하기' onClick={notify} />
          </div>
        </form>
      </div>
      <ToastContainer />
    </div>
  )
}

export default UserFileDocs