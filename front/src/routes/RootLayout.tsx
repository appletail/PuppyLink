import { useState, useEffect } from 'react'
import { Outlet } from 'react-router-dom';

import { axAuth, axBase } from '@/apis/api/axiosInstance';

import { Detail, NavBottom, LoadingBase } from '@/components';
import ChannelService from '@/components/ChannelTalk/ChannelService';

export default function RootLayout() {
  // 상황에 따른 NavTop 다르게 보여주기
  ChannelService.boot({
    "pluginKey": "bd89c55b-7bd5-44ac-b566-06372fef4c55", // fill your plugin key
    "hideChannelButtonOnBoot": true,
    "language": "ko",
  });

  const [loading, setLoading] = useState(false)

  useEffect(() => {
    //axios 호출시 인터셉트
    axBase.interceptors.request.use((config) => {
      if (!config.url?.includes('news') || !config.url.includes('gps')) {
        setLoading(true)
      }
      return config
    },
      (error) => {
        setLoading(false)
        return Promise.reject(error);
      });
    axAuth.interceptors.request.use((config) => {
      setLoading(true)
      return config
    },
      (error) => {
        setLoading(false)
        return Promise.reject(error);
      });


    //axios 호출 종료시 인터셉트
    axBase.interceptors.response.use((response) => {
      setLoading(false)
      return response;
    },
      (error) => {
        setLoading(false)
        return Promise.reject(error);
      });
    axAuth.interceptors.response.use((response) => {
      setLoading(false)
      return response;
    },
      (error) => {
        setLoading(false)
        return Promise.reject(error);
      });
  }, []);

  return (
    <>
      <Detail className="pt-12 pb-14 px-5 text-body flex flex-col items-center">
        <Outlet />
      </Detail>
      <NavBottom />
      {loading && <LoadingBase />}
    </>
  );
}
