import { useState, useEffect } from 'react';
import { fetchDataGetRet } from "../../../../../utils";

export const useNemotecnicos = () => {
  const [nemoTecnicoHistorico, setNemoTecnicoHistorico] = useState<string[]>([]);
  const [loadingNemo, setLoadingNemo] = useState(false);
  const [nemoTecnicoLoaded, setNemoTecnicoLoaded] = useState(false);

  useEffect(() => {
    const getNemoTenicoHistorico = async () => {
      if (!nemoTecnicoLoaded) {
        setLoadingNemo(true);
        const responseNemo = await fetchDataGetRet(
          "/latam/panama/historico/nemotecnicos",
          " al obtener catálogos panamá",
          {}
        );
        setNemoTecnicoHistorico(responseNemo.body);
        setLoadingNemo(false);
        setNemoTecnicoLoaded(true);
      }
    }

    getNemoTenicoHistorico();
  }, []);

  return {
    nemoTecnicoHistorico,
    loadingNemo,
    setLoadingNemo,
    setNemoTecnicoLoaded
  };
};
