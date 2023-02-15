import { FiChevronLeft } from 'react-icons/fi';
import { useNavigate } from 'react-router-dom'

import NavStyle from './NavTop.module.css';

interface NavBack {
  NavContent: string;
}

function NavBack({ NavContent }: NavBack) {
  const navigate = useNavigate()
  const goToBack = () => {
    navigate(-1)
  }
  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left} onClick={goToBack}>
        <FiChevronLeft color="white" />
        <div> 이전 </div>
      </div>
      <div className={NavStyle.Center}>{NavContent}</div>

      <div className={NavStyle.Right}></div>
    </div>
  );
}

export default NavBack;
