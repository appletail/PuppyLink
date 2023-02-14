import { useNavigate } from 'react-router-dom'

import { NavBottom, NavTop } from '@/components'
import BtnLg from '@/components/Buttons/BtnLg/BtnLg'
import NotFoundStyle from '@/styles/pages/NotFoundPage.module.css'


function NotFoundPage() {
  const navigate = useNavigate()
  const goToBack = () => navigate(-1)

  return (
    <div>
      <NavTop.NavLogo />
      <div className={NotFoundStyle.Wrapper}>
        <div>
          <div className={NotFoundStyle.Header}>
            <p>서비스에 접속할 수 없습니다.</p>
          </div>
          <div className={NotFoundStyle.Content}>
            <p>
              죄송합니다. 기술적인 문제로 인해 <br />
              일시적으로 서비스에 접속할 수 없습니다. <br />
              이용에 불편을 드려 죄송합니다.
            </p>
          </div>
        </div>
        <div className={NotFoundStyle.NavBottom}>
          <BtnLg
            BtnValue='이전 페이지로 돌아가기'
            onClick={goToBack}
          />
        </div>
      </div>
      <NavBottom />
    </div>
  )
}

export default NotFoundPage