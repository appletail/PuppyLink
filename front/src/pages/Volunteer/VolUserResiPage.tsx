import { useForm } from 'react-hook-form';

import DepDateFooter from './Components/DepDateFooter';
import DestFooter from './Components/DestFooter';
import FlightName from './Components/FilghtName';
import { DestTitle, DepDateTitle } from './Components/FlightTitles';
import Foundation from './Components/FoundationSelect';

import { cards, buttons } from '@/components';

import VolUserStyle from '@/styles/pages/Volunteer/VolUserResiPage.module.css';

function VolUserResi() {
  const requiredData = {
    dest: 'JFK',
    depDate: '2023-02-04',
    filghtName: 'korair',
    foundation: 'kara',
    isAgree: true
  }
  // 각각 컴포넌트 아래에 form 프롭스해야함
  // 선택하기 아래에 하나 선택하면 다른거 선택풀리게 해야함
  const { control, handleSubmit, register } = useForm()


  return (
    <div>
      <button className={`${VolUserStyle.GoToMyRegi} bg-main-30`} type="button">
        <p className="text-body-bold text-white">내 봉사 확인하기</p>
        <p className="text-caption1 text-black">현황 확인 및 서류 제출하기</p>
      </button>
      <form onSubmit={handleSubmit((data) => console.log(data))}>
        <div className={VolUserStyle.FlightWrapper}>
          <cards.CardSm CardFooter={<DestFooter control={control} name="dest" />} CardTitle={<DestTitle />} />
          <cards.CardSm CardTitle={<DepDateTitle />} CardFooter={<DepDateFooter control={control} name="depDate" rules={{
            required: { value: true, message: "날짜를 입력하세요." },
          }} />} />
        </div>
        <p className={VolUserStyle.SelectTitle}>항공사 선택하기</p>
        <div className={VolUserStyle.FlightSelect}>
          <FlightName control={control} name="filghtName" />
        </div>
        <p className={VolUserStyle.SelectTitle}>단체 선택하기</p>
        <div className={VolUserStyle.GroupSelect}>
          <Foundation control={control} name="foundation" />
        </div>
        <div className={VolUserStyle.Terms}>
          <input className="chk" id="termsService" type="checkbox" {...register("termsService")} />
          <label htmlFor="termsService">주의사항을 읽었고 개인정보 제공에 동의합니다.</label>
        </div>
        <buttons.BtnLg BtnValue="신청하기" />
      </form>
    </div >
  );
}

export default VolUserResi;
