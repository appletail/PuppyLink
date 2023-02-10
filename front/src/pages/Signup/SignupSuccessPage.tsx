import { useLocation, useNavigate } from 'react-router-dom';

import { ReactComponent as Logo } from '@/assets/logo.svg';
import { buttons, NavTop } from '@/components';

function SignupSuccessPage() {
  const location = useLocation();
  const state = location.state;
  const navigate = useNavigate()
  const goToLogin = () => {
    navigate('/login')
  }
  return (
    <div>
      <NavTop.NavBack NavContent='회원가입' />
      <div className="mt-24">
        {/* 로고 */}
        <Logo />
        <div className="text-title1-bold mb-8 text-center">
          <p>{state.nickName ? state.nickName : state.businessName}님</p>
          <p>회원가입을 축하드립니다.</p>
        </div>
        <div className='flex justify-center'>
          <buttons.BtnMd BtnValue="로그인하러 가기" onClick={goToLogin} />
        </div>
      </div>
    </div>
  );
}

export default SignupSuccessPage;
