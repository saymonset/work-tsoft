import React, {useEffect, useState} from "react";
import {downloadFiles, fetchDataGetRet} from "../../../../../utils";
import {RespHistoricoConsultaTabla, RespHistoricoDownloadCsv} from "../../../../../model";
import {useNavigate} from "react-router-dom";
import {useNemotecnicos} from "./useNemotecnicos";
import {useCampos} from "./useCampos";

type SelectedCamposState = string[];

export const usePanamaHistorico = () => {

    const {
        nemoTecnicoHistorico,
        loadingNemo,
        setLoadingNemo,
        setNemoTecnicoLoaded
    } = useNemotecnicos();

    const {campos, loadingCampos, setCamposLoaded} = useCampos();

    const [selectedCampos, setSelectedCampos] = useState<SelectedCamposState>([]);
    const [checkNemotecnicos, setCheckNemotecnicos] = useState(false);
    const [checkCampos, setCheckCampos] = useState(false);
    const [fechaInicio, setFechaInicio] = useState('');
    const [fechaFin, setFechaFin] = useState('');
    const [nemotecnicoSeleccionado, setNemotecnicoSeleccionado] = useState('');
    const [allFieldsKeys, setAllFieldsKeys] = useState<string[]>([]);
    const [downloadResponse, setDownloadResponse] = useState<RespHistoricoDownloadCsv | null>(null);
    const [loadingLogCsv, setLoadingLogCsv] = useState(false);
    const [tabla, setTabla] = useState<RespHistoricoConsultaTabla | null>(null);
    const [genera, setGenera] = useState(false);
    const [loadingGenera, setLoadingGenera] = useState(false);
    const [loadingCancelar, setLoadingCancelar] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        if (checkCampos) {
            handleMoveAllFieldsToRight();
        } else {
            handleMoveAllFieldsToLeft();
        }
    }, [checkCampos]);

    const handleAllFieldsChange = () => setCheckCampos(!checkCampos);

    const handleMoveAllFieldsToRight = () => {
        const sourceSelect = document.getElementById('campos') as HTMLSelectElement | null;
        const targetSelect = document.getElementById('pickList') as HTMLSelectElement | null;

        if (sourceSelect && targetSelect) {
            let options = Array.from(sourceSelect.options);

            options.forEach(option => {
                targetSelect.add(new Option(option.text, option.value));
                setSelectedCampos(prevSelected => [...prevSelected, option.value]);
            });
        }
    };


    const handleMoveAllFieldsToLeft = () => {
        const pickListSelect = document.getElementById('pickList') as HTMLSelectElement | null;
        const sourceSelect = document.getElementById('campos') as HTMLSelectElement | null;

        if (pickListSelect && sourceSelect) {
            const options = Array.from(pickListSelect.options);

            options.forEach(option => {
                sourceSelect.add(new Option(option.text, option.value));
                setSelectedCampos(prevSelected => prevSelected.filter(value => value !== option.value));
                pickListSelect.remove(option.index);
            });
        }
    }

    const handleRightArrowClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();

        const sourceSelect = document.getElementById('campos') as HTMLSelectElement | null;
        const targetSelect = document.getElementById('pickList') as HTMLSelectElement | null;

        if (sourceSelect && targetSelect) {
            const options = Array.from(sourceSelect.options);

            options.forEach(option => {
                if (option.selected) {
                    targetSelect.add(new Option(option.text, option.value));
                    setSelectedCampos(prevSelected => [...prevSelected, option.value]);
                }
            });
        }
    };

    const handleLeftArrowClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();

        const pickListSelect = document.getElementById('pickList') as HTMLSelectElement | null;

        if (pickListSelect) {
            for (let i = pickListSelect.options.length - 1; i >= 0; i--) {
                const option = pickListSelect.options[i];
                if (option.selected) {
                    pickListSelect.remove(i);
                    setSelectedCampos((prevSelectedCampos) =>
                        prevSelectedCampos.filter((value) => value !== option.value)
                    );
                }
            }
        }
    };

    const handleGeneraClick = async () => {
        setLoadingGenera(true);

        const checkNemotecnicosBoolean = checkNemotecnicos ? 1 : 0;
        const checkCamposBoolean = checkCampos ? 1 : 0;
        const selectedCamposRequest = checkCampos ? allFieldsKeys.join(',') : selectedCampos.join(',');

        try {
            const [responseCsv, responseTabla] = await Promise.all([
                fetchDataGetRet('/latam/panama/historico/genera-csv', 'Fetching CSV Data', {
                    check_campos: checkCamposBoolean,
                    check_nemotecnicos: checkNemotecnicosBoolean,
                    fecha_fin: fechaFin,
                    fecha_inicio: fechaInicio,
                    s_nemotecnico: nemotecnicoSeleccionado,
                    selected_campos: selectedCamposRequest
                }),
                fetchDataGetRet('/latam/panama/historico/consulta-tabla', 'Fetching Table Data', {
                    check_campos: checkCamposBoolean,
                    check_nemotecnicos: checkNemotecnicosBoolean,
                    fecha_fin: fechaFin,
                    fecha_inicio: fechaInicio,
                    s_nemotecnico: nemotecnicoSeleccionado,
                    selected_campos: selectedCamposRequest
                })
            ]);

            setDownloadResponse(responseCsv);
            setTabla(responseTabla);
            setGenera(true);
        } catch (error) {
            console.error('Error fetching data:', error);
        } finally {
            setLoadingGenera(false);
        }
    };

    const downloadFilesFromUrl = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        setLoadingLogCsv(true);
        if (downloadResponse) {
            if (!downloadResponse.message) {
                downloadFiles(downloadResponse.body.contenido,
                    downloadResponse.body.nombreCompleto,
                    'text/csv')
            } else {
                const contenido = "NEMOTECNICO,FECHA,BASE,CIERRE LIBRO,CLASE"
                const contenidoBase64 = btoa(contenido);

                downloadFiles(contenidoBase64, 'Hist_Panama.csv', 'text/csv')
            }
        }
        setLoadingLogCsv(false);
    };

    const resetState = () => {
        setLoadingCancelar(true);
        setSelectedCampos([]);
        setCheckNemotecnicos(false);
        setCheckCampos(false);
        setFechaInicio('');
        setFechaFin('');
        setNemotecnicoSeleccionado('default');
        setNemoTecnicoLoaded(false);
        setCamposLoaded(false);
        setAllFieldsKeys([]);
        setDownloadResponse(null);
        setLoadingLogCsv(false);
        setTabla(null);
        setLoadingNemo(false);
        setGenera(false);

        const pickListSelect = document.getElementById('pickList');

        if (pickListSelect) {
            pickListSelect.innerHTML = '';
        }

        setLoadingCancelar(false);
    };

    const handleRegresar = () => {
        navigate(-1);
    };

    return {
        nemoTecnicoHistorico,
        loadingNemo,
        campos,
        checkCampos,
        checkNemotecnicos,
        fechaInicio,
        fechaFin,
        nemotecnicoSeleccionado,
        loadingLogCsv,
        tabla,
        genera,
        loadingGenera,
        loadingCancelar,
        loadingCampos,
        resetState,
        handleRegresar,
        handleRightArrowClick,
        handleLeftArrowClick,
        setCheckNemotecnicos,
        handleGeneraClick,
        setFechaInicio,
        setFechaFin,
        setNemotecnicoSeleccionado,
        handleAllFieldsChange,
        downloadFilesFromUrl
    }
}