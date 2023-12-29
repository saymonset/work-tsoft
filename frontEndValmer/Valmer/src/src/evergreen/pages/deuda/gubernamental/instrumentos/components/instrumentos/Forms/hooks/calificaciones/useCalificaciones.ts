import {ChangeEvent, useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import {useCalificacionesProcess} from "./useCalificacionesProcess";
import {updateConsultaData} from "../../../../../../../../../../redux";
import {ResponseConsultaData, SectionType} from "../../../../../../../../../../model";
import {handleFileChangeCalif} from "../../../../../../../../../../utils";

export const useCalificaciones = () => {

    const {
        loadingSubmitCalif,
        catalog,
        loading,
        consultaData,
        handleChange,
        handleSubmit
    } = useCalificacionesProcess()

    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [fileBase64, setFileBase64] = useState<string | null>(null);
    const [section, setSection] = useState<SectionType | "">("");
    const dispatch = useDispatch();


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
            const combinedValues = { body: {...consultaData.body, ...updatedValues, ...updatedValuesName} };
            dispatch(updateConsultaData(combinedValues as ResponseConsultaData));
        }
    }, [fileBase64, section]);


    const handleFileChange = (event: ChangeEvent<HTMLInputElement>, section: string) => {
        handleFileChangeCalif(event, section, setFileBase64, setSection, setSelectedFile)
    }


    return {
        loading,
        loadingSubmitCalif,
        catalog,
        consultaData,
        handleChange,
        handleSubmit,
        handleFileChange,
    }
}