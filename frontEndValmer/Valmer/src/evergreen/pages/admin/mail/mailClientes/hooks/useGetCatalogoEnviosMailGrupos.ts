import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchDataGetConsultaData } from '../../../../../../utils';
import { ResponseAdminEnviosMailGrupos } from '../../../../../../model';
import { RootState } from '@reduxjs/toolkit/dist/query/core/apiState';
import { updateCatalogoEnviosMailGrupos } from '../../../../../../redux';

export const useGetCatalogoEnviosMailGrupos = () => {
  const catalogoEnviosMailGrupos = useSelector(
    (state: RootState<any, any, any>) => state.catalogoEnviosMailGrupos
  ) as unknown as ResponseAdminEnviosMailGrupos;

  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();
  
  const fetchDataGrupos = async () => {
      setLoading(true);
      try {
        await fetchDataGetConsultaData(
          "/catalogos/consulta-catalogo",
          " al obtener catalogo de envíos mail grupos",
          {
            num_registros: 0,
            posicion: 0,
            s_nombre_catalogo: 'ENVIOS_MAIL_GRUPOS',
          },
          updateCatalogoEnviosMailGrupos,
          dispatch
        );
        setLoading(false);
      } catch (error) {
        setLoading(false);
        console.error("Error al obtener datos:", error);
      }
  };

  return { catalogoEnviosMailGrupos, loading, fetchDataGrupos };
};
