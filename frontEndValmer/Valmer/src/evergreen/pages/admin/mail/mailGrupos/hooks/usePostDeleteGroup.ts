import React, { SetStateAction, useState } from 'react';
import { fetchDataPostAct, userEncoded } from '../../../../../../utils';
import { useSelector } from 'react-redux';
import { RootState } from '@reduxjs/toolkit/dist/query/core/apiState';
import { ResponseAdminEnviosMailGrupos } from '../../../../../../model';

export const usePostDeleteGroup = (
  setSelectedGrupo: React.Dispatch<SetStateAction<{
    n_grupo: string,
    s_descripcion: string
  }>>) => {

  const [loadingCargaDelete, setLoadingCargaDelete] = useState(false);
  const [changeCatalogDelete, setChangeCatalogDelete] = useState(false);

  const catalogoEnviosMailGrupos = useSelector(
    (state: RootState<any, any, any>) => state.catalogoEnviosMailGrupos
  ) as unknown as ResponseAdminEnviosMailGrupos;

  const isNewData = (selectedGrupo: any): boolean => {
    return catalogoEnviosMailGrupos.body.registros.some(object => object.n_grupo === selectedGrupo.n_grupo)
  }

  const fetchDataPostDeleteGroup = async (selectedGroup: any) => {
    if (isNewData(selectedGroup)) {
      setChangeCatalogDelete(false);
      const request = {
        "n_grupo": String(selectedGroup.n_grupo),
        "s_descripcion": String(selectedGroup.s_descripcion)};
      setLoadingCargaDelete(true);
      await fetchDataPostAct(
        "/catalogos/borrar-catalogo",
        "Eliminado",
        " al borrar grupo",
        request,
        {
          s_nombre_catalogo: 'ENVIOS_MAIL_GRUPOS',
          s_user: userEncoded()
        }
        );
        setLoadingCargaDelete(false);
        setChangeCatalogDelete(true)
    } else {
      setSelectedGrupo({n_grupo: "", s_descripcion: ""})
    }
  };
    
  return { loadingCargaDelete, fetchDataPostDeleteGroup, changeCatalogDelete };
};