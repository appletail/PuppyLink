import PrivateRouter from './PrivateRouter';

import ProtectRoute from './ProtectRoute';

import FoundationInfoChangePage from '@/pages/Mypage/Fountadation/Components/FoundationInfoChangePage';
import FoundationMyPage from '@/pages/Mypage/Fountadation/FoundationMyPage';

import MyReviewPage from '@/pages/Mypage/MyReviewPage';
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
            path: '/mypage/manager/introduce',
            element: <FoundationInfoChangePage />
          },
          {
            path: '/mypage/myreview',
            element: <MyReviewPage />
          },
          {
            path: '/mypage/changepassword',
            element: <UserChangePassword />
          },
        ]
      },
    ]
  },
]

export default Mypage