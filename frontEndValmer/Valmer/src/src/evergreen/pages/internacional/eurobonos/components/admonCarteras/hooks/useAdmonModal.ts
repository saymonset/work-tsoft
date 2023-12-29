import React, {useState} from "react";
import {fetchDataGetSave} from "../../../../../../../utils";

export const useAdmonModal = () => {

    const [isOpen, setOpen] = useState(false)
    const [loading, setLoading] = useState(false)
    const [cliente, setCliente] = useState<string>('');
    const [envioSFTP, setEnvioSFTP] = useState<number>(0);
    const [envioEmail, setEnvioEmail] = useState<number>(0);

    const handleOpenModal = () => {
        setOpen(!isOpen)
    }

    const handleCloseModal = () => {
        setOpen(!isOpen)
    }

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setCliente(event.target.value);
    }

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const checked = event.target.checked;

        const value = checked ? 1 : 0;

        if (name === "b_sftp") {
            setEnvioSFTP(value);
        } else if (name === "b_email") {
            setEnvioEmail(value);
        }
    }

    const handleSave = async () => {
        setLoading(true)
        await fetchDataGetSave(
            "/internacional/proceso-eurobonos/nuevo-cliente",
            " se genero un error al generar nuevo cliente",
            {
                s_cliente: cliente,
                b_envio_sftp: envioSFTP,
                b_envio_mail: envioEmail})
        setLoading(false)
    }

    return {
        loading,
        isOpen,
        cliente,
        envioSFTP,
        envioEmail,
        handleOpenModal,
        handleCloseModal,
        handleInputChange,
        handleCheckboxChange,
        handleSave}

}