import React, { useState, useEffect } from 'react'

import { AiFillEye } from 'react-icons/ai'

import { Link } from 'react-router-dom'

import HTMLReactParser from 'html-react-parser'

import { axBase, axAuth } from '@/apis/api/axiosInstance'
import { cards, footers } from '@/components'


function ReviewLst() {

  const accessToken = sessionStorage.getItem('access-token')
  const refreshToken = sessionStorage.getItem('refresh-token')
  const useData = sessionStorage.getItem('userData')
  const nickName = useData != null ? JSON.parse(useData).nickName : undefined
  const [reviews, setReviews] = useState<Reivew[]>([])

  useEffect(() => {
    if (accessToken) {
      axAuth({
        url: '/board/list/like/member',
        params: { accessToken, refreshToken }
      })
        .then((res) => {
          setReviews(res.data.data)
        })
        .catch((err) => console.log(err.response.data))
    } else {
      axBase({
        url: '/board/list/like/non',
      })
        .then((res) => {
          setReviews(res.data.data)
        })
        .catch((err) => console.log(err.response.data))
    }
  }, [])



  const likeUpdate = (boardNo: number) => setReviews(reviews.map((review: Reivew) => {
    if (review.boardNo === boardNo) {
      if (review.isLikes == true || review.isLikes == 'true') {
        review.isLikes = false;
        review.likes--;
      } else {
        review.isLikes = true;
        review.likes++;
      }
    }

    return review
  }))

  const likeBoard = (boardNo: number, nickName: string) => {
    if (nickName) {
      axAuth({
        method: 'put',
        url: `/board/like/${boardNo}/${nickName}`
      })
      likeUpdate(boardNo)
    }
  }

  const reviewLst = reviews.map((review) => {

    const parsedHtml = HTMLReactParser(review.contents)

    return (
      <div key={`board-${review.boardNo}`} className='mb-4 flex flex-col items-center'>
        <cards.CardXL
          CardContents={<Link state={{ review }} to={`/review/${review.boardNo}`}>{parsedHtml}</Link>}
          CardImg={{ src: review.pictureURL, alt: '리뷰사진' }}
          CardTitle={review.subject}
          CardFooter={<footers.FooterHeart
            HeartCount={review.likes}
            IsLiked={JSON.parse(review.isLikes as string)}
            Username={review.email.nickName}
            onClick={() => likeBoard(review.boardNo, nickName)}
          />}
        />
      </div>
    )
  })


  return (
    <>
      {reviewLst}
    </>
  )
}

export default ReviewLst