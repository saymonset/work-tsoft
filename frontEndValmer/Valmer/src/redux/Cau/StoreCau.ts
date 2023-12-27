import { combineReducers, configureStore, createReducer } from "@reduxjs/toolkit";
import { Catalogo } from "../../model";
import { updateCatalogCau } from "./actions";

const catalogCauReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogCau, (_state, action) => {
            return action.payload
        })
    }
)

const rootReducer = combineReducers({
    catalogCau: catalogCauReducer,
})

export const storeCau = configureStore({
    reducer: rootReducer
})