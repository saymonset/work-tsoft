import {valmerApi} from "../../api";
import {useState} from "react";
import {LoginRequest} from "../../model";

export const useLoginRest = () =>
{
    const [errorLogin, setErrorLogin] = useState('');
    const loginApi = async(  user: any, password : any ) => {
        try
        {
            const params = {
                s_user: user,
                s_pass: password,
            };

            const data =
                await valmerApi.get<LoginRequest>('/login/consulta-usuario', { params });

            localStorage.setItem('user', data.data.body.sNombre );
            localStorage.setItem('userEncoded', data.data.body.userEncoded );
            
            return true
        }
        catch (error: any)
        {
            setErrorLogin(error.response.data)
            return false
        }
    }

    return {
        setErrorLogin,
        loginApi,
        errorLogin
    }
}