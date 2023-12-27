import { useEffect, useState } from "react"
import { fetchDataGetRet, validChangeTvEmiSerie } from "../../../../../../utils"
import { RespDerivadosDef, TvEmiHookProps } from "../../../../../../model";
import { useDispatch } from "react-redux";

export const useTvEmiDef = (props: TvEmiHookProps) => {
    const [emisora, setEmisora] = useState<string[]>([])
    const [loadingEmisoras, setLoadingEmisoras] = useState(false)
    const [loadingConsultaData, setLoadingConsultaData] = useState(false)
    const [triggerEmisora, setTriggerEmisora] = useState(false)
    const [triggerConsultaData, setTriggerConsultaData] = useState(false)
    const [tipoTv, setTipoTv] = useState("")

    const dispatch = useDispatch()

    useEffect(() => {
        const getEmisoras = async () => {
            if (triggerEmisora) {
                setLoadingEmisoras(true);
                const response = await fetchDataGetRet(
                    "/derivados/def/emisora",
                    " al obtener catalogo emisora",
                    { sTv: props.selectedTv })
                setEmisora(response.body)
                setTriggerEmisora(false)
                setLoadingEmisoras(false)
            }
        }
        getEmisoras().then()
    }, [triggerEmisora]);

    useEffect(() => {
        const getConsultaData = async () => {
            if (triggerConsultaData) {
                setLoadingConsultaData(true);

                if (props.selectedTv !== 'default' && props.selectedEmisora !== 'default') {
                    const response = await fetchDataGetRet(
                        "/derivados/def/consulta-info",
                        " al obtener consulta data",
                        {
                            sTv: props.selectedTv,
                            sEmisora: props.selectedEmisora
                        }
                    );
                    props.setConsultaDataDerDef(response);
                    props.setIsVencimiento(true);
                } else {
                    props.setConsultaDataDerDef({} as RespDerivadosDef);
                    props.setIsVencimiento(true);
                }

                setTriggerConsultaData(false);
                setLoadingConsultaData(false);
            }
        };

        getConsultaData().then();
    }, [triggerConsultaData, props.selectedEmisora, props.selectedTv]);

    const handleTv = async (e: any) => {
        if (e.target.value === 'default') {
            // Si se elige 'default', reiniciar la emisora y consultaDataDerDef
            props.setSelectedEmisora('default');
            setTriggerConsultaData(true); // Asegúrate de activar triggerConsultaData aquí
        } else {
            // Si no es 'default', buscar emisoras
            setTriggerEmisora(true);
        }

        props.setSelectedTv(e.target.value);
        validChangeTvEmiSerie('s_tv', dispatch);

        if (e.target.value.includes('F')) {
            setTipoTv('F');
        } else if (e.target.value.includes('O')) {
            setTipoTv('O');
        } else {
            setTipoTv('otro');
        }
    };

    const handleEmisora = async (e: any) => {
        props.setConsultaDataDerDef({} as RespDerivadosDef);
        const { type } = e.target;

        if (type !== 'text') setTriggerConsultaData(true);

        if (e.target.value === 'default') {
            // Reiniciar la TV y la emisora seleccionadas si se elige 'default'
            props.setSelectedTv('default');
            props.setSelectedEmisora('default');
        } else {
            // Actualizar la emisora seleccionada
            props.setSelectedEmisora(e.target.value);
        }

        validChangeTvEmiSerie('s_emisora', dispatch);
    };

    const handleSelectedInst = (tv: string, emi: string) => {
        if (tv === 'default') {
            // Reiniciar consultaDataDerDef y seleccionar 'default' para la TV y la emisora
            props.setConsultaDataDerDef({} as RespDerivadosDef);
            props.setSelectedTv('default');
            props.setSelectedEmisora('default');
        } else {
            // Actualizar la TV y la emisora seleccionadas
            props.setSelectedTv(tv);
            props.setSelectedEmisora(emi);
        }

        // Desencadenar las actualizaciones
        setTriggerEmisora(true);
        setTriggerConsultaData(true);
    };

    return {
        loadingConsultaData,
        loadingEmisoras,
        emisora,
        tipoTv,
        handleTv,
        handleEmisora,
        handleSelectedInst
    }
}