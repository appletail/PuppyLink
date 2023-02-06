function BtnSm({ BtnValue, onClick }: BtnProps): JSX.Element {
  return (
    <button
      className="bg-main-100 text-white rounded-full px-3 h-7 text-footnote"
      onClick={onClick}
      type="button"
    >
      {BtnValue}
    </button>
  );
}

export default BtnSm;
