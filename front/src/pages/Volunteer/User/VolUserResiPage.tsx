import { useState, useEffect } from 'react'
import { useForm } from 'react-hook-form';

import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from 'react-router-dom';

import { ToastContainer, toast } from 'react-toastify';

import { useRecoilValue } from 'recoil';

import { DepDateFooter, DestFooter, FlightName, Foundation, DestTitle, DepDateTitle } from './Components'

import { axBase, axAuth } from '@/apis/api/axiosInstance'
import { cards, buttons, ModalCard, NavTop } from '@/components';
import { LoginState } from '@/states/LoginState';
import VolUserStyle from '@/styles/pages/Volunteer/VolUserResiPage.module.css';



function VolUserResi() {

  const { control, handleSubmit, register, formState: { isValid } } = useForm()


  const [foundations, setFoundations] = useState<foundation[]>([])
  useEffect(() => {
    axBase({
      url: '/foundation/list'
    })
      .then((res) => {
        setFoundations([...res.data.data])
      })
  }, [])

  const notify = () => {
    if (!isValid) {
      toast.error('모든 항목을 작성해주세요.', {
        position: "bottom-center",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: 0,
        theme: "colored",
      })
    }
  }
  const [term, setTerm] = useState(false)

  const isLoggedIn = useRecoilValue(LoginState)
  let useremail = ""
  if (isLoggedIn) {
    // 로그인 되어있다면 userData를 가져온다
    const userData = sessionStorage.getItem("userData") || ""
    const { email } = JSON.parse(userData)
    useremail = email
  }

  const volunteerSubmit = (data: object) => {
    data = { ...data, email: useremail }
    axAuth({
      method: 'post',
      url: '/volunteer/submit',
      data: data
    })
      .then(() => {
        alert('봉사 신청이 완료되었습니다.')
        navigate('/mypage/user/vollist')
      })
  }

  const navigate = useNavigate()

  return (
    <div>
      <NavTop.NavLogo />
      <button className={`${VolUserStyle.GoToMyRegi} bg-main-30`} type="button" onClick={() => navigate('/mypage/user/vollist')}>
        <p className="text-body-bold text-white">내 봉사 확인하기</p>
        <p className="text-caption1 text-black">현황 확인 및 서류 제출하기</p>
      </button>
      <form onSubmit={handleSubmit((data) => volunteerSubmit(data))}>
        <div className={VolUserStyle.FlightWrapper}>
          <cards.CardSm
            CardTitle={<DestTitle />}
            CardFooter={<DestFooter control={control} name="dest"
              rules={{
                validate: (v: string) => v != undefined && v != 'none',
              }}
            />} />
          <cards.CardSm
            CardTitle={<DepDateTitle />}
            CardFooter={<DepDateFooter control={control} name="depTime"
              rules={{
                validate: (v: string) => v != undefined,
              }}
            />}
          />
        </div>
        <p className={VolUserStyle.SelectTitle}>항공사 선택하기</p>
        <div className={VolUserStyle.FlightSelect}>
          <FlightName control={control} name="flightName"
            rules={{
              validate: (v: string) => v != undefined,
            }}
          />
        </div>
        <p className={VolUserStyle.SelectTitle}>단체 선택하기</p>
        <div className={VolUserStyle.GroupSelect}>
          <Foundation control={control} foundations={foundations}
            name="businessNo"
            rules={{ validate: (v: string) => v != undefined, }}
          />
        </div>
        <div className={VolUserStyle.Terms}>
          <input id="termsService" type="checkbox" onClick={() => setTerm(!term)}
            {...register("termsService", {
              validate: v => v
            })}
          />
          <label htmlFor="termsService">주의사항을 읽었고 개인정보 제공에 동의합니다.</label>
        </div>
        <buttons.BtnLg BtnValue="신청하기" onClick={notify} />
      </form>
      {term
        &&
        <ModalCard
          CardTitle='유의사항'
          closeModal={() => setTerm(!term)}
          CardContents={[(
            <ol key='asdf'>
              <li><b className='text-title2-bold'>1.</b> 출발 전 48시간 이상 남은 시점에 신청해주세요.</li>
              <li><b className='text-title2-bold'>2.</b> 현지의 사정이나 입양견의 상황에 따라 이동봉사가 취소될 수 있습니다.</li>
              <li><b className='text-title2-bold'>3.</b> 단체의 봉사 확정 이후 여권 등 서류를 제출하셔야 합니다.</li>
            </ol>
          )]}
        />}
      <ToastContainer />
    </div >
  );
}

export default VolUserResi;