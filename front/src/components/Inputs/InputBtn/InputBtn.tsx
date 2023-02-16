import InputStyle from './InputBtn.module.css';

function InputBtn({
  placeholder,
  helper,
  InputType,
  button,
}: InputProps): JSX.Element {
  const helpermessage = helper ? helper : '';
  return (
    <div className={InputStyle.Div}>
      <div className={InputStyle.BoxDiv}>
        <input
          className={InputStyle.InputBox}
          placeholder={placeholder}
          type={InputType}
        />
        <div>{button}</div>
      </div>
      <div className={InputStyle.Helper}>{helpermessage}</div>
    </div>
  );
}

export default InputBtn;
