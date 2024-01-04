import {
    AccCalifLatam,
    Acciones,
    AccionesAdd,
    Catalogo,
    CatalogoSubastas,
    ClienteInfo,
    DataTypeKey,
    FormValuesCorp,
    FormValuesDerivados,
    FormValuesInter,
    FvDeudaCorpInstrumentos,
    FvDeudaGobInstrumentos,
    FvInterInstrumentos,
    initialFormValues,
    initialFormValuesCorp,
    initialFormValuesInter,
    InstrumentosDerivadosType,
    InstrumentosType,
    IsFieldModifiedFvDdGobIns,
    IsFieldModifiedFvDerivados,
    IsFieldModifiedFvInterIns,
    IsFieldReqCalifInst,
    IsFieldReqCalifProg,
    IsFieldRequiredAccInst,
    IsFieldRequiredLatPanama,
    RefReqAccCalifLatam,
    RequeridosCorp,
    RequeridosDefDerivados,
    RequeridosDerivados,
    ResponseConsultaData,
    ResponseConsultaDataCorp,
    ResponseConsultaDataInter,
    Revisar1Data,
    Revisar1Response,
    Revisar2Data,
    Revisar2Response,
    RevisarData,
    SectionType,
    SortableFields
} from "../model";
import {
    updateConsultaData,
    updateConsultaDataCorp,
    updateConsultaDataInter,
    updateFormValuesCorp,
    updateFormValuesInst,
    updateFormValuesInter,
    updateRequiredEmisora,
    updateRequiredEmisoraAcc,
    updateRequiredEmisoraCalifInst,
    updateRequiredEmisoraCorp,
    updateRequiredEmisoraDer,
    updateRequiredEmisoraInter,
    updateRequiredSerie,
    updateRequiredSerieAcc,
    updateRequiredSerieCalifInst,
    updateRequiredSerieCorp,
    updateRequiredSerieDer,
    updateRequiredSerieInter,
    updateRequiredTv,
    updateRequiredTvAcc,
    updateRequiredTvCalifInst,
    updateRequiredTvCalifProg,
    updateRequiredTvCorp,
    updateRequiredTvDer,
    updateRequiredTvInter
} from "../redux";
import {Dispatch} from "redux";
import dayjs from 'dayjs'
import 'dayjs/locale/es'
// @ts-ignore
import _ from "lodash";
import {v4 as uuidv4} from "uuid";
import Sweet from "sweetalert2";
import React, {ChangeEvent} from "react";
import {fetchDataPost} from "./UtilsAxios";
import {ActionCreatorWithPayload} from "@reduxjs/toolkit";
import {
    validAccionesInstField, 
    validCalifInstField, 
    validCorpField, 
    validDefDerivadosField, 
    validDerivadosField, 
    validGubField, 
    validInternacionalField, 
    validProgramasField} from "./ValidFields";
import { validLatPamanaField } from "./ValidFields/Latam/Panama";
import { useDispatch } from "react-redux";

export const generateUUID = (): string => {
    return uuidv4();
};

export const userEncoded = () => {
    if ('userEncoded' in localStorage) {
        return localStorage.getItem('userEncoded');
    } else {
        console.log('No userEncoded item in local storage.');
        return "";
    }
}
export const getCurrentDate = () => {
    return dayjs().format('YYYY-MM-DD');
}
export const handleFileChange = (event: ChangeEvent<HTMLInputElement>,
                                 setFileBase64: React.Dispatch<React.SetStateAction<string>>,
                                 setSelectedFile: React.Dispatch<React.SetStateAction<File | null>>) => {
    const file = event.target.files?.[0];

    if (file) {
        const reader = new FileReader();
        reader.onloadend = () => {
            setFileBase64(reader.result as string);
            setSelectedFile(file);
        }
        reader.readAsDataURL(file);
    }

    event.target.value = '';
};

export const handleFileChangeCalif = (event: ChangeEvent<HTMLInputElement>, section: string,
                                      setFileBase64: React.Dispatch<React.SetStateAction<string | null>>,
                                      setSection: React.Dispatch<React.SetStateAction<SectionType | "">>,
                                      setSelectedFile: React.Dispatch<React.SetStateAction<File | null>>) => {
    const file = event.target.files?.[0];

    if (file) {
        const reader = new FileReader();
        reader.onloadend = () => {
            const base64Data = reader.result as string;
            const pureBase64 = base64Data.split(',')[1];
            setFileBase64(pureBase64);

            setSelectedFile(file);
            setSection(section as SectionType);
        };
        reader.readAsDataURL(file);
    }
};

