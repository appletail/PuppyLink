import NavStyle from './NavTop.module.css';

import LogoWhite from '@/assets/logo-white.svg';
import { useRecoilValue } from 'recoil';
import { LoginState } from '@/states/LoginState';
import { useNavigate } from 'react-router-dom';


function NavTop(): JSX.Element {
  // 로그인 되어있는지 확인
  const auth = useRecoilValue(LoginState);

  const navigate = useNavigate();
  const goLogin = () => {
     navigate('/login')
  }


  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left}>
        <div className={NavStyle.Logo}>
          <img src={LogoWhite} className={NavStyle.Logo} />
        </div>
      </div>
      <div className={NavStyle.Right}>
        {auth
          ? //   로그인한 사람은 => 로그아웃이 보인다
            '로그아웃'
          : //   로그아웃 된 사람에게는 로그인이 보인다
          <div onClick={goLogin}>로그인</div>}
      </div>
    </div>
  );
}

export default NavTop;
