import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { useRecoilValue, useRecoilState } from 'recoil';

import NavStyle from './NavTop.module.css';

import LogoWhite from '@/assets/logo-white.svg';
import { LoginState } from '@/states/LoginState';


function NavTop(): JSX.Element {
  // 로그인 되어있는지 확인
  const auth = useRecoilValue(LoginState);

  const [isLoggedIn, setisLoggedIn] = useRecoilState(LoginState);
  const hasToken = localStorage.getItem('access-token');
  useEffect(() => {
    if (hasToken) {
      setisLoggedIn(true);
    }
  }, [])

  const navigate = useNavigate();
  const goLogin = () => {
    navigate('/login')
  }

  const goLogout = () => {

  }


  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left}>
        <div className={NavStyle.Logo}>
          <img className={NavStyle.Logo} src={LogoWhite} />
        </div>
      </div>
      <div className={NavStyle.Right}>
        {auth
          ? //   로그인한 사람은 => 로그아웃이 보인다
          <div onClick={goLogout}>로그아웃</div>
          : //   로그아웃 된 사람에게는 로그인이 보인다
          <div onClick={goLogin}>로그인</div>}
      </div>
    </div>
  );
}

export default NavTop;
