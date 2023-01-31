import { useNavigate } from 'react-router-dom'

import { NavBottom } from '@/components'
import BtnLg from '@/components/Buttons/BtnLg/BtnLg'
import NotFoundStyle from '@/styles/pages/NotFoundPage.module.css'


function NotFoundPage() {
  const navigate = useNavigate()
  const goToBack = () => navigate(-1)

  return (
    <div className='px-5'>
      <div className='flex flex-col justify-between'>
        <div>
          <div className={NotFoundStyle.Header}>
            <p>페이지를 찾을 수 없습니다.</p>
          </div>
          <div className={NotFoundStyle.Content}>
            <p>
              유효하지 않은 주소를 입력하였습니다. <br />
              주소를 다시 확인해 주세요.
            </p>
          </div>
        </div>
        <div className='flex justify-center'>
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