import { useState, useEffect } from 'react'
import { SubmitHandler, useForm } from 'react-hook-form'

import { useNavigate } from 'react-router-dom';

import FoundationImage from './FoundationImage';

import FormStyle from './FoundationInfoChange.module.css';
import { axAuth } from "@/apis/api/axiosInstance"

import { NavTop, buttons } from '@/components'
interface InfoProps {
  description: string;
  nickName: string;
  pictureURL: string
}


function FoundationInfoChangePage() {
  const [useremail, setUserEmail] = useState("")
  const [usernickName, setUsernickName] = useState("")
  const userData = sessionStorage.getItem("userData") || ""
  const navigate = useNavigate()

  useEffect(() => {
    const { email, nickName } = JSON.parse(userData)
    setUserEmail(email)
    setUsernickName(nickName)
  }, [])


  const [imgValue, setImgValue] = useState<FileList | string>('')
  const { register, handleSubmit, formState: { errors }, control } = useForm<InfoProps>();
  const onSubmit: SubmitHandler<InfoProps> = async (data) => {
    const FoundationIntroduceData = {
      description: data.description,
      members_email: useremail,
    }

    const file = data.pictureURL[0]

    await axAuth({
      method: 'post',
      url: '/foundation',
      data: FoundationIntroduceData
    }).then((res) => {
      console.log(res)
      const formData = new FormData()
      file && formData.append('multipartFile', file)
      formData.append('nickName', usernickName)
      // console.log(formData)
      axAuth({
        method: 'post',
        url: '/file/profile',
        data: formData
      })
    }).then((res) => {
      alert('단체 소개 변경이 완료되었습니다.')
      navigate('/mypage/manager', { replace: true })
    })
    // .catch((err) => console.log(err))
    // console.log(userInfo)
  }
  return (
    <div>
      <div>
        <NavTop.NavBack NavContent='단체소개 변경' />
      </div>
      <div className="mt-14">
        <p className='text-title1-bold'> 단체 소개 변경</p>
        <form className='mt-4 flex flex-col gap-3' onSubmit={handleSubmit(onSubmit)}>
          <FoundationImage
            control={control}
            name='pictureURL'
            rules={{ validate: (v: any) => v != undefined && v != '', }}
            setImageData={(img: FileList | string) => setImgValue(img)}
          />

          <input className={FormStyle.InputBox} defaultValue={usernickName} {...register("nickName", { disabled: true })} />
          <textarea className={FormStyle.TextBox}
            defaultValue="우리는 동물을 사랑해요" {...register("description")} />
          <buttons.BtnLg BtnValue={"제출하기"} />
        </form>
      </div>

    </div >
  )
}

export default FoundationInfoChangePage