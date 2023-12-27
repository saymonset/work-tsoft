import {useState} from "react";
import {Catalogo, RespConsultaDataCR} from "../../../../../../model";

export const useInitialVarCr = () => {
    const [consultaData, setConsultaData] =
        useState<RespConsultaDataCR>({} as RespConsultaDataCR)

    const [emisor, setEmisor] = useState<string[]>([])

    const [nemoInstrumento, setNemoInstrumento] = useState<string[]>([])

    const [serie, setSerie] = useState<string[]>([])

    const [catalog, setCatalog] = useState<Catalogo[]>([])

    const [selectedEmisor, setSelectedEmisor] = useState<string>("")

    const [selectedNemo, setSelectedNemo] = useState<string>("")

    const [selectedSerie, setSelectedSerie] = useState<string>("")

    const [checkboxValue, setCheckboxValue] = useState(1);

    const [loadingSave, setLoadingSave] = useState(false);

    const [loadingEmisor, setLoadingEmisor] = useState(false);

    const [loadingNemoInst, setLoadingNemoInst] = useState(false);

    const [loadingSerie, setLoadingSerie] = useState(false);

    const [loadingCatalogo, setLoadingCatalogo] = useState(false);

    const [loadingConsultaInfo, setLoadingConsultaInfo] = useState(false);

    const [triggerNemo, setTriggerNemo] = useState(false);

    const [triggerSerie, setTriggerSerie] = useState(false);

    const [triggerConsultaInfo, setTriggerConsultaInfo] = useState(false);

    const [activeNuevo, setActiveNuevo] = useState(false)


    return {
        consultaData,
        emisor,
        nemoInstrumento,
        serie,
        catalog,
        selectedEmisor,
        selectedNemo,
        selectedSerie,
        checkboxValue,
        loadingSave,
        loadingEmisor,
        loadingNemoInst,
        loadingSerie,
        loadingCatalogo,
        loadingConsultaInfo,
        triggerNemo,
        triggerSerie,
        triggerConsultaInfo,
        activeNuevo,
        setConsultaData,
        setEmisor,
        setNemoInstrumento,
        setSerie,
        setCatalog,
        setSelectedEmisor,
        setSelectedNemo,
        setSelectedSerie,
        setCheckboxValue,
        setLoadingSave,
        setLoadingEmisor,
        setLoadingNemoInst,
        setLoadingSerie,
        setLoadingCatalogo,
        setLoadingConsultaInfo,
        setTriggerNemo,
        setTriggerSerie,
        setTriggerConsultaInfo,
        setActiveNuevo
    };
}