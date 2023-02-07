import { useNavigate } from 'react-router-dom';

function UserTabPage() {
  const navigate = useNavigate();
  const EmailSignup = () => {
    navigate('/signup/user');
  };

  return (
    <div className="">
      <div className="mt-14 mb-11 flex justify-center ">
        <div className="w-1/2 h-10 text-center border-b-2 border-main-100">
          <p className="text-title3-bold">개인회원</p>
        </div>
        <div className="w-1/2 h-10 text-center border-b-[1px] border-main-50">
          <p className="text-title3">단체회원</p>
        </div>
      </div>

      <div className="mb-20">
        <p className="text-title1 text-center">개인회원 가입</p>
      </div>

      <div className="flex flex-col gap-4">
        <div className="h-14 rounded-[0.625rem] w-[21.875rem]  flex bg-select items-center align-center justify-evenly">
          카카오로 계속하기
        </div>
        <div className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center justify-evenly">
          구글로 계속하기
        </div>
        <div className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center justify-evenly">
          네이버로 계속하기
        </div>
        <div
          className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center justify-evenly"
          onClick={EmailSignup}
        >
          이메일로 계속하기
        </div>
      </div>
    </div>
  );
}

export default UserTabPage;
