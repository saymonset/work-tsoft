import { useState } from "react"
import { fetchDataPostAct } from "../../../../../utils"
import { ButtonContent } from "../../../../../shared"

interface UserChangePwdProps {
    email: string
}

export const UserChangePwd = (data: UserChangePwdProps) => {

    const [password, setPassword] = useState<string>("")
    const [loading, setLoading] = useState<boolean>(false)

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const password = e.target.value
        setPassword(password)
    }

    const handleActPassword = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setLoading(true)
        await fetchDataPostAct(
            "/admin-user-web/cambiar-contrasenia",
            "Actualizada",
            " al actualizar contraseña",
            [],
            {email: data.email, nueva_contrasenia: password}
        )
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