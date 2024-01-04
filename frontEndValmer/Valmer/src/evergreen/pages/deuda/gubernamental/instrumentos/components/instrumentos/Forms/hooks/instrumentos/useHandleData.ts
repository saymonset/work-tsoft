import React, {useRef, useState} from "react";
import {
    Catalogo,
    ConsultaData,
    FvDeudaGobInstrumentos, InstrumentosType,
    IsFieldModifiedFvDdGobIns,
    ResponseConsultaData
} from "../../../../../../../../../../model";
import {
    fetchDataPost, handleParseMayus,
    userEncoded,
    validateFormFields,
    validateValues,
    validChangeTvEmiSerie
} from "../../../../../../../../../../utils";
import {updateConsultaData, updateFieldRequiredGub, updateFormValuesInst, updateNInfoGuber} from "../../../../../../../../../../redux";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";

export const useHandleData = ({requeridos}: any) => {

    const [loadingSubmitInstRW, setLoadingSubmitInstRW] = useState(false)

    const [isFieldModified, setIsFieldModified] =
        useState<IsFieldModifiedFvDdGobIns>({} as IsFieldModifiedFvDdGobIns);

    const formValues = useSelector((state: RootState<any, any, any>) =>
        state.formValuesIns) as unknown as FvDeudaGobInstrumentos;

    const consultaData = useSelector((state: RootState<any, any, any>) => 
        state.consultaData) as unknown as ResponseConsultaData

    const nInfo = useSelector((state: RootState<any, any, any>) =>
        state.nInfoGuber) as unknown as number;

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalog) as unknown as Catalogo[];

    const fieldRequired = useSelector((state: RootState<any, any, any>) => 
        state.fieldRequiredGub) as unknown as IsFieldModifiedFvDdGobIns;

    const formRef = useRef<HTMLFormElement>(null)

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
        return consultaData?.body?.[fieldName] !== undefined
            ? consultaData?.body?.[fieldName] === '1'
            : consultaData?.body?.[fieldName] === '0'
    }

    const selectValueEval = (fieldName: string,
                             formValues: FvDeudaGobInstrumentos,
                             consultaData: ConsultaData) => {
        return formValues[fieldName] ? formValues[fieldName] : consultaData?.body?.[fieldName]
    };

    const handleNumericChange = <T extends HTMLInputElement | HTMLSelectElement>(
        e: React.ChangeEvent<T>,
        callback: (e: React.ChangeEvent<T>) => void
    ) => {
        const value = e.target.value;

        if (!isNaN(Number(value))) {
            callback(e);
        }
    };

    const handleChange = <T extends HTMLInputElement | HTMLSelectElement>(e: React.ChangeEvent<T>) => {
        
        const { name, value} = e.target;
        const updatedData: ResponseConsultaData = { body: {...consultaData.body, [name]: value} };
        dispatch(updateConsultaData(updatedData));
        setIsFieldModified((prevModified) => ({ ...prevModified, [name]: true }));
        validChangeTvEmiSerie(name, dispatch)
        validateValues(nInfo, updatedData, name, value, updateConsultaData, catalog, dispatch)
        const fieldRequiredData: IsFieldModifiedFvDdGobIns = {...fieldRequired, [name]: false}
        dispatch(updateFieldRequiredGub(fieldRequiredData))
        if (name === "s_isin"){
            const valorMayus = handleParseMayus(e)
            const updateMayus: ResponseConsultaData = { body: {...consultaData.body, [name]: valorMayus} };
            dispatch(updateConsultaData(updateMayus));
        }
    };

    const handleTvNewForm = (value: string) => {
        const updatedValues: FvDeudaGobInstrumentos = { ...formValues, "s_tv": value };
        dispatch(updateFormValuesInst(updatedValues));
        setIsFieldModified((prevModified) => ({ ...prevModified, "s_tv": true }));
        validChangeTvEmiSerie("s_tv", dispatch)
    };

    const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = e.target;
        const updatedData = { body: {...consultaData?.body, [name]: checked ? '1' : '0' }};
        dispatch(updateConsultaData(updatedData));
    };

    const handleSubmitRW = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (await validateFormFields(consultaData.body, dispatch, requeridos)) {
            setLoadingSubmitInstRW(true);
            await fetchDataPost("/instrumentos/guarda-info-rw",
                " al intentar guardar datos RW",
                consultaData.body,
                { s_user: userEncoded() });
            setLoadingSubmitInstRW(false);
            dispatch(updateNInfoGuber(7))
        } else {
            formRef.current?.scrollIntoView({ behavior: 'smooth' });
        }
    };

    return {
        loadingSubmitInstRW,
        isFieldModified,
        formRef,
        fieldRequired,
        inputValueEval,
        checkboxValueEval,
        selectValueEval,
        handleChange,
        handleNumericChange,
        handleTvNewForm,
        handleCheckboxChange,
        handleSubmitRW
    }
}