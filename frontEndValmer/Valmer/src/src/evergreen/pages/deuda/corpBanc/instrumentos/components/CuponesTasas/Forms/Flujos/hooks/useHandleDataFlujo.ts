import { useEffect } from "react"
import { useVarDataFlujo } from "./useVarDataFlujo"
import { updateConsultaDataCorp } from "../../../../../../../../../../redux"
import {getUpdateFlujos, handleActualiza} from "../../../../../../../../../../shared";

export const useHandleDataFlujo = () => {
    
    const {
        tv,
        emisora,
        serie,
        isModalOpen,
        titleModal,
        isDiaSig,
        loading,
        txtAreaCargaFlujos,
        triggerUpdateFlujos,
        triggerUpdateDiaSig,
        setIsModalOpen,
        setTitleModal,
        setIsDiaSig,
        setLoading,
        setTxtAreaCargaFlujos,
        setTriggerUpdateFlujos,
        setTriggerUpdateDiaSig,
        dispatch
    } = useVarDataFlujo()

    useEffect(() => {
        const updateFlujos = async () => {
            await getUpdateFlujos({
                triggerUpdate: triggerUpdateFlujos,
                setLoading: setLoading,
                txtAreaCargaFlujos: txtAreaCargaFlujos,
                setIsModalOpen: setIsModalOpen,
                setTxtAreaCargaFlujos: setTxtAreaCargaFlujos,
                setTriggerUpdate: setTriggerUpdateFlujos,
                url: "instrumentos/corporativos/carga-flujos-instr",
                emisora: emisora,
                serie: serie,
                tv: tv,
                method: updateConsultaDataCorp,
                dispatch: dispatch
            });
        };

        updateFlujos().then();
    }, [triggerUpdateFlujos])

    useEffect(() => {
        const updateNextDay = async () => {
            await getUpdateFlujos({
                triggerUpdate: triggerUpdateDiaSig,
                setLoading: setLoading,
                txtAreaCargaFlujos: txtAreaCargaFlujos,
                setIsModalOpen: setIsModalOpen,
                setTxtAreaCargaFlujos: setTxtAreaCargaFlujos,
                setTriggerUpdate: setTriggerUpdateDiaSig,
                url: "instrumentos/corporativos/carga-flujos-instr-dia-sig",
                emisora: emisora,
                serie: serie,
                tv: tv,
                method: updateConsultaDataCorp,
                dispatch: dispatch
            });
        };
        updateNextDay().then();
    }, [triggerUpdateDiaSig])

    const handleActualizaFlujo = () => {
        handleActualizaGeneric(false);
    }

    const handleActualizaDiaSig = () => {
        setIsDiaSig(true);
        handleActualizaGeneric(true);
    }

    const handleActualizaGeneric = (isDiaSig: boolean) => {
        handleActualiza({
            tv: tv,
            emisora: emisora,
            serie: serie,
            isDiaSig: isDiaSig,
            setTitleModal: setTitleModal,
            setIsModalOpen: setIsModalOpen,
        });
    }

    const handleModalClose = () => {
		setIsModalOpen(false)
        setTriggerUpdateFlujos(false)
	}

    const handleChangeTxtFlujos = (e: any) => {
        setTxtAreaCargaFlujos(e.target.value)
    }

    const handleActFlujos = (e: any) => {
        e.preventDefault()
        setTriggerUpdateFlujos(true)
    }

    const handleActDiaSig = (e: any) => {
        e.preventDefault()
        setTriggerUpdateDiaSig(true)
    }


    return {
        isModalOpen,
        titleModal,
        isDiaSig,
        txtAreaCargaFlujos,
        loading,
        handleChangeTxtFlujos,
        handleActualizaFlujo,
        handleActualizaDiaSig,
        handleModalClose,
        handleActFlujos,
        handleActDiaSig
    }
}