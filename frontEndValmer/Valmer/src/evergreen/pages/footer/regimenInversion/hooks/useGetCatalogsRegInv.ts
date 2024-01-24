import { useState, useEffect } from 'react';
import {valmerApi} from '../../../../../api';
import { showAlert } from '../../../../../utils';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '@reduxjs/toolkit/dist/query/core/apiState';
import { updateCatalogRegInv } from '../../../../../redux/RegimenInversion';
import { CatalogoRegInv, ResponseDataRegInv } from '../../../../../model/RegimenInversion';

export const useGetCatalogsRegInv= () => {
  const catalog = useSelector((state: RootState<any, any, any>) => state.catalog) as unknown as CatalogoRegInv[];
  const [loadingCatalog, setLoadingCatalog] = useState(false);
  const dispatch = useDispatch();

  useEffect(() => {
    if (!catalog || catalog.length === 0) {
      setLoadingCatalog(true);
      valmerApi
        .get<ResponseDataRegInv>('/regimen-inv/catalogos')
        .then((response) => {
          setLoadingCatalog(false);
          dispatch(updateCatalogRegInv(response.data.body.catalogos));
        })
        .catch(async (error) => {
          setLoadingCatalog(false);
          if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
          } else {
            await showAlert('error', 'Error', error.message);
          }
        });
    }
  }, [catalog]);

  return { catalog, loadingCatalog };
};
