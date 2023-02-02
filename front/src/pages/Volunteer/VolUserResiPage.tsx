import { useForm } from 'react-hook-form';

import Airlines from './Components/AirlineSelect';
import { WhereTitle, WhenTitle } from './Components/FlightTitles';
import Groups from './Components/GroupSelect';
import WhenFooter from './Components/WhenFooter';
import WhereFooter from './Components/WhereFooter';

import { cards, buttons } from '@/components';

import VolUserStyle from '@/styles/pages/Volunteer/VolUserResiPage.module.css';

function VolUserResi() {
  const requiredData = {
    where: 'JFK',
    when: '2023-02-04',
    airline: 'korair',
    group: 'kara',
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
          <cards.CardSm CardFooter={<WhereFooter control={control} name="where" />} CardTitle={<WhereTitle />} />
          <cards.CardSm CardTitle={<WhenTitle />} CardFooter={<WhenFooter control={control} name="when" rules={{
            required: { value: true, message: "날짜를 입력하세요." },
          }} />} />
        </div>
        <p className={VolUserStyle.SelectTitle}>항공사 선택하기</p>
        <div className={VolUserStyle.FlightSelect}>
          <Airlines control={control} name="airline" />
        </div>
        <p className={VolUserStyle.SelectTitle}>단체 선택하기</p>
        <div className={VolUserStyle.GroupSelect}>
          <Groups />
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
