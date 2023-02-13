import PrivateRouter from './PrivateRouter';

import LoginPage from '@/pages/Login/LoginPage';

const Login = [
  {
    element: <PrivateRouter authentication={false} />,
    children: [
      {
        path: '/login',
        element: <LoginPage />
      }
    ]
  },
]

export default Login