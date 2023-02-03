import SmStyle from './CardSm.module.css';

interface SmProps extends Omit<CardProps, 'CardTitle'> {
  CardTitle: JSX.Element;
}

function CardSm({ CardTitle, CardContents, CardFooter }: SmProps): JSX.Element {
  return (
    <div className={SmStyle.SmBox}>
      <div>
        <div className={SmStyle.SmTitle}>{CardTitle}</div>
        <div className={SmStyle.SmContents}>
          {CardContents?.map((content, idx) => {
            return <p key={`${idx}-${new Date().getTime()}`}>{content}</p>;
          })}
        </div>
      </div>
      <div>{CardFooter}</div>
    </div>
  );
}

export default CardSm;
