import React, {useEffect, useState} from "react";
import {
    FvDeudaCorpInstrumentos,
    InstrumentosType,
    IsFieldModifiedFvDdCorpIns, IsFieldModifiedFvDdGobIns,
    ResponseConsultaDataCorp
} from "../../../../../../../../../../model";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import {
    fetchDataPost,
    getCurrentDate,
    handleParseMayus,
    userEncoded,
    validChangeTvEmiSerie
} from "../../../../../../../../../../utils";
import {
    updateConsultaDataCorp,
    updateFieldRequiredCorp,
    updateFormValuesCorp, updateIsFrecBimestral, updateIsInterBonosCorp
} from "../../../../../../../../../../redux";

interface ConsultaData {
    body: {
        [key: string]: string | any[];
    };
}
export const useHandleDataCorp = () => {

    const isNewFormCorp = useSelector((state: RootState<any, any, any>) =>
        state.isNewFormCorp) as unknown as boolean;

    const formValues = useSelector((state: RootState<any, any, any>) =>
        state.formValuesCorp) as unknown as FvDeudaCorpInstrumentos;

    const consultaData = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataCorp) as unknown as ResponseConsultaDataCorp;

    const fieldRequiredCorp = useSelector((state: RootState<any, any, any>) =>
        state.fieldRequiredCorp) as unknown as IsFieldModifiedFvDdGobIns;

    const [loadingSubmitCorpRW, setLoadingSubmitCorpRW] = useState(false)

    const isInterBonos = useSelector((state: RootState<any, any, any>) =>
        state.isInterBonos) as unknown as boolean;

    const [isFieldModified, setIsFieldModified] =
        useState<IsFieldModifiedFvDdCorpIns>({} as IsFieldModifiedFvDdCorpIns);

    const [lastModifiedField, setLastModifiedField] = useState("");
    
    const dispatch = useDispatch()

    const inputValueEval = (fieldName: string, 
                            formValues: InstrumentosType,
                            consultaData: ConsultaData | undefined,
                            isFieldModified: boolean): string => {
        if (isFieldModified && formValues[fieldName] !== undefined && formValues[fieldName] !== '')
        {
            return <string>formValues[fieldName];
        }
        else if (formValues[fieldName] === '')
        {
            return '';
        }
        else
        {
            return <string>consultaData?.body?.[fieldName] ?? '';
        }
    }

    const checkboxValueEval = (fieldName: string,
                               consultaData: ConsultaData | undefined) : boolean => {
        const fieldValue = consultaData?.body?.[fieldName]?.toString();
        return fieldValue === '1';
    }

    const handleNumericChange = <T extends HTMLInputElement | HTMLSelectElement>(
        e: React.ChangeEvent<T>,
        callback: (e: React.ChangeEvent<T>) => void
    ) => {
        const value = e.target.value;

        if (!isNaN(Number(value))) {
            callback(e);
        }
    };

    const handleChange = <T extends HTMLInputElement | HTMLSelectElement> (e: React.ChangeEvent<T>) => {
        const { name, value} = e.target;
        let formattedValue = value;

       


        setLastModifiedField(name);
        setIsFieldModified((prevModified) => ({ ...prevModified, [name]: true }));

        const fieldRequiredData: IsFieldModifiedFvDdGobIns = {...fieldRequiredCorp, [name]: false}
        dispatch(updateFieldRequiredCorp(fieldRequiredData))

        validChangeTvEmiSerie(name, dispatch)

        const fieldsToFormat = ['s_emisora', 's_serie'];

        if (fieldsToFormat.includes(name)) {
            formattedValue = value.replace(/\s+/g, '').toUpperCase();
        }

        let updatedData: ResponseConsultaDataCorp = { body: { ...consultaData.body, [name]: formattedValue } };

        if (name === "s_isin"){
            const valorMayus = handleParseMayus(e)
            const updateMayus: ResponseConsultaDataCorp = { body: {...consultaData.body, [name]: valorMayus} };
            dispatch(updateConsultaDataCorp(updateMayus));
        }

        if (name === 'n_plazo') {
            const dateChanges = handleDateLogic(value, consultaData.body.d_fecha_emision);
            if (dateChanges) {
                updatedData.body = { ...updatedData.body, ...dateChanges };
            }
        }

        if (name === 'n_titulos_iniciales') {
            const tituloChanges = handleTituloLogic(value);
            if (tituloChanges) {
                updatedData.body = { ...updatedData.body, ...tituloChanges };
            }
        }

        if (name === 'd_fecha_emision') {
            const tituloChanges = handleTituloLogic(value);
            if (tituloChanges) {
                updatedData.body = { ...updatedData.body, ...tituloChanges };
            }
        }

        if (name === 'n_frecuencia_cupon' && isNewFormCorp)
        {
            const frecuenciaChanges = handleFrecuenciaCupon(value);
            if (frecuenciaChanges) {
                updatedData.body = { ...updatedData.body, ...frecuenciaChanges };
            }
        }

        dispatch(updateConsultaDataCorp(updatedData));
    };

    useEffect(() => {
        if (validFieldReferencia(lastModifiedField)) {
            dispatch(updateIsInterBonosCorp(true));
        } else {
            dispatch(updateIsInterBonosCorp(false));
        }
    }, [consultaData, lastModifiedField]);

    const validFieldReferencia = (name: string) => {
        return consultaData.body?.n_referencia_mercado === '54'||
            name === 's_tv_ref_1' ||
            name === 's_emisora_ref_1' ||
            name === 's_serie_ref_1' ||
            name === 's_tv_ref_2' ||
            name === 's_emisora_ref_2' ||
            name === 's_serie_ref_2'
    }


    const handleFrecuenciaCupon = (value: string) => {
        dispatch(updateIsFrecBimestral(false))
        switch (value) {
            case '5':
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: consultaData.body.d_fecha_ini_cupon ?? getCurrentDate(),
                    n_num_cupones: '1',
                    n_periodo_cupon: consultaData.body.n_plazo ?? "",
                    n_periodo_cupon_v: consultaData.body.n_plazo ?? ""
                }
            case '14': {
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: addDaysToDate(364),
                    n_num_cupones: '1',
                    n_periodo_cupon: '364',
                    n_periodo_cupon_v: '364'
                }
            }
            case '9': {
                dispatch(updateIsFrecBimestral(true))
                return {
                    d_fecha_fin_cupon: addDaysToDate(60),
                    n_num_cupones: '7',
                    n_periodo_cupon: '60',
                    n_periodo_cupon_v: '60'
                }
            }
            case '10':
            case '3': {
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: addDaysToDate(182),
                    n_num_cupones: '2',
                    n_periodo_cupon: '182',
                    n_periodo_cupon_v: '182'
                }
            }
            case '11':
            case '1': {
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: addDaysToDate(28),
                    n_num_cupones: '16',
                    n_periodo_cupon: '28',
                    n_periodo_cupon_v: '28'
                }
            }
            case '13':
            case '4': {
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: addDaysToDate(360),
                    n_num_cupones: '1',
                    n_periodo_cupon: '360',
                    n_periodo_cupon_v: '360'
                }
            }
            case '12':
            case '2': {
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: addDaysToDate(91),
                    n_num_cupones: '5',
                    n_periodo_cupon: '91',
                    n_periodo_cupon_v: '91'
                }
            }
            case '6': {
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: addDaysToDate(30),
                    n_num_cupones: '15',
                    n_periodo_cupon: '30',
                    n_periodo_cupon_v: '30'
                }
            }
            case '8': {
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: addDaysToDate(180),
                    n_num_cupones: '2',
                    n_periodo_cupon: '180',
                    n_periodo_cupon_v: '180'
                }
            }
            case '7': {
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: addDaysToDate(180),
                    n_num_cupones: '5',
                    n_periodo_cupon: '90',
                    n_periodo_cupon_v: '90'
                }
            }
            default:
                return {
                    n_dia_corte_cupon: "",
                    d_fecha_fin_cupon: "",
                    n_num_cupones: "",
                    n_periodo_cupon: '0',
                    n_periodo_cupon_v: '0'
                }
        }
    }

    const addDaysToDate = (daysToAdd: number) => {
        const date = new Date(consultaData.body.d_fecha_ini_cupon ?? getCurrentDate());
        date.setDate(date.getDate() + daysToAdd);
        return date.toISOString().split('T')[0];
    };

    const handleTituloLogic = (value: string): { [key: string]: string } | null => {

        const nTitulosIniciales = parseInt(value, 10);
        const isZeroOrUndefined = isNaN(nTitulosIniciales) || nTitulosIniciales === 0;

        return {
            n_titulos_circulacion: isZeroOrUndefined ? value : nTitulosIniciales.toString(),
            n_monto_emitido: isZeroOrUndefined ? '0' : (nTitulosIniciales * 100).toString(),
            n_monto_circulacion: isZeroOrUndefined ? '0' : (nTitulosIniciales * 100).toString()
        };
    }


    const handleDateLogic = (value: string, dateEmission: any) =>{

        const nPlazo = parseInt(value, 10);
        if (isNaN(nPlazo)) return null;

        const dFechaEmision = new Date(dateEmission);
        const dFechaVto = new Date(dFechaEmision);
        const dFechaVtoEstimada = new Date(dFechaEmision);

        if (nPlazo > 0) {
            dFechaVto.setDate(dFechaEmision.getDate() + nPlazo);
            dFechaVtoEstimada.setDate(dFechaEmision.getDate() + nPlazo);
        } else {
            dFechaVto.setDate(dFechaEmision.getDate());
            dFechaVtoEstimada.setDate(dFechaEmision.getDate());
        }

        return {
            d_fecha_vto: dFechaVto.toISOString().split('T')[0],
            d_fecha_vto_estimada: dFechaVtoEstimada.toISOString().split('T')[0],
        };
    }



    const handleTvNewForm = (value: string) => {
        const updatedValues: FvDeudaCorpInstrumentos = { ...formValues, "s_tv": value };
        const updatedData = { body: {...consultaData.body, "s_tv": value} };
        dispatch(updateConsultaDataCorp(updatedData));
        dispatch(updateFormValuesCorp(updatedValues));
        setIsFieldModified((prevModified) => ({ ...prevModified, "s_tv": true }));
        validChangeTvEmiSerie("s_tv", dispatch)
    };

    const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = e.target;
        const updatedData = { body: {...consultaData.body, [name]: checked ? '1' : '0'} };
        dispatch(updateConsultaDataCorp(updatedData));

        const fieldRequiredData: IsFieldModifiedFvDdGobIns = {...fieldRequiredCorp, [name]: false}
        dispatch(updateFieldRequiredCorp(fieldRequiredData))
    };

    const handleSubmitRW = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setLoadingSubmitCorpRW(true)
        await fetchDataPost('/instrumentos/corporativos/guarda-info-rw',
            "al intentar guardar datos RW",
            consultaData.body,
            {s_user: userEncoded()})
        setLoadingSubmitCorpRW(false)
    };

    return {
        isInterBonos,
        isFieldModified,
        loadingSubmitCorpRW,
        consultaData,
        handleParseMayus,
        inputValueEval,
        checkboxValueEval,
        handleChange,
        handleNumericChange,
        handleCheckboxChange,
        handleSubmitRW,
        handleTvNewForm,
    }
}