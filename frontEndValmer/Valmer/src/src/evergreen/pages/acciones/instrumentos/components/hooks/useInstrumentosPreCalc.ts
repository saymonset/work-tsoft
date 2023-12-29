import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {RespAccInstData} from "../../../../../../model";
import React, {useState} from "react";
import Sweet from "sweetalert2";
import {updateConsultaDataAccInst} from "../../../../../../redux";

export const useInstrumentosPreCalc = () => {

    const consultaDataAccInst = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataAccInst) as unknown as RespAccInstData;

    const dispatch = useDispatch()

    const [showVinculado, setShowVinculado] = useState(false)
    const [showAdr, setShowAdr] = useState(false)
    const [showSuspend, setShowSuspend] = useState(false)
    const [showFijo, setShowFijo] = useState(false)
    const [showDerCorp, setShowDerCorp] = useState(false)

    const handleSweetAlert = (
        currentShowState: boolean,
        newCheckedValue: boolean,
        setStateFunction: React.Dispatch<React.SetStateAction<boolean>>,
        fieldsToReset: string[],
        word: string
    ) => {
        if (currentShowState && !newCheckedValue) {
            Sweet.fire({
                icon: "warning",
                title: "¿Está seguro?",
                html: `Desea eliminar esta caracteristica? <br/><br/> <b style="color: #0e7490;">${word}</b>`,
                showCancelButton: true,
                confirmButtonColor: '#166534',
                confirmButtonText: '<span style="color: white;">Aceptar</span>',
                cancelButtonText: `Cancelar`,
            }).then((r) => {
                if (r.isConfirmed) {
                    setStateFunction(newCheckedValue);
                    resetFields(fieldsToReset);
                }
            }).catch(() => {})
        } else {
            setStateFunction(newCheckedValue);
        }
    }

    const resetFields = (fields: string[]) => {
        const updatedValues = {
            ...consultaDataAccInst,
            body: {
                ...consultaDataAccInst?.body,
                acciones: {
                    ...consultaDataAccInst?.body?.acciones,
                }
            }
        };

        fields.forEach(field => {
            (updatedValues.body.acciones as any)[field] = "";
        });

        dispatch(updateConsultaDataAccInst(updatedValues));
    };

    const handleShowVinculado = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newCheckedValue = e.target.checked;

        handleSweetAlert(
            showVinculado,
            newCheckedValue,
            setShowVinculado,
            ["s_tv_vin", "s_emisora_vin", "s_serie_vin"], "Vinculado");
    }

    const handleShowAdr = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newCheckedValue = e.target.checked;

        handleSweetAlert(
            showAdr,
            newCheckedValue,
            setShowAdr,
            ["s_tv_der", "s_emisora_der", "s_serie_der", "n_proporcion_adr"], "Adr");
    }

    const handleShowSuspend = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newCheckedValue = e.target.checked;

        handleSweetAlert(
            showSuspend,
            newCheckedValue,
            setShowSuspend,
            ["d_susp_fecha"], "Suspendido");
    }

    const handleShowFijo = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newCheckedValue = e.target.checked;

        handleSweetAlert(
            showFijo,
            newCheckedValue,
            setShowFijo,
            ["n_fijo_precio"], "Fijo");
    }

    const handleShowDerCorp = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newCheckedValue = e.target.checked;

        handleSweetAlert(
            showDerCorp,
            newCheckedValue,
            setShowDerCorp,
            ["d_der_corp_fecha", "n_der_corp_monto"], "DerCorp");
    }

    return {
        showVinculado,
        showAdr,
        showSuspend,
        showFijo,
        showDerCorp,
        handleShowVinculado,
        handleShowAdr,
        handleShowSuspend,
        handleShowFijo,
        handleShowDerCorp,
    };
}