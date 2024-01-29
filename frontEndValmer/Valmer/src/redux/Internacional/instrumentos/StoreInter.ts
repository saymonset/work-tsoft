import { combineReducers } from 'redux';
import { configureStore, createReducer } from "@reduxjs/toolkit";
import {
    Catalogo,
    FvInterInstrumentos,
    IsFieldModifiedFvInterIns,
    ResponseConsultaDataInter,
    RespTableClients
} from "../../../model";
import {
    updateCatalogCalifInter,
    updateCatalogInter,
    updateConsultaDataInter,
    updateEmisoraInter,
    updateFieldRequiredIntern,
    updateFormValuesInter,
    updateIsNewInterForm,
    updateRequiredEmisoraInter,
    updateRequiredSerieInter,
    updateRequiredTvInter,
    updateSelectedEmisoraInter,
    updateSelectedSerieInter,
    updateSelectedTvInter,
    updateSerieInter,
    updateTableClients,
    updateTriggerEraseInter,
    updateTvInter
} from "./actions";

const catalogInterReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogInter, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogCalifInterReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogCalifInter, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedTvInterReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedTvInter, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedEmisoraInterReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedEmisoraInter, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedSerieInterReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedSerieInter, (_state, aciton) => {
            return aciton.payload;
        });
    }
);

const fieldRequiredInternReducer = createReducer<IsFieldModifiedFvInterIns>(
    {} as IsFieldModifiedFvInterIns,
    (builder) => {
        builder.addCase(updateFieldRequiredIntern, (_state, action) => {
            return action.payload;
        });
    }
);

const emisoraInterReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateEmisoraInter, (_state, action) => {
            return action.payload;
        });
    }
);

const serieInterReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateSerieInter, (_state, action) => {
            return action.payload;
        });
    }
);

const isNewInterFormReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsNewInterForm, (_state, action) => {
            return action.payload;
        })
    }
);

const tvInterRequired = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredTvInter, (_state, action) => {
            return action.payload;
        });
    }
);

const emisoraInterRequired = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredEmisoraInter, (_state, action) => {
            return action.payload;
        });
    }
);

const serieInterRequired = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredSerieInter, (_state, action) => {
            return action.payload;
        });
    }
);

const formValuesInterReducer = createReducer<FvInterInstrumentos>(
    {} as FvInterInstrumentos,
    (builder) => {
        builder.addCase(updateFormValuesInter, (_state, action) => {
            return action.payload;
        });
    }
);

const consultaDataInterReducer = createReducer<ResponseConsultaDataInter>(
    {} as ResponseConsultaDataInter,
    (builder) => {
        builder.addCase(updateConsultaDataInter, (_state, action) => {
            return action.payload;
        });
    }
);

const tvReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateTvInter, (_state, action) => {
            return action.payload;
        });
    }
);

const triggerEraseReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateTriggerEraseInter, (_state, action) => {
            return action.payload;
        });
    }
);

const tableClientsReducer = createReducer<RespTableClients>(
    {} as RespTableClients,
    (builder) => {
        builder.addCase(updateTableClients, (_state, action) => {
            return action.payload;
        });
    }
);


const rootInterReducer = combineReducers({
    catalogInter: catalogInterReducer,
    catalogCalifInter: catalogCalifInterReducer,
    selectedTvInter: selectedTvInterReducer,
    selectedEmisoraInter: selectedEmisoraInterReducer,
    selectedSerieInter: selectedSerieInterReducer,
    fieldRequiredIntern: fieldRequiredInternReducer,
    emisoraInter: emisoraInterReducer,
    serieInter: serieInterReducer,
    isNewInterForm: isNewInterFormReducer,
    requiredTvInter: tvInterRequired,
    requiredEmisoraInter: emisoraInterRequired,
    requiredSerieInter: serieInterRequired,
    formValuesInter: formValuesInterReducer,
    consultaDataInter: consultaDataInterReducer,
    tv: tvReducer,
    triggerErase: triggerEraseReducer,
    tableClients: tableClientsReducer,
});

export const storeInter = configureStore({
    reducer: rootInterReducer
});