import React, { useEffect, useState } from 'react'

import FooterController from './FooterController'
import Modal from './ModalVolunteer'

import { axBase } from '@/apis/api/axiosInstance'
import { cards } from '@/components'

function NewVolCarousel() {

  const [volunteers, setVolunteers] = useState([])
  const [modal, setModal] = useState<boolean[]>([])

  useEffect(() => {
    axBase({
      url: `/volunteer/foundation/${'2308201019'}/submit`,
    })
      .then((res) => setVolunteers(res.data.data))
  }, [])

  useEffect(() => {
    setModal(Array(volunteers.length).fill(false))
  }, [volunteers])

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
            CardFooter={FooterController('submit')}
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
    <div className='flex gap-4 overflow-y-scroll'>
      {volunteerCards}
    </div>
  )
}

export default NewVolCarousel