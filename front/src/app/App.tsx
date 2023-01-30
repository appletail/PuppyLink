import { buttons, cards, labels, footers, imgs } from '@/components';


function App() {
  return (
    <div className="App p-7">
      <footers.FooterHeart Username="harim" IsLiked={true} HeartCount={123} />

      <labels.Label LabelValue="비행기 경로 확인" />
      <buttons.BtnSm BtnValue="hello" />
      <buttons.BtnBsm BtnValue="hello" />
      <buttons.BtnMd BtnValue="hello" />
      <buttons.BtnLg BtnValue="hello" />
      <cards.CardSm
        CardContents={['Lorem ipsum dolor sit']}
        CardTitle="Title"
      />
      <cards.CardMd
        CardContents={['Lorem ipsum dolor sit amet, elit.']}
        CardTitle="Title"
      />
      <cards.CardLg
        CardContents={[
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
        ]}
        CardTitle="Title"
        CardFooter={
          <footers.FooterBtn
            BtnLeft={<buttons.BtnBsm BtnValue="신청하기" />}
            BtnRight={<buttons.BtnSm BtnValue="거절하기" />}
          />
        }
      />
      <cards.CardLg
        CardContents={[
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
        ]}
        CardTitle="Title"
        CardFooter={
          <footers.FooterBtn
            BtnLeft={<labels.Label LabelValue="신청하기" />}
            BtnRight={<buttons.BtnSm BtnValue="거절하기" />}
          />
        }
      />
      <cards.CardLg
        CardContents={[
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
        ]}
        CardTitle="Title"
        CardFooter={
          <footers.FooterHeart
            Username="harim"
            IsLiked={true}
            HeartCount={123}
          />
        }
      />
      <cards.CardXL
        CardContents={[
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
          'Lorem ipsum dolor sit amet, elit.',
        ]}
        CardImg={{
          src: 'https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg',
        }}
        CardTitle="Title"
      />
      <imgs.ImgLg src='https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg' />
      <imgs.ImgSm src='https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg' />
    </div>
  );
}

export default App;
