import {useInitValPrecalc} from "./useInitValPrecalc";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {PrecalcAccVin, PrecalcDerCorp, PrecalcFijos, PrecalcSuspendidas, Precalculados, PropsPrecalc, ShowPrecalc} from "../../../../../../../../model";
import React, {useEffect, useState} from "react";
import {fetchDataGetRet, fetchDataPostAct, getEmisoras, getSerie} from "../../../../../../../../utils";
import {updatePrecalculados, updateTriggerPrecalc} from "../../../../../../../../redux";
import Sweet from "sweetalert2";

export const usePrecalcTvEmiSerie = ({setShowState}: PropsPrecalc) => {

    const {
        tv,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        triggerEmisora,
        triggerSerie,
        loadingTv,
        loadingEmisoras,
        loadingSerie,
        setTv,
        setEmisora,
        setSerie,
        setSelectedTv,
        setSelectedEmisora,
        setSelectedSerie,
        setTriggerEmisora,
        setTriggerSerie,
        setLoadingTv,
        setLoadingEmisoras,
        setLoadingSerie,
        dispatch
    } = useInitValPrecalc()

    const precalculados = useSelector(
        (state: RootState<any, any, any>) => state.precalculados
    ) as unknown as Precalculados;

    const tvAcc = useSelector(
        (state: RootState<any, any, any>) => state.selectedTvAcc
    ) as unknown as string;

    const emiAcc = useSelector(
        (state: RootState<any, any, any>) => state.selectedEmisoraAcc
    ) as unknown as string;

    const serieAcc = useSelector(
        (state: RootState<any, any, any>) => state.selectedSerieAcc
    ) as unknown as string;

    const [loadingAct, setLoadingAct] = useState(false)


    useEffect(() => {
        if (!tv || tv.length === 0) {
            const getTv = async () => {
                setLoadingTv(true)
                const response = await fetchDataGetRet(
                    "/instrumentos/tv",
                    " al obtener catalogo tv",
                    {sMercado: "ACC", isNew: false})

                setTv(response.body)
                setLoadingTv(false);
            }
            getTv().then()
        }
    }, [tv]);

    useEffect(() => {
        getEmisoras({
            url: "/acciones/instrumentos/emisoras",
            triggerEmisora,
            selectedTv,
            setTriggerEmisora,
            setLoadingEmisoras,
            setEmisora
        }).then();
    }, [triggerEmisora]);

    useEffect(() => {
        getSerie({
            url: "/acciones/instrumentos/series",
            triggerSerie,
            selectedTv,
            selectedEmisora,
            setTriggerSerie,
            setLoadingSerie,
            setSerie
        }).then();
    }, [triggerSerie]);

    const handleClickTv = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setSelectedEmisora("...")
        setTriggerEmisora(true);
        setSelectedTv(e.target.value)
    };

    const handleEmisora = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setSelectedSerie("...")
        setTriggerSerie(true);
        setSelectedEmisora(e.target.value)
    };


    const handleSerie = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setSelectedSerie(e.target.value)
    };

    const inputValue = (namePrecalc: any, name: string) => {
        const existPrecalc = precalculados?.[namePrecalc]?.[0]

        if (existPrecalc) {
            return existPrecalc[name]
        } else {
            return ""
        }

    }

    const selectValue = (namePrecalc: any, 
                         nameVin: string, 
                         name: string
                        ): string => {

        const existPrecalc = precalculados?.[namePrecalc]?.[0]

        if (existPrecalc) {
            const instrumento = existPrecalc[nameVin]?.split("_")
            
            if (instrumento) {
                switch (name) {
                    case "tv":
                        setSelectedTv(instrumento[0])
                        setTriggerEmisora(true)
                        return selectedTv
                    case "emisora":
                        setSelectedEmisora(instrumento[1])
                        setTriggerSerie(true)
                        return selectedEmisora
                    case "serie":
                        setSelectedSerie(instrumento[2])
                        return selectedSerie
                    default:
                        return ""
                }
            }
        }
        return ""
    }

    const validateFieldInst = (fields: string[]): boolean => {
        const isValid = fields.map(field => {
            return field != "" && field != "default" && field != "..."
        })

        if (isValid.some(valor => !valor)) {
            Sweet.fire({
                title: "Falta ingresar campo",
                icon: "warning"
            })
            return false
        } else {
            return true
        }
    }

    const validateField = (field: string | number | undefined): boolean => {
            return field !== "" && field !== undefined
    }

    const validateCampos = (campos: any[]): boolean => {
        if (!campos.every(validateField)) {
            Sweet.fire({ 
                title: "Falta ingresar campo",
                icon: "warning"
            })
            return false
        } else {
            return true
        }
    }

    const cleanUpdate = () => {
        setShowState({
            vinculado: false,
            adr: false,
            suspendido: false,
            fijo: false,
            derCorp: false
        } as ShowPrecalc)
        dispatch(updateTriggerPrecalc(true))
    }

    const actPrecalc = async (name: string) => {
        setLoadingAct(true)
        const url = "/acciones/instrumentos/actualiza-"
        const title = "Actualizado"
        const message = " al actualizar "
        const inst = [selectedTv, selectedEmisora, selectedSerie]
        const adrFields = [precalculados["precalc-acciones-vinculadas"]?.[0].prpoporcion]
        const suspFields = [precalculados["precalc-suspendidas"]?.[0].fecha_suspension]
        const fijoFields = [precalculados["precalc-fijos"]?.[0].n_precio]
        const dercorpFields = [
            precalculados["precalc-derecho-corp"]?.[0].n_monto_decorp,
            precalculados["precalc-derecho-corp"]?.[0].fecha_dercorp
        ]
        switch (name) {
            case "vinculados":
                if (validateFieldInst(inst)) {
                    await fetchDataPostAct(
                        url + name,
                        title,
                        message + "Vinculados",
                        [],
                        {
                            s_tv: tvAcc,
                            s_tv_vin: selectedTv,
                            s_emisora: emiAcc,
                            s_emisora_vin: selectedEmisora,
                            s_serie: serieAcc,
                            s_serie_vin: selectedSerie
                        }
                    )
                    cleanUpdate()
                    break
                } else {
                    break
                }
            case "adr":
                if (validateFieldInst(inst) && validateCampos(adrFields)) {
                    await fetchDataPostAct(
                        url + name,
                        title,
                        message + "ADR",
                        [],
                        {
                            s_tv: tvAcc,
                            s_tv_adr: selectedTv,
                            s_emisora: emiAcc,
                            s_emisora_adr: selectedEmisora,
                            s_serie: serieAcc,
                            s_serie_adr: selectedSerie,
                            n_proporcion_adr: precalculados["precalc-acciones-vinculadas"]?.[0].prpoporcion
                        }
                    )
                    cleanUpdate()
                    break
                } else {
                    break
                }
            case "suspendidos":
                if (validateCampos(suspFields)) {
                    await fetchDataPostAct(
                        url + name,
                        title,
                        message + "Suspendido",
                        [],
                        {
                            s_tv: tvAcc,
                            s_emisora: emiAcc,
                            s_serie: serieAcc,
                            d_susp_fecha: precalculados["precalc-suspendidas"]?.[0].fecha_suspension
                        }
                    )
                    cleanUpdate()
                    break
                } else {
                    break
                }
            case "fijos":
                if (validateCampos(fijoFields)) {
                    await fetchDataPostAct(
                        url + name,
                        title,
                        message + "Fijo",
                        [],
                        {
                            s_tv: tvAcc,
                            s_emisora: emiAcc,
                            s_serie: serieAcc,
                            n_fijo_precio: precalculados["precalc-fijos"]?.[0].n_precio
                        }
                    )
                    cleanUpdate()
                    break
                } else {
                    break
                }
            case "derecho-corp":
                if (validateCampos(dercorpFields)) {
                    await fetchDataPostAct(
                        url + name,
                        title,
                        message + "Der.Corp",
                        [],
                        {
                            s_tv: tvAcc,
                            s_emisora: emiAcc,
                            s_serie: serieAcc,
                            n_der_corp_monto: precalculados["precalc-derecho-corp"]?.[0].n_monto_decorp,
                            d_der_corp_fecha: precalculados["precalc-derecho-corp"]?.[0].fecha_dercorp
                        }
                    )
                    cleanUpdate()
                    break;
                } else {
                    break
                }
            default:
                return 0
        }
        setLoadingAct(false)
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>, precalc: string) => {
        const { name, value } = e.target;
    
        const dataup: Precalculados = { ...precalculados };
    
        const elements = dataup[precalc];
    
        if (Array.isArray(elements) && elements.length > 0) {
            const firstElement = elements[0];

            if (firstElement !== undefined && firstElement !== null) {
                const updatedFirstElement = { ...firstElement };

                switch (precalc) {
                    case "precalc-acciones-vinculadas":
                        updatedFirstElement[name as keyof PrecalcAccVin] = value;
                        break;
                    case "precalc-suspendidas":
                        updatedFirstElement[name as keyof PrecalcSuspendidas] = value;
                        break;
                    case "precalc-fijos":
                        updatedFirstElement[name as keyof PrecalcFijos] = value;
                        break;
                    case "precalc-derecho-corp":
                        updatedFirstElement[name as keyof PrecalcDerCorp] = value;
                        break;
                    default:
                        break;
                }

                const updatedDataup: Precalculados = {
                    ...dataup,
                    [precalc]: [updatedFirstElement, ...elements.slice(1)],
                };
    
                dispatch(updatePrecalculados(updatedDataup));
                return;
            }
        }

        const newDataElement = {
            [name]: value,
        };

        const updatedDataup: Precalculados = {
            ...dataup,
            [precalc]: [newDataElement]
        };

        dispatch(updatePrecalculados(updatedDataup));
    };

    return {
        tv,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        loadingTv,
        loadingEmisoras,
        loadingSerie,
        loadingAct,
        handleClickTv,
        handleEmisora,
        handleSerie,
        handleChange,
        inputValue,
        selectValue,
        actPrecalc
    }


}