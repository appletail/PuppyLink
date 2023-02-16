import React, { useEffect, useState } from 'react'

import { Link } from 'react-router-dom'

import HTMLReactParser from 'html-react-parser'

import { axAuth } from '@/apis/api/axiosInstance'
import { cards, footers, NavTop } from '@/components'

function MyReviewPage() {

  const useData = sessionStorage.getItem('userData')
  const nickName = useData != null ? JSON.parse(useData).nickName : undefined
  const [reviews, setReviews] = useState<Reivew[]>([])

  useEffect(() => {
    axAuth({
      url: `/board/history/${nickName}`
    })
      .then((res) => setReviews(res.data.data))
  }, [nickName])


  const reviewLst = reviews.map((review) => {

    const parsedHtml = HTMLReactParser(review.contents)

    return (
      <Link key={`board-${review.boardNo}`} className='mb-4 flex flex-col items-center' state={{ review }} to={`/review/${review.boardNo}`}>
        <cards.CardXL
          CardContents={parsedHtml}
          CardImg={{ src: review.pictureURL, alt: '리뷰사진' }}
          CardTitle={review.subject}
          CardFooter={<footers.FooterHeart
            HeartCount={review.likes}
            IsLiked={false}
            Username={review.email.nickName}
          />}
        />
      </Link>
    )
  })


  return (
    <div>
      <NavTop.NavBack NavContent='내 후기' />
      <p className='text-title1-bold mt-12 mb-5'>내 후기</p>
      {reviewLst}
    </div>
  )
}

export default MyReviewPage