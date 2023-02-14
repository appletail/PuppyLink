import { useEffect, useState } from 'react'

import FooterController from './FooterController'

import { axBase } from '@/apis/api/axiosInstance'
import { cards } from '@/components'


function UserSortedVol({ status, volCount }: { status: status, volCount: (count: number) => void }) {

  const [volunteers, setVolunteers] = useState([])
  const [modal, setModal] = useState<boolean[]>([])

  const userData = sessionStorage.getItem("userData") || ""
  const { nickName } = JSON.parse(userData)
  const usernickName = nickName

  useEffect(() => {
    usernickName &&
      axBase({
        url: `/volunteer/member/${usernickName}/${status}`,
      })
        .then((res) => {
          setVolunteers(res.data.data)
          volCount(res.data.data.length)
        })
  }, [status, usernickName, volCount])

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
      `봉사단체: ${volunteer.businessNo.businessName}`,
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
            CardFooter={FooterController({ status, volNo: volunteer.volunteerNo, removeCard })}
            CardTitle={volunteer.email.name}
          />
        </div>
      </div >
    )

  })

  return (
    <div className='overflow-y-scroll flex flex-col items-center'>
      {volunteerCards}
    </div>
  )
}

export default UserSortedVol
