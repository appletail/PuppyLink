import { useForm } from 'react-hook-form';

import { ErrorMessage } from '@hookform/error-message';

import { axBase } from '@/apis/api/axiosInstance'
import { inputs, buttons } from '@/components';

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';


interface BusinessProps {
  businessNo: number;
  setOpenModal: (value: boolean) => void;
  setNotbusinessNoValidateCheck: (value: boolean) => void;

}
interface ValidateProps extends BusinessProps {
  presidentName: string;
  startDate: Date;
}

function BusinessValidate({ businessNo, setOpenModal, setNotbusinessNoValidateCheck }: BusinessProps): JSX.Element {
  const {
    control,
    formState: { errors },
    getValues,
    setError,
    watch,
  } = useForm<ValidateProps>({
    defaultValues: {
      businessNo: businessNo
    }
  });

  const addZero = function (num: number): string {
    return num < 10 ? '0' + num : num.toString()
  }


  const dateFormat = function (date: Date) {
    const today = new Date(date)
    const year = today.getFullYear()
    const month = today.getMonth()
    const day = today.getDate()

    return `${year}${addZero(month + 1)}${addZero(day)}`
  }


  const BusinessNoValidate = () => {
    const startDate = getValues('startDate')
    const format_date = dateFormat(startDate)
    const businessNo = getValues('businessNo')
    const presidentName = getValues('presidentName')
    if (!presidentName || !startDate || !businessNo) {
      toast.error("모든 정보를 다 입력해주세요", {
        autoClose: 3000,
        position: toast.POSITION.BOTTOM_CENTER,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: 'colored',
      })
      return
    }
    else {

      // axios 통신
      axBase({
        method: 'post',
        url: '/foundation/validate',
        data: {
          "businessName": "",
          "businessNo": businessNo,
          "description": "",
          "email": {
            "activated": true,
            "authorities": [
              {
                "authorityName": ""
              }
            ],
            "email": "",
            "joinDate": "",
            "name": "",
            "nickName": "",
            "phone": "",
            "refreshToken": ""
          },
          "presidentName": presidentName,
          "profileURL": "",
          "startDate": format_date
        },
      })
        .then((res) => {
          // console.log(res)
          const isBusinessValid = res.data.data
          if (!isBusinessValid) {
            toast.error("사업자 인증에 실패했습니다. 다시 확인해주세요", {
              autoClose: 3000,
              position: toast.POSITION.BOTTOM_CENTER,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
              theme: 'colored',
            })
          }
          else {
            toast.success("사업자 인증에 성공했습니다.", {
              autoClose: 3000,
              position: toast.POSITION.BOTTOM_CENTER,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
              theme: 'colored',
            })
            setNotbusinessNoValidateCheck(false)
            setOpenModal(false)
          }

        })
        .catch((err) => console.log(err))
    }

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
          required: { value: true, message: "대표자 성명을 입력해주세요" }
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
          required: { value: true, message: "개업일을 입력해주세요" }
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
          required: { value: true, message: "사업자 번호를 입력해주세요" },

        }}
      />
      <ErrorMessage errors={errors} name="businessNo" />

      {/* 자식 컴포넌트에서 인증을 해야 닫힌다. */}
      <buttons.BtnMd BtnValue="인증하기" onClick={BusinessNoValidate} />

    </div>
  )
}

export default BusinessValidate;