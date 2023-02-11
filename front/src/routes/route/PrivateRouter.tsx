import { ReactElement } from 'react'
import { Navigate, Outlet } from 'react-router-dom'

interface PrivateRouterProps {
  children?: ReactElement;
  authentication: boolean;
}



function PrivateRouter({ authentication }: PrivateRouterProps): ReactElement | null {
  // localStorage에서 token이 있으면 로그인 완료 처리
  const isAuthenticated = localStorage.getItem("access-token")

  // 인증이 반드시 필요한 페이지 
  // 인증을 안했을 경우 로그인 페이지로, 했을 경우 해당페이지로
  if (authentication) {
    return (isAuthenticated === null || isAuthenticated === 'false') ? <Navigate to="/login" /> : <Outlet />
  }

  // 인증이 반드시 필요없는 페이지
  // 인증을 안했을 경우 해당페이지로 인증을 한 상태일 경우 main 페이지로
  else {
    return (isAuthenticated === null || isAuthenticated === 'false') ? <Outlet /> : <Navigate to="/" />
  }
}

export default PrivateRouter