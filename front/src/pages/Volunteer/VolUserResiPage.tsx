import { useForm } from 'react-hook-form';

import { ToastContainer, toast } from 'react-toastify';

import "react-toastify/dist/ReactToastify.css";

import { DepDateFooter, DestFooter, FlightName, Foundation, DestTitle, DepDateTitle } from './Components'

import { cards, buttons } from '@/components';
import VolUserStyle from '@/styles/pages/Volunteer/VolUserResiPage.module.css';


function VolUserResi() {

  const { control, handleSubmit, register, formState: { isValid } } = useForm()

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


  return (
    <div>
      <button className={`${VolUserStyle.GoToMyRegi} bg-main-30`} type="button">
        <p className="text-body-bold text-white">내 봉사 확인하기</p>
        <p className="text-caption1 text-black">현황 확인 및 서류 제출하기</p>
      </button>
      <form onSubmit={handleSubmit((data) => console.log(data))}>
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
            CardFooter={<DepDateFooter control={control} name="depDate"
              rules={{
                validate: (v: string) => v != undefined,
              }}
            />}
          />
        </div>
        <p className={VolUserStyle.SelectTitle}>항공사 선택하기</p>
        <div className={VolUserStyle.FlightSelect}>
          <FlightName control={control} name="filghtName"
            rules={{
              validate: (v: string) => v != undefined,
            }}
          />
        </div>
        <p className={VolUserStyle.SelectTitle}>단체 선택하기</p>
        <div className={VolUserStyle.GroupSelect}>
          <Foundation control={control} name="businessNo"
            rules={{
              validate: (v: string) => v != undefined,
            }}
          />
        </div>
        <div className={VolUserStyle.Terms}>
          <input id="termsService" type="checkbox"
            {...register("termsService", {
              validate: v => v
            })}
          />
          <label htmlFor="termsService">주의사항을 읽었고 개인정보 제공에 동의합니다.</label>
        </div>
        <buttons.BtnLg BtnValue="신청하기" onClick={notify} />
        <ToastContainer />
      </form>
    </div >
  );
}

export default VolUserResi;