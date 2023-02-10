function BtnMd({ BtnValue, onClick }: BtnProps): JSX.Element {
  return (
    <button
      className="bg-main-100 text-white rounded-full px-6 h-10 text-callout-bold"
      type="button"
      onClick={onClick}
    >
      {BtnValue}
    </button>
  );
}

export default BtnMd;
