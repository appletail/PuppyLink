import { inputs } from '@/components';
import { URL as ServerURL } from '@/states/Server';
import { useRecoilValue } from 'recoil';

function SignupPage() {
  const URL = useRecoilValue(ServerURL);
  return (
    <div>
      SignupPage
      <inputs.Input placeholder="이메일" InputType="email" />
    </div>
  );
}

export default SignupPage;
