import {useRef, useState} from "react";
import {Catalogo, InputOrNull, InputOrSelect, IsFieldRequiredLatCr, RefReqLatCr, RespConsultaDataCR, SelectOrNull} from "../../../../../../model";

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

    const [isFieldRequired, setIsFieldRequired] = useState<IsFieldRequiredLatCr>({} as IsFieldRequiredLatCr)

    const requeridos: RefReqLatCr = {
        s_emisor: useRef<InputOrSelect>(null),
        s_nemo_instr: useRef<InputOrSelect>(null),
        s_serie: useRef<InputOrSelect>(null),
        s_isin: useRef<InputOrNull>(null),
        n_tipo_tasa: useRef<SelectOrNull>(null),
        n_tipo_merc: useRef<SelectOrNull>(null),
        n_moneda: useRef<SelectOrNull>(null),
        n_edo_instrumento: useRef<SelectOrNull>(null),
        n_monto_colocado: useRef<InputOrNull>(null),
        n_pais: useRef<SelectOrNull>(null),
        n_monto_aprobado: useRef<InputOrNull>(null),
        n_tipo_valor: useRef<SelectOrNull>(null)
    }

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
        isFieldRequired,
        requeridos,
        setIsFieldRequired,
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