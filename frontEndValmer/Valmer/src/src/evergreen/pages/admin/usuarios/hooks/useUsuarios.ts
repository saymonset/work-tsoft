import { useEffect, useRef, useState } from "react";
import { fetchDataGetRet, fetchDataPost, fetchDataPostAct, userEncoded } from "../../../../../utils";
import { IUserList, IUserData, InitialUser, checks_list, DataPost } from '../Models'
import { showAlert } from "../../../../../utils/Utils";

export const useUsuarios = () => {
  const [userList, setUserList] = useState<IUserList[] | []>([])
  const [userData, setUserData] = useState<IUserData | null>(InitialUser)
  const [userId, setUserId] = useState<number | null>(null)
  const [loadingIdUser, setLoadingIdUser] = useState<boolean>(false)
  const [loadingUsers, setLoadingUsers] = useState<boolean>(false)
  const [loadingSave, setLoadingSave] = useState<boolean>(false)

  const checkReload = useRef(true);

  useEffect(() => {
    if (checkReload.current) {
      checkReload.current = false
      getUsers().then();
    }
  }, []);

  useEffect(() => {
    if (userId != null) {
      setUserDataById(userId).then()
    }
  }, [userId]);

  const getUsers = async () => {
    try {
      setLoadingUsers(true);
      setUserData(InitialUser)
      const response = await fetchDataGetRet(
        "/usuarios/",
        "al obtener usuarios",
        {})
      response.body.catalogos.length > 0 ?
        setUserList(sortUsers(response.body.catalogos[0].registros)) : setUserList([])
      setLoadingUsers(false);
    } catch (error) {
      console.log(error)
    }
  }

  /**
       * @name sortUsers
       * @description Ordena la lista de usuarios 
       * @param users arreglo de Objetos Json  con la lista de usuarios desordenada
       * @returns  {<UserList[] | []>} Arreglo de Objetos Json con la lista de usuarios ordenados
   */
  const sortUsers = (users: any[]): IUserList[] | [] => {
    const userArray: IUserList[] = []
    for (const key in users) {
      const registro: IUserList = {
        user_id: Number(key),
        name: users[key]
      }
      userArray.push(registro)
    }
    if (userArray.length > 0) userArray.sort((x, y) => x.name.localeCompare(y.name));
    return userArray
  }

  /**
       * @name setUserDataById
       * @description actualiza el Objeto UserData 
       * @param id representa el id del usuario 
   */
  const setUserDataById = async (id: number) => {
    setLoadingIdUser(true)
    try {
      const response = await fetchDataGetRet(
        "/usuarios/consulta-usuario-id",
        "al obtener usuarios",
        { n_id_usuario: id }
      )
      setUserData(response.body)
    } catch (error: any) {
      console.log('ocurrio un error  : ' + error)
    }
    setLoadingIdUser(false)
  }

  const HandleCahngeInputsForms = (e: React.FormEvent<HTMLInputElement>) => {
    const valor: string = e.currentTarget.hasOwnProperty('checked') ? (e.currentTarget.checked ? "1" : "0") : e.currentTarget.value
    const nameInput: string = e.currentTarget.name == 's_nombre_0' ? 's_nombre' : e.currentTarget.name
    setUserData({ ...userData, [nameInput]: valor })
  }

  const getObjectKey = (obj: any, value: string) => {
    let cad = ''
    for (const key in obj) {
      if (key == value) cad = obj[key]
    }
    return cad;
  }

  const jsonToPost = (id: string) => {
    const json: DataPost = {
      "n_id_usuario": id,
      "s_nombre": userData?.s_nombre,
      "s_usuario": userData?.s_usuario,
      "s_pass": userData?.s_pass,
      "s_area": userData?.s_area,
      "s_mail": userData?.s_mail,
      "s_tel": userData?.s_tel,
      "s_puesto": userData?.s_puesto
    }
    checks_list.forEach(function (value) {
      if (getObjectKey(userData, value) == "1") {
        json[value] = "1"
      }
    })
    return json
  }

  const saveDataUser = async (id: string): Promise<boolean> => {
    try {
      const user = userData?.s_usuario ? userData?.s_usuario.trim() : ''
      const user_pass = userData?.s_pass ? userData?.s_pass.trim() : ''
      const user_name = userData?.s_nombre ? userData?.s_nombre.trim() : ''
      setLoadingSave(true)
      if (user.length <= 0) throw new Error("El user no puede estar vacio");
      if (user_pass.length <= 0) throw new Error("El Password no puede estar vacio");
      if (user_name.length <= 0) throw new Error("El Nombre no puede estar vacio");

      const dataPost = jsonToPost(id)
      await fetchDataPost(
        "/usuarios/guardar-usuario",
        " al intentar actualizar informacion de usuario",
        dataPost,
        { s_user: userEncoded() })
      setLoadingSave(false)
      return true

    } catch (error: any) {
      console.log('el error es : --->> ' + error)
      await showAlert('error', 'Error', error.toString());
      return false
    }
  }

  const deleteUser = async (id: number): Promise<boolean> => {
    try {
      setLoadingSave(true)
      await fetchDataPostAct(
        "/usuarios/borrar-usuario",
        "Borrar",
        " al borrar usuario",
        [], { n_id_usuario: id }
      )
      setLoadingSave(false)
      return true
    } catch (error: any) {
      console.log('el error es : --->> ' + error)
      await showAlert('error', 'Error', error.toString());
      return false
    }

  }

  return {
    userList,
    loadingUsers,
    userData,
    userId,
    loadingSave,
    loadingIdUser,
    setUserData,
    setUserId,
    setUserDataById,
    HandleCahngeInputsForms,
    saveDataUser,
    setLoadingSave,
    getUsers,
    deleteUser
  }
}
