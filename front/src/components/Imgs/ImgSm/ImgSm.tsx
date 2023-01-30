import SmStyle from './ImgSm.module.css'

function ImgSm({ src, alt = '작은 사진' }: imgProps): JSX.Element {

  return (
    <img alt={alt} className={SmStyle.ImgSm} src={src} />
  )
}

export default ImgSm