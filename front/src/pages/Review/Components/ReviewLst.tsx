import React, { useState, useEffect } from 'react'

import { AiFillEye } from 'react-icons/ai'

import { Link } from 'react-router-dom'

import HTMLReactParser from 'html-react-parser'

import { axBase } from '@/apis/api/axiosInstance'
import { cards, ChannelTalk } from '@/components'


function ReviewLst() {

  const [reviews, setReviews] = useState<any[]>([])
  ChannelTalk.hideChannelButton()
  useEffect(() => {
    axBase({
      url: '/board/list'
    })
      .then((res) => {
        setReviews(res.data.data)
      })
      .catch((err) => console.log(err.response.data))
  }, [])


  const reviewLst = reviews.map((review) => {

    const reviewFooter = (
      <div>
        <hr className='bg-grey h-[1px] border-none mb-2' />
        <div className='flex justify-between'>
          <p className='text-caption1 text-grey'>@ {review.email.nickName}</p>
          <div className='flex gap-1'>
            <AiFillEye className='fill-grey' />
            <p className='text-caption2 text-grey leading-4'>{review.boardNo}</p>
          </div>
        </div>

      </div>
    )

    const parsedHtml = HTMLReactParser(review.contents)

    return (
      <Link key={review.boardNo} className='mb-4 flex flex-col items-center' state={{ review }} to={`/review/${review.boardNo}`}>
        <cards.CardXL
          CardContents={parsedHtml}
          CardFooter={reviewFooter}
          CardTitle={review.subject}
          CardImg={
            {
              src: review.pictureURL,
              alt: '리뷰사진'
            }}
        />
      </Link>
    )
  })


  return (
    <>
      {reviewLst}
    </>
  )
}

export default ReviewLst