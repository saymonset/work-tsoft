import {useEffect, useState} from 'react'
import {ResponseRestricted} from "../../../../model";
import {valmerApi} from "../../../../api";
import {showAlert, userEncoded} from "../../../../utils";

export const useRouteRestricted = (idPantalla: number) => {

    const [IsRestricted, setIsRestricted] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const params = {
            sUser: userEncoded(),
            idPantalla: idPantalla,
        };

        valmerApi.get<ResponseRestricted>('/login/validar-perfil', {params})
            .then(response => {
                setLoading(false);
                setIsRestricted(!response.data.body);
            })
            .catch(async error => {
                setLoading(false);
                if (error.message.includes('Network Error')) {
                    await showAlert('error', 'Error', 'No hay conexión con el servidor');
                } else {
                    await showAlert('error', 'Error validar pantalla', error.message);
                }
            })
    }, [idPantalla]);

    return {
        IsRestricted,
        loading
    }
}