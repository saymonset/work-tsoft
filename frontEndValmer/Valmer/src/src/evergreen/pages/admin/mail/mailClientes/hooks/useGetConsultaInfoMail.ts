import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchDataGetConsultaData } from '../../../../../../utils';
import { RootState } from '@reduxjs/toolkit/dist/query/core/apiState';
import { updateInfoTableMail } from '../../../../../../redux/Admin';
import { InfoClienteMail } from '../../../../../../model/Admin/Models';

export const useGetConsultaInfoMail= () => {
  const infoTableMail = useSelector(
    (state: RootState<any, any, any>) => state.infoTableMail
  ) as unknown as InfoClienteMail;

  const [loadingCarga, setLoadingCarga] = useState(false);
  const dispatch = useDispatch();

  const fetchDataInfoMail = async (id: number | null) => {
    setLoadingCarga(true);
    await fetchDataGetConsultaData(
      "/mail-clientes/consulta-info-mail",
      " al obtener info tabla mail",
      {
        id: id
      },
      updateInfoTableMail, dispatch)
      setLoadingCarga(false);
  };

  return { infoTableMail, loadingCarga, fetchDataInfoMail };
};
