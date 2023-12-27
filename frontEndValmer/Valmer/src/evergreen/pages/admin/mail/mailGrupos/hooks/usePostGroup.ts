import { useState } from 'react';
import { fetchDataPostRet, userEncoded } from '../../../../../../utils';

export const usePostGroup = () => {
    const [loadingCarga, setLoadingCarga] = useState(false);
    const [changeCatalogPost, setChangeCatalogPost] = useState(false);

    const fetchDataPostGroup = async (selectedGroup: any) => {
      setChangeCatalogPost(false);

      const request = {"n_grupo": String(selectedGroup.n_grupo), "s_descripcion": String(selectedGroup.s_descripcion)};

      setLoadingCarga(true);
      
      await fetchDataPostRet(
        "/catalogos/guardar-catalogo",
        " al guardar grupo",
        request,
        {
          s_nombre_catalogo: 'ENVIOS_MAIL_GRUPOS',
          s_user: userEncoded()
        }
        );
        setLoadingCarga(false);
        setChangeCatalogPost(true)
      };
      
    return { loadingCarga, fetchDataPostGroup, changeCatalogPost };
};