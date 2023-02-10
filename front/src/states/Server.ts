import { atom, useRecoilState } from 'recoil';

export const URL = atom({
  key: 'URL',
  default: 'http://localhost:8085/puppy',
  // default: 'http://i8c107.p.ssafy.io:8085/puppy', // ec2에 올릴 경우 체인지
});
