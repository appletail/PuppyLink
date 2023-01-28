import XLStyle from './CardXL.module.css'

interface CardXL extends CardProps {
  CardImg: imgProps
}

function CardXL({ CardImg, CardTitle, CardContents, CardFooter }: CardProps): JSX.Element {

  return (
    <div className={XLStyle.XLBox}>
      <div>
        <img alt={CardImg?.alt} id={XLStyle['card-img']} src={CardImg?.src} />
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