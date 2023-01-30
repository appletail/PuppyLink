import XLStyle from './CardXL.module.css'
import { imgs } from '@/components';


interface CardXL extends CardProps {
  CardImg: imgProps
}

function CardXL({ CardImg, CardTitle, CardContents, CardFooter }: CardXL): JSX.Element {

  return (
    <div className={XLStyle.XLBox}>
      <div>
        <imgs.ImgLg
          alt={CardImg?.alt}
          src={CardImg.src}
        />
        <div className={XLStyle.XLTitle}>{CardTitle}</div>
        <div className={XLStyle.XLContents}>
          {
            CardContents?.map((content, idx) => {
              return <p key={`${idx}-${new Date().getTime()}`}>{content}</p>
            })
          }
        </div>
      </div>
      <div>{CardFooter}</div>
    </div >
  );
}

export default CardXL