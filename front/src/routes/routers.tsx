import { createBrowserRouter } from 'react-router-dom';

import RootLayout from './RootLayout';

import ComponentCollectsPage from '@/pages/ComponentCollectsPage';
import NotFoundPage from '@/pages/NotFoundPage';
import VolUserResiPage from '@/pages/Volunteer/VolUserResiPage';
import LoginPage from '@/pages/LoginPage';

const routers = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    errorElement: <NotFoundPage />,
    children: [
      {
        path: '/volunteer',
        element: <VolUserResiPage />,
      },
      {
        path: '/components',
        element: <ComponentCollectsPage />,
      },
      {
        path: '/login',
        element: <LoginPage />,
      },
    ],
  },
]);

export default routers;
