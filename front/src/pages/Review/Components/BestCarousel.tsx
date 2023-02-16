import { useEffect, useState } from 'react'

import { Link } from 'react-router-dom'

import HTMLReactParser from 'html-react-parser'

import { axAuth, axBase } from '@/apis/api/axiosInstance'
import { footers } from '@/components'



function BestCarousel() {
  const [bestReviews, setBestReviews] = useState<Reivew[]>([])
  const accessToken = sessionStorage.getItem('access-token')
  const refreshToken = sessionStorage.getItem('refresh-token')
  const useData = sessionStorage.getItem('userData')
  const nickName = useData != null ? JSON.parse(useData).nickName : undefined

  useEffect(() => {
    if (accessToken) {
      axAuth({
        url: '/board/best/member',
        params: { accessToken, refreshToken }
      })
        .then((res) => {
          setBestReviews(res.data.data)
        })
        .catch((err) => console.log(err.response.data))
    } else {
      axBase({
        url: '/board/best/non',
      })
        .then((res) => {
          setBestReviews(res.data.data)
        })
        .catch((err) => console.log(err.response.data))
    }
  }, [])

  const likeUpdate = (boardNo: number) => setBestReviews(bestReviews.map((review: Reivew) => {
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

  const bestReviewList = bestReviews.map((review) => {

    const parsedHtml = HTMLReactParser(review.contents)

    const bestReview = (
      <div key={review.boardNo} className="min-w-[21.875rem] min-h-[10.1rem] rounded-lg bg-white pt-5 pb-4 px-5 mb-2 drop-shadow-lg flex flex-col gap-2 justify-between" >
        <Link state={{ review }} to={`/review/${review.boardNo}`}>
          <div className="mb-3 text-title2-bold">{review.subject}</div>
          <div className=' max-h-10 text-ellipsis overflow-hidden'>
            <div className="text-body overflow-hidden">{parsedHtml}</div>
          </div>
        </Link>
        <div>{<footers.FooterHeart
          HeartCount={review.likes}
          IsLiked={JSON.parse(review.isLikes as string)}
          Username={review.email.nickName}
          onClick={() => likeBoard(review.boardNo, nickName)}
        />}
        </div>

      </div>
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