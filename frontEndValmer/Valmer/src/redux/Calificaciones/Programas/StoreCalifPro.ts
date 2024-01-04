import {combineReducers, configureStore, createReducer} from "@reduxjs/toolkit";
import {
    updateCalifProCarac,
    updateCatalogCalifPro,
    updateDataCalifProgramas,
    updateEmisoraCalifPro, updateRequiredCalifProg, updateRequiredTvCalifProg, updateSelectedTvCalifPro, updateTriggerEraseCalifPro,
    updateTvCalifPro
} from "./actions";
import {CalifProgramas, Catalogo, IsFieldReqCalifProg, RatingAgency} from "../../../model";

const tvCalifProReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateTvCalifPro, (_state, action) => {
            return action.payload;
        });
    }
);

const emisoraCalifProReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateEmisoraCalifPro, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedTvCalifProReducer = createReducer<string>(
    "",
    (builder) => {
        builder.addCase(updateSelectedTvCalifPro, (_state, action) => {
            return action.payload;
        });
    }
);

const triggerEraseCalifProReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateTriggerEraseCalifPro, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogCalifProReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogCalifPro, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogProCaracReducer = createReducer<RatingAgency>(
    {} as RatingAgency,
    (builder) => {
        builder.addCase(updateCalifProCarac, (_state, action) => {
            return action.payload;
        });
    }
);

const califProgramasDataReducer = createReducer<CalifProgramas>(
    {} as CalifProgramas,
    (builder) => {
        builder.addCase(updateDataCalifProgramas, (_state, action) => {
            return action.payload;
        });
    }
);

const isRequiredCalifProgReducer = createReducer<IsFieldReqCalifProg>(
    {} as IsFieldReqCalifProg,
    (builder) => {
        builder.addCase(updateRequiredCalifProg, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredTvCalifProgReducer = createReducer<boolean>(
    false,
    (builder) =>{
        builder.addCase(updateRequiredTvCalifProg, (_state, action) => {
            return action.payload;
        });
    }
);

const rootReducer = combineReducers({
    tvCalifPro: tvCalifProReducer,
    emisoraCalifPro: emisoraCalifProReducer,
    selectedTvCalifPro: selectedTvCalifProReducer,
    triggerEraseCalifPro: triggerEraseCalifProReducer,
    catalogoCalifPro: catalogCalifProReducer,
    catalogProCarac: catalogProCaracReducer,
    califProgramasData: califProgramasDataReducer,
    isFieldReqCalifProg: isRequiredCalifProgReducer,
    requiredTvCalifProg: requiredTvCalifProgReducer,
});

export const storeCalifPro = configureStore({
    reducer: rootReducer,
})