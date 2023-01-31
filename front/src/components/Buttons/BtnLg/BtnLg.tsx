function BtnLg({ BtnValue, onClick }: BtnProps): JSX.Element {
  return (
    <button
      className="bg-main-100 text-white rounded-t-lg h-14 w-[21.875rem] text-body-bold"
      onClick={onClick}
    >
      {BtnValue}
    </button>
  );
}

export default BtnLg;
