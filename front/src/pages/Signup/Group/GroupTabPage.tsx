import React from 'react'
import { useNavigate } from 'react-router-dom';

import { ReactComponent as Email } from '@/assets/SocialLogos/Signup/Email.svg'
import { NavTop, ChannelTalk } from '@/components';

function GroupTabPage() {
  ChannelTalk.hideChannelButton();
  const navigate = useNavigate();
  return (
    <div >
      <NavTop.NavBack NavContent='회원가입' />
      <div className="mt-14 mb-11 flex justify-center ">
        <div className="w-1/2 h-10 text-center border-b-[1px] border-main-50">
          <div className="text-title3" onClick={() => navigate('/signup/usertab')}>개인회원</div>
        </div>
        <div className="w-1/2 h-10 text-center border-b-2 border-main-100">
          <div className="text-title3-bold" >단체회원</div>
        </div>
      </div>

      <div className="mb-20">
        <p className="text-title1 text-center">단체회원 가입</p>
      </div>

      <div className="flex flex-col gap-4">
        <div className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center px-16 gap-10"
          onClick={() => navigate('/signup/group')}>
          <Email />
          이메일로 계속하기
        </div>
      </div>
    </div>
  )
}

export default GroupTabPage