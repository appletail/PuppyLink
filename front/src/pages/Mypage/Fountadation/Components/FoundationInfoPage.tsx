import { useEffect, useMemo, useState } from 'react'
import { SubmitHandler, useForm } from 'react-hook-form'

import ClassicEditor from "@ckeditor/ckeditor5-build-classic"
import { CKEditor } from "@ckeditor/ckeditor5-react"
import { ErrorMessage } from '@hookform/error-message'


import { axBase } from '@/apis/api/axiosInstance'
import { NavTop, inputs, buttons } from '@/components'


interface InfoProps {
  businessNo: string;
  description: string;
  nickName: string;
}




function FoundationInfoPage() {

  const [useremail, setUserEmail] = useState()
  const [usernickName, setUsernickName] = useState()
  const userData = sessionStorage.getItem("userData") || ""
  useEffect(() => {
    // 로그인 되어있다면 userData를 가져온다

    const { email, nickName } = JSON.parse(userData)
    setUserEmail(email)
    // setUsernickName(nickName)
    // console.log(useremail, usernickName)
  })

  const {
    control,
    handleSubmit,
    formState: { errors },
    register,
    setValue
  } = useForm<InfoProps>({
    defaultValues: {
      businessNo: useremail,
    }
    // defaultValues: {
    //   businessNo: useMemo(() => {
    //     const userData = sessionStorage.getItem("userData") || ""
    //     const { email, nickName } = JSON.parse(userData)
    //     setUserEmail(email)
    //     setUsernickName(nickName)
    //     return usernickName
    //   }, [usernickName])
    // }
  });

  useEffect(() => {
    register('description')
  })



  const onSubmit = (data: InfoProps) => {
    console.log(data)
  }



  return (
    <div>
      <div>
        <NavTop.NavBack NavContent='단체소개 변경' />
      </div>
      <div className="mt-14">
        <p className='text-title1-bold'> 단체 소개 변경</p>
        {/* 단체 이미지가 들어갈 곳입니다 */}
        <div></div>

        {/* form이 들어갈 부분입니다. */}
        <form className='flex flex-col gap-2' onSubmit={handleSubmit(onSubmit)}>
          <fieldset disabled>
            <inputs.InputForm
              control={control}
              name="businessNo"
              type="text"
              rules={{
                value: usernickName,

              }}

            />
          </fieldset>

          <CKEditor
            // class="ck-editor__editable "
            editor={ClassicEditor}
            config={{
              placeholder: "여기에 내용을 입력하세요.",
              toolbar: [],
              width: 100,

            }}
            onChange={(event, editor) => {
              setValue('description', editor.getData())
            }}


          />

          <buttons.BtnLg BtnValue={"제출하기"} />
        </form>
      </div>
    </div>
  )
}

export default FoundationInfoPage