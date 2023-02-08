 import { useForm, SubmitHandler } from 'react-hook-form';

import { ErrorMessage } from '@hookform/error-message';

import { inputs, buttons } from '@/components';

interface BusinessProps {
 businessNo : number;
}
interface ValidateProps extends BusinessProps{
 presidentName : string;
 startDate : Date;
}

function BusinessValidate({businessNo}:BusinessProps):JSX.Element {
  const {
    control,
    formState: { errors },
    getValues,
    setError,
    watch
  } = useForm<ValidateProps>({defaultValues:{
    businessNo : businessNo
  }});

  const BusinessNoValidate = () => {
    console.log("따로 움직일 수 있을까?")
  }
  // 사업자 인증 form
  return (
    <div className="flex flex-col gap-3">
     
      {/* 대표자 성명 */}
      <inputs.InputForm 
      control={control} 
      name="presidentName"
      placeholder='대표자성명'
      type="text"
      rules={{
        required : {value: true, message : "대표자 성명을 입력해주세요"}
      }}
      />
       <ErrorMessage errors={errors} name="presidentName" />

       {/* 개업일 */}
      <inputs.InputForm 
      control={control} 
      name="startDate"
      placeholder='개업일'
      type="date"
      rules={{
        required : {value: true, message : "개업일을 입력해주세요"}
      }}
      />
    <ErrorMessage errors={errors} name="startDate" />

    {/* 사업자 등록 번호 */}
    <inputs.InputForm 
      control={control} 
      name="businessNo"
      placeholder="사업자 번호"
      type="number"     
      rules={{
        required : {value: true, message : "사업자 번호를 입력해주세요"},
        
      }}
      />
    <ErrorMessage errors={errors} name="businessNo" />

    {/* 자식 컴포넌트에서 인증을 해야 닫힌다. */}
    <buttons.BtnLg BtnValue="인증하기" onClick={BusinessNoValidate}/>
      </div>
  )
}

export default BusinessValidate;