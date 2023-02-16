import { useNavigate, redirect } from 'react-router-dom';

import { kakaoAuth } from '@/apis/api/axiosInstance';


import { ReactComponent as Email } from '@/assets/SocialLogos/Signup/Email.svg'
import { ReactComponent as Google } from '@/assets/SocialLogos/Signup/Google.svg'
import { ReactComponent as Kakao } from '@/assets/SocialLogos/Signup/Kakao.svg'
import { ReactComponent as Naver } from '@/assets/SocialLogos/Signup/Naver.svg'
import { NavTop } from '@/components';

function UserTabPage() {
  const navigate = useNavigate();

  // Kakao restapi key
  const KAKAO_KEY = 'abc501edb920accef066a054e7659254';
  // 인가코드가 리턴되는 서버주소
  const REDIRECT_URL = "http://i8c107.p.ssafy.io:3000/Social/kakao";
  // const REDIRECT_URL = "http://localhost:3000/Social/kakao";


  const KakaoLogin = () => {
    const url = "https://kauth.kakao.com/oauth/authorize"
      + `?client_id=${KAKAO_KEY}`
      + `&redirect_uri=${REDIRECT_URL}`
      + `&response_type=code`;
    location.href = url;
  };

  return (
    <div >
      <NavTop.NavBack NavContent='회원가입' />
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
          className="h-14 rounded-[0.625rem] w-[21.875rem]  flex bg-select items-center align-center px-16 gap-10"
          onClick={KakaoLogin}
        >
          <Kakao />
          카카오로 계속하기
        </div>
        <div className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center px-16 gap-10">
          <Google />
          구글로 계속하기
        </div>
        <div className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center px-16 gap-10">
          <Naver />
          네이버로 계속하기
        </div>
        <div
          aria-hidden='true'
          className="h-14 rounded-[0.625rem] w-[21.875rem] flex bg-select items-center align-center px-16 gap-10"
          onClick={() => navigate('/signup/user')}
        >
          <Email />
          이메일로 계속하기
        </div>
      </div>
    </div>
  );
}

export default UserTabPage;
