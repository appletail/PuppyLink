import React, { useState, useEffect } from 'react'

import { AiFillEye } from 'react-icons/ai'

import { Link } from 'react-router-dom'

import { axBase } from '@/apis/api/axiosInstance'
import { cards } from '@/components'


function ReviewLst() {

  // const [reviews, setReviews] = useState([])

  // useEffect(() => {
  //   axBase({
  //     url: '어딘가엔가'
  //   })
  //     .then((res) => setReviews(res.data))
  //     .catch((err) => console.log(err.response.data))
  // }, [])

  const reviews = [
    {
      author: 'harim_chung',
      view: '317',
      reviewNo: 'review_01',
      title: '우리 뽀삐를 보세요.',
      content: '빠삐링끄를 통해서 이동봉사를 처음 한 정하림입니다. 빠삐링끄 쓰기도 편리하고 너무 UI도 예뻐요. 프론트엔드 디자이너가 누구인지 너무너무 최고네요. 최고에요 진짜 짱입니다.',
      imgSrc: 'https://cdn.pixabay.com/photo/2021/10/13/09/01/corgi-6705821_960_720.jpg'
    },
    {
      author: 'harim_chung',
      view: '745',
      reviewNo: 'review_02',
      title: '프론트 깎는 장인 심빵',
      content: '빵 설에 잘 쉬고 강아지처럼 가방에 쏙 들어가면 좋으련만... 힘드시겠네요. 울산에 내려가셔서 푹 쉬고 오시고 설에는 디스코드에서 모각코 합시다. 화이팅.',
      imgSrc: 'https://cdn.pixabay.com/photo/2022/08/29/16/20/dog-7419243_960_720.jpg'
    },
    {
      author: 'harim_chung',
      view: '348',
      reviewNo: 'review_03',
      title: '비행기 타고 가요~',
      content: '파란 하늘 위로 훨훨 날아 가겠죠~ 어렸을 적 꿈꾸었던 비행기 타고 기다리는 동안 아무말도 못해요. 내 생각 말할 수 없어요. 비행기를 타고 가던 너~ 따라 가고 싶어 울었던~',
      imgSrc: 'https://cdn.pixabay.com/photo/2018/05/13/16/57/dog-3397110_960_720.jpg'
    }
  ]

  const reviewLst = reviews.map((review) => {

    const reviewFooter = (
      <div>
        <hr className='bg-grey h-[1px] border-none mb-2' />
        <div className='flex justify-between'>
          <p className='text-caption1 text-grey'>@{review.author}</p>
          <div className='flex gap-1'>
            <AiFillEye className='fill-grey' />
            <p className='text-caption2 text-grey leading-4'>{review.view}</p>
          </div>
        </div>

      </div>
    )

    return (
      <Link key={review.reviewNo} className='mb-4 flex flex-col items-center' to='/error'>
        <cards.CardXL
          CardContents={[review.content]}
          CardFooter={reviewFooter}
          CardTitle={review.title}
          CardImg={
            {
              src: review.imgSrc,
              alt: '멍뭉이'
            }}
        />
      </Link>
    )
  })


  return (
    <>
      {reviewLst}
    </>
  )
}

export default ReviewLst