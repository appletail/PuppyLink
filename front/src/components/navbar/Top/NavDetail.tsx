import NavStyle from './NavTop.module.css';
import { FiChevronLeft, FiMoreVertical } from 'react-icons/fi';

function NavDetail() {
  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left}>
        <FiChevronLeft color="white" />
        <div> 이전 </div>
      </div>
      <div className={NavStyle.Center}></div>

      <div className={NavStyle.Right}>
        <FiMoreVertical size={25} />
      </div>
    </div>
  );
}

export default NavDetail;
