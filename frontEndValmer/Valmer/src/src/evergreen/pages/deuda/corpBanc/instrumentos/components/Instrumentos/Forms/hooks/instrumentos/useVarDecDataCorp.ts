import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {IsFieldModifiedFvDdGobIns, ResponseConsultaDataCorp, StateTvEmiSerieDataCorpEdit}
    from "../../../../../../../../../../model";
import {useTvOpt} from "../../../../../../../../../../shared";
import { useGetCatalogsCorp } from "./useGetCatalogsCorp";
import {useReducer} from "react";
import {initialStateTvEmiSerieCorp, reducerTvEmiSerieCorp} from "./components";

export const useVarDecDataCorp = () => {

    const consultaData = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataCorp) as unknown as ResponseConsultaDataCorp;

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvCorp) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisoraCorp) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerieCorp) as unknown as string;

    const emisora = useSelector((state: RootState<any, any, any>) =>
        state.emisoraCorp) as unknown as string[];

    const serie = useSelector((state: RootState<any, any, any>) =>
        state.serieCorp) as unknown as string[];

    const emisorasRef1 = useSelector((state: RootState<any, any, any>) =>
        state.emisoraRef1Corp) as unknown as string[];

    const emisorasRef2 = useSelector((state: RootState<any, any, any>) =>
        state.emisoraRef2Corp) as unknown as string[];

    const serieRef1 = useSelector((state: RootState<any, any, any>) =>
        state.serieRef1Corp) as unknown as string[];

    const serieRef2 = useSelector((state: RootState<any, any, any>) =>
        state.serieRef2Corp) as unknown as string[];

    const isNewFormCorp = useSelector((state: RootState<any, any, any>) => 
        state.isNewFormCorp) as unknown as boolean;

    const isNewSerieCorp = useSelector((state: RootState<any, any, any>) =>
        state.isNewSerieCorp) as unknown as boolean;

    const requiredTvCorp = useSelector((state: RootState<any, any, any>) =>
        state.requiredTvCorp) as unknown as boolean;

    const requiredEmisoraCorp = useSelector((state: RootState<any, any, any>) =>
        state.requiredEmisoraCorp) as unknown as boolean;
    
    const requiredSerieCorp = useSelector((state: RootState<any, any, any>) =>
        state.requiredSerieCorp) as unknown as boolean;

    const triggerErase = useSelector((state: RootState<any, any, any>) =>
        state.triggerErase) as unknown as boolean;

    const fieldRequiredCorp = useSelector((state: RootState<any, any, any>) =>
        state.fieldRequiredCorp) as unknown as IsFieldModifiedFvDdGobIns;

    const { catalog, loading } = useGetCatalogsCorp();

    const { tv, loadingTv } = useTvOpt("CORPO", isNewFormCorp);

    const [state, dispatchRed] =
        useReducer(reducerTvEmiSerieCorp, initialStateTvEmiSerieCorp);

    const updateState = (newState: Partial<StateTvEmiSerieDataCorpEdit>) => {
        dispatchRed({ type: 'UPDATE', payload: newState });
    };

    const dispatch = useDispatch();

    return {
        emisorasRef1, serieRef1,
        emisorasRef2, serieRef2,
        fieldRequiredCorp, triggerErase,
        consultaData, selectedTv, selectedEmisora,
        selectedSerie, emisora, serie,
        catalog, loading,
        tv, loadingTv,
        isNewFormCorp, isNewSerieCorp,
        requiredTvCorp, requiredEmisoraCorp,
        requiredSerieCorp,
        state, updateState,
        dispatch,
    };
}