import { useState, useEffect } from 'react'
import { useController } from 'react-hook-form'

import { ModalCard } from '@/components'


interface FoundationProps extends UCProps {
  foundations: foundation[]
}

function FoundationSelect({ name, control, rules, foundations }: FoundationProps): JSX.Element {

  const { field: { value, onChange } } = useController({ name, control, rules })

  const isChecked = function (curValue: string): boolean {
    return curValue === value ? true : false;
  };


  const [modalOn, setModal] = useState<boolean[]>([])
  useEffect(() => {
    setModal(Array(foundations.length).fill(false))
  }, [foundations])

  const foundationsBtn = foundations.map((item, idx) => {
    const title = (
      <div className="flex justify-between items-center text-title2-bold">
        <p>{item?.businessName}</p>
        <img alt="foundation" className='w-14 h-14 rounded-full' src={item.profileURL} />
      </div>
    )

    return (
      <div key={item?.businessNo}>
        <input className='hidden' id={item?.businessNo} name="foundation" type="radio" value={item?.businessNo} onChange={onChange}
          onClick={() =>
            setModal(modalOn.reduce((acc: boolean[], cur, modalIdx) => {
              modalIdx === idx ? acc.push(true) : acc.push(false)
              return acc
            }, []))
          } />
        <label htmlFor={item?.businessNo}>
          <img alt="foundation" className={`w-14 h-14 rounded-full ${isChecked(item?.businessNo) ? 'bg-white rounded-full brightness-[.60]' : ''}`} src={item.profileURL} />

        </label>
        {modalOn[idx]
          &&
          <ModalCard
            CardContents={[item?.description]}
            CardTitle={title}
            closeModal={() =>
              setModal(modalOn.reduce((acc: boolean[]) => {
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

export default FoundationSelect;
