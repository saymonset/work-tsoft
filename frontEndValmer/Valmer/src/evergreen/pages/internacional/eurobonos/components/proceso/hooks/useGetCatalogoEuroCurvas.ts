import { useState, useEffect } from 'react';
import { fetchDataGetConsultaData } from '../../../../../../../utils';
import { ResponseCatalogoEuroCurvas } from '../../../../../../../model';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '@reduxjs/toolkit/dist/query/core/apiState';
import { updateCatalogoEuroCurvas } from '../../../../../../../redux';

export const useGetCatalogoEuroCurvas = () => {
  const catalogoEuroCurva = useSelector(
    (state: RootState<any, any, any>) => state.catalogoEuroCurva
  ) as unknown as ResponseCatalogoEuroCurvas;
  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();

  const fetchCatalogoEuroCurvas = async () => {
    setLoading(true);
    try {
      await fetchDataGetConsultaData(
        "/internacional/proceso-eurobonos/consulta-catalogo-insumo-curvas",
        " al obtener catalogo de envíos mail grupos",
        {
          num_registros: 0,
          posicion: 0,
          s_nombre_catalogo: 'EURO_CURVAS',
        },
        updateCatalogoEuroCurvas,
        dispatch
      );
      setLoading(false);
    } catch (error) {
      setLoading(false);
      console.error("Error al obtener datos:", error);
    }
  };

  const updateCatalogo = () => {
    fetchCatalogoEuroCurvas().then();
  };

  useEffect(() => {
    fetchCatalogoEuroCurvas().then();
  }, []);

  return { catalogoEuroCurva, loading, fetchCatalogoEuroCurvas, updateCatalogo };
};
