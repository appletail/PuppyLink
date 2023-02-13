import { FiChevronLeft } from 'react-icons/fi';

import NavStyle from './NavTop.module.css';

function NavCreate() {
  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left}>
        <FiChevronLeft color="white" />
        <div> 이전 </div>
      </div>
      <div className={NavStyle.Center}></div>

      <div className={NavStyle.Right}>작성</div>
    </div>
  );
}

export default NavCreate;
