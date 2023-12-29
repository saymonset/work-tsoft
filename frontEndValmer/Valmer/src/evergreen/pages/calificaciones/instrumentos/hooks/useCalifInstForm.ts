import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useDispatch, useSelector } from "react-redux"
import { CalifInstData, Catalogo, CatalogoCalificadoras, SectionType, ValuesNewInstr } from "../../../../../model"
import React, { useEffect, useState } from "react"
import { fetchDataGetRet, fetchDataPost, getCurrentDate, handleFileChangeCalif, userEncoded } from "../../../../../utils"
import { updateCatalogCalifInst, updateCatalogCalificadoras } from "../../../../../redux"
import { useTvEmiSerieCalif } from "./useTvEmiSerieCalif"

export const useCalifInstForm = () => {

    const {
        isFondos,
        isNewInstr,
        isNewSerie,
        selectedTv,
        tv,
        loadingTv,
        tvFondos,
        loadingTvFondos,
        selectedEmisora,
        emisoras,
        loadingEmisoras,
        selectedSerie,
        series,
        loadingSeries,
        consultaData,
        loadingConsultaData,
        loadingSave,
        section,
        fileBase64,
        selectedFile,
        setSection,
        setFileBase64,
        setSelectedFile,
        setTv,
        setTvFondos,
        setIsFondos,
        setEmisoras,
        setSeries,
        setIsNewInstr,
        setIsNewSerie,
        setLoadingSave,
        setSelectedTv,
        setSelectedEmisora,
        setSelectedSerie,
        setConsultaData,
        handleTv,
        handleEmisora,
        handleSerie
    } = useTvEmiSerieCalif()

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalogCalif) as unknown as Catalogo[]

    const catalogCalif = useSelector((state: RootState<any, any, any>) => 
        state.catalogCalificadoras) as unknown as CatalogoCalificadoras

    const dispatch = useDispatch()

    const [loadingCatalog, setLoadingCatalog] = useState(false)
    const [loadingCatalogCalif, setLoadingCatalogCalif] = useState(false)

    useEffect(() => {
        const getCatalog = async () => {
            setLoadingCatalog(true)
            const response = await fetchDataGetRet(
                "/deuda/catalogos",
                " al obtener catalogos",
                {}
            )
            dispatch(updateCatalogCalifInst(response.body.catalogos))
            setLoadingCatalog(false)
        }
        if(!catalog || catalog.length === 0) {
            getCatalog().then()
        }
    }, [catalog]);

    useEffect(() => {
        const getCatalog = async () => {
            setLoadingCatalogCalif(true)
            const response = await fetchDataGetRet(
                isFondos ? "/calificaciones/instrumentos/fondos/tabla-calificadora"
                         : "/calificaciones/instrumentos/tabla-calificadora",
                " al obtener catalogos",
                {}
            )
            dispatch(updateCatalogCalificadoras(response))
            setLoadingCatalogCalif(false)
        }
        if(!catalogCalif?.body) {
            getCatalog().then()
        }
    }, [catalogCalif]);

    useEffect(() => {
        const sectionConfig: Record<SectionType, { pdfKey: string, b64Key: string}> = {
            fitch: { pdfKey: 's_pdf_fitch', b64Key: 'fitch_b64'},
            sp: { pdfKey: 's_pdf_sp', b64Key: 'sp_b64'},
            moody: { pdfKey: 's_pdf_moody', b64Key: 'moody_b64'},
            hr: { pdfKey: 's_pdf_hr', b64Key: 'hr_b64'},
            verum: { pdfKey: 's_pdf_verum', b64Key: 'verum_b64'},
            dbrs: { pdfKey: 's_pdf_dbrs', b64Key: 'dbrs_b64'},
            best: { pdfKey: 's_pdf_best', b64Key: 'best_b64'}
        };

        if (section) {
            const config = sectionConfig[section];
            const updatedValues = { [config.pdfKey]: selectedFile?.name ?? "" };
            const updatedValuesName = { [config.b64Key]: fileBase64 ?? '' };
            const combinedValues = { ...consultaData, ...updatedValues, ...updatedValuesName };
            setConsultaData(combinedValues);
        }
    }, [fileBase64, section]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target

        const updateFormData = {
            ...consultaData,
            [name]: value
        }

        setConsultaData(updateFormData)
    }

    const handleLimpiar = () => {
        setTimeout(() => {
            setIsNewInstr(false)
            setIsNewSerie(false)
            setTv([])
            setTvFondos([])
            setEmisoras([])
            setSeries([])
            setSelectedTv("")
            setSelectedEmisora("")
            setSelectedSerie("")
            setConsultaData({} as CalifInstData)
        }, 10)
    }

    const handleGuardar = async () => {
        setLoadingSave(true)
        await fetchDataPost(
            isFondos ? "calificaciones/instrumentos/fondos/actualiza-info" : "calificaciones/instrumentos/actualiza-info",
            " al guardar instrumento",
            consultaData,
            {s_user: userEncoded()}
        )
        setLoadingSave(false)
    }

    const handleNuevo = () => {
        setSelectedTv("")
        setSelectedEmisora("")
        setSelectedSerie("")
        const formValues = {
            ...ValuesNewInstr,
            d_fec_ins: getCurrentDate()
        }
        setConsultaData(formValues)
        setIsNewSerie(false)
        setIsNewInstr(true)
    }

    const handleNuevaSerie = () => {
        const formValues = {
            ...consultaData,
            s_serie: ""
        }
        setConsultaData(formValues)
        setSelectedSerie("")
        setIsNewInstr(false)
        setIsNewSerie(true)
    }

    const handleChangeFile = (e: React.ChangeEvent<HTMLInputElement>, section: string) => {
        handleFileChangeCalif(e, section, setFileBase64, setSection, setSelectedFile)
    }

    return {
        isFondos,
        isNewInstr,
        isNewSerie,
        loadingCatalog,
        catalog,
        loadingCatalogCalif,
        catalogCalif,
        selectedTv,
        tv,
        loadingTv,
        tvFondos,
        loadingTvFondos,
        selectedEmisora,
        emisoras,
        loadingEmisoras,
        selectedSerie,
        series,
        loadingSeries,
        consultaData,
        loadingConsultaData,
        loadingSave,
        setIsFondos,
        handleNuevo,
        handleNuevaSerie,
        handleChange,
        handleTv,
        handleEmisora,
        handleSerie,
        handleLimpiar,
        handleGuardar,
        handleChangeFile
    }
}