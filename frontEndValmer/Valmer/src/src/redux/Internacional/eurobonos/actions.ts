import {createAction} from "@reduxjs/toolkit";
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

export const updateFechaEurobonos = createAction<string>('UPDATE_FECHA_EUROBONOS')
export const updateSelectedVector = createAction<string>('UPDATE_SELECTED_VECTOR')
export const updateRevisar1 = createAction<Revisar1Response>('UPDATE_REVISAR_1')
export const updateIsRevFormInsumos = createAction<boolean>('UPDATE_ISREVFORM_INSUMOS')
export const updateIsShowLogBox = createAction<boolean>('UPDATE_IS_SHOW_LOGBOX')
export const updateIsShowLogBoxCont = createAction<boolean>('UPDATE_IS_SHOW_LOGBOX_CONT')
export const updateIsShowLogBoxProc = createAction<boolean>('UPDATE_IS_SHOW_LOGBOX_PROC')
export const updateRevisar2 = createAction<Revisar2Response>('UPDATE_REVISAR_2')
export const updateFilePreciosCont = createAction<ArchivoPreciosCont>('UPDATE_FILE_PRECIOS_CONT')
export const updateIsRevFormInsumos2 = createAction<boolean>('UPDATE_ISREVFORM_INSUMOS2')
export const updateCatalogoInt = createAction<RespCatalogoInt>('UPDATE_CATALOGO_INTERNACIONAL')
export const updateSelectedClient = createAction<string>('UPDATE_SELECTED_CLIENT')
export const updateTriggerEraseInt = createAction<boolean>('UPDATE_TRIGGER_ERASE')
export const updateShowTableWallet = createAction<boolean>('UPDATE_SHOW_TABLE_WALLET')
export const updateRespClientWallet = createAction<RespClientWallet>('UPDATE_DATA_CLIENT')
export const updateSearchClientWallet = createAction<string>('UPDATE_SEARCH_CLIENT_WALLET')
export const updateSearchContributor = createAction<string>('UPDATE_SEARCH_CONTRIBUTOR')
export const updateSearchContributor2 = createAction<string>('UPDATE_SEARCH_CONTRIBUTOR2')
export const updateShowFileWallet = createAction<boolean>('UPDATE_SHOW_FILE_WALLET')
export const updateFileWallet = createAction<CargaArchivoContent>('UPDATE_FILE_WALLET')
export const updateShowFileContributor= createAction<boolean>('UPDATE_SHOW_FILE_CONTRIBUTOR')
export const updateFileContributor = createAction<CargaArchivoContent>('UPDATE_FILE_CONTRIBUTOR')
export const updateCatalogoEuroCurvas = createAction<ResponseCatalogoEuroCurvas>('UPDATE_CATALOGO_EURO_CURVAS');
export const updateStateCheckbox = createAction<stateCheckbox>('UPDATE_STATE_CHECKBOX')