export const handleSubmitCalif = async (e: React.FormEvent<HTMLFormElement>,
                                        formValues: object,
                                        setLoading: React.Dispatch<React.SetStateAction<boolean>>) => {
    e.preventDefault();
    setLoading(true)
    await fetchDataPost("/instrumentos/guarda-info-calif",
        "al intentar guardar info calif",
        formValues,
        {s_user: userEncoded()})
    setLoading(false)
}

export const subMenuClose = (item: any, isOpen: boolean): any => {

    if (item.subMenu && isOpen) {
        return item.iconOpen
    } else {
        return item.subMenu ? item.iconClose : null
    }
}

export const capitalizeDate = (): string => {
    const date = dayjs().locale('es');
    const formattedDate = date.format("dddd[,] D [de] MMMM [de] YYYY");
    return _.startCase(formattedDate.toLowerCase()).replaceAll("De", "de");
}

export const capitalizeText = (text: string) => {
    const lowerCaseText = text.toLowerCase();
    const replacedText = lowerCaseText.replace(/_/g, ' ');
    return replacedText.replace(/\b\w/g, (match) =>
        match.toUpperCase()
    );
};

export const showAlert = (iconType: any, title: string, text: string | undefined): Promise<void> => {
    return new Promise<void>((resolve, reject) => {
        Sweet.fire({
            icon: iconType,
            title: title,
            html: text,
        })
            .then(() => {
                resolve();
            })
            .catch((error) => {
                reject(error);
            });
    });
};

export const eraseFormInsValues = (formValuesIns: InstrumentosType, dispatch: Dispatch) => {

    if (formValuesIns !== null && Object.keys(formValuesIns).length > 0) {
        const newFormValues: FvDeudaGobInstrumentos = {...initialFormValues};
        const newFormValuesCorp: FvDeudaCorpInstrumentos = {...initialFormValuesCorp};
        const newFormValuesInter: FvInterInstrumentos = {...initialFormValuesInter};
        Object.keys(formValuesIns).forEach((key) => {
            if (Array.isArray(formValuesIns[key])) {
                // @ts-ignore
                newFormValues[key] = [] as string[];
                // @ts-ignore
                newFormValuesCorp[key] = [] as string[];
                // @ts-ignore
                newFormValuesInter[key] = [] as string[];
            } else {
                newFormValues[key] = "";
                newFormValuesCorp[key] = "";
                newFormValuesInter[key] = "";
            }
        });

        dispatch(updateFormValuesInst(newFormValues));
        dispatch(updateFormValuesCorp(newFormValuesCorp));
        dispatch(updateFormValuesInter(newFormValuesInter));
    }
}

export const getCatalogsNoOrder = (catalog: Catalogo[], c: string) => {
    let selectCatalog = catalog.find((item) => item.catalogo === c);
    return Object.entries(selectCatalog?.registros ?? {});
}

export const getCatalogs = (catalog: Catalogo[] | undefined, c: string | undefined) => {
    if (!catalog || !c) return [];

    const selectCatalog = catalog.find((item) => item.catalogo === c);
    if (!selectCatalog) return [];

    const entries = Object.entries(selectCatalog?.registros ?? {});

    entries.sort((a, b) => {
        if (a[1] === 'SD' || b[1] === 'SD') return a[1] === 'SD' ? 1 : -1;
        return a[1].localeCompare(b[1]);
    });

    return entries;
};

export const getCatalogsSub = (catalog: CatalogoSubastas[], c: string) => {
    let selectCatalog = catalog.find((item) => item.catalogo === c);
    return Object.entries(selectCatalog?.listado ?? selectCatalog?.registros ?? {});
}

export const validChangeTvEmiSerie = (name: string, dispatch: Dispatch) => {
    if (name === 's_tv') {
        dispatch(updateRequiredTv(false))
        dispatch(updateRequiredTvCorp(false))
        dispatch(updateRequiredTvInter(false))
        dispatch(updateRequiredTvDer(false))
        dispatch(updateRequiredTvAcc(false))
        dispatch(updateRequiredTvCalifProg(false))
        dispatch(updateRequiredTvCalifInst(false))
    }
    if (name === 's_emisora') {
        dispatch(updateRequiredEmisora(false))
        dispatch(updateRequiredEmisoraCorp(false))
        dispatch(updateRequiredEmisoraInter(false))
        dispatch(updateRequiredEmisoraDer(false))
        dispatch(updateRequiredEmisoraAcc(false))
        dispatch(updateRequiredEmisoraCalifInst(false))
    }
    if (name === 's_serie') {
        dispatch(updateRequiredSerie(false))
        dispatch(updateRequiredSerieCorp(false))
        dispatch(updateRequiredSerieInter(false))
        dispatch(updateRequiredSerieDer(false))
        dispatch(updateRequiredSerieAcc(false))
        dispatch(updateRequiredSerieCalifInst(false))
    }
}

