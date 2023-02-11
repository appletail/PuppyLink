
import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

import { useRecoilValue } from 'recoil'

import { axBase } from '@/apis/api/axiosInstance'
import { buttons, NavTop, ModalForm } from '@/components'
import { LoginState } from '@/states/LoginState'

function UserMyPage() {

  // user의 이메일과 닉네임 받아오는 부분
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

  // 탈퇴하기 모달띄우기 위해 필요한 부분
  const [withdraw, setWithdraw] = useState(false)
  // 탈퇴
  const userWithdraw = () => {
    const accessToken = localStorage.getItem('access-token')
    const refreshToken = localStorage.getItem('refresh-token')
    axBase({
      url: '/members',
      method: 'delete',
      data: { accessToken, refreshToken }
    }).then((res) => console.log(res))
      .catch((err) => console.log(err))

  }

  const navigate = useNavigate();

  return (
    <div>
      <NavTop.NavLogo />
      <div className='px-5 mt-12'>
        <p className='text-largetitle-bold'>
          {usernickName}
        </p>
        <div className='flex justify-between mb-8'>
          <p>
            {useremail}
          </p>
          <buttons.BtnSm BtnValue='비밀번호변경' onClick={() => navigate("/mypage/changepassword")} />
        </div>
      </div>
      <hr className='w-screen bg-grey border-none h-[0.5px] mb-5' />
      <div className='px-5 mb-8'>
        <p className='text-title2-bold mb-8'>나의 봉사 현황</p>
        <div className='flex flex-col gap-2'>
          <Link to='/mypage/user/vollist'><p className='h-10 flex items-center'>봉사 신청 내역</p></Link>
          <p className='h-10 flex items-center'>내 후기</p>
        </div>
      </div>
      <hr className='w-screen bg-grey border-none h-[0.5px] mb-5' />
      <div className='px-5 mb-8'>
        <p className='text-title2-bold mb-8'>기타</p>
        <div className='flex flex-col gap-2'>
          <p className='h-10 flex items-center'>공지사항</p>
          <div onClick={() => setWithdraw(!withdraw)}>
            <p className='h-10 flex items-center text-red'>탈퇴하기</p>
            {
              withdraw && <ModalForm
                closeModal={() => setWithdraw(!withdraw)}
                ModalContent={
                  <div className="flex flex-col gap-4 p-3 ">
                    <p className='text-title2-bold'>
                      정말 탈퇴하시겠습니까?
                    </p>
                    <div className='flex justify-around px-6'>
                      <buttons.BtnBsm BtnValue={"아니오"} onClick={() => { setWithdraw(!withdraw) }} />
                      <buttons.BtnSm BtnValue={"네"} onClick={userWithdraw} />
                    </div>
                  </div>
                } />
            }
          </div>
        </div>
      </div>
      <hr className='w-screen bg-grey border-none h-[0.5px] mb-5' />
    </div>
  )
}

export default UserMyPage