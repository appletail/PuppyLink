import UserChangePassword from '@/pages/Mypage/User/UserChangePassword';
import UserFileDocs from '@/pages/Mypage/User/UserFileDocs';
import UserMyPage from '@/pages/Mypage/User/UserMyPage';
import UserVolLst from '@/pages/Mypage/User/UserVolLst';


const Mypage = [
  {
    path: '/mypage',
    element: <UserMyPage />,
  },
  {
    path: '/mypage/vollist',
    element: <UserVolLst />,
  },
  {
    path: '/mypage/userfiledocs',
    element: <UserFileDocs />,
  },
  {
    path: '/mypage/changepassword',
    element: <UserChangePassword />
  }
]

export default Mypage