import { useController } from "react-hook-form"

import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'
import { ReactComponent as React } from '@/assets/react.svg'

function FoundationSelect({ name, control, rules }: UseControllerProps): JSX.Element {

  const { field: { onChange } } = useController({ name, control })

  const foundations = [
    { foundation: Korair, key: 'KorairLogo', isTrue: false },
    { foundation: Asiana, key: 'AsianaLogo', isTrue: false },
    { foundation: AirCanada, key: 'AirCanadaLogo', isTrue: false },
    { foundation: React, key: 'ReactLogo', isTrue: false },
  ]

  const foundationsBtn = foundations.map((item) => {
    return (
      <div key={item.key}>
        <input key={item.key} className='hidden' id={item.key} type="radio" onChange={onChange} />
        <label htmlFor={item.key}>
          <item.foundation
            className={`rounded-full `}
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