export const focusElement = (
    name: string,
    requerido: React.MutableRefObject<HTMLInputElement | HTMLSelectElement | null>
) => {
    if (requerido.current && requerido.current.name == name) {
        requerido.current.focus()
    }
}

export const validateFormFields = async (
    formValues: InstrumentosType,
    dispatch: Dispatch,
    requeridos: RequeridosCorp,
    fieldRequired?: IsFieldModifiedFvDdGobIns,
    fieldRequiredCorp?: IsFieldModifiedFvDdGobIns,
    fieldRequiredInter?: IsFieldModifiedFvInterIns) => {


    if (!formValues?.s_tv || formValues.s_tv == 'default') {
        dispatch(updateRequiredTv(true));
        dispatch(updateRequiredTvCorp(true));
        dispatch(updateRequiredTvInter(true))
        focusElement("s_tv", requeridos.s_tv)
        return false;
    }

    if (!formValues.s_emisora || formValues.s_emisora == 'default') {
        dispatch(updateRequiredEmisora(true));
        dispatch(updateRequiredEmisoraCorp(true));
        dispatch(updateRequiredEmisoraInter(true));
        focusElement("s_emisora", requeridos.s_emisora)
        return false;
    }

    if (!formValues.s_serie || formValues.s_serie == 'default') {
        dispatch(updateRequiredSerie(true));
        dispatch(updateRequiredSerieCorp(true));
        dispatch(updateRequiredSerieInter(true));
        focusElement("s_serie", requeridos.s_serie)
        return false;
    }

    if (fieldRequired) {
        return validGubField(formValues, dispatch, fieldRequired)
    }

    if (fieldRequiredCorp) {
        return validCorpField(formValues, dispatch, fieldRequiredCorp, requeridos)
    }

    if (fieldRequiredInter) {
        return validInternacionalField(formValues, dispatch, fieldRequiredInter)
    }
    return true;
}

export const validateFormDerivadosFields = async (
    formValues: InstrumentosDerivadosType,
    dispatch: Dispatch,
    requeridos: RequeridosDerivados | RequeridosDefDerivados,
    isTeoricos: boolean,
    isSerie: boolean,
    fieldRequiredDerivados?: IsFieldModifiedFvDerivados,
    fieldRequiredDefDerivados?: IsFieldModifiedFvDerivados) => {

    if (!formValues?.s_tv || formValues.s_tv == 'default') {
        dispatch(updateRequiredTvDer(true))
        focusElement("s_tv", requeridos.s_tv)
        return false;
    }
    if (!formValues.s_emisora || formValues.s_emisora == 'default') {
        dispatch(updateRequiredEmisoraDer(true))
        focusElement("s_emisora", requeridos.s_emisora)
        return false;
    }
    if (isSerie && (!formValues.s_serie || formValues.s_serie == 'default')) {
        dispatch(updateRequiredSerieDer(true))
        focusElement("s_serie", requeridos.s_serie)
        return false;
    }
    if (fieldRequiredDerivados) {
        return validDerivadosField(formValues, dispatch, fieldRequiredDerivados, requeridos, isTeoricos)
    }
    if (fieldRequiredDefDerivados) {
        return validDefDerivadosField(formValues, dispatch, fieldRequiredDefDerivados, requeridos)
    }
    return true;
}

