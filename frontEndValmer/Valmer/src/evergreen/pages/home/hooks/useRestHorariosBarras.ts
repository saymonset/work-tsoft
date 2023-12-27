import {useEffect, useState} from "react";
import {HorarioBarra, Insumo, ResponseDataHome} from "../../../../model";
import {valmerApi} from "../../../../api";
import {showAlert} from "../../../../utils";
import dayjs from "dayjs";
export const useRestHorariosBarras = () => {
    const [horariosBarras, setHorariosBarras] = useState<HorarioBarra[]>([]);
    const [loadingBarras, setLoadingBarras] = useState(true);

    useEffect(() => {
        valmerApi.get<ResponseDataHome>("home/graficas/horarios-web/", )
            .then((response) => {
                setLoadingBarras(false);
                setHorariosBarras(processData(response.data.body));
            })
            .catch(async (error) => {
                setLoadingBarras(false);
                if (error.message.includes("Network Error")) {
                    await showAlert("error", "Error", "No hay conexión con el servidor");
                } else {
                    await showAlert("error", "Error horarios barras", error.message);
                }
            });

    },[]);

    const processData = (horariosBarras : Insumo[]): HorarioBarra[] => {
        const renameMap: any = {
            Vector_Gubernamental: 'Gubernamental',
            Vector_Preliminar: 'Preliminar',
            Vector_Precios_Definitivo: 'Definitivo',
            Vector_Precios_Notas_PV: 'Definitivo_Notas_PV',
            Vector_Precios_Notas_Estructuradas: 'Def_Notas_Estruct',
        };

        const grouped: any = {};

        horariosBarras.forEach((item: any) => {
            const { fecha, idInsumo, insumo, ...rest } = item;
            const formattedDate = dayjs(fecha).format('YYYY-MM-DD');

            if (!grouped[formattedDate]) {
                grouped[formattedDate] = {
                    name: formattedDate,
                };
            }

            const renamedInsumo = renameMap[insumo] || insumo;

            grouped[formattedDate][renamedInsumo] = parseFloat(rest.hora.replace(':', '.'));

            delete rest.hora;

            Object.assign(grouped[formattedDate], rest);
        });

        return Object.values(grouped).slice(-6) as HorarioBarra[]
    };

    return { horariosBarras, loadingBarras };
}