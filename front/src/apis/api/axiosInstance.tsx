import axios, { AxiosInstance } from 'axios';

export const interceptors = (instance: AxiosInstance) => {
  instance.interceptors.request.use(
    (config) => {
      const token = sessionStorage.getItem('access-token');

      config.headers.Authorization = `Bearer ${token}`;
      return config;
    },
    (error) => Promise.reject(error.response)
  );
  return instance;
};

// const BASE_URL = 'http://i8c107.p.ssafy.io:8085/puppy'; // ec2에 올릴 경우 체인지
const BASE_URL = 'http://localhost:8085/puppy';
// 카카오 로그인 요청을 보내는 URL
const KAKAO_URL = 'https://kauth.kakao.com';

// 단순 get요청으로 인증값이 필요없는 경우
const axiosApi = (url: string, options?: object) => {
  const instance = axios.create({ baseURL: url, ...options });
  return instance;
};

// post, delete등 api요청 시 인증값이 필요한 경우
const axiosAuthApi = (url: string, options?: object) => {
  const instance = axios.create({ baseURL: url, ...options });
  interceptors(instance);
  return instance;
};

const axioskakao = (url: string, options?: object) => {
  const instance = axios.create({ baseURL: url, ...options });
  return instance;
}

export const axBase = axiosApi(BASE_URL);
export const axAuth = axiosAuthApi(BASE_URL);
export const kakaoAuth = axioskakao(KAKAO_URL);
