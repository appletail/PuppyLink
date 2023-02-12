import React, { ReactElement } from 'react'
import { Navigate, Outlet, useLocation } from 'react-router-dom'

const role: Role = {
  USER: "ROLE_USER",
  MANAGER: "ROLE_MANAGER",
  ADMIN: "ROLE_ADMIN",
}

const category: Array<Category> = [
  {
    idx: 1
    , name: "봉사페이지"
    , path: "/volunteer"
    , role: [role.USER, role.MANAGER]
    , sub: [
      {
        idx: 2
        , name: "일반 회원 봉사페이지"
        , path: "/user"
        , role: [role.USER]
        , sub: []
      },
      {
        idx: 3
        , name: "단체 회원 봉사페이지"
        , path: "/manager"
        , role: [role.MANAGER]
        , sub: []
      },
    ],

  },
  {
    idx: 4
    , name: "마이페이지"
    , path: "/mypage"
    , role: [role.USER, role.MANAGER]
    , sub: [
      {
        idx: 5
        , name: "일반 회원 마이페이지"
        , path: "/user"
        , role: [role.USER]
        , sub: [
          {
            idx: 7
            , name: "일반 회원 봉사 관리페이지"
            , path: "/vollist"
            , role: [role.USER]
            , sub: []
          },
          {
            idx: 10
            , name: "일반 회원 봉사 서류 제출 페이지"
            , path: "/userfiledocs"
            , role: [role.USER]
            , sub: []
          },
        ]
      },
      {
        idx: 6
        , name: "단체 회원 단체페이지"
        , path: "/manager"
        , role: [role.MANAGER]
        , sub: []
      },
      {
        idx: 8
        , name: "일반 회원 파일 제출 페이지"
        , path: "/user/userfiledocs"
        , role: [role.USER]
        , sub: []
      },
      {
        idx: 9
        , name: "비밀번호 변경페이지"
        , path: "/changepassword"
        , role: [role.USER, role.MANAGER]
        , sub: []
      },
    ],

  }
]


function ProtectRoute(): ReactElement | null {

  // 로그인 되어있을 때에는 roles를 변경한다.
  const userData = localStorage.getItem("userData") || ""
  const loginMember: Member = JSON.parse(userData)
  // console.log(loginMember.role)

  const menu: Category = { ...category[0], sub: [...category] }
  // const mypage: Category = { ...category[1], sub: [...category] }


  // 현재 URL정보를 useLocation과 split을 이용해서 가져온다.
  const location = useLocation();
  const pathArr: Array<string> = location.pathname.substring(1).split("/");

  const roleCheck = (menu: Category, pathArr: Array<string>, index = 0): Array<Category> => {

    const result = menu.sub.filter(item => item.role.includes(loginMember.role) && item.path.includes(`/${pathArr[index]}`));

    /**
     * url길이를 비교해 한번더 호출될지를 결정한다.
     * url이 http://localhost:3000/admin일 경우는 pathArr의 length가 1이기 때문에 해당 if문은 false가 되고 roleCheck함수는 끝난다.
     * url이 http://localhost:3000/admin/member일 경우는 pathArr의 length가 2이기 때문에 해당 if문은 처음에 true가 되고 roleCheck함수를 한번더 호출한다.
     */
    if (++index < pathArr.length) {
      return roleCheck(result[0], pathArr, index);
    }
    return result;
  }
  // roleCheck함수에 최종 반환 되는건 접근가능한 menu 항목이다.
  const result: Array<Category> = roleCheck(menu, pathArr)
  // console.log(result)
  // const result_2: Array<Category> = roleCheck(mypage, pathArr)
  // console.log(result_2)

  /**
   * roleCheck에서 최종 반환 되는 항목은 접근가능한 menu항목이기 때문에,
   * result변수의 length가 0일경우는 접근간으한 메뉴가 아니라는 뜻으로 된다.
   */
  if (result.length === 0) {
    alert('해당 페이지 접근 권한이 없습니다! 홈 메인페이지로 이동합니다.');
    return <Navigate to="/" />;
  }

  // 접근 가능한 페이지일 경우 해당 페이지를 보여준다.
  return <Outlet />
}
export default ProtectRoute