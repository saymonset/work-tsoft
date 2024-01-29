import React, { useState } from "react"
import {showAlert} from "../../../../../utils"
import { ButtonContent } from "../../../../../shared"
import {AxiosResponse} from "axios";
import {valmerApi} from "../../../../../api";
import {useDispatch} from "react-redux";
import {updateInfoUser} from "../../../../../redux";
import {InfoUser} from "../../../../../model";

interface UserChangePwdProps {
    email: string,
    setTriggerProducts: React.Dispatch<React.SetStateAction<boolean>>,
    setTriggerInfoTrial: React.Dispatch<React.SetStateAction<boolean>>,
    setTriggerInfo: React.Dispatch<React.SetStateAction<boolean>>
}

export const UserChangePwd = (data: UserChangePwdProps) => {

    const [password, setPassword] = useState<string>("")
    const [loading, setLoading] = useState<boolean>(false)

    const dispatch = useDispatch()

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const password = e.target.value
        setPassword(password)
    }

    const handleActPassword = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setLoading(true)

        let params = {email: data.email, nueva_contrasenia: password}

        try {
            const response: AxiosResponse<any> = await valmerApi.post(
                "/admin-user-web/cambiar-contrasenia",
                data,
                {params});
            await showAlert("success", `Actualizada`,
                response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);

            dispatch(updateInfoUser({} as InfoUser))
            data.setTriggerProducts(true)
            data.setTriggerInfoTrial(true)
            data.setTriggerInfo(true)
        } catch (error: any)
        {
            if (error.message.includes('Network Error'))
            {
                await showAlert('error', 'Error', 'No hay conexión con el servidor');
            } else {
                await showAlert('error', `Error al actualizar contraseña`, error.message);
            }
        }

        setLoading(false)
        setPassword("")
    }

    return (
        <div className="form-cols-2 animate__animated animate__fadeIn">
            <div className="form-title col-span-2 -mb-3">Cambiar Contraseña</div>
            <div className="form-input">
                <input 
                    type="password"
                    name="password"
                    id="password"
                    value={password}
                    onChange={handleChange}
                />
                <label htmlFor="password">Nueva Contaseña</label>
            </div>
            <div className="flex items-center ml-16">
                <button
                    className="btn w-8/12"
                    onClick={handleActPassword}
                >
                    <ButtonContent name="Cambiar Contraseña" loading={loading} />
                </button>
            </div>
        </div>
    )
}