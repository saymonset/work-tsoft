import { useState } from "react"
import { fetchDataGet } from "../../../../../utils"
import { updateInfoUser } from "../../../../../redux"
import { useDispatch } from "react-redux"
import { ButtonContent } from "../../../../../shared"

interface UserBloqueoProps {
    n_nombre: number
}

export const UserBloqueo = (data: UserBloqueoProps) => {

    const [loading, setLoading] = useState<boolean>(false)
    const dispatch = useDispatch()

    const desbloqueaUsuario = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setLoading(true)
        await fetchDataGet(
            "/admin-user-web/desbloquear-usuario",
            " al intentar desbloquear usuario",
            {n_nombre: data.n_nombre},
            updateInfoUser,
            dispatch
        )
        setLoading(false)
    }

    return (
        <div className="form-cols-2 animate__animated animate__fadeIn flex items-center">
            <div className="text-red-700 font-bold text-center">USUARIO BLOQUEADO</div>
            <button
                className="btn w-min m-0 p-0"
                onClick={desbloqueaUsuario}
            >
                <ButtonContent name="DESBLOQUEAR" loading={loading} />
            </button>
        </div>
    )
}