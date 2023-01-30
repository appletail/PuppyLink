function BtnBsm({ BtnValue, onClick }: BtnProps): JSX.Element {
  return (
    <button
      className="text-main-100 border-main-100 rounded-full px-3 h-7 text-footnote"
      onClick={onClick}
    >
      {BtnValue}
    </button>
  );
}

export default BtnBsm;
