import { atom, useRecoilState } from 'recoil';

export const URL = atom({
  key: 'URL',
  default: 'http://localhost:8085',
});
