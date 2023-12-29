import { createAction } from "@reduxjs/toolkit";
import { ConsultaLipper, FormValuesLipper, ResponseDataTableHead } from "../../../model";

export const updateDataTableHead = createAction<ResponseDataTableHead>('UPDATE_DATA_TABLE_HEAD')
export const updateConsultaLipper = createAction<ConsultaLipper>('UPDATE_CONSULTA_DATA_LIPPER')
export const updateFormValuesLipper = createAction<FormValuesLipper>('UPDATE_FORM_VALUES_LIPPER')
