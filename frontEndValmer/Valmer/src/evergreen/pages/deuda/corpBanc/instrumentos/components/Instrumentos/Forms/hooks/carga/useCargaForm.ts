import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {CargaArchivoContent} from "../../../../../../../../../../model";
import React, {useState} from "react";
import {fetchDataPost} from "../../../../../../../../../../utils";

export const useCargaForm = () => {
    const title = ""

    const cargaArchivoCorp = useSelector((state: RootState<any, any, any>) =>
        state.cargaArchivoCorp) as unknown as CargaArchivoContent;

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleModalOpen = () => {
        setIsModalOpen(true);
    }

    const handleModalClose = () => {
        setIsModalOpen(false);
    }

    const handleCargaArchivo = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setLoading(true)
        await fetchDataPost("/instrumentos/corporativos/sube-archivo",
            "al intentar guardar datos carga archivo",
            cargaArchivoCorp)
        setLoading(false)
    }


    return {
        title,
        isModalOpen,
        loading,
        handleModalOpen,
        handleModalClose,
        handleCargaArchivo
    }
}