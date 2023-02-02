import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'
import { ReactComponent as React } from '@/assets/react.svg'

function VolUserResiAirport(): JSX.Element {
  const airports = [
    { airport: Korair, key: 'KorairLogo', isTrue: false },
    { airport: Asiana, key: 'AsianaLogo', isTrue: false },
    { airport: AirCanada, key: 'AirCanadaLogo', isTrue: false },
    { airport: React, key: 'ReactLogo', isTrue: false },
  ]
  const selectAit = function () {
    const asdf = 123
  }
  const airportBtn = airports.map((item) => {
    return (
      <item.airport key={item.key}
        className={`rounded-full `}
      />
    )
  })
  return (
    <>
      {airportBtn}
    </>
  )
}

export default VolUserResiAirport