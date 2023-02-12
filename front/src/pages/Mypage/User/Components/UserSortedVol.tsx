import { useEffect, useState } from 'react'

import { useRecoilValue } from 'recoil'

import FooterController from './FooterController'

import { axBase } from '@/apis/api/axiosInstance'
import { cards } from '@/components'
import { LoginState } from '@/states/LoginState'

function UserSortedVol({ status, volCount }: { status: status, volCount: (count: number) => void }) {

  const [volunteers, setVolunteers] = useState([])
  const [modal, setModal] = useState<boolean[]>([])


  let usernickName = ""
  // recoil에서 로그인 여부를 판단한다.
  const isLoggedIn = useRecoilValue(LoginState)
  if (isLoggedIn) {
    // 로그인 되어있다면 userData를 가져온다
    const userData = sessionStorage.getItem("userData") || ""
    const { nickName } = JSON.parse(userData)
    usernickName = nickName
  }

  useEffect(() => {
    axBase({
      url: `/volunteer/member/${usernickName}/${status}`,
    })
      .then((res) => {
        setVolunteers(res.data.data)
        volCount(res.data.data.length)
      })
  }, [status])

  useEffect(() => {
    setModal(Array(volunteers.length).fill(false))
  }, [volunteers])


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
            CardFooter={FooterController({ status, volNo: volunteer.volunteerNo })}
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
