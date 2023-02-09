import { Outlet } from 'react-router-dom';

import { Detail, NavBottom, NavTop } from '@/components';

export default function RootLayout() {

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
