import {combineReducers, configureStore, createReducer} from "@reduxjs/toolkit";
import {
    updateCatalogAcc,
    updateConsultaDataAccInst,
    updateDividendosTable,
    updateEmisoraAccInst, updateNewFormInst, updateRequiredEmisoraAcc, updateRequiredFieldAccInst, updateRequiredSerieAcc, updateRequiredTvAcc, updateSelectedEmisoraAcc, updateSelectedSerieAcc, updateSelectedTvAcc,
    updateSerieAccInst, updateShowCarRvAcc, updateShowPrecalc, updateTriggerEraseAcc,
    updateTvAccInst
} from "./actions";
import {Catalogo, DividendosData, IsFieldRequiredAccInst, RespAccInstData} from "../../../model";

const selectedTvAccReducer = createReducer<string>(
    "default",
    (builder) => {
        builder.addCase(updateSelectedTvAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedEmisoraAccReducer = createReducer<string>(
    "default",
    (builder) => {
        builder.addCase(updateSelectedEmisoraAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedSerieAccReducer = createReducer<string>(
    "default",
    (builder) => {
        builder.addCase(updateSelectedSerieAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const tvAccInstReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateTvAccInst, (_state, action) => {
            return action.payload;
        });
    }
);

const emisorasAccInstReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateEmisoraAccInst, (_state, action) => {
            return action.payload;
        });
    }
);

const serieAccInstReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateSerieAccInst, (_state, action) => {
            return action.payload;
        });
    }
);

const consultaDataAccInstReducer = createReducer<RespAccInstData>(
    {} as RespAccInstData,
    (builder) => {
        builder.addCase(updateConsultaDataAccInst, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogAccReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const dividendosTableReducer = createReducer<DividendosData[]>(
    [],
    (builder) => {
        builder.addCase(updateDividendosTable, (_state, action) => {
            return action.payload;
        });
    }
);

const triggerEraseAccReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateTriggerEraseAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const showCarRvAccReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowCarRvAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const newFormInstReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateNewFormInst, (_state, action) => {
            return action.payload;
        });
    }
);

const showPrecalcReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowPrecalc, (_state, action) => {
            return action.payload;
        });
    }
);

const fieldRequiredAccInstReducer = createReducer<IsFieldRequiredAccInst>(
    {} as IsFieldRequiredAccInst,
    (builder) => {
        builder.addCase(updateRequiredFieldAccInst, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredTvAccReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredTvAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredEmisoraAccReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredEmisoraAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredSerieAccReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredSerieAcc, (_state, action) => {
            return action.payload;
        });
    }
);

const rootReducer = combineReducers({
    selectedTvAcc: selectedTvAccReducer,
    selectedEmisoraAcc: selectedEmisoraAccReducer,
    selectedSerieAcc: selectedSerieAccReducer,
    tvAccInst: tvAccInstReducer,
    emisorasAccInst: emisorasAccInstReducer,
    serieAccInst: serieAccInstReducer,
    consultaDataAccInst: consultaDataAccInstReducer,
    dividendosTable: dividendosTableReducer,
    catalog: catalogAccReducer,
    triggerEraseAcc: triggerEraseAccReducer,
    showCarRvAcc: showCarRvAccReducer,
    isNewFormInst: newFormInstReducer,
    showPrecalc: showPrecalcReducer,
    fieldRequiredAccInst: fieldRequiredAccInstReducer,
    requiredTvAcc: requiredTvAccReducer,
    requiredEmisoraAcc: requiredEmisoraAccReducer,
    requiredSerieAcc: requiredSerieAccReducer
});

export const storeAccInst = configureStore({
    reducer: rootReducer,
})