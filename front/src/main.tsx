import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';

import { RouterProvider } from 'react-router-dom';

import { RecoilRoot } from 'recoil';

import routers from './routes/routers';

import '@/styles/global.css';
import '@/index.css';

const container = document.getElementById('root') as HTMLElement;

createRoot(container).render(

  // <StrictMode>
  <RecoilRoot>
    <RouterProvider router={routers} />
  </RecoilRoot>
  // </StrictMode>

);
