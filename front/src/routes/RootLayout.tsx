import { Outlet } from 'react-router-dom';
import { Detail, NavBottom } from '@/components';
import  ChannelService  from '@/components/ChannelTalk/ChannelService';

export default function RootLayout() {
  // 상황에 따른 NavTop 다르게 보여주기
  ChannelService.boot({
    "pluginKey": "bd89c55b-7bd5-44ac-b566-06372fef4c55", // fill your plugin key
    "mobileMessengerMode": "newTab",
    "language":"ko",
  });
  return (
    <>
        <Detail className="pt-12 pb-14 px-5 text-body flex flex-col items-center">
          <Outlet />
        </Detail>
        <NavBottom />
    </>
  );
}
