import {useState} from "react";
import {useDispatch} from "react-redux";

export const useInitValPrecalc = () => {

    const [tv, setTv] = useState<string[]>([]);
    const [emisora, setEmisora] = useState<string[]>([]);
    const [serie, setSerie] = useState<string[]>([]);

    const [selectedTv, setSelectedTv] = useState("");
    const [selectedEmisora, setSelectedEmisora] = useState("");
    const [selectedSerie, setSelectedSerie] = useState("");

    const [triggerEmisora, setTriggerEmisora] = useState(false);
    const [triggerSerie, setTriggerSerie] = useState(false);

    const [loadingTv, setLoadingTv] = useState(false);
    const [loadingEmisoras, setLoadingEmisoras] = useState(false);
    const [loadingSerie, setLoadingSerie] = useState(false);

    const dispatch = useDispatch()

    return {
        tv,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        triggerEmisora,
        triggerSerie,
        loadingTv,
        loadingEmisoras,
        loadingSerie,
        setTv,
        setEmisora,
        setSerie,
        setSelectedTv,
        setSelectedEmisora,
        setSelectedSerie,
        setTriggerEmisora,
        setTriggerSerie,
        setLoadingTv,
        setLoadingEmisoras,
        setLoadingSerie,
        dispatch
    };
}