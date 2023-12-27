import {
    CargaArchivoContent, CatalogCalificadora,
    Catalogo,
    FvDeudaCorpInstrumentos,
    IsFieldModifiedFvDdGobIns,
    ResponseConsultaDataCorp
} from "../../model";
import {createAction} from "@reduxjs/toolkit";

export const updateFormValuesCorp = createAction<FvDeudaCorpInstrumentos>('UPDATE_FORM_VALUES_CORP');
export const updateConsultaDataCorp = createAction<ResponseConsultaDataCorp>('UPDATE_CONSULTA_DATA_CORP')
export const updateSelectedTvCorp = createAction<string>('UPDATE_SELECTED_TV_CORP')
export const updateSelectedEmisoraCorp = createAction<string>('UPDATE_SELECTED_EMISORA_CORP')
export const updateSelectedSerieCorp = createAction<string>('UPDATE_SELECTED_SERIE_CORP')
export const updateSelectDatePrice = createAction<string>('UPDATE_SELECTED_DATE_PRICE')
export const updateTv = createAction<string[]>('UPDATE_TV');
export const updateCatalogCorp = createAction<Catalogo[]>('UPDATE_CATALOG_CORP');
export const updateCatalogCalifCorp = createAction<Catalogo[]>('UPDATE_CATALOG_CALIFICACIONES_CORP');
export const updateEmisoraCorp = createAction<string[]>('UPDATE_EMISORA_CORP')
export const updateSerieCorp = createAction<string[]>('UPDATE_SERIE_CORP')
export const updateCargaArchivoCorp = createAction<CargaArchivoContent>('UPDATE_CARGA_ARCHIVO_CORP')
export const updateIsNewFormCorp = createAction<boolean>('IS_NEW_FORM_CORP_INST')
export const updateIsNewSerieCorp = createAction<boolean>('IS_NEW_SERIE_CORP')
export const updateRequiredTvCorp = createAction<boolean>('REQUIRED_TV_CORP');
export const updateRequiredEmisoraCorp = createAction<boolean>('REQUIRED_EMISORA_CORP');
export const updateRequiredSerieCorp = createAction<boolean>('REQUIRED_SERIE_CORP');
export const updateTriggerEraseCorp = createAction<boolean>('UPDATE_TRIGGER_ERASE')
export const updateIsInterBonosCorp = createAction<boolean>('UPDATE_IS_INTER_BONOS_CORP')
export const updateCatalogCalificadoras = createAction<CatalogCalificadora>('UPDATE_CATALOG_CALIFICADORAS')
export const updateFieldRequiredCorp = createAction<IsFieldModifiedFvDdGobIns>('UPDATE_FIELD_REQUIRED_CORP');
export const updateIsFrecBimestral = createAction<boolean>('UPDATE_IS_FREC_BIMESTAL');
export const updateIsSelectPanelCorp = createAction<boolean>('UPDATE_IS_SELECT_PANEL_CORP')
export const updateEmisoraRef1Corp = createAction<string[]>('UPDATE_EMISORA_REF1_CORP')
export const updateEmisoraRef2Corp = createAction<string[]>('UPDATE_EMISORA_REF2_CORP')
export const updateSerieRef1Corp = createAction<string[]>('UPDATE_SERIE_REF1_CORP')
export const updateSerieRef2Corp = createAction<string[]>('UPDATE_SERIE_REF2_CORP')
