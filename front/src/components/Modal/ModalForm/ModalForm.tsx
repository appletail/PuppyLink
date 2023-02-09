import style from './ModalForm.module.css'

interface ModalProps {
  ModalContent : JSX.Element
  closeModal: () => void;
}


function ModalForm({ModalContent, closeModal}:ModalProps): JSX.Element {
  return (
        <div aria-hidden="true" className={style.Wrapper}
        onClick={closeModal}
      >
      <div
        aria-hidden="true"
        className={style.Body}
        onClick={(e) => e.stopPropagation()}
      >
        {ModalContent}
      </div>
    </div>
  )
}

export default ModalForm