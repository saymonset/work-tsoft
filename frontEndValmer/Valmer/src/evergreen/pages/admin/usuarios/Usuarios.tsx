import {  useState } from 'react'
import { TitleDate } from "../../../../shared"
import { UsuariosForm } from './components/UsuariosForm'
import {useUsuarios} from './hooks/useUsuarios'
import {IUserList,InitialUser} from './Models'

export const Usuarios = () => {
    const [nuevo, setNuevo] = useState(false)
    const {
        userList,
        userData,
        loadingSave,
        setUserData,
        setUsserId,  
        HandleCahngeInputsForms  ,
        saveDataUser    ,
        getUsers,
        deleteUser
    } = useUsuarios()

    const handleNuevo = (e: React.SyntheticEvent) => {
        e.preventDefault()
        setNuevo(true)
        setUserData(InitialUser) 
    }

    const handleCancel = (e: React.SyntheticEvent) => {
        e.preventDefault()
        setNuevo(false)
        setUserData(InitialUser) 
    }

    const  HandleSaveUser = async (e: React.SyntheticEvent) => {
        e.preventDefault()
        const id: string = nuevo ? "0" : userData?.n_id_usuario ? userData?.n_id_usuario : ""
        const isok = await saveDataUser(id)        
        if (isok) {
            setNuevo(false)
            await getUsers()
        } 
    }

    const HandleDeleteUser = async (e: React.SyntheticEvent) => {
        e.preventDefault()      
        if (!nuevo) {
            const id: number =  userData?.n_id_usuario ?  Number(userData?.n_id_usuario) : 0
            const isok = await deleteUser(id)
            if (isok) {
                await getUsers()
            }             
        }
    }

    const  handleOnChangeName =(e: React.ChangeEvent<HTMLSelectElement>) => {
        const id_user : number = Number(e.target.value)    
        setUserData(InitialUser)
        id_user ==9999 ? setUserData(InitialUser)    : setUsserId(id_user)
    }


    return (
        <>
            <TitleDate title="Usuarios"/>

            <form className='animate__animated animate__fadeIn'>
                <div className="flex justify-center w-full">
                    <span className="form-title w-1/2">Usuario</span>
                </div>
                <div className="flex justify-center">
                    {nuevo ?  (
                        <div className="form-input w-1/4 m-1">
                            <input type="text" name="s_nombre_0" id="s_nombre_0" placeholder="" onChange={HandleCahngeInputsForms}/>
                            <label htmlFor="s_nombre_0">Nombre</label>
                        </div>
                    ) : (
                        <div className="form-select w-1/4 m-1">
                            <select name="nombre_select" id="nombre_select" onChange={handleOnChangeName}>
                            <option key={9999} value ={99999}> ............</option>
                                {userList && userList.map((item: IUserList) => {
                                  return (
                                    <option key={item.user_id} value={item.user_id}>
                                      {item.name}
                                    </option>
                                  )
                                })}

                            </select>
                            <label htmlFor="nombre_select">Nombre</label>
                        </div>
                    )}
                    <div className="content-center grid m-1">
                        <button 
                            className="btn" 
                            type="button"
                            onClick={handleNuevo}
                        >
                            Nuevo
                        </button>
                    </div>
                    <div className="content-center grid m-1">
                        <button 
                            className="btn" 
                            type="button"
                            onClick={HandleSaveUser}
                        >
                            Guardar
                        </button>
                    </div>
                    <div className="content-center grid m-1">
                        <button 
                            className="btn" 
                            type="button"
                            onClick={HandleDeleteUser}
                        >
                            Borrar
                        </button>
                    </div>
                    <div className="content-center grid m-1">
                        <button 
                            className="btn" 
                            type="button"
                            onClick={handleCancel}
                        >
                            Cancelar
                        </button>
                    </div>
                </div>

                <UsuariosForm datos={userData } handleChange = {HandleCahngeInputsForms}/>

            </form>
        </>
    )
}