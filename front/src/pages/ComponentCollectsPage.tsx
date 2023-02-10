import { FaPlaneDeparture } from 'react-icons/fa';

import { ReactComponent as Korair } from '@/assets/Korair.svg'
import { buttons, cards, labels, footers, imgs, NavBottom, inputs } from '@/components';

function ComponentCollectsPage() {
  const whereareyou = (
    <div className="flex">
      <FaPlaneDeparture />
      <p className="ml-2">어디로 가세요?</p>
    </div>
  )

  return (
    <div className="ComponentCollectsPage">
      <footers.FooterHeart HeartCount={123} IsLiked={true} Username="harim" />
      <Korair />
      <labels.Label LabelValue="비행기 경로 확인" />
      <buttons.BtnSm BtnValue="hello" />
      <buttons.BtnBsm BtnValue="hello" />
      <buttons.BtnMd BtnValue="hello" />
      <buttons.BtnLg BtnValue="hello" />
      <cards.CardSm
        CardContents={['Lorem ipsum dolor sit']}
        CardTitle={whereareyou}
      />
      <cards.CardMd
        CardContents={['Lorem ipsum dolor sit amet, elit.']}
        CardTitle="Title"
      />
      <cards.CardLg
        CardTitle="Title"
        CardContents={[
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
        ]}
        CardFooter={
          <footers.FooterBtn
            BtnLeft={<buttons.BtnBsm BtnValue="신청하기" />}
            BtnRight={<buttons.BtnSm BtnValue="거절하기" />}
          />
        }
      />
      <cards.CardLg
        CardTitle="Title"
        CardContents={[
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
        ]}
        CardFooter={
          <footers.FooterBtn
            BtnLeft={<labels.Label LabelValue="신청하기" />}
            BtnRight={<buttons.BtnSm BtnValue="거절하기" />}
          />
        }
      />
      <cards.CardLg
        CardTitle="Title"
        CardContents={[
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
        ]}
        CardFooter={
          <footers.FooterHeart
            HeartCount={123}
            IsLiked={true}
            Username="harim"
          />
        }
      />
      <cards.CardXL
        CardTitle="Title"
        CardContents={[
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
        ]}
        CardImg={{
          src: 'https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg',
        }}
      />
      <imgs.ImgLg src="https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg" />
      <imgs.ImgSm src="https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg" />

      <inputs.Input InputType="text" helper="asdf??" placeholder="r개편함" />
      <inputs.Input
        InputType="password"
        helper="비밀번호를 입력하세뇨"
        placeholder="비밀번호를 입력해주세요"
      />

      <inputs.InputBtn
        InputType="text"
        placeholder="버튼눌러보세요?"
        button={
          <buttons.BtnSm
            BtnValue="검증하기"
            onClick={() => console.log('hello world!')}
          />
        }
      />


      <NavBottom />
    </div>
  );
}

export default ComponentCollectsPage;
