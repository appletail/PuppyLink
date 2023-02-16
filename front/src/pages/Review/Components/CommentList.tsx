import { useState, useEffect } from 'react'
import { useForm, SubmitHandler } from 'react-hook-form';
import { useParams } from 'react-router-dom'

import { useRecoilValue } from 'recoil';

import { axAuth, axBase } from '@/apis/api/axiosInstance'
import { inputs, buttons, ChannelTalk } from '@/components'
import { LoginState } from '@/states/LoginState';

interface CommentProps {
  letter: string
}

function CommentList() {
  const [commentList, setCommentList] = useState<any[]>([])
  const boardNo = useParams().boardNo
  ChannelTalk.hideChannelButton()
  const auth = sessionStorage.getItem('access-token')


  useEffect(() => {
    axBase({
      url: `/board/comment/list/${boardNo}`
    })
      .then((res) => {
        setCommentList(res.data.data)
      })
      .catch((err) => console.log(err.response.data))
  }, [])

  const comment = commentList.map((comment) => {
    return (
      <div key={comment.commentNo} className="my-2 py-2  
      px-3 rounded-md bg-main-10 bg-transparent-30 flex flex-col gap-1">
        <p className="text-footnote text-grey">@ {comment.email.nickName}</p>
        <p className='text-footnote'>{comment.letter}</p>
      </div>
    )
  })

  const {
    control,
    handleSubmit,
    reset
  } = useForm<CommentProps>({});

  const onSubmit: SubmitHandler<CommentProps> = (data) => {

    const userData = sessionStorage.getItem("userData") || ""
    const parsedUserData = JSON.parse(userData)
    const email = parsedUserData.email
    const nickName = parsedUserData.nickName
    const newComment = {
      commentNo: Math.random(),
      email: {
        nickName: nickName
      },
      letter: data.letter
    }
    setCommentList((commentList) => {

      return [newComment, ...commentList]
    })
    reset()


    const commentData = {
      boardNo: boardNo,
      comentNo: 1,
      email: email,
      letter: data.letter,
      regDate: "",
    }
    axAuth({
      url: '/board/comment',
      method: 'post',
      data: commentData
    })
      .then((res) => {
        // console.log(res)
      })
      .catch((err) => {
        // console.log(err)
      })


  }

  return (
    <div>
      {/* 새로운 덧글 작성하는 부분 */}
      <div className="mt-2 ">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="flex justify-between items-center gap-1">
            {/* 로그인 여부에 따른 disabled 처리 */}
            {auth ?
              <inputs.InputForm
                control={control}
                name="letter"
                placeholder='댓글을 입력하세요'
              />
              :
              <fieldset disabled>
                <inputs.InputForm
                  control={control}
                  name="letter"
                  placeholder='로그인 후에 입력할 수 있습니다.'
                  rules={{
                    validate: v => v != undefined
                  }}
                />
              </fieldset>
            }

            {/* 작성버튼 */}
            <div className='min-w-[50px] flex justify-end' onClick={() => handleSubmit(onSubmit)()}>
              <buttons.BtnSm BtnValue={"작성"} />
            </div>
          </div>
        </form>
      </div>

      {/* 댓글내용 */}
      {comment}
    </div>
  )
}

export default CommentList