import VolAdminPage from '@/pages/Volunteer/Foundation/VolAdminPage';
import VolUserResiPage from '@/pages/Volunteer/User/VolUserResiPage';

const Volunteer = [
  {
    path: '/volunteer',
    element: <VolUserResiPage />,
  },
  {
    path: '/volunteer/admin',
    element: <VolAdminPage />,
  }
]

export default Volunteer