export const validateFieldsAccCalifLatam = async (
    formValues: AccCalifLatam,
    requeridos: RefReqAccCalifLatam,
    isEmisora: boolean,
    isSerie: boolean,
    dispatch?: Dispatch,
    fieldRequiredAccInst?: IsFieldRequiredAccInst,
    fieldRequiredCalifProg?: IsFieldReqCalifProg,
    fieldRequiredCalifInst?: IsFieldReqCalifInst,
    fieldRequiredLatPanama?: IsFieldRequiredLatPanama,
    setFieldRequiredLatPanama?: React.Dispatch<React.SetStateAction<IsFieldRequiredLatPanama>>) => {

    if (dispatch) {
        if (!formValues?.s_tv || formValues.s_tv == 'default') {
            dispatch(updateRequiredTvAcc(true))
            dispatch(updateRequiredTvCalifProg(true))
            dispatch(updateRequiredTvCalifInst(true))
            focusElement("s_tv", requeridos.s_tv)
            return false;
        }
        if (isEmisora && (!formValues.s_emisora || formValues.s_emisora == 'default')) {
            dispatch(updateRequiredEmisoraAcc(true))
            dispatch(updateRequiredEmisoraCalifInst(true))
            focusElement("s_emisora", requeridos.s_emisora)
            return false;
        }
        if (isSerie && (!formValues.s_serie || formValues.s_serie == 'default')) {
            dispatch(updateRequiredSerieAcc(true))
            dispatch(updateRequiredSerieCalifInst(true))
            focusElement("s_serie", requeridos.s_serie)
            return false;
        }
        if (fieldRequiredAccInst) {
            return validAccionesInstField(formValues, dispatch, fieldRequiredAccInst, requeridos)
        }
    
        if (fieldRequiredCalifProg) {
            return validProgramasField(formValues, dispatch, fieldRequiredCalifProg, requeridos)
        }
    
        if (fieldRequiredCalifInst) {
            return validCalifInstField(formValues, dispatch, fieldRequiredCalifInst, requeridos)
        }
    }

    if (fieldRequiredLatPanama && setFieldRequiredLatPanama) {
        return validLatPamanaField(formValues, setFieldRequiredLatPanama, fieldRequiredLatPanama, requeridos)
    }

    return true;
}

export const eraseFormValuesInstrumentos = (
    formValues: FormValuesDerivados | FormValuesCorp | FormValuesInter,
    dispatch: Dispatch
) => {
    if (formValues !== null && Object.keys(formValues).length > 0) {
        const erasedFormValues = eraseFormValues(formValues);

        dispatch(updateFormValuesInst(erasedFormValues as FvDeudaGobInstrumentos));
        dispatch(updateFormValuesCorp(erasedFormValues as FvDeudaCorpInstrumentos));
        dispatch(updateFormValuesInter(erasedFormValues as FvInterInstrumentos));
    }
};

const eraseFormValues = (formValues: Record<string, any>) => {
    return Object.keys(formValues).reduce((newFormValues: Record<string, any>, key) => {
        newFormValues[key] = Array.isArray(formValues[key]) ? [] : '';
        return newFormValues;
    }, {});
};

export const eraseConsultaData = (
    consultaData: ResponseConsultaData | ResponseConsultaDataCorp | ResponseConsultaDataInter,
    dispatch: Dispatch
) => {
    if (consultaData?.body && Object.keys(consultaData.body).length > 0) {

        const dataMap: Record<DataTypeKey, { initialValues: InstrumentosType, dispatchAction: any }> = {
            "FvDeudaGobInstrumentos": {initialValues: initialFormValues, dispatchAction: updateConsultaData},
            "FvDeudaCorpInstrumentos": {initialValues: initialFormValuesCorp, dispatchAction: updateConsultaDataCorp},
            "FvInterInstrumentos": {initialValues: initialFormValuesInter, dispatchAction: updateConsultaDataInter}
        };

        (Object.keys(dataMap) as DataTypeKey[]).forEach(key => {
            const newFormValues = getErasedData(consultaData.body, dataMap[key].initialValues);
            dispatch(dataMap[key].dispatchAction({body: newFormValues}));
        });
    }
};

const getErasedData = <T extends object>(bodyData: Record<string, any>, initialValues: T): T => {
    const newFormValues = {...initialValues};

    Object.keys(bodyData).forEach((key) => {
        if (Array.isArray(bodyData[key])) {
            // @ts-ignore
            newFormValues[key] = [] as string[];
        } else {
            // @ts-ignore
            newFormValues[key] = "";
        }
    });

    return newFormValues;
};

type SortDirection = 'asc' | 'desc' | null;

export const sortData = (
    dataRevisar: Revisar1Response | Revisar2Response,
    sortField?: SortableFields | null,
    sortDirection: SortDirection = 'asc'
): (Revisar1Data | Revisar2Data)[] => {
    if (!sortField) return [...dataRevisar.body];
    return [...dataRevisar.body].sort((a, b) =>
        sortFunction(a, b, sortField, sortDirection));
};

const sortFunction = (
    a: Revisar1Data | Revisar2Data,
    b: Revisar1Data | Revisar2Data,
    sortField: SortableFields,
    sortDirection: SortDirection
): number => {
    const aValue = getSortableValue(a, sortField, sortDirection);
    const bValue = getSortableValue(b, sortField, sortDirection);

    return compareSortableValues(aValue, bValue, sortDirection);
};

