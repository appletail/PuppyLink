import { useEffect, useState } from 'react'

import Modal from './ModalVolunteer'
import SortedFooter from './SortedFooter'

import { axBase } from '@/apis/api/axiosInstance'
import { cards } from '@/components'


function SortedVol({ status }: { status: status }) {

  const [volunteers, setVolunteers] = useState([])
  const [modal, setModal] = useState<boolean[]>([])

  useEffect(() => {
    axBase({
      url: '/volunteer/select/foundation/status',
      params: {
        businessNo: '1148209801',
        status: status
      }
    })
      .then((res) => setVolunteers(res.data))
  }, [status])

  useEffect(() => {
    setModal(Array(volunteers.length).fill(false))
  }, [volunteers])


  const volunteerCards = volunteers.map((volunteer: volunteer, idx: number) => {

    const cardBody = [
      `신청일: ${volunteer.regDate}`,
      `출국일: ${volunteer.depTime}`,
      `도착지: ${volunteer.dest} | ${volunteer.flightName}`,
    ]

    return (
      <div key={volunteer.volunteerNo} className='mb-3' >
        <div aria-hidden="true"
          onClick={() => setModal(modal.reduce((acc: boolean[], cur, modalIdx) => {
            modalIdx === idx ? acc.push(true) : acc.push(false)
            return acc
          }, []))}>
          <cards.CardLg
            CardContents={cardBody}
            CardFooter={SortedFooter(status)}
            CardTitle={volunteer.email.name}
          />
        </div>
        {
          modal[idx] &&
          <Modal volunteer={volunteer}
            closeModal={() => setModal(modal.reduce((acc: boolean[]) => {
              acc.push(false)
              return acc
            }, []))} />
        }
      </div >
    )

  })

  return (
    <div className='h-[40vh] overflow-y-scroll'>
      {volunteerCards}
    </div>
  )
}

export default SortedVol
