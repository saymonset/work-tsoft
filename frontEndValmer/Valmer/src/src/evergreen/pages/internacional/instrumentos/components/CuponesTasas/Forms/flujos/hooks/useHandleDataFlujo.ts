import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {updateConsultaDataInter} from "../../../../../../../../../redux";
import {getUpdateFlujos, handleActualiza} from "../../../../../../../../../shared";

export const useHandleDataFlujo = () => {

    const tv = useSelector((state: RootState<any, any, any>) => 
      state.selectedTvInter) as unknown as string;

    const emisora = useSelector((state: RootState<any, any, any>) => 
      state.selectedEmisoraInter) as unknown as string;

    const serie = useSelector((state: RootState<any, any, any>) => 
      state.selectedSerieInter) as unknown as string;
    
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [loading, setLoading] = useState(false);
    const [titleModal, setTitleModal] = useState('');
    const [txtAreaCargaFlujos, setTxtAreaCargaFlujos] = useState('');
    const [triggerUpdateDiaSig, setTriggerUpdateDiaSig] = useState(false);
    const [triggerUpdateFlujos, setTriggerUpdateFlujos] = useState(false);
    const [isDiaSig, setIsDiaSig] = useState(false);

    const dispatch = useDispatch();

    useEffect(() => {
        const updateFlujos = async () => {
            await getUpdateFlujos({
                triggerUpdate: triggerUpdateFlujos,
                setLoading: setLoading,
                txtAreaCargaFlujos: txtAreaCargaFlujos,
                setIsModalOpen: setIsModalOpen,
                setTxtAreaCargaFlujos: setTxtAreaCargaFlujos,
                setTriggerUpdate: setTriggerUpdateFlujos,
                url: "/instrumentos/eurobonos/carga-flujos-instr",
                emisora: emisora,
                serie: serie,
                tv: tv,
                method: updateConsultaDataInter,
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
                url: "/instrumentos/eurobonos/carga-flujos-instr-dia-sig",
                emisora: emisora,
                serie: serie,
                tv: tv,
                method: updateConsultaDataInter,
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

    const handleModalClose = () => {
        setIsModalOpen(false);
    }

    return {
        isModalOpen,
        isDiaSig,
        loading,
        titleModal,
        txtAreaCargaFlujos,
        handleChangeTxtFlujos,
        handleModalClose,
        handleActualizaFlujo,
        handleActualizaDiaSig,
        handleActFlujos,
        handleActDiaSig
    }
}