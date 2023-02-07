import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';

import routers from './routes/routers';
import '@/styles/global.css';
import '@/index.css';

import {
  RecoilRoot,
  atom,
  selector,
  useRecoilState,
  useRecoilValue,
} from 'recoil';

const container = document.getElementById('root') as HTMLElement;

// 로그인 여부를 알 수 있는 처리


createRoot(container).render(
  <StrictMode>
    <RecoilRoot>
      <RouterProvider router={routers} />
    </RecoilRoot>
  </StrictMode>
);
