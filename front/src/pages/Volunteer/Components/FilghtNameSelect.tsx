import { useController } from "react-hook-form"

import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'


function FilghtNameSelect({ name, control, rules }: UseControllerProps): JSX.Element {

  const { field: { value, onChange } } = useController({ name, control, rules })

  const airlines = [
    { airline: Korair, value: 'Korair' },
    { airline: Asiana, value: 'Asiana' },
    { airline: AirCanada, value: 'AirCanada' },
  ]

  const isChecked = function (curValue: string): boolean {
    return curValue === value ? true : false
  }

  const airlineBtn = airlines.map((item) => {
    return (
      <div key={item.value}>
        <input className='hidden' id={item.value} name="filghtName" type="radio" value={item.value} onChange={onChange} />
        <label htmlFor={item.value}>
          <item.airline
            className={isChecked(item.value) ? 'bg-white rounded-full brightness-[.60]' : ''}
          />
        </label>
      </div>
    )
  })
  return (
    <>
      {airlineBtn}
    </>
  )
}

export default FilghtNameSelect