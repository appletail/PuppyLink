import { useEffect, useState } from 'react'

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
      <div key={review.boardNo} className="min-w-[21.875rem] min-h-[12rem] rounded-lg bg-white pt-5 pb-3 px-5 drop-shadow-lg flex flex-col gap-2 justify-between">
        <div>
          <div className="mb-3 text-title2-bold">{review.subject}</div>
          <div className="text-body">{parsedHtml}</div>
        </div>
        <div>{<footers.FooterHeart HeartCount={123} IsLiked={false} Username="무웅" />}</div>

      </div>
    )

    return bestReview



  })

  return (
    <div className='flex gap-4 overflow-y-scroll h-48'>
      {bestReviewList}
    </div>
  )
}

export default BestCarousel