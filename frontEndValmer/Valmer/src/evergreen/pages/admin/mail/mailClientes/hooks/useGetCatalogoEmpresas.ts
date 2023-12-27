import { useState, useEffect } from 'react';
import {valmerApi} from '../../../../../../api';
import { showAlert } from '../../../../../../utils';
import { Catalogo, ResponseDataCorp } from '../../../../../../model';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '@reduxjs/toolkit/dist/query/core/apiState';
import { updateCatalogo } from '../../../../../../redux';

export const useGetCatalogoEmpresas = () => {
  const catalogo = useSelector((state: RootState<any, any, any>) => state.catalogo) as unknown as Catalogo[];
  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();

  useEffect(() => {
    if (!catalogo || catalogo.length === 0) {
      setLoading(true);
      valmerApi
        .get<ResponseDataCorp>('/latam/cr/mantenimiento-cau/catalogos')
        .then((response) => {
          setLoading(false);
          dispatch(updateCatalogo(response.data.body.catalogos));
        })
        .catch(async (error) => {
          setLoading(false);
          if (error.message.includes('Network Error')) {
            await showAlert('error', 'Error', 'No hay conexión con el servidor');
          } else {
            await showAlert('error', 'Error', error.message);
          }
        });
    }
  }, [catalogo]);

  return { catalogo, loading };
};
