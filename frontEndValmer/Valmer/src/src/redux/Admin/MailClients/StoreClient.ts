import { createReducer, combineReducers, configureStore } from "@reduxjs/toolkit";
import { updateCatalogo, updateCatalogoEnviosMailGrupos, updateInfoTableMail, updateTableMail } from "./actions";
import { Catalogo, ResponseAdminEnviosMailGrupos } from "../../../model";
import { InfoClienteMail, ResponseTableMail } from "../../../model/Admin";

const catalogoReducer = createReducer<Catalogo[]>(
  [],
  (builder) => {
      builder.addCase(updateCatalogo, (_state, action) => {
          return action.payload;
      });
  }
);

const tablaMailReducer = createReducer<ResponseTableMail | null>(
   null,
  (builder) => {
      builder.addCase(updateTableMail, (_state, action) => {
          return action.payload;
      });
  }
);

const infoTablaMailReducer = createReducer<InfoClienteMail | null>(
   null,
  (builder) => {
      builder.addCase(updateInfoTableMail, (_state, action) => {
          return action.payload;
      });
  }
);

const catalogoEnviosMailGruposReducer = createReducer<ResponseAdminEnviosMailGrupos>(
   {} as ResponseAdminEnviosMailGrupos,
  (builder) => {
      builder.addCase(updateCatalogoEnviosMailGrupos, (_state, action) => {
          return action.payload;
      });
  }
);

const rootReducer = combineReducers({
    catalogo: catalogoReducer,
    tableMail: tablaMailReducer,
    infoTableMail: infoTablaMailReducer,
    catalogoEnviosMailGrupos: catalogoEnviosMailGruposReducer,
});

export const storeClient = configureStore({
    reducer: rootReducer,
});