const getSortableValue = (
    data: Revisar1Data | Revisar2Data,
    sortField: SortableFields,
    sortDirection: SortDirection
): string | number => {
    const value = getValue(data, sortField);
    return convertValue(value, sortDirection);
};

const compareSortableValues = (
    aValue: string | number,
    bValue: string | number,
    sortDirection: SortDirection
): number => {
    if (aValue === -Infinity || bValue === Infinity) {
        return -1;
    }
    if (aValue === Infinity || bValue === -Infinity) {
        return 1;
    }

    if (typeof aValue === 'number' && typeof bValue === 'number') {
        return sortDirection === 'asc' ? aValue - bValue : bValue - aValue;
    }

    if (typeof aValue === 'string' && typeof bValue === 'string') {
        return sortDirection === 'asc' ? aValue.localeCompare(bValue) : bValue.localeCompare(aValue);
    }

    return 0;
};

const convertValue = (value: string | number | ClienteInfo[] | undefined,
                      sortDirection: SortDirection = 'asc'): number | string => {
    if (value === "" || value === null || value === undefined) {
        return sortDirection === 'asc' ? -Infinity : Infinity;
    } else if (Array.isArray(value)) {
        return value.length;
    } else if (typeof value === 'string' && value.trim() !== '' && !isNaN(Number(value))) {
        return parseFloat(value);
    }
    return value;
}

const getValue = (data: Revisar1Data | Revisar2Data, field: SortableFields): string | number | ClienteInfo[] | undefined => {
    if (isRevisar1Data(data) || isRevisar2Data(data)) {
        return data[field as keyof typeof data];
    }
    return undefined;
}

const isRevisar1Data = (data: any): data is Revisar1Data => {
    return 'isin' in data && !('clientes' in data);
}

const isRevisar2Data = (data: any): data is Revisar2Data => {
    return 'clientes' in data;
}

export const handleSort = (
    field: SortableFields,
    currentSortField: SortableFields | null,
    currentSortDirection: "asc" | "desc" | null,
    setSortField: (field: SortableFields | null) => void,
    setSortDirection: (direction: "asc" | "desc" | null) => void
) => {
    if (currentSortField === field) {
        setSortDirection(currentSortDirection === "asc" ? "desc" : "asc");
    } else {
        setSortField(field);
        setSortDirection("asc");
    }
};

export const updateMissingFields = (existingObj: Acciones | AccionesAdd,
                                    initObj: Acciones | AccionesAdd,
                                    selectedTv: string,
                                    selectedEmisora: string,
                                    selectedSerie: string
): Acciones | AccionesAdd => {

    const updatedObj: Acciones | AccionesAdd = {...existingObj};
    let tempObj: any = updatedObj;
    for (const key in initObj) {
        if (existingObj && !Object.hasOwn(existingObj, key)) {
            if (key === "s_tv") {
                tempObj[key] = selectedTv;
            } else if (key === "s_emisora") {
                tempObj[key] = selectedEmisora;
            } else if (key === "s_serie") {
                tempObj[key] = selectedSerie;
            } else {
                // @ts-ignore
                tempObj[key] = initObj[key];
            }
        }
    }
    return updatedObj;
};

const desiredOrder = ["S", "IM", "IS", "MS", "IQ", "M", "LD", "LF", "BI", "LG"];
export const reorderTvData = (data: string[]) => {

    data.sort((a, b) => {
        let indexA = desiredOrder.indexOf(a);
        let indexB = desiredOrder.indexOf(b);

        if (indexA === -1) indexA = Infinity;
        if (indexB === -1) indexB = Infinity;

        return indexA - indexB;
    });
    return data;
};

export const validateValues = (
    nInfo: number,
    consultaData: ResponseConsultaData,
    name: string,
    value: string,
    updateData: ActionCreatorWithPayload<ResponseConsultaData>,
    catalog: Catalogo[],
    dispatch: Dispatch
) => {
    if (nInfo !== 2 && nInfo !== 5) return;

    const updatedData = {body: {...consultaData.body}};

    switch (name) {
        case 'd_fecha_emision':
            updatedData.body.d_fecha_ini_cupon = consultaData.body.d_fecha_emision;
            break;
        case 'n_plazo':
            handlePlazoChange(value, updatedData, consultaData);
            break;
        case 'n_titulos_circulacion':
            handleTitulosCirculacionChange(updatedData, consultaData);
            break;
        case 'n_frecuencia_cupon':
            handleFrecuenciaCuponChange(updatedData, consultaData, catalog, dispatch);
            break;
        default:
            return;
    }

    dispatch(updateData(updatedData));
};

