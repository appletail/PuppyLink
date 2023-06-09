import { createBrowserRouter } from 'react-router-dom';

import RootLayout from './RootLayout';
import { Login, Mypage, Others, Review, SignUp, Volunteer,Social } from './route';

import HomePage from '@/pages/Home/HomePage';
import NotFoundPage from '@/pages/NotFoundPage';
import VolGps from '@/pages/Volunteer/User/VolGps';

const routers = createBrowserRouter([
  {
    element: <RootLayout />,
    errorElement: <NotFoundPage />,
    children: [
      {
        path: '/',
        element: <HomePage />
      },
      ...Login,
      ...Mypage,
      ...Others,
      ...Review,
      ...SignUp,
      ...Volunteer,
      ...Social,
      {
        path: '/gps',
        element: <VolGps />,
      },
    ],
  },
]);

export default routers;
