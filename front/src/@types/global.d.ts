type ButtonTypes = React.DetailedHTMLProps<
  React.ButtonHTMLAttributes<HTMLButtonElement>,
  HTMLButtonElement
>;

type UCProps<TFieldValues extends FieldValues = FieldValues> = {
  name: FieldName<TFieldValues>;
  rules?: Exclude<
    RegisterOptions,
    'valueAsNumber' | 'valueAsDate' | 'setValueAs'
  >;
  onFocus?: () => void;
  defaultValue?: unknown;
  control?: Control<TFieldValues>;
};

interface BtnProps extends ButtonTypes {
  // onClick: () => void;
  BtnValue: string;
}
interface imgProps {
  src: string;
  alt?: string;
  width?: number;
  height?: number;
}

interface CardProps {
  CardImg?: imgProps;
  CardTitle: string | JSX.Element;
  CardContents?: string[];
  CardFooter?: JSX.Element;
}

interface FooterProps {
  BtnLeft?: JSX.Element;
  BtnRight?: JSX.Element;
  label?: string;
  UserName?: string;
  IsLiked?: boolean;
  HeartCount?: number;
}

interface LabelProps {
  LabelValue: string;
}

interface InputProps {
  placeholder: string;
  button?: JSX.Element;
  helper?: string;
  InputType: string;
}
