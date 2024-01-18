import {useRestHorarios} from "./useRestHorarios";
import {useRestHorariosBarras} from "./useRestHorariosBarras";
import dayjs from "dayjs";
import {useRestCau} from "./useRestCau";
import { downloadFiles, fetchDataGetRet, showAlert } from "../../../../utils";

export const useHome = () => {

    const {horarios, loadingHorarios} = useRestHorarios()
    const {horariosBarras, loadingBarras} = useRestHorariosBarras()
    const {cau, loadingCau} = useRestCau()

    const previousDate = () => {
        return dayjs().subtract(1, 'day').format('DD/MM/YYYY')
    }

    const userName = localStorage.getItem('user');

    const handleDownloadObjetivosCalidad = async () => {
        try {
            const response = await fetchDataGetRet(
              '/documentos/home/objetivos-calidad',
              'Error al obtener el archivo',
              {});
              const contenido = response.body.contenido
              const nombreCompleto = response.body.nombreCompleto
            if (contenido && nombreCompleto){
                downloadFiles(contenido, nombreCompleto,'pdf')
            } else {
                await showAlert('error', 'Error', 'Respuesta de la API inesperada');
            }
        } catch (error) {
            console.error('Error al descargar el archivo:', error);
            await showAlert('error', 'Error', 'Ocurrió un error al descargar el archivo');
          }
    };

    const handleDownloadCodigoConducta = async () => {
        try {
            const response = await fetchDataGetRet(
              '/documentos/home/codigo-conducta',
              'Error al obtener el archivo',
              {});
              const contenido = response.body.contenido
              const nombreCompleto = response.body.nombreCompleto
            if (contenido && nombreCompleto){
                downloadFiles(contenido, nombreCompleto,'pdf')
            } else {
                await showAlert('error', 'Error', 'Respuesta de la API inesperada');
            }
        } catch (error) {
            console.error('Error al descargar el archivo:', error);
            await showAlert('error', 'Error', 'Ocurrió un error al descargar el archivo');
          }
    };

    const handleDownloadGuiaAtencionCau = async () => {
        try {
            const response = await fetchDataGetRet(
              '/documentos/home/guia-atencion-cau',
              'Error al obtener el archivo',
              {});
              const contenido = response.body.contenido
              const nombreCompleto = response.body.nombreCompleto
            if (contenido && nombreCompleto){
                downloadFiles(contenido, nombreCompleto,'pptx')
            } else {
                await showAlert('error', 'Error', 'Respuesta de la API inesperada');
            }
        } catch (error) {
            console.error('Error al descargar el archivo:', error);
            await showAlert('error', 'Error', 'Ocurrió un error al descargar el archivo');
          }
    };

    return {
        loadingHorarios,
        loadingBarras,
        horarios,
        horariosBarras,
        cau,
        loadingCau,
        handleDownloadObjetivosCalidad,
        handleDownloadCodigoConducta,
        handleDownloadGuiaAtencionCau,
        previousDate,
        userName
    }

}