import { useDispatch } from 'react-redux';
import { fetchDataGetConsultaData } from '../../../../../../utils';
import { updateInfoTableMail } from '../../../../../../redux/Admin';

export const useNuevoEnvioMailClientes = () => {
  const dispatch = useDispatch();

  const fetchDataInfoMail = async (nCliente: string, nGrupo: number | null) => {
    await fetchDataGetConsultaData(
      "/mail-clientes/nuevo-envio-mail-clientes",
      " al insertar un nuevo grupo en un cliente",
      {
        n_cliente: nCliente,
        n_grupo: nGrupo
      },
      updateInfoTableMail,
      dispatch
    );
  };

  return { fetchDataInfoMail };
};
