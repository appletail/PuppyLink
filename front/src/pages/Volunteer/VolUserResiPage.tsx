import { FaPlaneDeparture, FaRegCalendar } from 'react-icons/fa';

import { cards, buttons } from '@/components';
import VolUserStyle from '@/styles/pages/Volunteer/VolUserResiPage.module.css';

function VolUserResi() {
  const where = (
    <div className="flex">
      <FaPlaneDeparture />
      <p className="ml-2">어디로 가세요?</p>
    </div>
  );
  const when = (
    <div className="flex">
      <FaRegCalendar />
      <p className="ml-2">언제 출국하세요?</p>
    </div>
  );

  return (
    <div className={VolUserStyle.PageWrapper}>
      <button className={VolUserStyle.GoToMyRegi} type="button">
        <p className="text-body-bold text-white">내 봉사 확인하기</p>
        <p className="text-caption1 text-black">현황 확인 및 서류 제출하기</p>
      </button>
      <div className={VolUserStyle.FlightWrapper}>
        <cards.CardSm CardTitle={where} />
        <cards.CardSm CardTitle={when} />
      </div>
      <p className={VolUserStyle.SelectTitle}>항공사 선택하기</p>
      <div className={VolUserStyle.FlightSelect}>

      </div>
      <p className={VolUserStyle.SelectTitle}>단체 선택하기</p>
      <div className={VolUserStyle.GroupSelect}>

      </div>
      <div className={VolUserStyle.Terms}>
        <input className="chk" id="termsService" name="termsService" type="checkbox" />
        <label htmlFor="termsService">주의사항을 읽었고 개인정보 제공에 동의합니다.</label>
      </div>
      <buttons.BtnLg BtnValue="신청하기" />
    </div>
  );
}

export default VolUserResi;
