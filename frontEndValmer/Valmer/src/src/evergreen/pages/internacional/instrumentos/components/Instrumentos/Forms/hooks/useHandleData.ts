import React, {useState} from "react";
import {
    ConsultaData,
    FvInterInstrumentos,
    InstrumentosType,
    IsFieldModifiedFvInterIns,
    ResponseConsultaDataInter
    
} from "../../../../../../../../model"
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import { updateConsultaDataInter, updateFieldRequiredIntern, updateFormValuesInter } from "../../../../../../../../redux";
import { handleParseMayus, validChangeTvEmiSerie } from "../../../../../../../../utils";

export const useHandleData = ({requeridos}: any) => {
    const fieldRequireInter = useSelector((state: RootState<any, any, any>) =>
    state.fieldRequiredIntern) as unknown as IsFieldModifiedFvInterIns;

    const [isFieldModified, setIsFieldModified] = 
        useState<IsFieldModifiedFvInterIns>({} as IsFieldModifiedFvInterIns);
        const [isChecked, setIsChecked] = useState(false);

    const formValues = useSelector((state: RootState<any, any, any>) => 
        state.formValuesInter) as unknown as FvInterInstrumentos;

    const consultaData = useSelector((state: RootState<any, any, any>) => 
        state.consultaDataInter) as unknown as ResponseConsultaDataInter

    const [isDisabledRnv, setIsDisabledRnv] = useState<boolean>(false)

    const dispatch = useDispatch();

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
    };

    const checkboxValueEval = (fieldName: string,
                               consultaData: ConsultaData | undefined) : boolean => {
        return consultaData?.body?.[fieldName] !== undefined
            ? consultaData?.body?.[fieldName] === '1'
            : consultaData?.body?.[fieldName] === '0'
    }
    
    const handleChange = (e: any) => {
        const { name, value} = e.target;
        const updatedData: ResponseConsultaDataInter = {body: {...consultaData.body, [name]: value}};
        dispatch(updateConsultaDataInter(updatedData));
        setIsFieldModified((prevModified) => ({ ...prevModified, [name]: true }));
        validChangeTvEmiSerie(name, dispatch)
        const fieldRequiredData: IsFieldModifiedFvInterIns = {...fieldRequireInter, [name]: false}
        dispatch(updateFieldRequiredIntern(fieldRequiredData))
        if (name === "s_isin"){
            const valorMayus = handleParseMayus(e)
            const updateMayus: ResponseConsultaDataInter = { body: {...consultaData.body, [name]: valorMayus} };
            dispatch(updateConsultaDataInter(updateMayus));
        }

        if (name === "s_pais") {
            if (value === "default") {
                setIsDisabledRnv(false)
            } else {
                setIsDisabledRnv(true)
            }
        }
    }

    const handleTvNewForm = (value: string) => {
        const updatedValues: FvInterInstrumentos = {...formValues, 's_tv': value};
        dispatch(updateFormValuesInter(updatedValues));
        setIsFieldModified((prevModified) => ({...prevModified, 's_tv': true}));
        validChangeTvEmiSerie('s_tv', dispatch)
    }

    const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = e.target;
        
        if (name === 'b_esg' && checked) {
            setIsChecked(true);
        } else {
            setIsChecked(false);
        }

        const updatedValues = { body: {...consultaData.body, [name]: checked ? '1' : '0' }};
        dispatch(updateConsultaDataInter(updatedValues));
    };

    return {
        isDisabledRnv,
        isFieldModified,
        inputValueEval,
        checkboxValueEval,
        handleChange,
        handleTvNewForm,
        handleCheckboxChange,
        isChecked
    }
}