import { useState } from 'react'

import NewVolCarousel from './Components/NewVolCarousel'
import SortedVol from './Components/SortedVol'

import { NavTop } from '@/components'
import style from '@/styles/pages/Volunteer/VolAdminPage.module.css'

function VolAdminPage() {

  const [status, setStatus] = useState<status>("submit")

  return (
    <div>
      <NavTop.NavLogo />
      <div className={style.Carousel}>
        <p className={style.NewTitle}>새로 요청이 들어온 봉사자</p>
        {/* 새로운 요청이 들어온 봉사자 */}
        <NewVolCarousel />
      </div>
      <div className={style.Scroll}>
        <p className={style.SortTitle}>봉사자 관리하기</p>
        <div className={style.Selector}>
          <p>진행 상태</p>
          <select className="text-caption1" onChange={(e) => { setStatus(e.target.value as status) }}>
            <option value="신청 완료">접수 대기</option>
            <option value="접수 완료">접수 완료</option>
            <option value="제출 완료">서류 승인 대기</option>
            <option value="서류 미흡">서류 미흡</option>
            <option value="승인 완료">봉사 진행 중</option>
            <option value="봉사 완료">봉사 완료</option>
          </select>
        </div>
        <SortedVol status={status} />
      </div>
    </div>
  )
}

export default VolAdminPage