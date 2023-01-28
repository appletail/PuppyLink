import { createBrowserRouter } from 'react-router-dom';

import App from '@/app/App';

const routers = createBrowserRouter([
  {
    path: '/',
    element: <App />,
  },
]);

export default routers;
