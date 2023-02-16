import { useState, useEffect } from "react"
import { useForm } from 'react-hook-form'
import { useNavigate } from "react-router-dom"
import { toast, ToastContainer } from "react-toastify"

import ClassicEditor from "@ckeditor/ckeditor5-build-classic"
import { CKEditor } from "@ckeditor/ckeditor5-react"

import UploadImg from "./Components/UploadImg"

import { axAuth } from "@/apis/api/axiosInstance"
import { inputs, NavTop, ChannelTalk } from "@/components"

interface Review {
  contents: string
  subject: string
  pictureURL: string
}


function ReviewCreatePage() {

  // const [boardNo, setBoardNo] = useState("")
  
  const userData = sessionStorage.getItem("userData") || ""
  const parsedUserData = JSON.parse(userData)
  const email = parsedUserData.email

  const [imgValue, setImgValue] = useState<FileList | string>('')
  const { control, handleSubmit, formState: { isValid }, register, setValue } = useForm<Review>({})
  const navigate = useNavigate()

  const onSubmit = async (data: Review) => {
    const file = data.pictureURL[0]

    await axAuth({
      method: 'post',
      url: '/board',
      data: {
        email: email,
        contents: data.contents,
        subject: data.subject,
        likes: 0,
      }
    })
      .then((res) => {
        return res.data.data.boardNo
      })
      .then(async (boardNo) => {
        const formData = new FormData()
        file && formData.append('multipartFile', file)
        formData.append('boardNo', boardNo)

        await axAuth({
          method: 'post',
          url: '/file/board/',
          data: formData
        })
      })
      .then(() => {
        alert('게시물이 작성되었습니다.')
        navigate('/review', { replace: true })
      })
      .catch((err) => console.log(err))
  }

  useEffect(() => {
    register('contents')
  })

  ChannelTalk.hideChannelButton();

  return (
    <div className="w-[21.875rem]">
      <div>
        <NavTop.NavCreate onClick={() => {
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
            return
          }
          handleSubmit(onSubmit)()
        }} />
      </div>
      <div className="mt-6 mb-6">
        <p className='text-title2-bold'> 봉사 후기를 남겨주세요! </p>
      </div>
      <div>
        <form className="flex flex-col gap-2" >
          <inputs.InputForm control={control}
            name="subject"
            placeholder="제목을 입력하세요."
            type="text" />
          <CKEditor
            // class="ck-editor__editable "
            editor={ClassicEditor}
            config={{
              placeholder: "여기에 내용을 입력하세요.",
              toolbar: [],
              width: 100,
            }}
            onChange={(event, editor) => {
              setValue('contents', editor.getData())
              // trigger('contents')
            }}
          />
          <UploadImg
            control={control}
            name='pictureURL'
            rules={{ validate: (v: any) => v != undefined && v != '', }}
            setImageData={(img: FileList | string) => setImgValue(img)}
          />
        </form>
      </div>
      <ToastContainer />
    </div >
  )
}

export default ReviewCreatePage