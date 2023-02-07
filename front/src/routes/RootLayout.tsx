import { Outlet } from 'react-router-dom';
import { Detail, NavBottom, NavTop } from '@/components';

import { useRecoilState } from 'recoil';
import { LoginState } from '@/states/LoginState';

export default function RootLayout() {
  const [isLoggedIn, setisLoggedIn] = useRecoilState(LoginState);
  const auth = localStorage.getItem('access-token');
  if (auth) {
    setisLoggedIn(true);
  }
  return (
    <>
      <NavTop.NavLogo />
      <Detail className="pt-12 pb-14 px-5 text-body flex flex-col items-center">
        <Outlet />
      </Detail>
      <NavBottom />
    </>
  );
}
