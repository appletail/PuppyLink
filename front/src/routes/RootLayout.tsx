import { Outlet } from 'react-router-dom';

<<<<<<< HEAD
import { Detail, NavBottom } from '@/components';
  // 상황에 따른 NavTop 다르게 보여주기
export default function RootLayout() {
=======

import { Detail, NavBottom, NavTop } from '@/components';


export default function RootLayout() {

>>>>>>> ff2144b5eb3d98ba365d55d7608dc0190c34fc46
  return (
    <>

      <Detail className="pt-12 pb-14 px-5 text-body flex flex-col items-center">
        <Outlet />
      </Detail>

      <NavBottom />
    </>
  );
}
