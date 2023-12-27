import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import { Catalogo, ResponseConsultaDataInter } from "../../../../../../../../model";
import { useSelector } from "react-redux";
import {useHandleData} from "../../../Instrumentos";

export const useDataCupones = ({requeridos}: any) => {
    
    const catalog = useSelector((state: RootState<any, any, any>) => 
        state.catalogInter) as unknown as Catalogo[];

    const consultaData = useSelector((state: RootState<any, any, any>) => 
        state.consultaDataInter) as unknown as ResponseConsultaDataInter;

    const triggerErase = useSelector((state: RootState<any, any, any>) =>
        state.triggerErase) as unknown as boolean;

    let hFlujosVal;
    try {
        // @ts-ignore
        hFlujosVal = JSON.parse(consultaData.body.h_flujos_val);
    } catch {
        hFlujosVal = null;
    }

    const isRegisters = hFlujosVal !== null && Array.isArray(hFlujosVal) && hFlujosVal.length > 0;

    const {
        isFieldModified,
        inputValueEval,
        handleChange
    } = useHandleData({requeridos});

    return {
        hFlujosVal,
        isRegisters,
        triggerErase,
        catalog,
        consultaData,
        isFieldModified,
        inputValueEval,
        handleChange
    }
}