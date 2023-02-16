import { useEffect, useState } from 'react'

import { Link } from 'react-router-dom'

import HTMLReactParser from 'html-react-parser'

import { axBase } from '@/apis/api/axiosInstance'
import { footers } from '@/components'



function BestCarousel() {
  const [bestReviews, setBestReviews] = useState<any[]>([])
  useEffect(() => {
    axBase({
      url: '/board/best'
    })
      .then((res) => {
        setBestReviews(res.data.data)

      })
      .catch((err) => console.log(err))
  }, [])

  const bestReviewList = bestReviews.map((review) => {

    const parsedHtml = HTMLReactParser(review.contents)

    const bestReview = (
      <Link key={review.boardNo} className="min-w-[21.875rem]  rounded-lg bg-white pt-5 pb-4 px-5 mb-2 drop-shadow-lg flex flex-col gap-2 justify-between" state={{ review }} to={`/review/${review.boardNo}`}>
        <div>
          <div className="mb-3 text-title2-bold">{review.subject}</div>
          <div className=' max-h-10 text-ellipsis overflow-hidden'>
            <div className="text-body overflow-hidden">{parsedHtml}</div>
          </div>
        </div>
        <div>{<footers.FooterHeart HeartCount={123} IsLiked={false} Username={review.email.nickName} />}</div>

      </Link>
    )

    return bestReview



  })

  return (
    <div className='flex gap-4 h-[10.1rem]  overflow-y-clip overflow-x-scroll'>
      {bestReviewList}
    </div>
  )
}

export default BestCarousel