import { Outlet } from 'react-router-dom';

import { NavBottom } from '@/components';

export default function RootLayout() {
  return (
    <>
      <Outlet />
      <NavBottom />
    </>
  );
}