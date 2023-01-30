import InputStyle from './Input.module.css';

function Input({ placeholder, helper, InputType }: InputProps): JSX.Element {
  let helpermessage = helper ? helper : '';
  return (
    <div className={InputStyle.Div}>
      <input
        className={InputStyle.InputBox}
        type={InputType}
        placeholder={placeholder}
      />
      <div className={InputStyle.Helper}>{helpermessage}</div>
    </div>
  );
}

export default Input;
