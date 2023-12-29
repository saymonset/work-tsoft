import {configureStore, createReducer} from "@reduxjs/toolkit";
import {combineReducers} from "redux";
import {
    updateCatalogoInt,
    updateFechaEurobonos,
    updateFileContributor,
    updateFilePreciosCont,
    updateFileWallet,
    updateIsRevFormInsumos,
    updateIsRevFormInsumos2,
    updateIsShowLogBox,
    updateIsShowLogBoxCont, updateIsShowLogBoxProc,
    updateRespClientWallet,
    updateRevisar1,
    updateRevisar2,
    updateSearchClientWallet,
    updateSearchContributor,
    updateSearchContributor2,
    updateSelectedClient,
    updateSelectedVector,
    updateShowFileContributor,
    updateShowFileWallet,
    updateShowTableWallet,
    updateTriggerEraseInt,
    updateCatalogoEuroCurvas,
    updateStateCheckbox
} from "./actions";
import dayjs from "dayjs";
import {
    ArchivoPreciosCont,
    CargaArchivoContent,
    RespCatalogoInt,
    RespClientWallet,
    ResponseCatalogoEuroCurvas,
    Revisar1Response,
    Revisar2Response,
    stateCheckbox
} from "../../../model";

const fechaEurobonosReducer = createReducer<string>(
    dayjs().format('YYYY-MM-DD'),
    (builder) => {
        builder.addCase(updateFechaEurobonos, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedVectorReducer = createReducer<string>(
    'DEFINITIVO',
    (builder) => {
        builder.addCase(updateSelectedVector, (_state, action) => {
            return action.payload;
        });
    }
);

const revisar1DataReducer = createReducer<Revisar1Response>(
    {} as Revisar1Response,
    (builder) => {
        builder.addCase(updateRevisar1, (_state, action) => {
            return action.payload;
        });
    }
);

const isRevInsFormReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsRevFormInsumos, (_state, action) => {
            return action.payload;
        });
    }
);

const revisar2DataReducer = createReducer<Revisar2Response>(
    {} as Revisar2Response,
    (builder) => {
        builder.addCase(updateRevisar2, (_state, action) => {
            return action.payload;
        });
    }
);

const filePreciosContReducer = createReducer<ArchivoPreciosCont>(
    {} as ArchivoPreciosCont,
    (builder) => {
        builder.addCase(updateFilePreciosCont, (_state, action) => {
            return action.payload;
        });
    }
);

const isRevInsForm2Reducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsRevFormInsumos2, (_state, action) => {
            return action.payload;
        });
    }
);

const isShowLogBoxReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsShowLogBox, (_state, action) => {
            return action.payload;
        });
    }
);

const isShowLogBoxContReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsShowLogBoxCont, (_state, action) => {
            return action.payload;
        });
    }
);

const isShowLogBoxProcReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsShowLogBoxProc, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogInterReducer = createReducer<RespCatalogoInt>(
    {} as RespCatalogoInt,
    (builder) => {
        builder.addCase(updateCatalogoInt, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedClientReducer = createReducer<string>(
    "",
    (builder) => {
        builder.addCase(updateSelectedClient, (_state, action) => {
            return action.payload;
        });
    }
);

const eraseTriggerReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateTriggerEraseInt, (_state, action) => {
            return action.payload;
        });
    }
);

const showTableWalletReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowTableWallet, (_state, action) => {
            return action.payload;
        });
    }
);

const respClientWalletReducer = createReducer<RespClientWallet>(
    {} as RespClientWallet,
    (builder) => {
        builder.addCase(updateRespClientWallet, (_state, action) => {
            return action.payload;
        });
    }
);

const searchClientWalletReducer = createReducer<string>(
    "",
    (builder) => {
        builder.addCase(updateSearchClientWallet, (_state, action) => {
            return action.payload;
        });
    }
);

const searchContributorReducer = createReducer<string>(
    "",
    (builder) => {
        builder.addCase(updateSearchContributor, (_state, action) => {
            return action.payload;
        });
    }
);

const searchContributor2Reducer = createReducer<string>(
    "",
    (builder) => {
        builder.addCase(updateSearchContributor2, (_state, action) => {
            return action.payload;
        });
    }
);

const showFileWalletReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowFileWallet, (_state, action) => {
            return action.payload;
        });
    }
);

const chargeFileWalletReducer = createReducer<CargaArchivoContent>(
    {} as CargaArchivoContent,
    (builder) => {
        builder.addCase(updateFileWallet, (_state, action) => {
            return action.payload;
        });
    }
);

const showFileContributorReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowFileContributor, (_state, action) => {
            return action.payload;
        });
    }
);

const chargeFileContributorReducer = createReducer<CargaArchivoContent>(
    {} as CargaArchivoContent,
    (builder) => {
        builder.addCase(updateFileContributor, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogoEuroCurvasReducer = createReducer<ResponseCatalogoEuroCurvas>(
    {} as ResponseCatalogoEuroCurvas,
   (builder) => {
       builder.addCase(updateCatalogoEuroCurvas, (_state, action) => {
           return action.payload;
       });
   }
 );

 const isCheckboxCheckedReducer = createReducer<stateCheckbox>(
    {} as stateCheckbox,
    (builder) => {
        builder.addCase(updateStateCheckbox, (_state, action) => {
            return action.payload;
        });
    }
);

const rootInterReducer = combineReducers({
    fechaEurobonos: fechaEurobonosReducer,
    selectedVector: selectedVectorReducer,
    revisar1Data: revisar1DataReducer,
    isRevInsForm: isRevInsFormReducer,
    revisar2Data: revisar2DataReducer,
    filePreciosCont: filePreciosContReducer,
    isRevInsForm2: isRevInsForm2Reducer,
    isShowLogBox: isShowLogBoxReducer,
    isShowLogBoxCont: isShowLogBoxContReducer,
    isShowLogBoxProc: isShowLogBoxProcReducer,
    catalogInter: catalogInterReducer,
    selectedClient: selectedClientReducer,
    triggerEraseInt: eraseTriggerReducer,
    showTableWallet: showTableWalletReducer,
    respClientWallet: respClientWalletReducer,
    searchClientWallet: searchClientWalletReducer,
    searchContributor: searchContributorReducer,
    searchContributor2: searchContributor2Reducer,
    showFileWallet: showFileWalletReducer,
    fileWallet: chargeFileWalletReducer,
    showFileContributor: showFileContributorReducer,
    fileContributor: chargeFileContributorReducer,
    catalogoEuroCurva: catalogoEuroCurvasReducer,
    isCheckboxChecked: isCheckboxCheckedReducer
});

export const storeEuro = configureStore({
    reducer: rootInterReducer
});