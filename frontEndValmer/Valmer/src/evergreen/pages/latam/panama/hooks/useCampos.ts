import { useState, useEffect } from 'react';
import { fetchDataGetRet } from "../../../../../utils";
import { ResponseCamposHistorico } from '../../../../../model';

export const useCampos = () => {
  const [campos, setCampos] = useState<ResponseCamposHistorico>({} as ResponseCamposHistorico);
  const [loadingCampos, setLoadingCampos] = useState(false);
  const [camposLoaded, setCamposLoaded] = useState(false);

  useEffect(() => {
    const getCampos = async () => {
        if (!camposLoaded) {
            setLoadingCampos(true);
            const responseCampos = await fetchDataGetRet(
              "/latam/panama/historico/campos",
              " al obtener catálogos panamá",
              {}
            );
            setCampos(responseCampos);
            setLoadingCampos(false);
            setCamposLoaded(true);
        }
    }

    getCampos();
  }, []);

  return { campos, loadingCampos, setCampos, setCamposLoaded };
};
