import { atom, useRecoilState } from 'recoil';
import { json } from 'stream/consumers';


export const LoginState = atom<boolean>({
  key: 'LoginState',
  default: false,
});


// const [isLoggedIn, setisLoggedIn] = useRecoilState(LoginState);
// const auth = localStorage.getItem('access-token');
// if (auth) {
//   setisLoggedIn(true);
// }