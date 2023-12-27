import {Action, Dispatch} from "redux"
import {ActionCreatorWithPayload} from "@reduxjs/toolkit"
import {AxiosResponse} from "axios"
import {valmerApi} from "../api"
import {showAlert} from "./Utils"
import {GetConsultaDataParams, GetEmisorasParams, GetSerieParams} from "../model"


interface ApiResponsePost {
    message: string | undefined;
    body: {
        message: string;
    };
}
interface ApiResponse<T> {
    message: string | undefined;
    body: T;
}

export const fetchDataGetRet = async <T extends Action>(
    url: string,
    errorTitle: string,
    params: object,
): Promise<any> => {
    try {
        const response: AxiosResponse<ApiResponse<T>> = await valmerApi.get(url, { params })
        return response.data
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.response.data.message);
        }
    }
};

export const fetchDataGet = async <T extends Action>(
    url: string,
    errorTitle: string,
    params: object,
    method: ActionCreatorWithPayload<any>,
    dispatch: Dispatch
): Promise<void> => {
    try {
        const response: AxiosResponse<ApiResponse<T>> = await valmerApi.get(url, { params })
        dispatch(method(response.data.body));
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.response.data.message);
        }
    }
};

export const fetchDataGetConsultaData = async <T extends Action>(
    url: string,
    errorTitle: string,
    params: object,
    method: ActionCreatorWithPayload<any>,
    dispatch: Dispatch
): Promise<void> => {
    try {
        const response: AxiosResponse<ApiResponse<T>> = await valmerApi.get(url, { params })
        dispatch(method(response.data));
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const fetchDataGetSave = async <V>(
    url: string,
    errorTitle: string,
    params?: V
): Promise<void> => {
    try {
        const response: AxiosResponse<ApiResponsePost> = await valmerApi.get(url, {params});
        await showAlert("success", "Guardado",
            response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
    } catch (error: any) {
        console.log(error)
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const fetchDataGetEnvioMail = async <V>(
    url: string,
    errorTitle: string,
    email: string,
    params?: V
): Promise<void> => {
    try {
        await valmerApi.get(url, {params});
        await showAlert("success", "Enviado", "Se envío la contraseña a " + email);
    } catch (error: any) {
        console.log(error)
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const fetchDataPost = async <U, V>(
    url: string,
    errorTitle: string,
    data: U,
    params?: V
): Promise<void> => {
    try {
        const response: AxiosResponse<ApiResponsePost> = await valmerApi.post(url, data, {params});
        await showAlert("success", "Guardado",
            response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const fetchDataPostNoMsj = async <U, V>(
    url: string,
    errorTitle: string,
    data: U,
    params?: V
): Promise<void> => {
    try {
        await valmerApi.post(url, data, {params});
        await showAlert("success", "Guardado","Registro actualizado correctamente");
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const fetchDataPostRet = async <U, V>(
    url: string,
    errorTitle: string,
    data: U,
    params?: V
): Promise<any> => {
    try {
        const response: AxiosResponse<ApiResponsePost> = await valmerApi.post(url, data, {params});
        await showAlert("success", "Guardado",
            response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
        return response.data.body
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const fetchDataPostRetNoMsj = async <U, V>(
    url: string,
    successTitle: string,
    successMenssage: string,
    errorTitle: string,
    data: U,
    params?: V
): Promise<any> => {
    try {
        const response: AxiosResponse<ApiResponsePost> = await valmerApi.post(url, data, {params});
        await showAlert("success", successTitle, successMenssage);
        return response.data.body
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const fetchDataPostAct = async <U, V>(
    url: string,
    successTitle: string,
    errorTitle: string,
    data?: U,
    params?: V
): Promise<any> => {
    try {
        const response: AxiosResponse<ApiResponsePost> = await valmerApi.post(url, data, {params});
        await showAlert("success", `${successTitle}`,
            response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
        return response.data
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const downloadFile = async   <U, V>(
    url: string,
    successTitle: string,
    errorTitle: string,
    data?: U,
    params?: V
): Promise<any> => {
    try {
 
        const response: AxiosResponse<ApiResponsePost> = await valmerApi.post(url, data, {params});
        return response.data
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }    
}


export const downloadFileGET = async   <U, V>(
    url: string,
    successTitle: string,
    errorTitle: string,
    data?: U,
    params?: V
): Promise<any> => {
    try {
        const response: AxiosResponse<ApiResponsePost> = await valmerApi.get(url, {params});
        return response.data
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }    
}



export const fetchDataSIGet = async <T extends Action>(
    url: string,
    errorTitle: string,
    params: object,
): Promise<void> => {
    try {
        const response: AxiosResponse<ApiResponse<T>> = await valmerApi.get(url, { params })
        await showAlert("info", "Respuesta SSH", response?.data?.message);
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const fetchDataUpdateGet = async <T extends Action>(
    url: string,
    errorTitle: string,
    params: object,
    method: ActionCreatorWithPayload<any>,
    dispatch: Dispatch
): Promise<void> => {
    try {
        const response: AxiosResponse<ApiResponse<T>> = await valmerApi.get(url, { params })
        await showAlert("success", "Actualizado", "Registro actualizado exitosamente")
        dispatch(method(response.data));
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};

export const getEmisoras = async ({
                                      url,
                                      triggerEmisora,
                                      selectedTv,
                                      setTriggerEmisora,
                                      setLoadingEmisoras,
                                      setEmisora,
                                      dispatch,
                                      reduxAction
                                  }: GetEmisorasParams) => {
    if (triggerEmisora) {
        setLoadingEmisoras(true);
        const response = await fetchDataGetRet(
            url,
            " al obtener catalogo emisora",
            { sTv: selectedTv });

        if (dispatch && reduxAction) {
            dispatch(reduxAction(response.body));
        } else if (setEmisora) {
            setEmisora(response.body);
        }

        setTriggerEmisora(false);
        setLoadingEmisoras(false);
    }
};

export const getSerie = async ({
                                   url,
                                   triggerSerie,
                                   selectedTv,
                                   selectedEmisora,
                                   setSerie,
                                   setTriggerSerie,
                                   setLoadingSerie,
                                   dispatch,
                                   reduxAction
                               }: GetSerieParams) => {
    if (triggerSerie) {
        setLoadingSerie(true);
        const response = await fetchDataGetRet(
            url,
            " al obtener catalogo serie",
            {
                sTv: selectedTv,
                sEmisora: selectedEmisora
            });

        if (dispatch && reduxAction) {
            dispatch(reduxAction(response.body));
        } else if (setSerie) {
            setSerie(response.body);
        }

        setTriggerSerie(false);
        setLoadingSerie(false);
    }
};

export const getConsultaData = async ({
                                          url,
                                          triggerConsultaData,
                                          selectedTv,
                                          selectedEmisora,
                                          selectedSerie,
                                          setConsultaData,
                                          setTriggerConsultaData,
                                          setLoadingConsultaData,
                                          dispatch,
                                          reduxAction
                                      }: GetConsultaDataParams) => {
    if (triggerConsultaData) {
        setLoadingConsultaData(true);
        const response = await fetchDataGetRet(
            url,
            " al obtener consulta data",
            {
                sTv: selectedTv,
                sEmisora: selectedEmisora,
                sSerie: selectedSerie
            });

        if (dispatch && reduxAction) {
            dispatch(reduxAction(response));
        } else if (setConsultaData) {
            setConsultaData(response);
        }

        setTriggerConsultaData(false);
        setLoadingConsultaData(false);
    }
};

export const handlePostError400 = async <U, V>(
    url: string,
    errorTitle: string,
    data: U,
    params?: V
): Promise<void> => {
    try {
        const response: AxiosResponse<ApiResponsePost> = await valmerApi.post(url, data, { params });
        await showAlert("success", "Guardado",
            response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
    } catch (error: any) {
        if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
        } else if (error.response && error.response.status === 400) {
            const errorMessage = error.response.data.message || 'Error 400';
            await showAlert('error', 'Error', errorMessage);
        } else {
            await showAlert('error', `Error ${errorTitle}`, error.message);
        }
    }
};