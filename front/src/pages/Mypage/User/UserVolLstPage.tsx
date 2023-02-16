import { useState } from 'react'

import UserSortedVol from './Components/UserSortedVol'

import { NavTop, ChannelTalk } from '@/components'

function UserVolLst() {

  const [status, setStatus] = useState<status>("submit")
  const [count, setCount] = useState(0)
  ChannelTalk.hideChannelButton();

  return (
    <div>
      <NavTop.NavBack NavContent='봉사 정보' />
      <div>
        <p className='my-12 px-5 text-title1-bold'>봉사 신청 내역</p>
        <div className='w-screen'>
          <div className='pt-4 px-5 '>
            <p className='text-title3-bold mb-4'>총 {count}건</p>
            <div className='flex justify-between mb-7'>
              <p>진행 상태</p>
              <select className="text-caption1" onChange={(e) => { setStatus(e.target.value as status) }}>
                <option value="submit">접수 대기</option>
                <option value="regist">접수 완료</option>
                <option value="lack">서류 미흡</option>
                <option value="docs">서류 승인 대기</option>
                <option value="confirm">봉사 진행 중</option>
                <option value="complete">봉사 완료</option>
                <option value="refuse">봉사 취소</option>
              </select>
            </div>
            <hr className='bg-grey border-none h-[0.5px] mb-5' />
          </div>
          {/* 봉사 카드 */}
          <UserSortedVol status={status} volCount={(count: number) => setCount(count)} />
        </div>
      </div>
    </div>
  )
}

export default UserVolLst