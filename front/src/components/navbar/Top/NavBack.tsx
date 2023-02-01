import NavStyle from './NavTop.module.css';
import { FiChevronLeft } from 'react-icons/fi';

interface NavBack {
  NavContent: string;
}

function NavBack({ NavContent }: NavBack) {
  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left}>
        <FiChevronLeft color="white" />
        <div> 이전 </div>
      </div>
      <div className={NavStyle.Center}>{NavContent}</div>

      <div className={NavStyle.Right}></div>
    </div>
  );
}

export default NavBack;
