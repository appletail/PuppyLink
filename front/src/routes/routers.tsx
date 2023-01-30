import { createBrowserRouter } from 'react-router-dom';

import App from '@/app/App';
import Components from '@/pages/ComponentCollects';

const routers = createBrowserRouter([
  {
    path: '/',
    element: <App />,
  },
  {
    path: '/components',
    element: <Components />,
  }
]);

export default routers;
