import { useNavigate, redirect } from 'react-router-dom';
import { kakaoAuth } from '@/apis/api/axiosInstance';

import { NavTop } from '@/components';

function UserTabPage() {
  const navigate = useNavigate();

  // Kakao restapi key
  const KAKAO_KEY = 'abc501edb920accef066a054e7659254';
  // 인가코드가 리턴되는 서버주소
  const REDIRECT_URL = "http://i8c107.p.ssafy.io:3000/Social/kakao";
  // const REDIRECT_URL = "http://localhost:3000/Social/kakao";
  

  const KakaoLogin = () => {
    let url = "https://kauth.kakao.com/oauth/authorize"
              + `?client_id=${KAKAO_KEY}`
              + `&redirect_uri=${REDIRECT_URL}`
              + `&response_type=code`;
    location.href = url;
  };

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
        <div 
          className="h-14 rounded-[0.625rem] w-[21.875rem]  flex bg-select items-center align-center justify-evenly" 
          onClick={KakaoLogin}
        >
          카카오로 계속하기
        </div>
        <div className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center justify-evenly">
          구글로 계속하기
        </div>
        <div className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center justify-evenly">
          네이버로 계속하기
        </div>
        <div
          aria-hidden='true'
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
