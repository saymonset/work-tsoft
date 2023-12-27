import { createAction } from "@reduxjs/toolkit";
import { Catalogo, ResponseAdminEnviosMailGrupos } from "../../../model";
import { InfoClienteMail, ResponseTableMail } from "../../../model/Admin";

export const updateCatalogo = createAction<Catalogo[]>('UPDATE_CATALOGO');
export const updateCatalogoEnviosMailGrupos = createAction<ResponseAdminEnviosMailGrupos>('UPDATE_CATALOGO_MAIL_GRUPOS');
export const updateTableMail = createAction<ResponseTableMail>('UPDATE_TABLA_MAIL');
export const updateInfoTableMail = createAction<InfoClienteMail>('UPDATE_INFO_TABLA_MAIL');