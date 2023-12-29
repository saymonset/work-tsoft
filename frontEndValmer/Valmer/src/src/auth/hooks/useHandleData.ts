import {useState} from "react";
import {useFormLogin} from "./useFormLogin";

export const useHandleData = () => {

    const {user, password, errors, errorLogin,
        handleChange, handleSubmit} = useFormLogin()

    const [isOpen, setOpen] = useState(false)
    const [isLoading, setLoading] = useState(false);

    const login = async (event: any): Promise<void> => {
        event.preventDefault();
        setLoading(true);
        try {
            await handleSubmit();
        }
        catch (error) {
            console.error(error);
        }
        finally {
            setLoading(false);
        }
    }

    const handleLogin = (event: any) => {
        login(event).catch(error => {
            console.log(error)
        });
    }

    const handleOpenModal = () => {
        setOpen(!isOpen)
    }

    const handleCloseModal = () => {
        setOpen(!isOpen)
    }

    return {
        user,
        password,
        errors,
        errorLogin,
        isLoading,
        isOpen,
        handleLogin,
        handleChange,
        handleOpenModal,
        handleCloseModal
    }
}