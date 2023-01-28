import { cards } from '@/components';

function App() {

  return (
    <div className="App p-7">
      <cards.CardSm
        CardContents={['Lorem ipsum dolor sit amet, elit.']}
        CardTitle='Title'
      />
      <cards.CardMd
        CardContents={['Lorem ipsum dolor sit amet, elit.']}
        CardTitle='Title'
      />
      <cards.CardLg
        CardContents={['Lorem ipsum dolor sit amet, elit.', 'Lorem ipsum dolor sit amet, elit.', 'Lorem ipsum dolor sit amet, elit.', 'Lorem ipsum dolor sit amet, elit.']}
        CardFooter='asdl;kfjaskl;dfj'
        CardTitle='Title'
      />
      <cards.CardXL
        CardContents={['Lorem ipsum dolor sit amet, elit.', 'Lorem ipsum dolor sit amet, elit.', 'Lorem ipsum dolor sit amet, elit.', 'Lorem ipsum dolor sit amet, elit.']}
        CardFooter='jaskldfjaskldfj'
        CardImg={{ src: 'https://cdn.pixabay.com/photo/2020/06/02/06/52/cat-5249722__480.jpg' }}
        CardTitle='Title'
      />
    </div>
  )
}

export default App;
