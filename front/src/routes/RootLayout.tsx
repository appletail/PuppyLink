import { Outlet } from 'react-router-dom';

<<<<<<< HEAD


import { Detail, NavBottom } from '@/components';


=======
import { useRecoilState } from 'recoil';

import { Detail, NavBottom, NavTop } from '@/components';

import { LoginState } from '@/states/LoginState';
>>>>>>> dev

export default function RootLayout() {


  // 상황에 따른 NavTop 다르게 보여주기

  return (
    <>

      <Detail className="pt-12 pb-14 px-5 text-body flex flex-col items-center">
        <Outlet />
      </Detail>

      <NavBottom />
    </>
  );
}
