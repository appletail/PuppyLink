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
]

export default Mypage