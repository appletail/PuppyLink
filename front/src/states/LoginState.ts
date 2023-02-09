import { json } from 'stream/consumers';

import { atom, useRecoilState } from 'recoil';


export const LoginState = atom<boolean>({
  key: 'LoginState',
  default: false,
});


// const [isLoggedIn, setisLoggedIn] = useRecoilState(LoginState);
// const auth = localStorage.getItem('access-token');
// if (auth) {
//   setisLoggedIn(true);
// }