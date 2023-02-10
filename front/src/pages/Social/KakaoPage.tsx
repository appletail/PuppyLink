import { axBase } from "@/apis/api/axiosInstance"
import { useNavigate } from "react-router-dom"

function KakaoPage() {
    const navigate = useNavigate();
    const urlParams = new URL(location.href).searchParams;
    console.log(location.href);
    const CODE = urlParams.get('code');
    console.log(urlParams);
    axBase({
        method: "GET",
        url: `/members/kakao?code=${CODE}`
    }).then(( response ) => {
        if ( response.data.code == "SUCCESS_LOGIN" ) {
            const access_token = response.headers.authorization.split(" ")[1];
            const refresh_token = response.headers.refreshtoken.split(" ")[1];

            if (access_token) {
                localStorage.setItem('access-token', access_token);
                localStorage.setItem('refresh-token', refresh_token);
            }
        }
        navigate('/')
    });
    return(
        <div></div>
    )
}

export default KakaoPage;