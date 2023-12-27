import {combineReducers, configureStore, createReducer} from "@reduxjs/toolkit";
import {
    updateCalifProCarac,
    updateCatalogCalifPro,
    updateDataCalifProgramas,
    updateEmisoraCalifPro, updateSelectedTvCalifPro, updateTriggerEraseCalifPro,
    updateTvCalifPro
} from "./actions";
import {CalifProgramas, Catalogo, RatingAgency} from "../../../model";

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

const rootReducer = combineReducers({
    tvCalifPro: tvCalifProReducer,
    emisoraCalifPro: emisoraCalifProReducer,
    selectedTvCalifPro: selectedTvCalifProReducer,
    triggerEraseCalifPro: triggerEraseCalifProReducer,
    catalogoCalifPro: catalogCalifProReducer,
    catalogProCarac: catalogProCaracReducer,
    califProgramasData: califProgramasDataReducer
});

export const storeCalifPro = configureStore({
    reducer: rootReducer,
})