import React, {useEffect, useRef, useState} from "react";
import {Catalogo, InputOrNull, IsFieldRequiredLatPanama, RefReqLatPanama, RespConsultaDataPanam, SelectOrNull} from "../../../../../model";
import {fetchDataGetRet, fetchDataPost, userEncoded, validateFieldsAccCalifLatam} from "../../../../../utils";

export const usePanama = () => {

    const refReqLatPanama: RefReqLatPanama = {
        n_tipo_instrumento: useRef<SelectOrNull>(null),
        n_tipo_instrumento_edit: useRef<SelectOrNull>(null),
        d_fecha_emision: useRef<InputOrNull>(null),
        n_frecuencia_cupon: useRef<SelectOrNull>(null),
        d_fecha_liquidacion: useRef<InputOrNull>(null),
        n_tipo_mercado: useRef<SelectOrNull>(null),
        d_fecha_vencimiento: useRef<InputOrNull>(null),
        n_clase: useRef<SelectOrNull>(null),
        d_fecha_inicio_cupon: useRef<InputOrNull>(null),
        n_sector: useRef<SelectOrNull>(null),
        d_fecha_vto_cupon: useRef<InputOrNull>(null),
        n_curva_desc: useRef<SelectOrNull>(null),
        n_plazo: useRef<InputOrNull>(null),
        n_moneda: useRef<SelectOrNull>(null),
        n_monto_colocado: useRef<InputOrNull>(null),
        n_theo_model: useRef<SelectOrNull>(null),
        n_valor_nominal: useRef<InputOrNull>(null),
        n_tasa: useRef<InputOrNull>(null),
        n_base_calculo: useRef<SelectOrNull>(null),
        n_sobretasa: useRef<InputOrNull>(null),
        n_status: useRef<SelectOrNull>(null),
        s_isin: useRef<InputOrNull>(null),
        n_precio: useRef<InputOrNull>(null),
        n_form_cotizacion: useRef<SelectOrNull>(null),
        n_coupon_gen_met: useRef<SelectOrNull>(null),
        n_odd_last_coupon: useRef<SelectOrNull>(null),
        n_fixed_coupon_date: useRef<InputOrNull>(null),
        n_odd_first_coupon: useRef<SelectOrNull>(null),
        n_pais: useRef<SelectOrNull>(null),
        n_emisor: useRef<SelectOrNull>(null),
        d_fecha_ingreso_titulo: useRef<InputOrNull>(null),
        n_crv_index: useRef<SelectOrNull>(null)
    }

    const [nemoTecnico, setNemoTecnico] = useState<string[]>([])

    const [selectedNemoTecnico, setSelectedNemoTecnico] = useState<string>("")

    const [consultaData, setConsultaData] =
        useState<RespConsultaDataPanam>({} as RespConsultaDataPanam);

    const [catalog, setCatalog] = useState<Catalogo[]>([]);

    const [activeNuevo, setActiveNuevo] = useState(false)

    const [loadingNemo, setLoadingNemo] = useState(false);

    const [loading, setLoading] = useState(false);

    const [loadingSave, setLoadingSave] = useState(false);

    const [loadingConsultaData, setLoadingConsultaData] = useState(false);

    const [checkboxValues, setCheckboxValues] = useState({
        inactivas: 0,
        amortAnticipadas: 0
    });

    const [isFieldRequiredLatPanama, setIsFieldRequiredLatPanama] = useState<IsFieldRequiredLatPanama>({} as IsFieldRequiredLatPanama)

    useEffect(() => {
        const getNemoTenico = async () => {
            setLoadingNemo(true);
            const response = await fetchDataGetRet(
                "/latam/panama/nemotecnicos",
                " al obtener catalogos panama",
                {
                    check_amort_antic: checkboxValues.amortAnticipadas,
                    check_inactivas: checkboxValues.inactivas,
                    n_tipo_instrumento: 0})

            setNemoTecnico(response.body);
            setLoadingNemo(false);
        }

        getNemoTenico().then();

    }, []);

    useEffect(() => {
        const getCatalog = async () => {
            setLoading(true);
            const response = await fetchDataGetRet(
                "/latam/panama/catalogos",
                " al obtener catalogos panama",
                {})

            setCatalog(response.body.catalogos);
            setLoading(false);
        }

        getCatalog().then();

    }, []);

    const handleNuevo = () => {
        setActiveNuevo(true)
        setSelectedNemoTecnico("")
        setConsultaData({} as RespConsultaDataPanam)
        setIsFieldRequiredLatPanama({} as IsFieldRequiredLatPanama)
    }

    const handleCancel = () => {
        setActiveNuevo(false)
        setSelectedNemoTecnico("")
        setConsultaData({} as RespConsultaDataPanam)
        setIsFieldRequiredLatPanama({} as IsFieldRequiredLatPanama)
    }

    const handleNewNemo = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedNemoTecnico(e.target.value)
        setIsFieldRequiredLatPanama({...isFieldRequiredLatPanama, "s_nemotecnico": false})
        setConsultaData(prevConsultaData => ({
            ...prevConsultaData,
            body: {
                ...(prevConsultaData.body || {}),
                info_bd: {
                    ...(prevConsultaData.body?.info_bd || {}),
                    "s_nemotecnico": e.target.value
                }
            }
        }))
    }

    const handleSelectNemo = async (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedNemoTecnico(e.target.value)
        setIsFieldRequiredLatPanama({...isFieldRequiredLatPanama, "s_nemotecnico": false})
        setLoadingConsultaData(true)
        const response: RespConsultaDataPanam = await fetchDataGetRet(
            "/latam/panama/consulta-info",
            " al obtener consultas info",
            {s_nemotecnico: e.target.value}
        )
        setConsultaData(response)
        setLoadingConsultaData(false)
    }

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target;
        setCheckboxValues(prevValues => ({
            ...prevValues,
            [name]: checked ? 1 : 0
        }));
    };

    const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = event.target;

        setConsultaData(prevData => ({
            ...prevData,
            body: {
                ...prevData.body,
                info_bd: {
                    ...prevData.body?.info_bd,
                    [name]: value
                }
            }
        }));

        setIsFieldRequiredLatPanama({...isFieldRequiredLatPanama, [name]: false})
    };

    const handleSave = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if(await validateFieldsAccCalifLatam(
                consultaData?.body?.info_bd,
                refReqLatPanama, 
                false, 
                false, 
                undefined, 
                undefined, 
                undefined, 
                undefined,
                isFieldRequiredLatPanama, 
                setIsFieldRequiredLatPanama
        )) {
            setLoadingSave(true)
            const request = {
                ...consultaData.body.info_bd,
                s_nemotecnico: selectedNemoTecnico
            };

            await fetchDataPost(
                "/latam/panama/actualiza-info",
                " al actualizar info Latam Panamá",
                request,
                {
                    check_amort_antic: checkboxValues.amortAnticipadas,
                    check_inactivas: checkboxValues.inactivas,
                    s_user: userEncoded()}
            )

            setLoadingSave(false)
        }
    }

    return {
        checkboxValues,
        consultaData,
        activeNuevo,
        nemoTecnico,
        selectedNemoTecnico,
        loading,
        loadingSave,
        loadingConsultaData,
        loadingNemo,
        catalog,
        isFieldRequiredLatPanama,
        refReqLatPanama,
        handleNewNemo,
        handleCheckboxChange,
        handleSelectNemo,
        handleNuevo,
        handleCancel,
        handleSave,
        handleChange
    }
}