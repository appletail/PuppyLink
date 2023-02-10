
import { Link, useNavigate } from 'react-router-dom'

import { useRecoilValue } from 'recoil'

import { buttons, NavTop } from '@/components'
import { LoginState } from '@/states/LoginState'


function UserMyPage() {
  let useremail = ""
  let usernickName = ""
  // recoil에서 로그인 여부를 판단한다.
  const isLoggedIn = useRecoilValue(LoginState)
  if (isLoggedIn) {
    // 로그인 되어있다면 userData를 가져온다
    const userData = localStorage.getItem("userData") || ""
    const { email, nickName } = JSON.parse(userData)
    useremail = email
    usernickName = nickName
  }
  // 로그인이 안되어있으면 로그인 페이지로 리다이렉트 한다.
  const navigate = useNavigate();

  return (
    <div>
      <NavTop.NavLogo />
      <div className='px-5 mt-12'>
        <p className='text-largetitle-bold'>
          {/* 봉사자 이름이나 닉네임 넣을 것 */}
          {usernickName}
        </p>
        <div className='flex justify-between mb-8'>
          <p>
            {/* 봉사자 이메일 넣을 것*/}

            {useremail}
          </p>
          <buttons.BtnSm BtnValue='비밀번호변경' onClick={() => navigate("/mypage/changepassword")} />
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