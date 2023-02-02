import { useController } from "react-hook-form"

import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'
import { ReactComponent as React } from '@/assets/react.svg'

function FoundationSelect({ name, control, rules }: UseControllerProps): JSX.Element {

  const { field: { value, onChange } } = useController({ name, control })

  const foundations = [
    { foundation: Korair, value: 'Kara', isTrue: false },
    { foundation: Asiana, value: 'WeAct', isTrue: false },
    { foundation: AirCanada, value: 'WelComeDog', isTrue: false },
    { foundation: React, value: 'Care', isTrue: false },
  ]

  const isChecked = function (curValue: string): boolean {
    return curValue === value ? true : false
  }

  const foundationsBtn = foundations.map((item) => {
    return (
      <div key={item.value}>
        <input className='hidden' id={item.value} name="foundation" type="radio" value={item.value} onChange={onChange} />
        <label htmlFor={item.value}>
          <item.foundation
            className={isChecked(item.value) ? 'bg-white rounded-full brightness-[.60]' : ''}
          />
        </label>
      </div>
    )
  })
  return (
    <>
      {foundationsBtn}
    </>
  )
}

export default FoundationSelect