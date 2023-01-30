import { buttons, cards, labels, footers, imgs, inputs } from '@/components';

function App() {

  return (
    <div className="App p-7">
      <inputs.Input
        placeholder="아이디를 입력해주세요"
        InputType="password"
        helper="아이디를 입력하세뇨"
      />
      <inputs.Input
        placeholder="아이디를 입력해주세요"
        InputType="text"
        helper="외않되??"
      />

      <inputs.InputBtn
        placeholder="될까요?"
        InputType="text"
        button={
          <buttons.BtnSm
            BtnValue="검증하기"
            onClick={() => console.log('hello world!')}
          />
        }
      />
    </div>
  );
}

export default App;
