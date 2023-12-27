import {createAction} from "@reduxjs/toolkit";
import {TasasConsultaResponse} from "../../model";

export const updateConsultaTasas
    = createAction<TasasConsultaResponse>('UPDATE_CONSULTA_TASAS');