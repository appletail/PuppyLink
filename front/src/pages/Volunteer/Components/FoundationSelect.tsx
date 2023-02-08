import { useState, useEffect } from 'react'
import { useController } from 'react-hook-form'

import { ModalCard } from '@/components'


interface foundation {
  businessNo: string
  businessName: string
  presidentName: string
  startDate: Date
  description: string
  image?: string
}
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
      <div className="flex justify-between items-center text-title3-bold">
        <p>{item?.businessName}</p>
        <img alt="foundation" src={`https://puppylink-test.s3.ap-northeast-2.amazonaws.com/foundation-profile/${item?.businessName}`} />
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
          <img alt="foundation" className={isChecked(item?.businessNo) ? 'bg-white rounded-full brightness-[.60]' : ''} src={`https://puppylink-test.s3.ap-northeast-2.amazonaws.com/foundation-profile/${item?.businessName}`} />

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
