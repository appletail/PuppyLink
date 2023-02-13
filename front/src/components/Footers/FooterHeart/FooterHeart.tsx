import { FaHeart } from 'react-icons/fa';
import { FiHeart } from 'react-icons/fi';

import FtStyle from './FooterHeart.module.css';

interface FooterHeart extends FooterProps {
  Username: string;
  IsLiked: boolean;
  HeartCount: number;
}

function FooterHeart({
  Username,
  IsLiked,
  HeartCount,
}: FooterHeart): JSX.Element {
  const HeartIcon =
    IsLiked === true ? (
      <FaHeart className="fill-red" />
    ) : (
      <FiHeart className="stroke-grey" />
    );

  return (
    <div>
      <hr className={FtStyle.Line} />
      <div className={FtStyle.Btns}>
        <div className={FtStyle.User}>
          <div>@</div>
          <div>{Username}</div>
        </div>
        <div className={FtStyle.Heart}>
          {HeartIcon}
          {HeartCount}
        </div>
      </div>
    </div>
  );
}

export default FooterHeart;
