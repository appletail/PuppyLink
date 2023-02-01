import { atom, useRecoilState } from 'recoil';
import { json } from 'stream/consumers';
// import { recoilPersist } from 'recoil-persist';

// const { persistAtom } = recoilPersist();

export const LoginState = atom<boolean>({
  key: 'LoginState',
  default: false,
});

const IsLoggedIn = function () {
  const accessToken = JSON.parse('accessToken');
  const refreshToken = JSON.parse('refreshToken');
};
