import InputStyle from './Input.module.css';

function Input({ placeholder, helper, InputType }: InputProps): JSX.Element {
  const helpermessage = helper ? helper : '';
  return (
    <div className={InputStyle.Div}>
      <input
        className={InputStyle.InputBox}
        placeholder={placeholder}
        type={InputType}
      />
      <div className={InputStyle.Helper}>{helpermessage}</div>
    </div>
  );
}

export default Input;
