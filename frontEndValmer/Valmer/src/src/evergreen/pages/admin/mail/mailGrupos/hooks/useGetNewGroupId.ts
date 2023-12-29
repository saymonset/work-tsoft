import { fetchDataGetRet } from '../../../../../../utils';

export const useGetNewGroupId = () => {

  const getNewGroupId = async () => {
    try {
      const newId = await fetchDataGetRet(
        "/catalogos/obtiene-nuevo-id",
        " al obtener nuevo id",
        { s_nombre_catalogo: "ENVIOS_MAIL_GRUPOS" }
      );
      return newId.body.N_GRUPO;
    } catch (error) {
      console.error("Error al obtener el nuevo ID:", error);
    }
  };

  return { getNewGroupId };
};