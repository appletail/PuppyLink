import React, { useEffect, useState } from 'react'

import { Link } from 'react-router-dom';

import { axBase } from '@/apis/api/axiosInstance';
import { cards } from '@/components'


interface News {
  content: string
  newsURL: string
  picURL: string
  subject: string
}


function CrawlingNews() {

  const [news, setNews] = useState<News[]>()

  useEffect(() => {
    axBase({
      url: '/news/list'
    })
      .then((res) => {
        setNews(res.data)
      })
  }, [])

  const imgTab = (url: string) => window.open(url, "_blank", "noopener, noreferrer");

  const newsCards = news?.map((card, idx) => {

    return (
      <div key={`${idx}-${new Date().getTime()}`} className='mb-4'>
        <cards.CardXL
          CardFooter={<p aria-hidden='true' className='flex justify-end' onClick={() => imgTab(card.newsURL)}>더보기</p>}
          CardImg={{ src: card.picURL }}
          CardTitle={card.subject}
        />
      </div>
    )
  })

  return (
    <div className='flex justify-center mb-6'>
      <div className='flex gap-5 overflow-x-scroll w-[18.875rem]'>
        {newsCards}
      </div>
    </div>
  )
}

export default CrawlingNews