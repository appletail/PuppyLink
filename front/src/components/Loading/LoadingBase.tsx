import React from 'react'

import style from './LoadingBase.module.css'
import PawBlue from '@/assets/PawBlue.png'


function LoadingBase() {
  return (
    <div className={style.Wrapper}>
      <img alt="top"
        className={`${style.Paw} ${style.TopPaw}`}
        src={PawBlue} />
      <img alt="middle"
        className={`${style.Paw} ${style.MiddlePaw}`}
        src={PawBlue} />
      <img alt="low"
        className={`${style.Paw} ${style.LowPaw}`}
        src={PawBlue} />
    </div>
  )
}

export default LoadingBase