import XLStyle from './CardXL.module.css'
import { imgs } from '@/components';


interface CardXL extends Omit<CardProps, 'CardContents'> {
  CardImg: imgProps;
  CardContents?: string | JSX.Element | JSX.Element[];
}

function CardXL({
  CardImg,
  CardTitle,
  CardContents,
  CardFooter,
}: CardXL): JSX.Element {
  return (
    <div className={XLStyle.XLBox}>
      <div>
        <imgs.ImgLg
          alt={CardImg?.alt}
          src={CardImg.src}
        />
        <div className={XLStyle.XLContentBox}>
          <div className={XLStyle.XLTitle}>{CardTitle}</div>
          <div className={XLStyle.XLContents}>
            {CardContents}
          </div>
        </div>
      </div>
      <div>{CardFooter}</div>
    </div>
  );
}

export default CardXL;
