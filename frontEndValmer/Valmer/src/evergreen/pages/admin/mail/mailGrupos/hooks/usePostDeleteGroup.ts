import { useState } from 'react';
import { fetchDataPostRet, userEncoded } from '../../../../../../utils';

export const usePostDeleteGroup = () => {
    const [loadingCargaDelete, setLoadingCarga] = useState(false);
    const [changeCatalogDelete, setChangeCatalogDelete] = useState(false);

    const fetchDataPostDeleteGroup = async (selectedGroup: any) => {
      setChangeCatalogDelete(false);
      const request = {"n_grupo": String(selectedGroup.n_grupo), "s_descripcion": String(selectedGroup.s_descripcion)};
      setLoadingCarga(true);
      await fetchDataPostRet(
        "/catalogos/borrar-catalogo",
        " al borrar grupo",
        request,
        {
          s_nombre_catalogo: 'ENVIOS_MAIL_GRUPOS',
          s_user: userEncoded()
        }
        );
        setLoadingCarga(false);
        setChangeCatalogDelete(true)
      };
      
    return { loadingCargaDelete, fetchDataPostDeleteGroup, changeCatalogDelete };
};