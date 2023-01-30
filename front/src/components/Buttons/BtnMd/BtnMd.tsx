function BtnMd({ BtnValue }: BtnProps): JSX.Element {
  return (
    <button className="bg-main-100 text-white rounded-full px-6 h-10 text-callout-bold">
      {BtnValue}
    </button>
  );
}

export default BtnMd;
