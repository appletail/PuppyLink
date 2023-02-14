import { useState } from 'react'

import { useRecoilValue } from 'recoil'

import NewVolCarousel from './Components/NewVolCarousel'
import SortedVol from './Components/SortedVol'

import { axBase } from '@/apis/api/axiosInstance'
import { NavTop } from '@/components'
import { LoginState } from '@/states/LoginState'
import style from '@/styles/pages/Volunteer/VolAdminPage.module.css'

function VolAdminPage() {

  const [status, setStatus] = useState<status>("regist")
  const [businessNo, setBusinessNo] = useState<string>("")

  let nickName = ""

  // recoil에서 로그인 여부를 판단한다.
  const isLoggedIn = useRecoilValue(LoginState)
  if (isLoggedIn) {
    // 로그인 되어있다면 userData를 가져온다
    const userData = sessionStorage.getItem("userData") || ""
    const parsedUserData = JSON.parse(userData)
    nickName = parsedUserData.nickName
  }

  axBase({ url: '/foundation/list' })
    .then((res) => {
      const datas = res.data.data
      datas.forEach((cur: foundation) => {
        if (cur.email.nickName === nickName) {
          setBusinessNo(cur.businessNo)
        }
      })
    })

  return (
    <div>
      <NavTop.NavLogo />
      <div className={style.Carousel}>
        <p className={style.NewTitle}>새로 요청이 들어온 봉사자</p>
        {/* 새로운 요청이 들어온 봉사자 */}
        <NewVolCarousel businessNo={businessNo} />
      </div>
      <div className={style.Scroll}>
        <p className={style.SortTitle}>봉사자 관리하기</p>
        <div className={style.Selector}>
          <p>진행 상태</p>
          <select className="text-caption1" onChange={(e) => { setStatus(e.target.value as status) }}>
            <option value="regist">접수 완료</option>
            <option value="docs">서류 승인 대기</option>
            <option value="lack">서류 미흡</option>
            <option value="confirm">봉사 진행 중</option>
            <option value="complete">봉사 완료</option>
          </select>
        </div>
        <SortedVol businessNo={businessNo} status={status} />
      </div>
    </div>
  )
}

export default VolAdminPage