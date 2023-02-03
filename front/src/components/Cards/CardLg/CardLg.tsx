import LgStyle from './CardLg.module.css';

function CardLg({
  CardTitle,
  CardContents,
  CardFooter,
}: CardProps): JSX.Element {
  return (
    <div className={LgStyle.LgBox}>
      <div>
        <div className={LgStyle.LgTitle}>{CardTitle}</div>
        <div className={LgStyle.LgContents}>
          {CardContents?.map((content, idx) => {
            return <p key={`${idx}-${new Date().getTime()}`}>{content}</p>;
          })}
        </div>
      </div>
      <div>{CardFooter}</div>
    </div>
  );
}

export default CardLg;
