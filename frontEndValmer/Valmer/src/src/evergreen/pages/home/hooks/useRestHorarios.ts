import {useEffect, useState} from "react";
import {valmerApi} from "../../../../api";
import {showAlert} from "../../../../utils";
import {BDBody, Insumo, ResponseDataHome} from "../../../../model";

export const useRestHorarios = () => {
    const [horarios, setHorarios] = useState<BDBody[]>([]);
    const [loadingHorarios, setLoadingHorarios] = useState(true);

    useEffect(() => {
        valmerApi.get<ResponseDataHome>("home/graficas/horarios", )
            .then((response) => {
                setLoadingHorarios(false);
                setHorarios(processData(response.data.body));
            })
            .catch(async (error) => {
                setLoadingHorarios(false);
                if (error.message.includes("Network Error")) {
                    await showAlert("error", "Error", "No hay conexión con el servidor");
                } else {
                    await showAlert("error", "Error horarios", error.message);
                }
            });

    },[]);

    const processData = (horarios: Insumo[]) => {
        if (!horarios || horarios.length === 0) {
            return [];
        }

        return horarios.map((v) => ({
            BD: v.insumo,
            horario: v.hora,
        }));
    };

    return { horarios, loadingHorarios };
};