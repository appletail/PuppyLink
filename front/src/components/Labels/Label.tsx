import LabelStyle from './Label.module.css';

function Label({ LabelValue }: LabelProps): JSX.Element {
  return <div className={LabelStyle.LabelBox}>{LabelValue}</div>;
}

export default Label;
