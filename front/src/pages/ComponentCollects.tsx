import { buttons, cards, labels, footers, imgs, NavBottom } from '@/components';


function Components() {
  return (
    <div className="Components">
      <footers.FooterHeart HeartCount={123} IsLiked={true} Username="harim" />

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
      <imgs.ImgLg src='https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg' />
      <imgs.ImgSm src='https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg' />
      <NavBottom />
    </div>
  );
}

export default Components;
