import FtStyle from './FooterBtn.module.css';

interface FooterBtn extends FooterProps {
  BtnLeft: JSX.Element;
  BtnRight: JSX.Element;
  onClick?: React.MouseEventHandler<HTMLDivElement> | undefined;
}

function FooterBtn({ BtnLeft, BtnRight, onClick }: FooterBtn): JSX.Element {
  return (
    <div aria-hidden='true' onClick={onClick}>
      <hr className={FtStyle.Line} />
      <div className={FtStyle.Btns}>
        {BtnLeft}
        {BtnRight}
      </div>
    </div>
  );
}

export default FooterBtn;
