import PrivateRouter from './PrivateRouter';

import ProtectRoute from './ProtectRoute';

import FoundationInfoPage from '@/pages/Mypage/Fountadation/Components/FoundationInfoPage';
import FoundationMyPage from '@/pages/Mypage/Fountadation/FoundationMyPage';

import UserFileDocs from '@/pages/Mypage/User/UserFileDocsPage';
import UserMyPage from '@/pages/Mypage/User/UserMyPage';
import UserVolLst from '@/pages/Mypage/User/UserVolLstPage';
import UserChangePassword from '@/pages/Mypage/UserChangePassword';


const Mypage = [
  {
    element: <PrivateRouter authentication={true} />,
    children: [
      {
        element: <ProtectRoute />,
        children: [
          {
            path: '/mypage/user',
            element: <UserMyPage />
          },
          {
            path: '/mypage/manager',
            element: <FoundationMyPage />
          },
          {
            path: '/mypage/user/vollist',
            element: <UserVolLst />,
          },
          {
            path: '/mypage/user/userfiledocs',
            element: <UserFileDocs />,
          },
          {
            path: '/mypage/changepassword',
            element: <UserChangePassword />
          },
          {
            path: '/mypage/manager/introduce',
            element: <FoundationInfoPage />
          },

        ]
      },
    ]
  },
]

export default Mypage