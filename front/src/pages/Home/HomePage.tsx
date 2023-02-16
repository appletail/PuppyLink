import { useRef } from 'react'

import CrawlingNews from './Components/CrawlingNews';
import useNumberCount from './CustomHook/useNumberCount';

import DoggyImage from '@/assets/Doggy_image.png'
import planet from '@/assets/planet.gif'
import { NavTop } from '@/components';
import { ChannelTalk } from '@/components';
function HomePage() {
  ChannelTalk.showChannelButton();
  return (
    <div>
      <NavTop.NavLogo />
      <div id="mainBody">
        <img alt="개사진" className="mt-9 mb-16" src={DoggyImage} />

        <div className='flex flex-col gap-3 mb-16 px-8'>
          <div>
            <p>함께하는 단체</p>
            <p className='text-largetitle-bold'>{useNumberCount(10, 0, 4000)}</p>
          </div>
          <div>
            <p>가족을 찾은 강아지</p>
            <p className='text-largetitle-bold'>{useNumberCount(2512, 0, 2000)}</p>
          </div>
          <div>
            <p>현재 진행중인 봉사</p>
            <p className='text-largetitle-bold'>{useNumberCount(143, 0, 2000)}</p>
          </div>
        </div>


        <img alt='planet' className='mb-8' src={planet} />
        <div className='mb-6 flex justify-center' id='mainTitle'>
          <p className='text-title2-bold'>미국 혹은 캐나다로 출국 하시나요?</p>
        </div>
        <div className='flex flex-col gap-8 mb-12 px-8 items-center'>
          <div className='flex'>
            <div className='text-title1-bold whitespace-nowrap min-w-[61.650px] mr-4'>{useNumberCount(30, 0, 2000)}분</div>
            <p>봉사자님께서 계획하신 출국시간보다 더 일찍 도착해주시면 됩니다</p>
          </div>
          <div className='flex'>
            <div className='text-title1-bold whitespace-nowrap mr-9'>{0}원</div>
            <p>출국하는데 발생하는 모든 비용은 단체에서 지원합니다</p>
          </div>
          <div className='flex'>
            <div className='text-title1-bold whitespace-nowrap mr-9'>0개</div>
            <p>출국하는데 필요한 서류는 모두 단체에서 준비합니다</p>
          </div>
        </div>

        <CrawlingNews />
      </div>
    </div >
  )
}

export default HomePage