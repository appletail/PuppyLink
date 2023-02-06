import React from 'react'
import { ReactComponent as Logo } from '@/assets/logo.svg';

function SignupSuccessPage() {
  return (
    <div>
      <div className="mt-24">
        {/* 로고 */}
        <Logo />
        <div className="text-title1-bold">
            <p>ㅇㅇㅇ님</p>
            <p>회원가입을 축하드립니다.</p>
        </div>
      </div>
    </div>
  )
}

export default SignupSuccessPage