import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchDataGetConsultaData } from '../../../../../../utils';
import { RootState } from '@reduxjs/toolkit/dist/query/core/apiState';
import { updateTableMail } from '../../../../../../redux/Admin';
import { ResponseTableMail } from '../../../../../../model/Admin/Models';

export const useGetTableMail = () => {
  const tableMail = useSelector(
    (state: RootState<any, any, any>) => state.tableMail
  ) as unknown as ResponseTableMail;

  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();

  const fetchData = async (selectedEmpresa: number | null, numRegistros: number, position: number) => {
    setLoading(true);
    await fetchDataGetConsultaData(
      "/mail-clientes/tabla-mail",
      " al obtener info tabla mail",
      {
        n_emp: selectedEmpresa,
        num_registros: numRegistros,
        posicion: position
      },
      updateTableMail, dispatch)
      setLoading(false);
  };

  return { tableMail, loading, fetchData };
};
