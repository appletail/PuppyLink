import React, { useEffect, useState } from 'react'

import FooterController from './FooterController'
import Modal from './ModalVolunteer'

import { axBase } from '@/apis/api/axiosInstance'
import { cards } from '@/components'

function NewVolCarousel({ businessNo }: { businessNo: string }) {

  const [volunteers, setVolunteers] = useState([])
  const [modal, setModal] = useState<boolean[]>([])

  useEffect(() => {
    businessNo &&
      axBase({
        url: `/volunteer/foundation/${businessNo}/submit`,
      })
        .then((res) => setVolunteers(res.data.data))
  }, [businessNo])

  useEffect(() => {
    setModal(Array(volunteers.length).fill(false))
  }, [volunteers])

  const removeCard = (volunteerNo: number) => {
    setVolunteers(volunteers.filter((volunteer: Volunteer) => volunteer.volunteerNo != volunteerNo))
  }

  const volunteerCards = volunteers.map((volunteer: Volunteer, idx: number) => {

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
            CardFooter={FooterController('submit', volunteer.volunteerNo, removeCard)}
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
    <div className='flex gap-4 overflow-y-scroll h-48'>
      {volunteers.length == 0 && <p className='text-title3-bold'>새로운 요청이 없습니다.</p>}
      {volunteerCards}
    </div>
  )
}

export default NewVolCarousel