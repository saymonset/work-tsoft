import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useDispatch, useSelector } from "react-redux"
import { CatSector, DataHistoricoTrial, InfoUser, UriInfo } from "../../../../../model"
import React, { useEffect, useState } from "react";
import {
    fetchDataGet,
    fetchDataGetAlert,
    fetchDataGetConsultaData,
    fetchDataGetRet,
    showAlert
} from "../../../../../utils";
import { updateCatNom, updateCatSector, updateInfoUser, updateUriInfo } from "../../../../../redux";
import { useGetCatalogs } from "./useGetCatalogs";
import fileDownload from "js-file-download";
import { Base64 } from "js-base64";

export const useHandleDataUserWeb = () => {

    const {
        catalogoInst, 
        loadingInst, 
        catNom,
        catTipoUser,
        loadingTipoUser,
        catUri,
        loadingCatUri,
    } = useGetCatalogs()

    const catSector = useSelector(
        (state: RootState<any, any, any>) => state.catSector
    ) as unknown as CatSector[];

    const infoUser = useSelector(
        (state: RootState<any, any, any>) => state.infoUser
    ) as unknown as InfoUser;

    const uriInfo = useSelector(
        (state: RootState<any, any, any>) => state.uriInfo
    ) as unknown as UriInfo;

    const [triggerSector, setTriggerSector] = useState<boolean>(false)
    const [loadingSector, setLoadingSector] = useState<boolean>(false)
    const [triggerInfo, setTriggerInfo] = useState<boolean>(false)
    const [loadingInfo, setLoadingInfo] = useState<boolean>(false)
    const [triggerUri, setTriggerUri] = useState<boolean>(false)
    const [loadingUri, setLoadingUri] = useState<boolean>(false)
    const [triggerLink, setTriggerLink] = useState<boolean>(false)
    const [loadingGeneraArch, setLoadingGeneraArch] = useState<boolean>(false)
    const [linkArchPermisos, setLinkArchPermisos] = useState<boolean>(false)
    const [triggerInfoTrial, setTriggerInfoTrial] = useState<boolean>(false)
    const [triggerProducts, setTriggerProducts] = useState<boolean>(false)
    const [triggerCatNom, setTriggerCatNom] = useState<boolean>(true)
    const [loadingCatNom, setLoadingCatNom] = useState<boolean>(false)

    const [selectedInstitucion, setSelectedInstitucion] = useState<number>(0)
    const [selectedSector, setSelectedSector] = useState<number>(0)
    const [selectedNombre, setSelectedNombre] = useState<number>(0)
    const [selectedUri, setSelectedUri] = useState<string>("")
    const [liga, setLiga] = useState<string>("")
    const [archivo, setArchivo] = useState<string>("")
    const [nombreArch, setNombreArch] = useState<string>("")
    const [dataTable, setDataTable] = useState<DataHistoricoTrial[]>([])

    const [loadingSave, setLoadingSave] = useState<boolean>(false)

    const dispatch = useDispatch()

    useEffect(() => {
        const getDataSector = async () => {
            if(triggerSector) {
                setLoadingSector(true)
                await fetchDataGet(
                    "/admin-user-web/sector",
                    "al obtener catalogo sector",
                    {n_institucion: selectedInstitucion},
                    updateCatSector,
                    dispatch
                )
                setLoadingSector(false)
                setTriggerSector(false)
            }
        }
        getDataSector().catch(() => {});
    }, [triggerSector]);

    useEffect(() => {
        const getDataNombre = async () => {
            if(triggerCatNom) {
                setLoadingCatNom(true)
                await fetchDataGetConsultaData(
                    "/admin-user-web/nombres",
                    "al obtener catalogo nombres",
                    {n_sector: selectedSector},
                    updateCatNom,
                    dispatch
                )
                setLoadingCatNom(false)
                setTriggerCatNom(false)
            }
        }
        getDataNombre().catch(() => {});
    }, [triggerCatNom]);

    useEffect(() => {
        const getDataInfo = async () => {
            if(triggerInfo) {
                setLoadingInfo(true)
                await fetchDataGet(
                    "/admin-user-web/info-user",
                    "al obtener Info Ususario",
                    {n_nombre: selectedNombre},
                    updateInfoUser,
                    dispatch
                )
                setLoadingInfo(false)
                setTriggerInfo(false)
                setTriggerLink(true)
            }
        }
        getDataInfo().catch(() => {});
    }, [triggerInfo]);

    useEffect(() => {
        const getDataUri = async () => {
            if(triggerUri) {
                setLoadingUri(true)
                await fetchDataGet(
                    "/admin-user-web/uri-info",
                    "al obtener Info Uri",
                    {uri: selectedUri},
                    updateUriInfo,
                    dispatch
                )
                setLoadingUri(false)
                setTriggerUri(false)
                setTriggerLink(true)
            }
        }
        getDataUri().catch(() => {});
    }, [triggerUri]);

    useEffect(() => {
        if (triggerLink) {
            const idProceso = uriInfo.id_proceso ?? "";
            const link = "http://www.valmer.com.mx/public/downloadFileUserSystemProcess.do?token=" +
                infoUser.token + "&idProceso=" + idProceso + "&uri=" + selectedUri;
            setLiga(link);
            setTriggerLink(false);
        }
    }, [triggerLink]);

    const handleClickInstitucion = (e: any) => {
        setSelectedSector(0)
        setSelectedNombre(0)
        setSelectedInstitucion(e.target.value)
        setTriggerSector(true)
    }

    const handleClickSector = (e: any) => {
        setTriggerCatNom(true)
        setSelectedNombre(0)
        setSelectedSector(e.target.value)
    }

    const handleClickNombre = (e: any) => {
        dispatch(updateInfoUser({} as InfoUser))
        setSelectedNombre(e.target.value)
        setLiga("")
        setTriggerInfo(true)
        setTriggerLink(true)
        setTriggerInfoTrial(true)
        setTriggerProducts(true)
    }

    const handleChage = <T extends HTMLInputElement | HTMLSelectElement>(e: React.ChangeEvent<T>) => {
        const {name, value} = e.target;
        const updatedInfoUser: InfoUser = {...infoUser, [name]: value};
        dispatch(updateInfoUser(updatedInfoUser));
    }

    const handleSaveUser = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if (selectedNombre != 0) {
            setLoadingSave(true);
            const params = {n_nombre: selectedNombre, 
                            n_tipo_usuario: infoUser.n_tipo_usuario, 
                            s_email: infoUser.email};
            await fetchDataGetAlert(
                "admin-user-web/update-info-user",
                "Guardado",
                "Registro actualizado correctamente",
                " al actualizar datos del usuario",
                params
            )
            setLoadingSave(false);
        } else {
            await showAlert("info", "No hay datos para guardar", "Debe seleccionar un nombre de usuario");
        }
    }

    const handleUri = (e: any) => {
        dispatch(updateUriInfo({} as UriInfo))
        setSelectedUri(e.target.value)
        setLiga("")
        setTriggerUri(true)
        setTriggerLink(true)
    }

    const handleProcesosPermisos = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setLinkArchPermisos(false)
        setLoadingGeneraArch(true)
        const params = {
            n_institucion: selectedInstitucion,
            n_nombre: selectedNombre,
            n_sector: selectedSector
        }
        const response = await fetchDataGetRet(
            "/admin-user-web/generar-archivo-procesos-permisos",
            " al descargar archivo csv",
            params
        )
        setArchivo(Base64.decode(response.body.contenido))
        setNombreArch(response.body.nombreCompleto)
        setLinkArchPermisos(true)
        setLoadingGeneraArch(false)
    }

    const downloadProcesosPermisos = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        fileDownload(archivo, nombreArch);
    }

    return {
        catalogoInst, 
        loadingInst, 
        catNom, 
        loadingCatNom,
        catTipoUser,
        loadingTipoUser,
        catUri,
        loadingCatUri,
        loadingSector,
        selectedInstitucion,
        catSector,
        selectedSector,
        loadingInfo,
        selectedNombre,
        infoUser,
        loadingSave,
        selectedUri,
        loadingUri,
        uriInfo,
        liga,
        loadingGeneraArch,
        linkArchPermisos,
        triggerInfoTrial,
        triggerProducts,
        dataTable,
        handleClickInstitucion,
        handleClickSector,
        handleClickNombre,
        handleChage,
        handleSaveUser,
        handleUri,
        handleProcesosPermisos,
        downloadProcesosPermisos,
        setTriggerInfoTrial,
        setTriggerProducts,
        setDataTable
    }
}