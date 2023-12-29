import { useDispatch } from 'react-redux';
import { updateInfoTableMail } from '../../../../../../redux/Admin';
import { fetchDataGetConsultaData } from '../../../../../../utils';

export const useBorrarEnvioMailClientes = () => {
  const dispatch = useDispatch();

  const fetchDeleteDataInfoMail = async (nCliente: string, nGrupo: number | undefined) => {
    await fetchDataGetConsultaData(
      "/mail-clientes/borrar-envio-mail-clientes",
      " al borrar un grupo en un cliente",
      {
        n_cliente: nCliente,
        n_grupo: nGrupo
      },
      updateInfoTableMail,
      dispatch
    );
  };

  return { fetchDeleteDataInfoMail };
};
