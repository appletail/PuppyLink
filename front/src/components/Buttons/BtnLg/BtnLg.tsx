function BtnLg({ BtnValue }: BtnProps): JSX.Element {
  return (
    <button className="bg-main-100 text-white rounded-t-lg  h-14 w-[350px] text-body-bold">
      {BtnValue}
    </button>
  );
}

export default BtnLg;
