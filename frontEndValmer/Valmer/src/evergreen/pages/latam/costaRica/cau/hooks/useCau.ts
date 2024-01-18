import React, {useEffect} from "react";
import {FolioCau, ResponseFolioCau, ResponseMantCau} from "../../../../../../model";
import {fetchDataGetRet, fetchDataPost, showAlert, userEncoded} from "../../../../../../utils";
import {useCauInitVar} from "./useCauInitVar";
import Sweet from "sweetalert2";

export const useCau = () => {

    const {
        selectedEnterprise, setSelectedEnterprise,
        status, setStatus,
        isEdit, setIsEdit,
        loadingSave, setLoadingSave,
        loadingCatalogo, setLoadingCatalogo,
        loadingInitSol, setLoadingInitSol,
        loadingOpened, setLoadingOpened,
        loadingClosed, setLoadingClosed,
        loadingModifies, setLoadingModifies,
        loadingFolio, setLoadingFolio,
        catalog, setCatalog,
        tableOpened, setTableOpened,
        queryFolio, setQueryFolio
    } = useCauInitVar();


    useEffect(() => {
        const getCatalogs = async () => {
            setLoadingCatalogo(true);
            const response = await fetchDataGetRet(
                "/latam/cr/mantenimiento-cau/catalogos",
                " al obtener catalogos mantenimiento cau",
                {})

            setCatalog(response.body.catalogos);
            setLoadingCatalogo(false);
        }

        if (!catalog || catalog.length === 0) {
            getCatalogs().then();
        }
    }, []);

    useEffect(() => {
        if (!tableOpened || tableOpened.length === 0) {
            getOpened("0", setLoadingInitSol).then();
        }
    }, []);

    useEffect(() => {
        validStatus("0").then();
    }, [status]);

    const getOpened = async (emp: string, setLoading: (arg: boolean) => void) => {
        setLoading(true);
        const response: ResponseMantCau = await fetchDataGetRet(
            "/latam/cr/mantenimiento-cau/abiertos/tabla-solicitudes",
            " al obtener solicitudes abiertas",
            {n_emp: emp})

        setTableOpened(response.body);
        setLoading(false);
    }

    const getClosed = async (emp: string) => {
        setLoadingClosed(true);
        const response: ResponseMantCau = await fetchDataGetRet(
            "/latam/cr/mantenimiento-cau/cerrados/tabla-solicitudes",
            " al obtener solicitudes cerradas",
            {n_emp: emp})

        setTableOpened(response.body);
        setLoadingClosed(false);
    }

    const getModifies = async (emp: string) => {
        setLoadingModifies(true);
        const response: ResponseMantCau = await fetchDataGetRet(
            "/latam/cr/mantenimiento-cau/modificados/tabla-solicitudes",
            " al obtener solicitudes cerradas",
            {n_emp: emp})

        setTableOpened(response.body);
        setLoadingModifies(false);
    }

    const handleClickFolio = async (folio: number) => {
        setLoadingFolio(true)
        let url = "";

        if(status == "Abiertos")
        {
            url = "/latam/cr/mantenimiento-cau/abiertos/consulta-folio"
        }
        else if (status == "Modificación")
        {
            url = "/latam/cr/mantenimiento-cau/modificados/consulta-folio"
        }
        else if (status == "Cerrados")
        {
            url = "/latam/cr/mantenimiento-cau/cerrados/consulta-folio"
        }

        const response: ResponseFolioCau = await fetchDataGetRet(
            url,
            " al obtener informacion folio",
            {n_folio: folio, s_user: userEncoded()})

        setIsEdit(true)
        setQueryFolio(response.body);
        setLoadingFolio(false)
    }

    const handleClickFolioCerrados = async (folio: number) => {
        setLoadingFolio(true)
        const response: ResponseFolioCau = await fetchDataGetRet(
            "/latam/cr/mantenimiento-cau/cerrados/consulta-folio",
            " al obtener informacion folio",
            {n_folio: folio, s_user: userEncoded()})

        setIsEdit(true)
        setQueryFolio(response.body);
        setLoadingFolio(false)
    }

    const handleEnterprise = async (e: React.ChangeEvent<HTMLSelectElement>) => {
        if(e.target.value == "...")
        {
            setSelectedEnterprise("0")
        }
        else
        {
            setSelectedEnterprise(e.target.value)
        }
        await validStatus(e.target.value)
    }

    const handleStatus = async (status: string) => {
        setStatus(status)
    }

    const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const {name, value} = event.target;

        setQueryFolio(prevData => ({
            ...prevData,
            n_emp: selectedEnterprise,
            [name]: value,
        }));
    };

    const handleServicio = (servicio: string) => {
        const selectCatalog = catalog.find((item) =>
            item.catalogo === "INTRANET.CR_CAU_SERVICIOS");

        if (selectCatalog) {
            const id = Object.keys(selectCatalog.registros).find(key =>
                selectCatalog.registros[key] === servicio);

            if (id) {
                return id;
            }
        }

        return "0"
    }

    const handleSave = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if (queryFolio.n_status !== "default") {
            setLoadingSave(true);
    
            let modifiedQueryFolio = { ...queryFolio };
    
            if (isNaN(Number(modifiedQueryFolio.n_servicio))) {
                modifiedQueryFolio.n_servicio = handleServicio(modifiedQueryFolio.n_servicio).toString();
            }
    
            await fetchDataPost(
                "/latam/cr/mantenimiento-cau/modificados/modifica-solicitud",
                " al intentar actualizar status modificados",
                modifiedQueryFolio,
                { s_user: userEncoded() }
            );
    
            setLoadingSave(false);
            return
        }

        await showAlert('warning', 'Faltan Campos', 'Debe seleccionar un status');
    };

    const eraseFilters = () => {
        setIsEdit(false)
        setSelectedEnterprise("")
        setQueryFolio({} as FolioCau);
        setStatus("Abiertos")
    }

    const validStatus = async (emp: string) => {
        if (status === "Abiertos" || status === "") {
            setIsEdit(false)
            setQueryFolio({} as FolioCau);
            await getOpened(emp, setLoadingOpened)
        }
        else if (status === "Cerrados")
        {
            setIsEdit(false)
            setQueryFolio({} as FolioCau);
            await getClosed(emp)
        }
        else if (status === "Modificación") {
            setIsEdit(false)
            setQueryFolio({} as FolioCau);
            await getModifies(emp)
        }
    }


    return {
        status, selectedEnterprise, queryFolio,
        tableOpened, catalog,
        isEdit, loadingSave,
        loadingCatalogo, loadingInitSol,
        loadingOpened, loadingClosed,
        loadingModifies, loadingFolio,
        handleClickFolio, handleEnterprise,
        handleStatus, handleChange,
        handleSave, eraseFilters,
        handleClickFolioCerrados
    }


}