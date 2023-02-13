import {
  Control,
  FieldValues,
  RegisterOptions,
} from 'react-hook-form'

import { inputs } from '@/components'


type EticketProps<T extends FieldValues> = {
  control: Control<T>;
  rules?: Exclude<
    RegisterOptions,
    'valueAsNumber' | 'valueAsDate' | 'setValueAs'
  >;
};

function EticketUpload({ control, rules }: EticketProps<any>) {

  return (
    <>
      <inputs.InputForm control={control} name='passengerName' placeholder='성명(Passenger Name)' rules={rules} />
      <inputs.InputForm control={control} name='ticketNo' placeholder='항공권 번호(Ticket Number)' rules={rules} />
      <inputs.InputForm control={control} name='bookingReference' placeholder='예약번호(Booking Reference)' rules={rules} />
      <inputs.InputForm control={control} name='depCity' placeholder='출발지(From)' rules={rules} />
      <inputs.InputForm control={control} name='depDate' placeholder='출발일정(Start Date)' rules={rules} />
      <inputs.InputForm control={control} name='arriveCity' placeholder='도착지(To)' rules={rules} />
      <inputs.InputForm control={control} name='arriveDate' placeholder='도착일정(Arrival Date)' rules={rules} />
      <inputs.InputForm control={control} name='flight' placeholder='편명(Flight)' rules={rules} />
    </>
  )
}

export default EticketUpload