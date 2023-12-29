import {combineReducers} from 'redux';
import {
    Catalogo,
    FvDeudaGobInstrumentos,
    IsFieldModifiedFvDdGobIns,
    ResponseConsultaData
} from "../../model";
import {configureStore, createReducer} from "@reduxjs/toolkit";
import {
    showCuponesTasas,
    updateCatalog,
    updateCatalogCalif,
    updateConsultaData,
    updateEmisora,
    updateFieldRequiredGub,
    updateFormValuesInst,
    updateIsNewFormGub,
    updateIsNewSerieGub,
    updateNInfoGuber,
    updateRequiredEmisora,
    updateRequiredSerie,
    updateRequiredTv,
    updateSelectedEmisora,
    updateSelectedSerie,
    updateSelectedTv,
    updateSerie, updateTriggerEraseGub,
    updateTV
} from "./actions";

const formValuesReducerIns = createReducer<FvDeudaGobInstrumentos>(
    {} as FvDeudaGobInstrumentos,
    (builder) => {
        builder.addCase(updateFormValuesInst, (_state, action) => {
            return action.payload;
        });
    }
);

const consultaDataReducer = createReducer<ResponseConsultaData>(
    {} as ResponseConsultaData,
    (builder) => {
        builder.addCase(updateConsultaData, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedTvReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedTv, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedEmisoraReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedEmisora, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedSerieReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedSerie, (_state, action) => {
            return action.payload;
        });
    }
);

const tvReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateTV, (_state, action) => {
            return action.payload;
        });
    }
);

const emisoraReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateEmisora, (_state, action) => {
            return action.payload;
        });
    }
);

const serieReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateSerie, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalog, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogCalificacionesReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogCalif, (_state, action) => {
            return action.payload;
        });
    }
);

const cuponesTasasReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(showCuponesTasas, (_state, action) => {
            return action.payload;
        });
    }
);

const isNewGubFormReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsNewFormGub, (_state, action) => {
            return action.payload;
        });
    }
);

const isNewSerieGubReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsNewSerieGub, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredTvReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredTv, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredEmisoraReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredEmisora, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredSerieReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredSerie, (_state, action) => {
            return action.payload;
        });
    }
);

const triggerEraseReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateTriggerEraseGub, (_state, action) => {
            return action.payload;
        });
    }
);

const nInfoGuberReducer = createReducer<number>(
    0,
    (builder) => {
        builder.addCase(updateNInfoGuber, (_state, action) => {
            return action.payload;
        });
    }
);

const fieldRequiredGubReducer = createReducer<IsFieldModifiedFvDdGobIns>(
    {} as IsFieldModifiedFvDdGobIns,
    (builder) => {
        builder.addCase(updateFieldRequiredGub, (_state, action) => {
            return action.payload;
        });
    }
);

const rootReducer = combineReducers({
    formValuesIns: formValuesReducerIns,
    consultaData: consultaDataReducer,
    selectedTv: selectedTvReducer,
    selectedEmisora: selectedEmisoraReducer,
    selectedSerie: selectedSerieReducer,
    tv: tvReducer,
    emisora: emisoraReducer,
    serie: serieReducer,
    catalog: catalogReducer,
    catalogCalif: catalogCalificacionesReducer,
    cuponesTasas: cuponesTasasReducer,
    isNewGubForm: isNewGubFormReducer,
    isNewSerieGub: isNewSerieGubReducer,
    requiredTv: requiredTvReducer,
    requiredEmisora: requiredEmisoraReducer,
    requiredSerie: requiredSerieReducer,
    triggerErase: triggerEraseReducer,
    nInfoGuber: nInfoGuberReducer,
    fieldRequiredGub: fieldRequiredGubReducer
});

export const storeGub = configureStore({
    reducer: rootReducer,
});