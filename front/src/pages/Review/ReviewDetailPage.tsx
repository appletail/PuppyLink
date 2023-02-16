import { useState, useEffect } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom'

import HTMLReactParser from 'html-react-parser'

import CommentList from './Components/CommentList'

import { axAuth } from '@/apis/api/axiosInstance'


import { NavTop, ChannelTalk, ModalForm, buttons } from '@/components'

interface DetailProps {
  subject: string
  contents: string
  nickName: string
  pictureURL: string
}


function ReviewDetailPage() {

  ChannelTalk.hideChannelButton()
  const location = useLocation()
  const review = location.state.review
  // console.log(review)
  const boardNo = useParams().boardNo
  const navigate = useNavigate()

  const writerEmail = review.email.email
  const userData = sessionStorage.getItem('userData')
  const userEmail = userData != null ? JSON.parse(userData).email : undefined

  const NotShowIcon = userEmail === writerEmail ? false : true



  const [modal, setModal] = useState(false)

  const deleteReview = () => {
    axAuth({
      url: '/board',
      method: 'delete',
      params: {
        boardNo
      }
    }).then((res) => {
      navigate("/review")

    })
      .catch((err) => console.log(err))
  }


  return (
    <div>
      <div><NavTop.NavDetail IsHidden={NotShowIcon} onClick={() => { setModal(true) }} /></div>
      {/* 게시물 부분 */}
      <div className='mt-6 flex flex-col gap-2 mb-4'>
        <p className="text-title2-bold">{review.subject}</p>
        <p className='text-footnote'>작성자: {review.email.nickName}</p>
        <div>
          <img alt="" src={review.pictureURL} />
        </div>
        <div>
          {HTMLReactParser(review.contents)}
        </div>
      </div>

      {/* 댓글 부분 */}
      <hr />
      <CommentList />

      {
        modal && <ModalForm
          closeModal={() => setModal(!modal)}
          ModalContent={
            <div className="flex flex-col gap-4 p-3 ">
              <p className='text-title2-bold'>
                정말 삭제하시겠습니까?
              </p>
              <div className='flex justify-around px-6'>
                <buttons.BtnBsm BtnValue={"아니오"} onClick={() => { setModal(!modal) }} />
                <buttons.BtnSm BtnValue={"네"} onClick={deleteReview} />
              </div>
            </div>
          } />
      }
    </div>
  )
}

export default ReviewDetailPage