import { useState, useEffect } from 'react'

import { useLocation, useParams } from 'react-router-dom'

import HTMLReactParser from 'html-react-parser'

import CommentList from './Components/CommentList'

import { axBase } from '@/apis/api/axiosInstance'

import { NavTop } from '@/components'

interface DetailProps {
  subject: string
  contents: string
  nickName: string
  pictureURL: string
}


function ReviewDetailPage() {


  const location = useLocation()
  const review = location.state.review


  return (
    <div>
      <div><NavTop.NavDetail /></div>
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
    </div>
  )
}

export default ReviewDetailPage