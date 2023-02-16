import { FiChevronLeft, FiMoreVertical } from 'react-icons/fi';
import { useNavigate } from 'react-router-dom';

import NavStyle from './NavTop.module.css';

function NavDetail() {
  const navigate = useNavigate()


  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left}>
        <FiChevronLeft color="white" />
        <div aria-hidden onClick={() => navigate(-1)}> 이전 </div>
      </div>
      <div className={NavStyle.Center}></div>

      <div className={NavStyle.Right}>
        <FiMoreVertical size={25} />
      </div>
    </div>
  );
}

export default NavDetail;
