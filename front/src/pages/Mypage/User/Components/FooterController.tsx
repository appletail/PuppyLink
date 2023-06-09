import React from 'react'

import { Link } from 'react-router-dom'

import { footers, buttons, labels } from '@/components'


function FooterController({ status, volNo, removeCard }: { status: status, volNo: number, removeCard: (volunteerNo: number) => void }) {



  const Controller = {
    'submit': <footers.FooterBtn
      BtnRight={<labels.Label LabelValue='접수 대기' />}
      onClick={(e) => e.stopPropagation()} />,
    'regist': <footers.FooterBtn
      BtnLeft={<Link state={{ volNo }} to='/mypage/user/userfiledocs'><buttons.BtnSm BtnValue='서류 제출' /></Link>}
      BtnRight={<labels.Label LabelValue='접수 완료' />}
      onClick={(e) => e.stopPropagation()} />,
    'lack': <footers.FooterBtn
      BtnLeft={<Link state={{ volNo }} to='/mypage/user/userfiledocs'><buttons.BtnSm BtnValue='서류 제출' /></Link>}
      BtnRight={<labels.Label LabelValue='서류 미흡' />}
      onClick={(e) => e.stopPropagation()} />,
    'docs': <footers.FooterBtn
      BtnRight={<labels.Label LabelValue='서류 승인 대기' />}
      onClick={(e) => e.stopPropagation()} />,
    'confirm': <footers.FooterBtn
      BtnRight={<labels.Label LabelValue='봉사 진행 중' />}
      onClick={(e) => e.stopPropagation()} />,
    'complete': <footers.FooterBtn
      BtnLeft={<Link to='/review/create'><buttons.BtnSm BtnValue='리뷰 쓰기' /></Link>}
      BtnRight={<labels.Label LabelValue='봉사 완료' />}
      onClick={(e) => e.stopPropagation()} />,
    'refuse': <footers.FooterBtn
      BtnRight={<labels.Label LabelValue='접수 거절' />}
      onClick={(e) => e.stopPropagation()} />,
  }
  const footer = Controller[status]

  return (
    <>
      {footer}
    </>
  )
}

export default FooterController