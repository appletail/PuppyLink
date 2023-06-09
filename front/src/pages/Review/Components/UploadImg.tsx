import { useState, useEffect } from 'react';
import { useController } from 'react-hook-form';
import { BsFillPlusCircleFill } from "react-icons/bs";

import { ChannelTalk } from '@/components'

interface ImageProps extends UCProps {
  setImageData: (img: string | FileList) => void
}

function UploadImg({ name, rules, control, setImageData }: ImageProps) {

  const [value, setValue] = useState("");
  const [imgPreview, setImgPreview] = useState<any>()
  const [image, setImage] = useState<any>()
  const { field: { onChange } } = useController({ name, rules, control });
  ChannelTalk.hideChannelButton()
  const onChangeFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
    onChange(e.target.files);
    setImage(e.target.files)
  }

  useEffect(() => {
    if (image && image.length > 0) {
      const file = image[0];
      setImgPreview(URL.createObjectURL(file));
      setImageData(image)
    }
  }, [image]);

  return (
    <div className="flex gap-4">
      <input accept="image/*" className='hidden' id="picture" type="file" value={value} onChange={onChangeFile} />
      <label htmlFor="picture">
        <div className='w-36 h-36 bg-grey flex justify-center items-center'>
          <BsFillPlusCircleFill className='fill-white w-11 h-11' />
        </div>
      </label>
      <div className='w-36 h-36 bg-grey'>
        {imgPreview && <img alt='passport' className='object-cover w-full h-full' src={imgPreview} />}
      </div>
    </div>
  )
}

export default UploadImg