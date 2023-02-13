import { SubmitHandler, useForm } from 'react-hook-form'

import { ErrorMessage } from '@hookform/error-message'

import { axBase } from '@/apis/api/axiosInstance'
import { NavTop, inputs } from '@/components'

interface InfoProps {
  businessNo: string;
  description: string;
}




function FoundationInfoPage() {

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<InfoProps>({});

  return (
    <div>
      <div>
        <NavTop.NavBack NavContent='단체소개 변경' />
      </div>
      <div className="mt-14">
        <p className='text-title1-bold'> 단체 소개 변경</p>
        {/* 단체 이미지가 들어갈 곳입니다 */}
        <div></div>

        {/* form이 들어갈 부분입니다. */}
        <form>
          <inputs.InputForm
            control={control}
            name="businessNo"
            placeholder="단체 이름"
            type="text"
            rules={{
              required: { value: true, message: '단체 이름을 입력해주세요' },
            }}
          />


        </form>
      </div>
    </div>
  )
}

export default FoundationInfoPage