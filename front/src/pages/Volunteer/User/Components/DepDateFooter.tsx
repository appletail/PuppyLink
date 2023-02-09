import { useController } from "react-hook-form"


function DepDateFooter({ name, control, rules }: UCProps): JSX.Element {

  const { field: { value, onChange } } = useController({ name, control, rules })

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

export default DepDateFooter