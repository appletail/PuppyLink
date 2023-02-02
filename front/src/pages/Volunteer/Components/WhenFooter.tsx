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


function WhenFooter({ name, control, rules }: UseControllerProps): JSX.Element {

  const { field: { value, onChange } } = useController({ name, control })

  const addZero = function (num: number): string {
    return num < 10 ? '0' + num : num.toString()
  }

  const dateFormat = function () {
    const today = new Date()
    const year = today.getFullYear()
    const month = today.getMonth()
    const day = today.getDate()

    return `${year}-${addZero(month + 1)}-${addZero(day)}`
  }

  const date = dateFormat()

  return (
    <div className="flex flex-col items-center">
      <hr className="border-none h-[0.5px] w-full bg-grey mb-2" />
      <input
        className="text-caption1"
        min={date}
        name="trip-start"
        type="date"
        value={value || ''}
        onChange={onChange} />
    </div>
  )
}

export default WhenFooter