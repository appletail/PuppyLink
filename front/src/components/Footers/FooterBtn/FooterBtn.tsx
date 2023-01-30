import FtStyle from './FooterBtn.module.css';

interface FooterBtn extends FooterProps {
  BtnLeft: JSX.Element;
  BtnRight: JSX.Element;
}

function FooterBtn({ BtnLeft, BtnRight }: FooterBtn): JSX.Element {
  return (
    <div>
      <hr className={FtStyle.Line} />
      <div className={FtStyle.Btns}>
        {BtnLeft}
        {BtnRight}
      </div>
    </div>
  );
}

export default FooterBtn;
