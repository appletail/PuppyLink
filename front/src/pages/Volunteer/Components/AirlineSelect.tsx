import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'

function VolUserResiAirline(): JSX.Element {
  const airlines = [
    { airline: Korair, key: 'KorairLogo', isTrue: false },
    { airline: Asiana, key: 'AsianaLogo', isTrue: false },
    { airline: AirCanada, key: 'AirCanadaLogo', isTrue: false },
  ]
  const selectAit = function () {
    const asdf = 123
  }
  const airlineBtn = airlines.map((item) => {
    return (
      <item.airline key={item.key}
        className={`rounded-full `}
      />
    )
  })
  return (
    <>
      {airlineBtn}
    </>
  )
}

export default VolUserResiAirline