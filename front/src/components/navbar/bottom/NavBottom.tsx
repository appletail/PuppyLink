import { useEffect } from "react";
import { AiFillHome, AiOutlineHome, } from "react-icons/ai";
import { FaGrinWink, FaRegGrin } from "react-icons/fa";
import { IoNewspaperSharp, IoNewspaperOutline, IoPerson, IoPersonOutline } from "react-icons/io5";
import { RiHandHeartFill, RiHandHeartLine } from "react-icons/ri";

import { NavLink } from "react-router-dom";

import { useRecoilValue } from "recoil";

import NavStyle from './NavBottom.module.css'
import { LoginState } from "@/states/LoginState";

function NavBottom(): JSX.Element {

  const roleIdentify = { 'user': 'ROLE_USER', 'manager': 'ROLE_MANAGER', 'undefined': "" }
  let roles = ""

  const isAuthenticated: boolean = sessionStorage.getItem("access-token") ? true : false
  if (isAuthenticated) {
    const userData = sessionStorage.getItem("userData") || ""
    const { role } = JSON.parse(userData)
    roles = role
    // console.log(role)
  }

  const IsLoggedIn = useRecoilValue(LoginState)
  useEffect(() => {

    // console.log(roles)

  }, [IsLoggedIn])

  const icons = [
    { id: 'Home', link: '/', fill: AiFillHome, empty: AiOutlineHome },
    {
      id: 'Volunteer',
      link: roles == roleIdentify.manager ? '/volunteer/manager' : '/volunteer/user',
      fill: RiHandHeartFill, empty: RiHandHeartLine
    },
    { id: 'Review', link: '/review', fill: IoNewspaperSharp, empty: IoNewspaperOutline },
    { id: 'MyPage', link: roles == roleIdentify.manager ? '/mypage/manager' : '/mypage/user', fill: IoPerson, empty: IoPersonOutline },
    // { id: 'ComponentCollect', link: '/components', fill: FaGrinWink, empty: FaRegGrin },
  ]

  const NavBtns = icons.map((icon) => {
    return (
      <NavLink
        key={icon.id}
        className={NavStyle.NavBtn}
        to={icon.link}>
        {
          ({ isActive }) => (
            isActive ?
              <icon.fill className='fill-main-100' size={'2rem'} />
              :
              <icon.empty size={'2rem'} />
          )
        }
      </NavLink >
    )
  })

  return (
    <div className={NavStyle.Wrapper}>
      {NavBtns}
    </div>
  )
}

export default NavBottom