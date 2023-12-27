import {combineReducers, configureStore, createReducer} from "@reduxjs/toolkit";
import {updateFormCalifEmi} from "./actions";
import {CalifEmisoraData} from "../../../model";

const formCalifEmiReducer = createReducer<CalifEmisoraData>(
    {} as CalifEmisoraData,
    (builder) => {
        builder.addCase(updateFormCalifEmi, (_state, action) => {
            return action.payload;
        });
    }
);


const rootReducer = combineReducers({
    formCalifEmi: formCalifEmiReducer,
});

export const storeCalifEmi = configureStore({
    reducer: rootReducer,
})