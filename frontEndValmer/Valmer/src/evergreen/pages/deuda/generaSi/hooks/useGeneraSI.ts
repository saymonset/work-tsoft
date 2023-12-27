import React, {useState} from "react";
import {fetchDataSIGet} from "../../../../../utils";
export const useGeneraSI = () => {

    const [todos, setTodos] = useState(true);
    const [gubernamental, setGubernamental] = useState(true);
    const [corpBanc, setCorpBanc] = useState(true);
    const [eurobonos, setEurobonos] = useState(true);
    const [loading, setLoading] = useState(false);
    const [loadingGenSalidasCorp, setLoadingGenSalidasCorp] = useState(false);
    const [loadingGenSalidasEuro, setLoadingGenSalidasEuro] = useState(false);
    const [loadingEnviaMov, setLoadingEnviaMov] = useState(false);

    const handleTodosChange = () => {
        if (!todos) {
            setTodos(true);
            setGubernamental(true);
            setCorpBanc(true);
            setEurobonos(true);
        }
        else {
            setTodos(false);
            setGubernamental(false);
            setCorpBanc(false);
            setEurobonos(false);
        }
    };

    const handleCheckboxChange = (checkboxState: boolean, setCheckboxState: React.Dispatch<React.SetStateAction<boolean>>) => {
        if (checkboxState) {
            setCheckboxState(false);
            if (todos) {
                setTodos(false);
            }
        }
        else {
            setCheckboxState(true);
        }
    };

    const buildChkFamily = (): string => {
        let chk_family = "";

        if (todos) {
            chk_family = "TODOS";
        } else {
            if (gubernamental) {
                chk_family += "_GUBER";
            }
            if (corpBanc) {
                chk_family += "_CORP";
            }
            if (eurobonos) {
                chk_family += "_EURO";
            }
            if (chk_family.startsWith("_")) {
                chk_family = chk_family.substring(1);
            }
        }
        return chk_family;
    };


    const handleCalculo = async () => {
        setLoading(true)
        const chk_family = buildChkFamily();
        await fetchDataSIGet ("/instrumentos/genera-SI", " al generar SI",
            {
                accion: 2,
                chk_familia: chk_family,
                reproceso: false
            })
        setLoading(false)
    }

    const handleSI = async (action: number,
                            setLoadingOption: React.Dispatch<React.SetStateAction<boolean>>) => {
        setLoadingOption(true)
        const chk_family = buildChkFamily();
        await fetchDataSIGet ("/instrumentos/genera-SI", " al generar SI",
            {
                accion: action,
                chk_familia: chk_family,
                reproceso: false
            })
        setLoadingOption(false)
    }


    return {
        todos,
        gubernamental,
        corpBanc,
        eurobonos,
        loading,
        loadingGenSalidasCorp,
        loadingGenSalidasEuro,
        loadingEnviaMov,
        setGubernamental,
        setCorpBanc,
        setEurobonos,
        setLoadingGenSalidasCorp,
        setLoadingGenSalidasEuro,
        setLoadingEnviaMov,
        handleTodosChange,
        handleCheckboxChange,
        handleCalculo,
        handleSI
    }

}