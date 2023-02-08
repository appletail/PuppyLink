// import { useLocation } from 'react-router-dom';
// import { useEffect } from 'react';
import { NavTop } from '@/components';
import Navtop from './navtop';

// function navbarController() {
//   let location = useLocation();

//   useEffect(() => {
//     let path = location.pathname;
//     let paths = path.split('/');

//     if (paths.includes('signup')) {
//     }
//   }, [location]);
//   return <div>navbarController</div>;
// }

// export default navbarController;
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Outlet,
} from 'react-router-dom';

const NavLayout = () => (
  <>
    <Navtop />
    <Outlet />
  </>
);

function navbarController(): JSX.Element {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<NavTop.NavLogo />} />
        <Route element={<NavTop.NavDetail />}>
          <Route path="/login" />
          <Route path="/signup" />
        </Route>
      </Routes>
    </Router>
  );
}
export default navbarController;
