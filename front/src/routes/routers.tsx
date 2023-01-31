import { createBrowserRouter } from 'react-router-dom';

import RootLayout from './RootLayout';

import Components from '@/pages/ComponentCollects';

const routers = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      {
        path: '/components',
        element: <Components />,
      }
    ]
  },
]);

export default routers;
