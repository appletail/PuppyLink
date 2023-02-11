import PrivateRouter from './PrivateRouter';

import ProtectRoute from './ProtectRoute';

import VolAdminPage from '@/pages/Volunteer/Foundation/VolAdminPage';
import VolUserResiPage from '@/pages/Volunteer/User/VolUserResiPage';

const Volunteer = [
  {
    element: <PrivateRouter authentication={true} />,
    // path: '/volunteer',
    children: [
      {
        element: <ProtectRoute />,
        children: [
          {
            path: '/volunteer/user',
            element: <VolUserResiPage />,
          },
          {
            path: '/volunteer/manager',
            element: <VolAdminPage />,
          }
        ]
      },
    ]
  }
]

export default Volunteer