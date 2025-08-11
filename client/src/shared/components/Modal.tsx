import { forwardRef } from "react";

interface ModalProps {
  children: React.ReactNode;
}

const Modal = forwardRef<HTMLDialogElement, ModalProps>(({ children }, ref) => {
  return (
    <dialog
      ref={ref}
      className="fix top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 transform rounded-[1.25rem] bg-white backdrop:bg-black/60"
    >
      {children}
    </dialog>
  );
});

Modal.displayName = "Modal";

export default Modal;
