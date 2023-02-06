// 이메일 유효성 검사를 진행하고, 중복확인을 하는 usecontroller 입니다 ^.^

import {
  useController,
  useForm,
  SubmitHandler,
  UseControllerProps,
} from 'react-hook-form';
import InputStyle from '@/components/Inputs/InputBtn/InputBtn.module.css';
import { useRecoilState, useRecoilValue } from 'recoil';
import { URL as ServerURL } from '@/states/Server';
import axios from 'axios';
import { useState } from 'react';
import { buttons } from '@/components';

interface EmailProps extends UCProps {
  // button: JSX.Element;
  // duplicateCheck: boolean;
}

function Email({ control, name, rules }: EmailProps) {
  const URL = useRecoilValue(ServerURL);

  const {
    field: { value, onChange, onBlur },
    fieldState,
    formState,
  } = useController({
    name,
    rules,
    control,
  });
  const [NotEmailDuplicateCheck, setNotEmailDuplicateCheck] = useState(false);
  const checkEmailDuplicateClick = () => {
    console.log(value);
    setNotEmailDuplicateCheck(true);
    console.log('email is checked');
  };
  return (
    <div>
      <div className={InputStyle.BoxDiv}>
        <input
          className={InputStyle.InputBox}
          value={value || ''}
          onChange={onChange}
          type="email"
          placeholder="이메일"
        />
        <div>
          <buttons.BtnSm
            BtnValue="중복확인"
            onClick={checkEmailDuplicateClick}
          />
        </div>
      </div>
      <p>{fieldState.isTouched && 'Touched'}</p>
      <p>{fieldState.isDirty && 'Dirty'}</p>
      <p>{fieldState.invalid ? 'invalid' : 'valid'}</p>
      <p>{NotEmailDuplicateCheck ? '' : '중복검사체크하삼요'}</p>
      {/* <p>{duplicateCheck }</p> */}
    </div>
  );
}

export default Email;
