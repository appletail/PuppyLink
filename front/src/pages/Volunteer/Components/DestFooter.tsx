import { useController } from "react-hook-form"


function DestFooter({ name, control, rules }: UCProps): JSX.Element {
  const {
    field: { value, onChange },
  } = useController({ name, control, rules });

  return (
    <div className="flex flex-col items-center">
      <hr className="border-none h-[0.5px] w-full bg-grey mb-2" />
      <select className="text-caption1" value={value} onChange={onChange}>
        <option value="none">공항을 선택해주세요.</option>
        <option value="미국 워싱턴">워싱턴</option>
        <option value="미국 존 F.케네디">존 F.케네디</option>
        <option value="캐나다 밴쿠버">밴쿠버</option>
        <option value="캐나다 토론토">토론토</option>
      </select>
    </div>
  );
}

export default DestFooter