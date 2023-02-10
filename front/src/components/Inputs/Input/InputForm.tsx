import {
  Control,
  FieldPath,
  FieldValues,
  RegisterOptions,
  useController,
} from 'react-hook-form';

export type TControl<T extends FieldValues> = {
  control: Control<T>;
  name: FieldPath<T>;
  rules?: Exclude<
    RegisterOptions,
    'valueAsNumber' | 'valueAsDate' | 'setValueAs'
  >;
  type?: string;
  placeholder?: string ;
};

function InputForm({ control, name, rules, type, placeholder }: TControl<any>) {
  const {
    field: { value, onChange },
  } = useController({ name, rules, control });
  return (
    <input
      className="input w-full p-4 appearance-none border border-main-30 rounded-lg text-body focus:border-none focus:outline-main-50 focus:shadow  invalid:border-red invalid:text-red"
      placeholder={placeholder}
      type={type}
      value={value || ''}
      onChange={onChange}
    />
  );
}

export default InputForm;
