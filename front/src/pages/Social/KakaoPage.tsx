import { useNavigate } from "react-router-dom"
import { toast } from 'react-toastify';
import { axBase } from "@/apis/api/axiosInstance"
import { ChannelTalk } from "@/components";

function KakaoPage() {
    const navigate = useNavigate();
    const urlParams = new URL(location.href).searchParams;
    // console.log(location.href);
    const CODE = urlParams.get('code');
    // console.log(urlParams);
    axBase({
        method: "GET",
        url: `/members/kakao?code=${CODE}`
    }).then((response) => {

        const resData = response.data.data;
        navigate('/')
        
        if (response.data.code == "SUCCESS_LOGIN") {
            const access_token = response.headers.authorization.split(" ")[1];
            const refresh_token = response.headers.refreshtoken.split(" ")[1];

            if (access_token) {
                sessionStorage.setItem('access-token', access_token);
                sessionStorage.setItem('refresh-token', refresh_token);
            }

            const LoginData: Member =
            {
                email: resData.email,
                name: resData.name,
                phone: resData.phone,
                nickName: resData.nickName,
                activated: true,
                authorities: [{ "authorityName": resData.authorities[0].authorityName }],
                joinDate: resData.joinDate,
                role: resData.authorities[0].authorityName
            }
            // localStorage.setItem('userData', JSON.stringify(LoginData))
            sessionStorage.setItem('userData', JSON.stringify(LoginData))
        }
        ChannelTalk.updateUser({
            language: 'ko',
            profile: {
              email: resData.email,
              phone: resData.phone,
              nickName: resData.nickName,
            }
        });

    })
    .catch((err) => {

        toast.error(err, {
          autoClose: 3000,
          position: toast.POSITION.BOTTOM_CENTER,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: 'colored',
        })
      });
    return (
        <div></div>
    )
}

export default KakaoPage;