import style from './Modal.module.css'
import { cards } from '@/components'


interface ModalProps extends CardProps {
  closeModal: () => void;
}

function Modal({ CardTitle, CardContents, closeModal }: ModalProps): JSX.Element {

  return (
    <div aria-hidden="true" className={style.Wrapper}
      onClick={closeModal}>
      <div
        aria-hidden="true"
        className={style.Body}
        onClick={closeModal}
      >
        <cards.CardLg CardContents={CardContents} CardTitle={CardTitle} />
      </div>
    </div>
  )
}

export default Modal