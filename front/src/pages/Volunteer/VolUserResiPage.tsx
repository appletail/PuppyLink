import { useForm } from 'react-hook-form';

import DepDateFooter from './Components/DepDateFooter';
import DestFooter from './Components/DestFooter';
import FlightName from './Components/FilghtNameSelect';
import { DestTitle, DepDateTitle } from './Components/FlightCardTitles';
import Foundation from './Components/FoundationSelect';

import { cards, buttons } from '@/components';

import VolUserStyle from '@/styles/pages/Volunteer/VolUserResiPage.module.css';

function VolUserResi() {
  const requiredData = {
    dest: '미국 워싱턴',
    depDate: '2023-02-04:00:00:00',
    filghtName: 'korair',
    businessNo: '1148209801',
    termsService: true
  }

  const { control, handleSubmit, register, formState: { errors } } = useForm()


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
            CardFooter={<DestFooter
              control={control}
              name="dest"
              rules={{
                validate: (v: string) => v != undefined || '도착지를 선택해주세요.',
              }}
            />} />
          {errors.dest && <span>{`${errors.dest.message}`}</span>}
          <cards.CardSm
            CardTitle={<DepDateTitle />}
            CardFooter={<DepDateFooter
              control={control}
              name="depDate"
              rules={{
                validate: (v: string) => v != undefined || '출국일을 선택해주세요.',
              }}
            />}
          />
          {errors.depDate && <span>{`${errors.depDate.message}`}</span>}
        </div>
        <p className={VolUserStyle.SelectTitle}>항공사 선택하기</p>
        <div className={VolUserStyle.FlightSelect}>
          <FlightName
            control={control}
            name="filghtName"
            rules={{
              validate: (v: string) => v != undefined || '항공사를 선택해주세요.',
            }}
          />
          {errors.filghtName && <span>{`${errors.filghtName.message}`}</span>}
        </div>
        <p className={VolUserStyle.SelectTitle}>단체 선택하기</p>
        <div className={VolUserStyle.GroupSelect}>
          <Foundation
            control={control}
            name="businessNo"
            rules={{
              validate: (v: string) => v != undefined || '단체를 선택해주세요.',
            }}
          />
          {errors.businessNo && <span>{`${errors.businessNo.message}`}</span>}
        </div>
        <div className={VolUserStyle.Terms}>
          <input
            id="termsService"
            type="checkbox"
            {...register("termsService", {
              validate: v => v || '약관에 동의해주세요.'
            })}
          />
          <label htmlFor="termsService">주의사항을 읽었고 개인정보 제공에 동의합니다.</label>
          {errors.termsService && <span>{`${errors.termsService.message}`}</span>}
        </div>
        <buttons.BtnLg BtnValue="신청하기" />
      </form>
    </div >
  );
}

export default VolUserResi;
