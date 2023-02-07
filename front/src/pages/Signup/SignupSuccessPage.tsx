import { ReactComponent as Logo } from '@/assets/logo.svg';
import { useLocation, useNavigate } from 'react-router-dom';
import { buttons } from '@/components';

function SignupSuccessPage() {
  const location = useLocation();
  const state = location.state;
  let navigate = useNavigate()
  const goToLogin = () => {
    navigate('/login')
  }
  return (
    <div>
      <div className="mt-24">
        {/* 로고 */}
        <Logo />
        <div className="text-title1-bold mb-8 text-center">
          <p>{state.nickName}님</p>
          <p>회원가입을 축하드립니다.</p>
        </div>
        <div className='flex justify-center'>
          <buttons.BtnMd BtnValue="로그인하러 가기" onClick={goToLogin } />
        </div>
      </div>
    </div>
  );
}

export default SignupSuccessPage;
