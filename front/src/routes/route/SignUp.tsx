import GroupSignupPage from '@/pages/Signup/Group/GroupSignupPage';
import GroupTabPage from '@/pages/Signup/Group/GroupTabPage';
import SignupConfirmPage from '@/pages/Signup/SignupConfirmPage';
import SignupSuccessPage from '@/pages/Signup/SignupSuccessPage';
import UserSignupPage from '@/pages/Signup/User/UserSignupPage';
import UserTabPage from '@/pages/Signup/User/UserTabPage';


const SignUp = [
  {
    path: '/signup/usertab',
    element: <UserTabPage />,
  },
  {
    path: '/signup/grouptab',
    element: <GroupTabPage />,
  },
  {
    path: '/signup/user',
    element: <UserSignupPage />,
  },
  {
    path: '/signup/group',
    element: <GroupSignupPage />,
  },
  {
    path: '/signup/confirm',
    element: <SignupConfirmPage />,
  },
  {
    path: '/signup/success',
    element: <SignupSuccessPage />,
  },
]

export default SignUp