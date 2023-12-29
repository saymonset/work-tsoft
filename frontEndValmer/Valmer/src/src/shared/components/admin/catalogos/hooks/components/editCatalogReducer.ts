import {ActionHookEdit, StateHookEdit} from "../../../../../../model";

export const initialState = {
    registroSeleccionado: null,
    loadingSave: false,
    loadingDelete: false,
    loadingNewId: false,
    loadingCatalogStatic: false,
    loadingNomCorto: false,
    loadingCatalog: false,
    triggerCatalogs: false,
    isNew: false,
    catalogs: []
};

export const reducer = (state: StateHookEdit, action: ActionHookEdit): StateHookEdit => {
    if (action.type === 'UPDATE') {
        return {
            ...state,
            ...action.payload
        };
    } else {
        throw new Error(`Unhandled action type: ${action.type}`);
    }
};