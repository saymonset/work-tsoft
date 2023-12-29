import {useEffect, useState} from "react";
import {Cau, GraphCau, ResponseCau} from "../../../../model";
import {valmerApi} from "../../../../api";
import {showAlert} from "../../../../utils";

export const useRestCau = () => {
    const [cau, setCau] = useState<GraphCau[]>([]);
    const [loadingCau, setLoadingCau] = useState(true);

    useEffect(() => {
        valmerApi.get<ResponseCau>("/home/graficas/caus", )
            .then((response) => {
                setLoadingCau(false);
                setCau(changeVariables(response.data.body));
            })
            .catch(async (error) => {
                setLoadingCau(false);
                if (error.message.includes("Network Error")) {
                    await showAlert("error", "Error", "No hay conexión con el servidor");
                } else {
                    await showAlert("error", "Error Cau", error.message);
                }
            });

    },[]);

    const accentMap = {
        "Consultas Automaticas": "Consultas Automáticas",
        "Riesgos Fiancieros": "Riesgos Financieros"
    };

    const changeVariables = (data: Cau[]): GraphCau[] => {
        return data.map(item => {
            let descriptionWithAccents = item.descripcion;

            for (let [word, replacement] of Object.entries(accentMap)) {
                descriptionWithAccents = descriptionWithAccents.replace(new RegExp(`\\b${word}\\b`, 'g'), replacement);
            }

            return {
                value: item.num,
                name: descriptionWithAccents,
            };
        });
    };

    return {cau, loadingCau}
}