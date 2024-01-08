import {
    ActionHookEditCauClient,
    ConsultaDataEditCauClient,
    StateHookEditCauClient
} from "../../../../../../../../../model";

export const initialStateEditCauClient: StateHookEditCauClient = {
    loadingClient: false,
    clients: {},
    enterprise: "",
    selectClient: "",
    catalogs: [],
    filteredCatalogs: [],
    enterpriseFilteredCatalogs: [],
    consultaDataClient: {} as ConsultaDataEditCauClient,
    catalogsCau: [],
    loadingCatalog: false,
    loadingCatalogCau: false,
    triggerCatalogs: false,
    loadingClientById: false,
    loadingCsv: false,
    loadingNewId: false,
    loadingErase: false,
    loadingSave: false,
    loadingClientId: null
};

export const reducerEditCauClient = (state: StateHookEditCauClient, action: ActionHookEditCauClient): StateHookEditCauClient => {
    if (action.type === 'UPDATE') {
        return {
            ...state,
            ...action.payload
        };
    } else {
        throw new Error(`Unhandled action type: ${action.type}`);
    }
};