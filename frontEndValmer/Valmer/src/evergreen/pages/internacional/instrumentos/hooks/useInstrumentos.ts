import React, {useEffect, useRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {
    FvInterInstrumentos,
    IsFieldModifiedFvInterIns,
    ResponseCalPrecios,
    ResponseConsultaDataInter
} from "../../../../../model";
import {
    updateConsultaDataInter,
    updateEmisoraInter,
    updateFieldRequiredIntern,
    updateFormValuesInter,
    updateIsNewInterForm,
    updateRequiredEmisoraInter,
    updateRequiredSerieInter,
    updateRequiredTvInter,
    updateSelectedEmisoraInter,
    updateSelectedSerieInter,
    updateSelectedTvInter,
    updateSerieInter, updateTriggerEraseInter, updateTv,
} from "../../../../../redux";
import {
    eraseConsultaData,
    eraseFormInsValues,
    eraseFormValuesInstrumentos,
    fetchDataPost, fetchDataPostRet, showAlert,
    userEncoded,
    validateFormFields
} from "../../../../../utils";
import {AxiosResponse} from "axios";
import {valmerApi} from "../../../../../api";

export const useInstrumentos = () => {

    useEffect(() => {
        handleLimpiarClick()
    }, [])

    const requeridosInter: any = {
        n_emisor: useRef<HTMLSelectElement | null>(null),
    }

    const fieldRequireInter = useSelector((state: RootState<any, any, any>) =>
    state.fieldRequiredIntern) as unknown as IsFieldModifiedFvInterIns;

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [loading, setLoading] = useState(false);

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvInter) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisoraInter) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerieInter) as unknown as string;

    const formValues = useSelector((state: RootState<any, any, any>) =>
        state.formValuesInter) as unknown as FvInterInstrumentos;

    const consultaData = useSelector((state: RootState<any, any, any>) => 
        state.consultaDataInter) as unknown as ResponseConsultaDataInter;

    const [instrument, setInstrument] = useState("");
    const [calPrecios, setCalPrecios] = useState({} as ResponseCalPrecios);

    const [loadingSubmit, setLoadingSubmit] = useState(false);

    const dispatch = useDispatch()

    const handleModalClose = () => {
        setIsModalOpen(false);
    }

    const handleCalculator = async () => {
        if (!isValidSelection(selectedTv, selectedEmisora, selectedSerie)) return;

        setInstrument(`${selectedTv}_${selectedEmisora}_${selectedSerie}`);
        setLoading(true);
        let data = { sEmisora: selectedEmisora, sSerie: selectedSerie, sTv: selectedTv };

        try {
            const response =
                await valmerApi.get("/instrumentos/eurobonos/calculadora-precios",
                { params: data });
            handleApiResponse(response, selectedTv, selectedEmisora, selectedSerie);
        } catch (error: any) {
            if (error.message.includes('Network Error')) {
                await showAlert('error', 'Error', 'No hay conexión con el servidor');
            }
        } finally {
            setLoading(false);
            setIsModalOpen(true);
        }
    };

    const isValidSelection = (selectedTv: string, selectedEmisora: string, selectedSerie: string) => {
        if (!selectedTv) {
            dispatch(updateRequiredTvInter(true));
            return false;
        }
        if (!selectedEmisora || selectedEmisora === "...") {
            dispatch(updateRequiredEmisoraInter(true));
            return false;
        }
        if (!selectedSerie || selectedSerie === "...") {
            dispatch(updateRequiredSerieInter(true));
            return false;
        }
        return true;
    };

    const handleApiResponse = (response : AxiosResponse<any,any>,
                               selectedTv: string,
                               selectedEmisora: string,
                               selectedSerie: string) => {
        if (response.status === 200 && response.data && response.data.body) {
            if (typeof response.data.body === 'object') {
                setCalPrecios(response.data);
                setIsModalOpen(true);
                setInstrument(`${selectedTv}_${selectedEmisora}_${selectedSerie}`);
            } else if (typeof response.data.body === 'string' && response.data.body.includes("No existen datos para el instrumento")) {
                setInstrument(`No se encontraron datos para el instrumento: ${selectedTv}_${selectedEmisora}_${selectedSerie}`);
            }
        } else {
            setInstrument(`No se encontraron datos para el instrumento: ${selectedTv}_${selectedEmisora}_${selectedSerie}`);
        }
    };

    const handleClickNew = () => {
        dispatch(updateIsNewInterForm(true))
        eraseConsultaData(consultaData, dispatch)
        eraseFormValuesInstrumentos(formValues, dispatch)
        eraseData()
    }

    const eraseData = () => {
        dispatch(updateTriggerEraseInter(true))
        eraseFormValuesInstrumentos(formValues, dispatch);
        setTimeout(() => {
            dispatch(updateSelectedTvInter(""))
            dispatch(updateSelectedEmisoraInter(""))
            dispatch(updateSelectedSerieInter(""))
            dispatch(updateEmisoraInter([]))
            dispatch(updateSerieInter([]))
            dispatch(updateTriggerEraseInter(false))
        }, 10);
    }

    const handleLimpiarClick = () => {
        eraseConsultaData(consultaData, dispatch)
        eraseFormValuesInstrumentos(formValues, dispatch)
        eraseFormInsValues(formValues, dispatch)
        dispatch(updateIsNewInterForm(false))
        dispatch(updateRequiredTvInter(false))
        dispatch(updateRequiredEmisoraInter(false))
        dispatch(updateRequiredSerieInter(false))
        dispatch(updateFieldRequiredIntern({} as IsFieldModifiedFvInterIns))
        eraseData()
    }

    const handleSubmit = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        if(await validateFormFields(consultaData.body, dispatch, requeridosInter, undefined, undefined, fieldRequireInter)) {
            setLoadingSubmit(true)
            const response = await fetchDataPostRet("/instrumentos/eurobonos/guarda-info",
                "al intentar guardar datos",
                consultaData.body,
                {s_user: userEncoded()})
            setLoadingSubmit(false)
            dispatch(updateTv([]))
            handleLimpiarClick()
        }
    }

    return {
        loadingSubmit,
        instrument,
        isModalOpen,
        loading,
        calPrecios,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        requeridosInter,
        handleModalClose,
        handleCalculator,
        handleClickNew,
        handleLimpiarClick,
        handleSubmit
    }
}