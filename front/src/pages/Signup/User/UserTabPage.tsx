import { useNavigate } from 'react-router-dom';

import { NavTop } from '@/components';

function UserTabPage() {
  const navigate = useNavigate();

  return (
    <div >
      <NavTop.NavBack NavContent='회원가입'/>
      <div className="mt-14 mb-11 flex justify-center ">
        <div className="w-1/2 h-10 text-center border-b-2 border-main-100">
          <div className="text-title3-bold">개인회원</div>
        </div>
        <div className="w-1/2 h-10 text-center border-b-[1px] border-main-50">
          <div className="text-title3" onClick={() => navigate('/signup/grouptab')}>단체회원</div>
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
          onClick={() => navigate('/signup/user')}
        >
          이메일로 계속하기
        </div>
      </div>
    </div>
  );
}

export default UserTabPage;
