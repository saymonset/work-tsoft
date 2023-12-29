import {useContext, useState} from "react";
import {useLoginRest} from "./useLoginRest";
import {AuthContext} from "../context";
import {NavigateFunction, useNavigate} from "react-router-dom";

interface ValidationErrors {
    user?: string;
    password?: string;
}
export const useFormLogin = () => {

    const { setErrorLogin, loginApi, errorLogin } = useLoginRest();

    const { login } = useContext(AuthContext)
    const navigate: NavigateFunction = useNavigate();
    const [user, setUser] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState<ValidationErrors>({});

    const handleChange = (event: any) => {
        const { name, value } = event.target;

        switch (name) {
            case 'user':
                setUser(value);
                break;
            case 'password':
                setPassword(value);
                break;
            default:
                break;
        }
    }

    const validateInputs = (): ValidationErrors => {
        const errors: ValidationErrors = {};

        if (user.trim() === '')
        {
            errors.user = 'El usuario es requerido';
        }
        else if (!/^[a-zA-Z]+$/.test(user.trim()))
        {
            errors.user = 'El usuario no debe contener números/caracteres especiales';
        }

        if (password.trim() === '')
        {
            errors.password = 'La contraseña es requerida';
        }
        else if (password.length < 8)
        {
            errors.password = 'La contraseña debe tener al menos 8 caracteres.';
        }

        return errors;
    }

    const handleSubmit = async (): Promise<void> => {
        const ve: ValidationErrors = validateInputs();

        if (Object.keys(ve).length === 0) {
            setEmptyError(ve);

            try {
                let r = await loginApi(user, password);

                if (r) {
                    onGoPage();
                }
            } catch (error) {
                console.error(error);
            }
        } else {
            setErrors(ve);
        }
    };

    const setEmptyError = (ve: ValidationErrors) => {
        delete ve.user;
        delete ve.password;
        setErrorLogin("");
        setErrors(ve);
    }

    const onGoPage = () => {
        const lastPath = localStorage.getItem('lastPath') ?? '/';
        login('Evergreen')
        navigate('/', {
            replace: true
        });
    }
    
    return {
        user,
        password,
        errors,
        errorLogin,
        handleChange,
        handleSubmit
    }
}