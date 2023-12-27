import { useState, ChangeEvent } from 'react';
import { fetchDataGetEnvioMail, fetchDataGetSave, fetchDataPost, fetchDataPostAct, fetchDataUpdateGet } from '../../utils';

interface Errors {
    email?: string;
}

export interface ModalRecoverPass {
    email: string;
    errors: Errors;
    loading: boolean;
    handleChange: (event: ChangeEvent<HTMLInputElement>) => void;
    handleSubmit: () => void;
}

export const useModalRecoverPass = (): ModalRecoverPass => {
    const [email, setEmail] = useState<string>('');
    const [errors, setErrors] = useState<Errors>({ email: undefined });
    const [loading, setLoading] = useState<boolean>(false)

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

        if (name === 'email') {
            setEmail(value);
        }
    };

    const validateEmail = (): Errors => {
        const errors: Errors = {};

        if (email.trim() === '') {
            errors.email = 'El email es requerido';
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.trim())) {
            errors.email = 'El email ingresado no es válido';
        }

        return errors;
    };

    const handleSubmit = () => {
        setLoading(true)
        const validationErrors = validateEmail();

        if (Object.keys(validationErrors).length === 0) {
            console.log('Form submitted:', { email })
            fetchDataGetEnvioMail(
                "login/recuperar-contrasenia",
                " al enviar email",
                email,
                {email}
            )
            setLoading(false)
        } else {
            setLoading(false)
            setErrors(validationErrors);
        }
    };

    return {
        email,
        errors,
        loading,
        handleChange,
        handleSubmit,
    };
};