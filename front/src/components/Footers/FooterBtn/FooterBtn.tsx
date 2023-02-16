import FtStyle from './FooterBtn.module.css';

interface FooterBtn extends FooterProps {
  BtnMiddle?: JSX.Element
  onClick?: React.MouseEventHandler<HTMLDivElement> | undefined;
}

function FooterBtn({ BtnLeft, BtnMiddle, BtnRight, onClick }: FooterBtn): JSX.Element {
  return (
    <div aria-hidden='true' onClick={onClick}>
      <hr className={FtStyle.Line} />
      <div className={FtStyle.Btns}>
        {BtnLeft}
        {BtnMiddle}
        {BtnRight}
      </div>
    </div>
  );
}

export default FooterBtn;
