import { useState } from 'react'
import { useController } from "react-hook-form"

import { ReactComponent as AirCanada } from '@/assets/AirCanada.svg'
import { ReactComponent as Asiana } from '@/assets/Asiana.svg'
import { ReactComponent as Korair } from '@/assets/Korair.svg'
import { ReactComponent as React } from '@/assets/react.svg'

import { Modal } from '@/components'

function FoundationSelect({ name, control, rules }: UseControllerProps): JSX.Element {

  const { field: { value, onChange } } = useController({ name, control, rules })

  const foundations = [
    { image: Korair, value: '1612314155', name: '동물권행동 카라', description: ['동물이 인간의 일방적인 착취와 이용에서 벗어나 존엄한 생명으로서 그들 본연의 삶을 영위하고, 모든 생명이 균형과 조화 속에 공존하는 세상을 지향한다.'] },
    { image: Asiana, value: '1562345234523', name: '사단법인 위액트', description: ['동물이 인간의 일방적인 착취와 이용에서 벗어나 존엄한 생명으로서 그들 본연의 삶을 영위하고, 모든 생명이 균형과 조화 속에 공존하는 세상을 지향한다.'] },
    { image: AirCanada, value: '1344523422', name: '웰컴독 코리아', description: ['동물이 인간의 일방적인 착취와 이용에서 벗어나 존엄한 생명으로서 그들 본연의 삶을 영위하고, 모든 생명이 균형과 조화 속에 공존하는 세상을 지향한다.'] },
    { image: React, value: '14324542345', name: '동물권단체 케어', description: ['동물이 인간의 일방적인 착취와 이용에서 벗어나 존엄한 생명으로서 그들 본연의 삶을 영위하고, 모든 생명이 균형과 조화 속에 공존하는 세상을 지향한다.'] },
  ]

  const isChecked = function (curValue: string): boolean {
    return curValue === value ? true : false
  }

  const [modalOn, setModal] = useState(Array(foundations.length).fill(false))

  const foundationsBtn = foundations.map((item, idx) => {

    const title = (
      <div className="flex justify-between items-center text-title3-bold">
        <p>{item.name}</p>
        <item.image />
      </div>
    )

    return (
      <div key={item.value}>
        <input className='hidden' id={item.value} name="foundation" type="radio" value={item.value} onChange={onChange}
          onClick={() =>
            setModal(modalOn.reduce((acc, cur, modalIdx) => {
              modalIdx === idx ? acc.push(true) : acc.push(false)
              return acc
            }, []))
          } />
        <label htmlFor={item.value}>
          <item.image
            className={isChecked(item.value) ? 'bg-white rounded-full brightness-[.60]' : ''}
          />
        </label>
        {modalOn[idx]
          &&
          <Modal
            CardContents={item.description}
            CardTitle={title}
            closeModal={() =>
              setModal(modalOn.reduce((acc) => {
                acc.push(false)
                return acc
              }, []))}
          />}
      </div>
    )
  })
  return (
    <>
      {foundationsBtn}
    </>
  )
}

export default FoundationSelect