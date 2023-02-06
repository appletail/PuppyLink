import React from 'react';
import Email from './Components/Email';
import { ErrorMessage } from '@hookform/error-message';

import { useForm, SubmitHandler } from 'react-hook-form';

interface FormValues {
  email: string;
}

function Test() {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<FormValues>({
    defaultValues: {},
    mode: 'onChange',
  });

  const onSubmit = (data: FormValues) => console.log(data);
  return (
    <div>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Email
          name="email"
          control={control}
          rules={{
            required: {
              value: true,
              message: '이메일을 입력해주세요',
            },
            onChange: (e: any) => {
              console.log(e.target);

              //   console.log(NotEmailDuplicateCheck);
            },
          }}
        />
        <ErrorMessage errors={errors} name="email" />
      </form>
    </div>
  );
}

export default Test;
