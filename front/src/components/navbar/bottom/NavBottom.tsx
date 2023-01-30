import { AiFillHome, AiOutlineHome, } from "react-icons/ai";
import { FaGrinWink, FaRegGrin } from "react-icons/fa";
import { IoNewspaperSharp, IoNewspaperOutline, IoPerson, IoPersonOutline } from "react-icons/io5";
import { RiHandHeartFill, RiHandHeartLine } from "react-icons/ri";

import { NavLink } from "react-router-dom";

import NavStyle from './NavBottom.module.css'

function NavBottom(): JSX.Element {

  const icons = [
    { id: 'Home', link: '/', fill: AiFillHome, empty: AiOutlineHome },
    { id: 'Volunteer', link: '/', fill: RiHandHeartFill, empty: RiHandHeartLine },
    { id: 'Review', link: '/', fill: IoNewspaperSharp, empty: IoNewspaperOutline },
    { id: 'MyPage', link: '/', fill: IoPerson, empty: IoPersonOutline },
    { id: 'ComponentCollect', link: '/components', fill: FaGrinWink, empty: FaRegGrin },
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