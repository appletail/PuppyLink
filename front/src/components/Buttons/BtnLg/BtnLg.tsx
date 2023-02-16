function BtnLg({ BtnValue, onClick }: BtnProps): JSX.Element {
  return (
    <button
      className="bg-main-100 text-white rounded-lg h-14 w-full text-body-bold"
      onClick={onClick}
    >
      {BtnValue}
    </button>
  );
}

export default BtnLg;
