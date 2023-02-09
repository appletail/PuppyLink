import { useState, useEffect } from 'react'
import { useForm } from 'react-hook-form';
import { ToastContainer, toast } from 'react-toastify';

import "react-toastify/dist/ReactToastify.css";

import { DepDateFooter, DestFooter, FlightName, Foundation, DestTitle, DepDateTitle } from './Components'

import { axBase } from '@/apis/api/axiosInstance'
import { cards, buttons, Modal } from '@/components';
import VolUserStyle from '@/styles/pages/Volunteer/VolUserResiPage.module.css';

function VolUserResi() {

  const { control, handleSubmit, register, formState: { isValid } } = useForm()

  interface foundation {
    businessNo: string
    businessName: string
    presidentName: string
    startDate: Date
    description: string
    profileURL?: string
  }


  const [foundations, setFoundations] = useState<foundation[]>([])
  useEffect(() => {
    axBase({
      url: '/foundation/list'
    })
      .then((res) => {
        setFoundations([...res.data.data])
      })
      .catch((err) => console.log(err))
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

  const volunteerSubmit = (data: object) => {
    data = { ...data, email: 'litan36@naver.com' }
    axBase({
      method: 'post',
      url: '/volunteer/submit',
      data: data
    })
      .then((res) => console.log(res.data))
      .catch((err) => console.log(err.response.data))
  }

  return (
    <div>
      <button className={`${VolUserStyle.GoToMyRegi} bg-main-30`} type="button">
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
        <Modal
          CardContents={['1. 출발 전 48시간 이상 남은 시점에 신청해주세요.', '2. 현지의 사정이나 입양견의 상황에 따라 이동봉사가 취소될 수 있습니다.', '3. 단체의 봉사 확정 이후 여권 등 서류를 제출하셔야 합니다.']}
          CardTitle='유의사항'
          closeModal={() => setTerm(!term)}
        />}
      <ToastContainer />
    </div >
  );
}

export default VolUserResi;