import { combineReducers, configureStore, createReducer } from "@reduxjs/toolkit";
import { 
    ConsultaLipper,
    FormValuesLipper,
    ResponseDataTableHead 
} from "../../../model";
import { 
    updateConsultaLipper, 
    updateDataTableHead, 
    updateFormValuesLipper
} from "./actions";

const dataTalbeHeadReducer = createReducer<ResponseDataTableHead>(
    {} as ResponseDataTableHead,
    (builder) => {
        builder.addCase(updateDataTableHead, (_state, action) => {
            return action.payload
        });
    }
);

const consultaDataLipperReducer = createReducer<ConsultaLipper>(
    {} as ConsultaLipper,
    (builder) => {
        builder.addCase(updateConsultaLipper, (_state, action) => {
            return action.payload
        });
    }
);

const formValuesLipperReducer = createReducer<FormValuesLipper>(
    {} as FormValuesLipper,
    (builder) => {
        builder.addCase(updateFormValuesLipper, (_state, action) => {
            return action.payload
        })
    }
)

const rootLipperReducer = combineReducers({
    dataTableHead: dataTalbeHeadReducer,
    consultaDataLipper: consultaDataLipperReducer,
    formValuesLipper: formValuesLipperReducer
});

export const storeLipper = configureStore({
    reducer: rootLipperReducer
});