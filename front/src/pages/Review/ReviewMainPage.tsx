import React from 'react'
import { AiFillPlusCircle } from 'react-icons/ai'
import { useNavigate } from 'react-router-dom'

import BestCarousel from './Components/BestCarousel'
import ReviewLst from './Components/ReviewLst'

import { NavTop, ChannelTalk } from '@/components'


function ReviewMainPage() {

  const navigate = useNavigate()
  ChannelTalk.hideChannelButton();

  return (
    <div className='w-[21.875rem]'>
      <NavTop.NavLogo />
      <div className='mt-6 mb-9 min-h-[100px]'>
        <p className='text-title2-bold mb-4'>이번 주의 BEST 후기</p>

        {/* BEST Carousel */}

        <BestCarousel />


      </div>

      <div>
        <div >
          <p className='text-title2-bold mb-4'>이동 봉사자들의 생생한 후기</p>
          {/* Review List */}
          <div>
            <ReviewLst />
          </div>

          {/* Review Create Button */}
          <div className='flex justify-end sticky bottom-20' onClick={() => navigate('/review/create')}>
            <AiFillPlusCircle className='fill-main-100' size={'2.5rem'} />
          </div>

        </div>
      </div>


    </div >
  )
}

export default ReviewMainPage