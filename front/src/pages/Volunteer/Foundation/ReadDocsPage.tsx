import { useState, useEffect } from 'react'

import { useLocation, useNavigate } from 'react-router-dom'

import { axAuth } from '@/apis/api/axiosInstance'
import { NavTop, buttons } from '@/components'


interface ReadDocsProps {
  0: string
  1: status
  2: ocrData
}


function ReadDocsPage() {

  const location = useLocation()
  const [passport, setPassport] = useState()
  const [docs, setDocs] = useState<ReadDocsProps>()
  const navigate = useNavigate()

  const volunteerNo = location.state.volunteerNo

  useEffect(() => {
    axAuth({
      url: '/volunteer/foundation/docs',
      params: { volunteerNo }
    })
      .then((res) => { setPassport(res.data.data) })

    axAuth({
      url: '/volunteer/foundation/flightDocs',
      params: { volunteerNo }
    })
      .then((res) => {
        setDocs(res.data.data)
      })
  }, [volunteerNo])


  const confirm = () => {
    axAuth({
      method: 'put',
      url: `/volunteer/confirm/${volunteerNo}`,
    })
      .then(() => {
        alert('서류가 승인되었습니다.')
        navigate('/volunteer/manager')
      })
  }
  const lack = () => {
    axAuth({
      method: 'put',
      url: `/volunteer/lack/${volunteerNo}`,
    })
      .then(() => {
        alert('서류가 반려되었습니다.')
        navigate('/volunteer/manager')
      })
  }


  const imgTab = (url: string) => window.open(url, "_blank", "noopener, noreferrer");

  return (
    <div>
      <NavTop.NavBack NavContent='여권 및 E-Ticket' />
      <div>
        <p className='text-title2-bold mt-5 mb-3'>봉사자 서류 확인</p>
        <div className='bg-grey w-[21.875rem] h-64 mb-9'>
          {passport && <img alt="passport" aria-hidden='true' className='object-cover w-full h-full' src={passport} onClick={() => imgTab(passport)} />}
          <p className='text-subheadline text-grey'>※ 사진을 누르시면 크게 볼 수 있습니다.</p>
        </div>
      </div>
      <div className='flex justify-between'>
        <p className='text-title2-bold mb-8'>봉사자 정보</p>
        {docs && docs[1] === 'docs' &&
          <div className='flex justify-end gap-3'>
            <buttons.BtnSm BtnValue={'반려'} onClick={lack} />
            <buttons.BtnSm BtnValue={'승인'} onClick={confirm} />
          </div>
        }

      </div>
      {docs &&
        <div className='flex flex-col gap-2 mb-1'>
          <p className='text-title3-bold mb-2'>{docs[0]}</p>

          <p>영문이름: <b className='text-body-bold'>{docs[2].passengerName}</b></p>
          <p>항공권 번호: <b className='text-body-bold'>{docs[2].ticketNo}</b></p>
          <p>예약번호: <b className='text-body-bold'>{docs[2].bookingReference}</b></p>
          <p>출발지: <b className='text-body-bold'>{docs[2].depCity}</b></p>
          <p>출발일정: <b className='text-body-bold'>{docs[2].depDate}</b></p>
          <p>도착지: <b className='text-body-bold'>{docs[2].arriveCity}</b></p>
          <p>도착일정: <b className='text-body-bold'>{docs[2].arriveDate}</b></p>
          <p>편명: <b className='text-body-bold'>{docs[2].flight}</b></p>
        </div >
      }
    </div >
  )
}

export default ReadDocsPage