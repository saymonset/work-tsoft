import { useModalRecoverPass } from '../hooks/useModalRecoverPass'
import {ButtonContent, Modal} from "../../shared";
import React from "react";

interface ModalProps {
    isOpen: boolean,
    onClose: () => void
}

export const ModalLogin:React.FC<ModalProps> = ({ isOpen, onClose }) => {

    const {email, errors, loading, handleChange, handleSubmit} = useModalRecoverPass()

    const sendEmail = async (event: any): Promise<void> => {
        event.preventDefault();
        handleSubmit();
    }

    const handleSendEmail = (event: any) => {
        sendEmail(event).catch(error => {
            console.log(error)
        });
    }

    return (
        <>
            <Modal isOpen={isOpen} onClose={onClose} title="">
                <div className="modal-login-title">
                    <span>Intranet Valmer Recuperar Contraseña</span>
                </div>
                <form onSubmit={handleSendEmail}>
                    <div className="flex justify-center mt-5">
                        <div className="grid grid-cols-1 w-2/3">
                            <input 
                                type="email" 
                                name="email"
                                autoComplete="on"
                                placeholder="Email"
                                className="border text-base px-2 py-1
                                focus:ouline-none
                                focus:ring-0
                                focus:border-gray-600"
                                value={email}
                                onChange={handleChange}
                            />
                                {errors.email &&
                                    <span className="fontError animate__animated animate__fadeIn">{errors.email}</span>}
                                <button
                                    className="btn mt-5"
                                    type="submit"
                                    name="btnSubmit"
                                    id="btnSubmit"
                                    role="button">
                                    <i className="fa fa-paper-plane"></i>
                                    <ButtonContent name=' Enviar' loading={loading} />
                                </button>
                        </div>
                    </div>
                </form>
                
            </Modal>
        </>
    )
}