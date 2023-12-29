import { combineReducers, configureStore, createReducer } from "@reduxjs/toolkit";
import { Catalogo } from "../../../model";
import { updateCatNom, updateCatSector, updateCatTipoUser, updateCatUri, updateCatalogoInst, updateInfoUser, updateUriInfo } from "./actions";
import { ResponseCat, CatSector, InfoUser, UriInfo } from "../../../model/Admin";

const catalogoInstReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogoInst, (_state, action) => {
            return action.payload;
        });
    }
);

const catNomReducer = createReducer<ResponseCat>(
    {} as ResponseCat,
    (builder) => {
        builder.addCase(updateCatNom, (_state, action) => {
            return action.payload;
        });
    }
);

const catTipoUserReducer = createReducer<ResponseCat>(
    {} as ResponseCat,
    (builder) => {
        builder.addCase(updateCatTipoUser, (_state, action) => {
            return action.payload;
        });
    }
);

const catUriReducer = createReducer<ResponseCat>(
    {} as ResponseCat,
    (builder) => {
        builder.addCase(updateCatUri, (_state, action) => {
            return action.payload;
        });
    }
);

const catSectorReducer = createReducer<CatSector[]>(
    [],
    (builder) => {
        builder.addCase(updateCatSector, (_state, action) => {
            return action.payload;
        });
    }
);

const infoUserReducer = createReducer<InfoUser>(
    {} as InfoUser,
    (builder) => {
        builder.addCase(updateInfoUser, (_state, action) => {
            return action.payload;
        });
    }
);

const uriInfoReducer = createReducer<UriInfo>(
    {} as UriInfo,
    (builder) => {
        builder.addCase(updateUriInfo, (_state, action) => {
            return action.payload;
        });
    }
);

const rootReducer = combineReducers({
    catalogoInst: catalogoInstReducer,
    catNom: catNomReducer,
    catTipoUser: catTipoUserReducer,
    catUri: catUriReducer,
    catSector: catSectorReducer,
    infoUser: infoUserReducer,
    uriInfo: uriInfoReducer
});

export const storeUserWeb = configureStore({
    reducer: rootReducer
});