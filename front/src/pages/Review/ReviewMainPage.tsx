import React from 'react'

import ReviewLst from './Components/ReviewLst'


function ReviewMainPage() {
  return (
    <div className='w-[21.875rem]'>
      <div className='mt-6 mb-9'>
        <p className='text-title2-bold mb-4'>이번 주의 BEST 후기</p>
        <div>
          BestCarousel
        </div>
      </div>
      <div>
        <p className='text-title2-bold mb-4'>이동 봉사자들의 생생한 후기</p>
        <div>
          <ReviewLst />
        </div>
      </div>
    </div >
  )
}

export default ReviewMainPage