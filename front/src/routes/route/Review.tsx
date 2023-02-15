import PrivateRouter from './PrivateRouter';

import ReviewCreatePage from '@/pages/Review/ReviewCreatePage';
import ReviewDetailPage from '@/pages/Review/ReviewDetailPage';
import ReviewMainPage from '@/pages/Review/ReviewMainPage';


const Review = [
  {
    path: '/review',
    element: <ReviewMainPage />,
  },
  {
    path: '/review/:boardNo',
    element: <ReviewDetailPage />,
  },
  {
    element: <PrivateRouter authentication={true} />,
    children: [
      {
        path: '/review/create',
        element: <ReviewCreatePage />
      },

    ]
  }
]

export default Review
