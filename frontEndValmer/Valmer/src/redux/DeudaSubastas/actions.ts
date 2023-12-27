import {CatalogoSubastas, ConsultaSubastasTransformArray} from '../../model';
import { createAction } from '@reduxjs/toolkit';

export const updateCatalogSubastas = createAction<CatalogoSubastas[]>('UPDATE_CATALOG_SUBASTAS');
export const updateConsultaSubastas = createAction<ConsultaSubastasTransformArray>('UPDATE_QUERY_SUBASTAS');
export const updateIsShowLog = createAction<boolean>('UPDATE_SHOW_LOG');
export const updateIsConsultaLog = createAction<boolean>('UPDATE_CONSULTA_LOG');