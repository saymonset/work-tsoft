import {fetchDataUpdateGet, showAlert, userEncoded} from "../../../../utils";
import {HandleActualizaParams, UpdateFlujosParams} from "../../../../model";

export const getUpdateFlujos = async ({
                                      triggerUpdate,
                                      setLoading,
                                      txtAreaCargaFlujos,
                                      setIsModalOpen,
                                      setTxtAreaCargaFlujos,
                                      setTriggerUpdate,
                                      url,
                                      emisora,
                                      serie,
                                      tv,
                                      method,
                                      dispatch
                                      }: UpdateFlujosParams) => {
    if (triggerUpdate) {
        setLoading(true)
        await fetchDataUpdateGet(url,
            "al actualizar flujos",
            {
                sEmisora: emisora,
                sSerie: serie,
                sTv: tv,
                sUser: userEncoded(),
                txtAreaCargaFlujos: txtAreaCargaFlujos,
            },
            method,
            dispatch)
        setTriggerUpdate(false)
        setLoading(false)
        setIsModalOpen(false)
        setTxtAreaCargaFlujos('')
    }
};

export const handleActualiza = ({
                             tv,
                             emisora,
                             serie,
                             isDiaSig,
                             setTitleModal,
                             setIsModalOpen,
                         }: HandleActualizaParams) => {

    if (!tv || !emisora || !serie)
    {
        showAlert('warning', 'Aviso!', 'Debe seleccionar TV, Emisora, Serie').then()
    }
    else
    {
        if (!isDiaSig)
        {
            setTitleModal('Flujos')
        }
        else
        {
            setTitleModal('Flujos Dia Sig')
        }
        setIsModalOpen(true)
    }
}

