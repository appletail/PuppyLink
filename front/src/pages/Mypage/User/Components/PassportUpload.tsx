import { useState, useEffect, } from 'react'
import { useController, useForm } from 'react-hook-form';
import { BsFillPlusCircleFill } from "react-icons/bs";


function PassportUpload({ name, rules, control }: UCProps) {

  const {
    field: { value, onChange },
  } = useController({ name, rules, control });

  const { watch, } = useForm();
  const [imagePreview, setImagePreview] = useState("");
  const image = watch(name);
  useEffect(() => {
    if (image && image.length > 0) {
      const file = image[0];
      console.log(image)
      setImagePreview(URL.createObjectURL(file));
    }
  }, [image]);


  return (
    <>
      <input className='hidden' id="picture" type="file" value={value || ''} onChange={onChange} />
      <label htmlFor="picture">
        <div className='w-36 h-36 bg-grey flex justify-center items-center'>
          <BsFillPlusCircleFill className='fill-white w-11 h-11' />
        </div>
      </label>
      <div className='w-36 h-36'>
        <img alt='passport' src={imagePreview} />
      </div>
    </>
  )
}

export default PassportUpload