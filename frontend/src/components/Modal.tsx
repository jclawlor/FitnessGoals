// components/Modal.tsx
import type { ReactNode } from 'react';
import { createPortal } from 'react-dom';
import '../Modal.css';

interface ModalProps {
    onClose: () => void;
    children: ReactNode;
}

//Parameter Destructuring ModalProps to onClose and children
export default function Modal ({ onClose, children }: ModalProps) {
    return createPortal(
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                {children}
            </div>
        </div>,
        document.body
    );
}