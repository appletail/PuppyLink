interface imgProps {
  src: string
  alt?: string
  width?: number
  height?: number
}

interface CardProps {
  CardImg?: imgProps
  CardTitle: string
  CardContents?: string[]
  CardFooter?: JSX.Element
}