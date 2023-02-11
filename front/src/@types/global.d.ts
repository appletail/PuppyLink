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
  BtnValue: string | JSX.Element;
}
interface imgProps {
  src: string;
  alt?: string;
  width?: number;
  height?: number;
}

interface CardProps {
  CardImg?: imgProps;
  CardTitle?: string | JSX.Element;
  CardContents?: string[] | JSX.Element[];
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

type status = 'submit' | 'regist' | 'refuse' | 'docs' | 'lack' | 'confirm' | 'complete'

interface Member {
  email: string
  name: string
  phone: number
  nickName: string
  activated: boolean
  authorities: Array<{ authorityName: string }>
  joinDate: Date
  role: RoleType
}

interface Business {
  businessNo: number,
  businessName: string,
  presidentName: string,
  startDate: Date,
  email: Member
}

interface Volunteer {
  volunteerNo: number
  depTime: Date
  dest: string
  status: string
  flightName: string
  regDate: Date
  email: member
  businessNo: Business
  description: string
}

interface Role {
  USER: "ROLE_USER"
  MANAGER: "ROLE_MANAGER"
  ADMIN: "ROLE_ADMIN"
}

type RoleType = typeof Role[keyof Role]

interface Category {
  idx: number
  name: string
  path: string
  role: Array<RoleType>
  sub: Array<Category>
}