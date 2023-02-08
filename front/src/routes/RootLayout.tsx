import { Outlet } from 'react-router-dom';
import { Detail, NavBottom, NavTop } from '@/components';

import { useRecoilState } from 'recoil';
import { LoginState } from '@/states/LoginState';

import navbarController from '@/components/navbar/navbarController';

export default function RootLayout() {
  const [isLoggedIn, setisLoggedIn] = useRecoilState(LoginState);
  const auth = localStorage.getItem('access-token');
  if (auth) {
    setisLoggedIn(true);
  }

  // 상황에 따른 NavTop 다르게 보여주기

  return (
    <>
      {/* <NavTop.NavLogo /> */}
      <navbarController />
      <Detail className="pt-12 pb-14 px-5 text-body flex flex-col items-center">
        <Outlet />
      </Detail>
      <NavBottom />
    </>
  );
}
