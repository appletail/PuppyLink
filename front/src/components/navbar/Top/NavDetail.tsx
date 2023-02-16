import { FiChevronLeft } from 'react-icons/fi';
import { TiDelete } from 'react-icons/ti'
import { useNavigate } from 'react-router-dom';

import NavStyle from './NavTop.module.css';

interface DetailProps {
  onClick: React.MouseEventHandler
  IsHidden: boolean
}

function NavDetail({ onClick, IsHidden }: DetailProps) {
  const navigate = useNavigate()


  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left}>
        <FiChevronLeft color="white" />
        <div aria-hidden onClick={() => navigate(-1)}> 이전 </div>
      </div>
      <div className={NavStyle.Center}></div>

      <div aria-hidden className={NavStyle.Right} hidden={IsHidden} onClick={onClick}>
        <TiDelete size={25} />
      </div>
    </div>
  );
}

export default NavDetail;
