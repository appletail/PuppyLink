import React from 'react'

import style from '@/components/Modal/Modal.module.css'


interface ModalProps {
  volunteer: volunteer
  closeModal: () => void
}

function ModalVolunteer({ volunteer, closeModal }: ModalProps) {

  return (
    <div aria-hidden="true" className={style.Wrapper}
      onClick={closeModal}>
      <div
        aria-hidden="true"
        className={style.Body}
      >
        <div className='bg-white w-[21.875rem] rounded-lg p-4 drop-shadow-lg'>
          <p className='text-title1-bold mb-2'>{volunteer.email.name}</p>
          <hr className='bg-grey h-[1px] border-none mb-3' />
          <ul className='text-body'>
            <li>{`신청일: ${volunteer.regDate}`}</li>
            <li>{`출국일: ${volunteer.depTime}`}</li>
            <li>{`도착지: ${volunteer.dest}`}</li>
            <li>{`항공편: ${volunteer.flightName}`}</li>
            <li>{`이메일: ${volunteer.email.email}`}</li>
            <li>{`연락처: ${volunteer.email.phone}`}</li>

          </ul>
        </div>
      </div>
    </div>
  )
}

export default ModalVolunteer