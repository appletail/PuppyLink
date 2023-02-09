import React from 'react'

import { footers, buttons, labels } from '@/components'


function SortedFooter(status: status) {

  const footerController = {
    'submit': <footers.FooterBtn
      BtnLeft={<buttons.BtnSm BtnValue='수락' />}
      BtnRight={<buttons.BtnSm BtnValue='거절' />}
      onClick={(e) => e.stopPropagation()} />,
    'regist': <footers.FooterBtn
      BtnLeft={<labels.Label LabelValue='접수 완료' />}
      BtnRight={<labels.Label LabelValue='서류 대기' />}
      onClick={(e) => e.stopPropagation()} />,
    'refuse': <footers.FooterBtn
      BtnLeft={<labels.Label LabelValue='접수 완료' />}
      BtnRight={<labels.Label LabelValue='서류 대기' />}
      onClick={(e) => e.stopPropagation()} />,
    'docs': <footers.FooterBtn
      BtnLeft={<buttons.BtnSm BtnValue='승인' />}
      BtnRight={<buttons.BtnSm BtnValue='반려' />}
      onClick={(e) => e.stopPropagation()} />,
    'lack': <footers.FooterBtn
      BtnLeft={<labels.Label LabelValue='서류 대기' />}
      BtnRight={<buttons.BtnSm BtnValue='서류 확인' />}
      onClick={(e) => e.stopPropagation()} />,
    'confirm': <footers.FooterBtn
      BtnLeft={<labels.Label LabelValue='봉사 진행 중' />}
      BtnRight={<buttons.BtnSm BtnValue='비행기 경로 확인' />}
      onClick={(e) => e.stopPropagation()} />,
    'complete': <footers.FooterBtn
      BtnLeft={<labels.Label LabelValue='봉사 완료' />}
      BtnRight={<buttons.BtnSm BtnValue='리뷰 쓰기' />}
      onClick={(e) => e.stopPropagation()} />,
  }
  const footer = footerController[status]

  return footer
}

export default SortedFooter