import { Outlet } from 'react-router-dom';

import { Detail, NavBottom } from '@/components';

export default function RootLayout() {
  return (
    <>
      <Detail className='pt-12 pb-14 px-5'>
        <Outlet />
      </Detail>
      <NavBottom />
    </>
  );
}