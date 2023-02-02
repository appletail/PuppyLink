import { FaPlaneDeparture, FaRegCalendar } from 'react-icons/fa'

export function WhereTitle() {
  return (
    <div className="flex">
      <FaPlaneDeparture className="fill-main-100" />
      <p className="ml-2 leading-none">어디로 가세요?</p>
    </div>
  )
}

export function WhenTitle() {
  return (
    <div className="flex">
      <FaRegCalendar className="fill-main-100" />
      <p className="ml-2 leading-none">언제 출국하세요?</p>
    </div>
  )
}
