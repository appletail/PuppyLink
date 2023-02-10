import { atom } from 'recoil';


export const UserState = atom<Member>({
  key: 'UserState',
  default: {
    email: "",
    name: "",
    phone: 0,
    nickName: "",
    activated: false,
    authorities: [{ "authorityName": "" }],
    joinDate: new Date(),
  }

});