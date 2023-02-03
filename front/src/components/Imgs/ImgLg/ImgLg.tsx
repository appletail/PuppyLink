import LgStyle from './ImgLg.module.css'

function ImgLg({ src, alt = '큰 사진' }: imgProps): JSX.Element {

  return (
    <img alt={alt} className={LgStyle.ImgLg} src={src} />
  )
}

export default ImgLg