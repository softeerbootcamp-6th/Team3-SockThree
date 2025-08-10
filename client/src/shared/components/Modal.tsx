import { forwardRef } from "react";

interface ModalProps {
  children: React.ReactNode;
}

const Modal = forwardRef<HTMLDialogElement, ModalProps>(({ children }, ref) => {
  return (
    <dialog ref={ref} className="backdrop:bg-black/60">
      {children}
    </dialog>
  );
});

Modal.displayName = "Modal";

export default Modal;
