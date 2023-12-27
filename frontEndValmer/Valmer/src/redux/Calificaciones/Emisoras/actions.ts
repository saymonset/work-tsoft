import {createAction} from "@reduxjs/toolkit";
import {CalifEmisoraData} from "../../../model";


export const updateFormCalifEmi = createAction<CalifEmisoraData>('UPDATE_FORM_CALIF_EMI')