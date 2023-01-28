import MdStyle from './CardMd.module.css'

function CardMd({ CardTitle, CardContents, CardFooter }: CardProps): JSX.Element {

  return (
    <div className={MdStyle.MdBox}>
      <div className={MdStyle.MdTitle}>{CardTitle}</div>
      <div className={MdStyle.MdContents}>
        {
          CardContents?.map((content, idx) => {
            return <p key={`${idx}-${new Date().getTime()}`}>{content}</p>
          })
        }
      </div>
      <div>{CardFooter}</div>
    </div >
  );
}

export default CardMd