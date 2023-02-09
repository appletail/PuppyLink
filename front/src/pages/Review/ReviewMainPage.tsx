import React from 'react'

import ReviewLst from './Components/ReviewLst'

import style from '@/styles/pages/Review/ReviewMainPage.module.css'

function ReviewMainPage() {
  return (
    <div className='w-[21.875rem]'>
      <div className='mt-6 mb-9'>
        <p className='text-title2-bold mb-4'>이번 주의 BEST 후기</p>
        <div className={style.BestCarousel}>
          BestCarousel
        </div>
      </div>
      <div className={style.ReviewLst}>
        <p className='text-title2-bold mb-4'>이동 봉사자들의 생생한 후기</p>
        <div className={style.ReviewScroll}>
          <ReviewLst />
        </div>
      </div>
    </div>
  )
}

export default ReviewMainPage