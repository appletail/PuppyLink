import { useController } from "react-hook-form"

import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'
import { ReactComponent as React } from '@/assets/react.svg'

function FoundationSelect({ name, control, rules }: UseControllerProps): JSX.Element {

  const { field: { value, onChange } } = useController({ name, control, rules })

  const foundations = [
    { foundation: Korair, value: '1612314155' },
    { foundation: Asiana, value: '1562345234523' },
    { foundation: AirCanada, value: '1344523422' },
    { foundation: React, value: '14324542345' },
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