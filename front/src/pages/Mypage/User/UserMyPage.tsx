import React from 'react'

import { Link } from 'react-router-dom'

import { buttons } from '@/components'

function UserMyPage() {
  return (
    <div>
      <div className='px-5 mt-12'>
        <p className='text-largetitle-bold'>
          {/* 봉사자 이름이나 닉네임 넣을 것 */}
          김퍼피
        </p>
        <div className='flex justify-between mb-8'>
          <p>
            {/* 봉사자 이메일 넣을 것*/}
            kimpuppy@email.com
          </p>
          <buttons.BtnSm BtnValue='비밀번호변경' />
        </div>
      </div>
      <hr className='w-screen bg-grey border-none h-[0.5px] mb-5' />
      <div className='px-5 mb-8'>
        <p className='text-title2-bold mb-8'>나의 봉사 현황</p>
        <div className='flex flex-col gap-2'>
          <Link to='/mypage/vollist'><p className='h-10 flex items-center'>봉사 신청 내역</p></Link>
          <p className='h-10 flex items-center'>내 후기</p>
        </div>
      </div>
      <hr className='w-screen bg-grey border-none h-[0.5px] mb-5' />
      <div className='px-5 mb-8'>
        <p className='text-title2-bold mb-8'>기타</p>
        <div className='flex flex-col gap-2'>
          <p className='h-10 flex items-center'>공지사항</p>
          <p className='h-10 flex items-center text-red'>탈퇴하기</p>
        </div>
      </div>
      <hr className='w-screen bg-grey border-none h-[0.5px] mb-5' />
    </div>
  )
}

export default UserMyPage