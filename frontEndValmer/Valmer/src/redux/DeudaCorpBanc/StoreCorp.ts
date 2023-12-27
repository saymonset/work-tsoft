import {combineReducers} from 'redux';
import {
    Catalogo,
    FvDeudaCorpInstrumentos,
    IsFieldModifiedFvDdGobIns,
    ResponseConsultaDataCorp,
    initialFormValuesCorp, CatalogCalificadora, CargaArchivoContent,
} from "../../model";
import {configureStore, createReducer} from "@reduxjs/toolkit";
import {
    updateCargaArchivoCorp,
    updateConsultaDataCorp,
    updateEmisoraCorp,
    updateFormValuesCorp,
    updateSelectedEmisoraCorp,
    updateSelectedSerieCorp,
    updateSelectedTvCorp,
    updateSerieCorp,
    updateCatalogCorp,
    updateIsNewFormCorp,
    updateIsNewSerieCorp,
    updateCatalogCalifCorp,
    updateTv,
    updateRequiredTvCorp,
    updateRequiredEmisoraCorp,
    updateRequiredSerieCorp,
    updateSelectDatePrice,
    updateTriggerEraseCorp,
    updateFieldRequiredCorp,
    updateCatalogCalificadoras,
    updateIsFrecBimestral,
    updateIsInterBonosCorp,
    updateIsSelectPanelCorp, updateEmisoraRef1Corp, updateEmisoraRef2Corp, updateSerieRef1Corp, updateSerieRef2Corp,
} from "./actions";


const formValuesCorpReducer = createReducer<FvDeudaCorpInstrumentos>(
    initialFormValuesCorp,
    (builder) => {
        builder.addCase(updateFormValuesCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const consultaDataCorpReducer = createReducer<ResponseConsultaDataCorp>(
    {} as ResponseConsultaDataCorp,
    (builder) => {
        builder.addCase(updateConsultaDataCorp, (_state, action) => {
            return action.payload;
        });
    }
);


const selectedTvCorpReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedTvCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedEmisoraCorpReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedEmisoraCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedSerieCorpReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedSerieCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const selectDatePriceReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectDatePrice, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogCalificacionesReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogCalifCorp, (_state, action) => {
            return action.payload;
        });
    }
);


const emisoraCorpReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateEmisoraCorp, (_state, action) => {
            return action.payload;
        });
    }
);
const serieCorpReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateSerieCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const tvReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateTv, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogCalificadorasReducer = createReducer<CatalogCalificadora>(
    {} as CatalogCalificadora,
    (builder) => {
        builder.addCase(updateCatalogCalificadoras, (_state, action) => {
            return action.payload;
        });
    }
);

const cargaArchivoCorpReducer = createReducer<CargaArchivoContent>(
    {} as CargaArchivoContent,
    (builder) => {
        builder.addCase(updateCargaArchivoCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const isNewFormCorpReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsNewFormCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const isNewSerieCorpReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsNewSerieCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const isFrecuenciaBimestralReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsFrecBimestral, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredTvCorpReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredTvCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredEmisoraCorpReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredEmisoraCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredSerieCorpReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredSerieCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const triggerEraseReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateTriggerEraseCorp, (_state, action) => {
            return action.payload;
        });
    }
);


const fieldRequiredCorpReducer = createReducer<IsFieldModifiedFvDdGobIns>(
    {} as IsFieldModifiedFvDdGobIns,
    (builder) => {
        builder.addCase(updateFieldRequiredCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const isInterBonosReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsInterBonosCorp, (_state, action) => {
            return action.payload;
        });
    }
);

const isSelectPanelCorpReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsSelectPanelCorp, (_state, action) => {
            return action.payload;
        });
    }
);


const emisoraRef1CorpReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateEmisoraRef1Corp, (_state, action) => {
            return action.payload;
        });
    }
);

const emisoraRef2CorpReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateEmisoraRef2Corp, (_state, action) => {
            return action.payload;
        });
    }
);

const serieRef1CorpReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateSerieRef1Corp, (_state, action) => {
            return action.payload;
        });
    }
);

const serieRef2CorpReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateSerieRef2Corp, (_state, action) => {
            return action.payload;
        });
    }
);


const rootCorpReducer = combineReducers({
    formValuesCorp: formValuesCorpReducer,
    consultaDataCorp: consultaDataCorpReducer,
    selectedTvCorp: selectedTvCorpReducer,
    selectedEmisoraCorp: selectedEmisoraCorpReducer,
    selectedSerieCorp: selectedSerieCorpReducer,
    selectedDatePrice: selectDatePriceReducer,
    catalog: catalogReducer,
    catalogCalifCorp: catalogCalificacionesReducer,
    emisoraCorp: emisoraCorpReducer,
    serieCorp: serieCorpReducer,
    tv: tvReducer,
    cargaArchivoCorp: cargaArchivoCorpReducer,
    isNewFormCorp: isNewFormCorpReducer,
    isNewSerieCorp: isNewSerieCorpReducer,
    isFrecuenciaBimestral: isFrecuenciaBimestralReducer,
    requiredTvCorp: requiredTvCorpReducer,
    requiredEmisoraCorp: requiredEmisoraCorpReducer,
    requiredSerieCorp: requiredSerieCorpReducer,
    catalogCalificadoras: catalogCalificadorasReducer,
    triggerErase: triggerEraseReducer,
    fieldRequiredCorp: fieldRequiredCorpReducer,
    isInterBonos: isInterBonosReducer,
    isSelectPanelCorp: isSelectPanelCorpReducer,

    emisoraRef1Corp: emisoraRef1CorpReducer,
    emisoraRef2Corp: emisoraRef2CorpReducer,
    serieRef1Corp: serieRef1CorpReducer,
    serieRef2Corp: serieRef2CorpReducer
});

export const storeCorp = configureStore({
    reducer: rootCorpReducer,
});