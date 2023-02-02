import { useController } from "react-hook-form"

import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'

function FilghtName({ name, control, rules }: UseControllerProps): JSX.Element {

  const { field: { onChange } } = useController({ name, control })

  const airlines = [
    { airline: Korair, value: 'Korair', isTrue: false },
    { airline: Asiana, value: 'Asiana', isTrue: false },
    { airline: AirCanada, value: 'AirCanada', isTrue: false },
  ]

  const airlineBtn = airlines.map((item) => {
    return (
      <div key={item.value}>
        <input className='hidden' id={item.value} type="radio" value={item.value} onChange={onChange} />
        <label htmlFor={item.value}>
          <item.airline
            className={`rounded-full `}
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

export default FilghtName