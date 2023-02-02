import { ReactNode } from "react";
import { Control, FieldName, FieldValues, RegisterOptions, useController } from "react-hook-form"


export type UseControllerProps<
  TFieldValues extends FieldValues = FieldValues
> = {
  name: FieldName<TFieldValues>;
  rules?: Exclude<RegisterOptions, 'valueAsNumber' | 'valueAsDate' | 'setValueAs'>;
  onFocus?: () => void;
  defaultValue?: unknown;
  control?: Control<TFieldValues>;
};


function WhereFooter({ name, control }: UseControllerProps): JSX.Element {
  const { field: { value, onChange } } = useController({ name, control })

  return (
    <div className="flex flex-col items-center">
      <hr className="border-none h-[0.5px] w-full bg-grey mb-2" />
      <select className="text-caption1" value={value} onChange={onChange}>
        <option value="none">공항을 선택해주세요.</option>
        <option value="IAD">워싱턴</option>
        <option value="JFK">존 F.케네디</option>
        <option value="YVR">밴쿠버</option>
        <option value="YYZ">토론토 </option>
      </select>
    </div>
  )
}

export default WhereFooter