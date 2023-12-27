import { combineReducers, configureStore, createReducer } from "@reduxjs/toolkit";
import {Catalogo} from "../../../model";
import {updateCatalogStatic} from "./actions";

const catalogStaticReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogStatic, (_state, action) => {
            return action.payload;
        });
    }
);

const rootAdmonCatalog = combineReducers({
    catalogStatic: catalogStaticReducer
});

export const storeAdmonCatalog = configureStore({
    reducer: rootAdmonCatalog
});