const handlePlazoChange = (value: string, updatedData: any, consultaData: ResponseConsultaData) => {
    const fecha = dayjs(consultaData.body.d_fecha_emision);
    const plazo = parseInt(value);
    updatedData.body.d_fecha_vto = fecha.add(plazo, 'day').format('YYYY-MM-DD');
};

const handleTitulosCirculacionChange = (updatedData: any, consultaData: ResponseConsultaData) => {
    if (consultaData.body.n_titulos_circulacion !== '' && consultaData.body.n_valor_nominal !== '') {
        const titulos = parseFloat(consultaData.body.n_titulos_circulacion);
        const vNominal = parseFloat(consultaData.body.n_valor_nominal);
        const monto = (titulos * vNominal).toString();
        updatedData.body.n_monto_emi = monto;
        updatedData.body.n_monto_circ = monto;
    }
};

const handleFrecuenciaCuponChange = (updateData: any,
                                     consultaData: ResponseConsultaData,
                                     catalog: Catalogo[],
                                     dispatch: Dispatch) => {
    const frecuencia_cupon = consultaData.body.n_frecuencia_cupon
    const result = getCatalogs(catalog, 'FRECUENCIA_CUPON-GUBER-NEW').find(item => item[0] === frecuencia_cupon)
    const n_plazo = consultaData.body.n_plazo

    if (result) {
        const plazoCupon = result[1]

        if (frecuencia_cupon === '5') {

            const fecha_fin_cupon = consultaData.body.d_fecha_ini_cupon
            const updatedData = {
                body: {
                    ...consultaData.body,
                    n_num_cupones: "1",
                    n_periodo_cupon: n_plazo,
                    n_periodo_cupon_v: n_plazo,
                    d_fecha_fin_cupon: fecha_fin_cupon
                }
            }
            dispatch(updateData(updatedData))
        } else {

            const fecha_fin = dayjs(consultaData.body.d_fecha_ini_cupon).add(parseInt(plazoCupon), 'day').format('YYYY-MM-DD')
            let numCupones

            const updatedData = {
                body: {
                    ...consultaData.body,
                    n_periodo_cupon: plazoCupon,
                    n_periodo_cupon_v: plazoCupon,
                    d_fecha_fin_cupon: fecha_fin
                }
            }
            dispatch(updateData(updatedData))

            if (n_plazo) {
                numCupones = parseInt(n_plazo) / parseInt(plazoCupon)
                numCupones = Math.trunc(numCupones)
                numCupones = numCupones.toString()
                const updatedData = {
                    body: {
                        ...consultaData.body,
                        n_num_cupones: numCupones,
                        n_periodo_cupon: plazoCupon,
                        n_periodo_cupon_v: plazoCupon,
                        d_fecha_fin_cupon: fecha_fin
                    }
                }
                dispatch(updateData(updatedData))
            }
        }
    }
};
export const handleParseMayus = <T extends HTMLInputElement | HTMLSelectElement>(event: React.ChangeEvent<T>): string => {
    return event.target.value.toUpperCase()
}

export const filterData = (data: RevisarData[], search: string): RevisarData[] => {
    return data.filter(item => {
        for (let key in item) {
            if (key !== 'clientes' && typeof (item as any)[key] === 'string' && (item as any)[key].includes(search)) {
                return true;
            }
        }

        if ('clientes' in item) {
            return item.clientes.some(cliente => {
                for (let innerKey in cliente) {
                    let details = cliente[innerKey];
                    if (details.nombre?.includes(search) || details.precio?.includes(search)) {
                        return true;
                    }
                }
                return false;
            });
        }

        return false;
    });
}

export const downloadFiles = (contenido: string, nombreCompleto: string, type: string) => {
    try {
        const binaryString = window.atob(contenido);
        const len = binaryString.length;
        const bytes = new Uint8Array(len);

        for (let i = 0; i < len; i++) {
            bytes[i] = binaryString.charCodeAt(i);
        }

        const blob = new Blob([bytes], {type: type});

        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = nombreCompleto ?? "archivo.csv";
        document.body.appendChild(a);
        a.click();

        window.URL.revokeObjectURL(url);
    } catch (error) {
        console.error('Error durante la descarga del archivo:', error);
    }
}