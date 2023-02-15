import { FiChevronLeft } from 'react-icons/fi';
import { useNavigate } from 'react-router-dom'

import NavStyle from './NavTop.module.css';

interface Review {
  contents: string
  subject: string
  pictureURL: string
}

interface ReviewProps {
  onClick: React.MouseEventHandler
}


function NavCreate({ onClick }: ReviewProps): JSX.Element {
  const navigate = useNavigate()
  const goToBack = () => navigate(-1)
  return (
    <div className={NavStyle.NavBg}>
      <div className={NavStyle.Left} onClick={goToBack}>
        <FiChevronLeft color="white" />
        <div> 이전 </div>
      </div>
      <div className={NavStyle.Center}></div>

      <div className={NavStyle.Right} onClick={onClick} >
        작성
      </div>
    </div>
  );
}

export default NavCreate;
