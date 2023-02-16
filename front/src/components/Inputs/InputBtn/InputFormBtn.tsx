import { useController, useForm, UseControllerProps } from 'react-hook-form';
import InputStyle from './InputBtn.module.css';

interface TControl extends UCProps {
  type?: string;
  placeholder?: string;
  button: JSX.Element;
}

function InputFormBtn({
  control,
  name,
  rules,
  type,
  placeholder,
  button,
}: TControl) {
  const {
    field: { value, onChange },
    fieldState: { invalid, isTouched, isDirty },
  } = useController({ name, rules, control });
  return (
    <div className={InputStyle.BoxDiv}>
      <input
        className={InputStyle.InputBox}
        value={value || ''}
        onChange={onChange}
        type={type}
        placeholder={placeholder}
      />
      <div className="min-w-[70px]">{button}</div>
      {/* <p>{fieldState.isTouched && 'Touched'}</p>
      <p>{fieldState.isDirty && 'Dirty'}</p>
      <p>{fieldState.invalid ? 'invalid' : 'valid'}</p> */}
    </div>
  );
}

export default InputFormBtn;
