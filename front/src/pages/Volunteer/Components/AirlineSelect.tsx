import { Control, FieldName, FieldValues, RegisterOptions, useController } from "react-hook-form"

import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'


export type UseControllerProps<
  TFieldValues extends FieldValues = FieldValues
> = {
  name: FieldName<TFieldValues>;
  rules?: Exclude<RegisterOptions, 'valueAsNumber' | 'valueAsDate' | 'setValueAs'>;
  onFocus?: () => void;
  defaultValue?: unknown;
  control?: Control<TFieldValues>;
};

function VolUserResiAirline({ name, control, rules }: UseControllerProps): JSX.Element {

  const { field: { value, onChange } } = useController({ name, control })

  const airlines = [
    { airline: Korair, value: 'Korair', isTrue: false },
    { airline: Asiana, value: 'Asiana', isTrue: false },
    { airline: AirCanada, value: 'AirCanada', isTrue: false },
  ]

  const airlineBtn = airlines.map((item) => {
    return (
      <div key={item.value}>
        <input className='hidden' id={item.value} name="airlines" type="radio" value={item.value} onChange={onChange} />
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

export default VolUserResiAirline