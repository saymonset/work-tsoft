import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {Catalogo, IsFieldModifiedFvDdGobIns, ResponseConsultaData} from "../../../../../../../../../model";
import {useHandleData} from "../../../instrumentos/Forms/hooks";

export const useDataCupones = ({requeridos}: any) => {
    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalog) as unknown as Catalogo[];

    const consultaData = useSelector((state: RootState<any, any, any>) =>
        state.consultaData) as unknown as ResponseConsultaData;

    const fieldRequired = useSelector((state: RootState<any, any, any>) =>
        state.fieldRequiredGub) as unknown as IsFieldModifiedFvDdGobIns;

    const
        {
            handleChange,
            handleNumericChange} = useHandleData({requeridos});

    return {
        consultaData,
        catalog,
        fieldRequired,
        handleChange,
        handleNumericChange
    }
}