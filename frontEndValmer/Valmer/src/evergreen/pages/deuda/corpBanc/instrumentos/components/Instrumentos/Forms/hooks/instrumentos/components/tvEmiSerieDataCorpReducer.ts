import {ActionTvEmiSerieCorpEdit, StateTvEmiSerieDataCorpEdit} from "../../../../../../../../../../../model";

export const initialStateTvEmiSerieCorp = {
    triggerEmisora: false,
    triggerSerie: false,
    triggerConsultaData: false,
    loadingEmisoras: false,
    loadingSerie: false,
    loadingConsultaData: false,
    triggerEmisoraRef1: false,
    triggerEmisoraRef2: false,
    triggerSerieRef1: false,
    triggerSerieRef2: false,
    loadingEmisorasRef1: false,
    loadingSerieRef1: false,
    loadingEmisorasRef2: false,
    loadingSerieRef2: false
};


export const reducerTvEmiSerieCorp = (state: StateTvEmiSerieDataCorpEdit, action: ActionTvEmiSerieCorpEdit): StateTvEmiSerieDataCorpEdit => {
    if (action.type === 'UPDATE') {
        return {
            ...state,
            ...action.payload
        };
    } else {
        throw new Error(`Unhandled action type: ${action.type}`);
    }
};
