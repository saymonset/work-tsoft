import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {Catalogo,initAccionesAdd, RespAccInstData} from "../../../../../../model";
import React, {useState} from "react";
import {updateConsultaDataAccInst} from "../../../../../../redux";
import {fetchDataPost, updateMissingFields, userEncoded} from "../../../../../../utils";

export const useModalCaracTvForm = () => {

    const consultaDataAccInst = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataAccInst) as unknown as RespAccInstData;

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalog) as unknown as Catalogo[];

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvAcc) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisoraAcc) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerieAcc) as unknown as string;

    const [loading, setLoading] = useState(false)

    const dispatch = useDispatch()


    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {

        const {name, value} = e.target;

        const updatedValues = {
            ...consultaDataAccInst,
            body: {
                ...consultaDataAccInst?.body,
                accionesAdd: {
                    ...consultaDataAccInst?.body?.accionesAdd,
                    [name]: value
                }
            }
        };

        dispatch(updateConsultaDataAccInst(updatedValues));
    };

    const handleSave = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        setLoading(true);

        const updatedAccionesAdd = updateMissingFields(
            consultaDataAccInst.body.accionesAdd,
            initAccionesAdd,
            selectedTv,
            selectedEmisora,
            selectedSerie);

        const updatedConsultaDataAccInst = {
            ...consultaDataAccInst,
            body: {
                ...consultaDataAccInst.body,
                accionesAdd: updatedAccionesAdd
            }
        };


        await fetchDataPost(
            "/acciones/instrumentos/actualiza-info-add",
            " al guardar instrumentos acciones",
            updatedConsultaDataAccInst.body.accionesAdd,
            { s_user: userEncoded() }
        );

        setLoading(false);
    };



    return {
        loading,
        catalog,
        consultaDataAccInst,
        handleSave,
        handleChange
    }
}