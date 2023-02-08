interface Props {
  [key: string]: unknown;
}

function Navtop(props: Props) {
  return <div id="detail" {...props} />;
}

export default Navtop;
