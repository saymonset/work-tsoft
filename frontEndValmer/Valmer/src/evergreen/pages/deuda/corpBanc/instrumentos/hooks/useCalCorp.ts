import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {
    updateRequiredEmisoraCorp,
    updateRequiredSerieCorp,
    updateRequiredTvCorp,
    updateSelectDatePrice
} from "../../../../../../redux";
import {fetchDataGetRet, fetchDataGetSave, showAlert, userEncoded} from "../../../../../../utils";
import {CalPrecios, CalTasasInt, FormValuesCorp} from "../../../../../../model";

export const useCalCorp = () => {

    const [instrument, setInstrument] = useState("");
    const [isDivVisible, setIsDivVisible] = useState(false);
    const [isAnimatingOut, setIsAnimatingOut] = useState(false);

    const [loadingSaveCalculator, setLoadingSaveCalculator] = useState(false);
    const [loadingCalPrecios, setLoadingCalPrecios] = useState(false);
    const [loadingCalTasas, setLoadingCalTasas] = useState(false);

    const [isModalOpenCalTasas, setIsModalOpenCalTasas] = useState(false);
    const [isModalOpenCalPrecio, setIsModalOpenCalPrecio] = useState(false);

    const [isSelectDate, setIsSelectDate] = useState(false);

    const [precios, setPrecios] = useState({} as CalPrecios);
    const [tasasInt, setTasasInt] = useState({} as CalTasasInt);

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvCorp) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisoraCorp) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerieCorp) as unknown as string;

    const selectDate = useSelector((state: RootState<any, any, any>) =>
        state.selectedDatePrice) as unknown as string;

    const formValues = useSelector((state: RootState<any, any, any>) =>
        state.formValuesCorp) as unknown as FormValuesCorp;

    const dispatch = useDispatch()

    const toggleDivVisibility = () => {
        if (isDivVisible && !isAnimatingOut) {
            setIsAnimatingOut(true);
            setTimeout(() => {
                setIsDivVisible(false);
                setIsAnimatingOut(false);
            }, 600);
        } else if (!isDivVisible) {
            setIsDivVisible(true);
        }
    };

    const handleDate = (e: React.ChangeEvent<HTMLInputElement>) => {
        setIsSelectDate(false)
        dispatch(updateSelectDatePrice(e.target.value))
    }

    const handleCalTasasInt = async () => {
        if (!selectedTv) {
            dispatch(updateRequiredTvCorp(true))
        } else if (!selectedEmisora || selectedEmisora === "...") {
            dispatch(updateRequiredEmisoraCorp(true))
        } else if (!selectedSerie || selectedSerie === "...") {
            dispatch(updateRequiredSerieCorp(true))
        } else if (selectDate === '') {
            await showAlert("warning", "Fecha Requerida", "Debe seleccionar una fecha")
        } else {
            setInstrument(`${selectedTv}_${selectedEmisora}_${selectedSerie}`)
            setLoadingCalTasas(true)

            let data: { [key: string]: any } = {
                sEmisora: selectedEmisora,
                sSerie: selectedSerie,
                sTv: selectedTv
            };

            if(selectDate) {
                data.dFechaVal = selectDate;
            }

            const response = await fetchDataGetRet(
                "/instrumentos/corporativos/calculadora-tasas-interes",
                " al obtener calculadora de tasas interes",
                data)
            setLoadingCalTasas(false)
            setIsModalOpenCalTasas(true)

            if(response)
            {
                setTasasInt(response)
                setIsModalOpenCalTasas(true)
            }
        }
    }

    const handleCalPrecio = async () => {
        if (!selectedTv) {
            dispatch(updateRequiredTvCorp(true))
        } else if (!selectedEmisora || selectedEmisora === "...") {
            dispatch(updateRequiredEmisoraCorp(true))
        } else if (!selectedSerie || selectedSerie === "...") {
            dispatch(updateRequiredSerieCorp(true))
        } else if (!selectDate) {
            setIsSelectDate(true)
        } else {
            setInstrument(`${selectedTv}_${selectedEmisora}_${selectedSerie}`)
            setLoadingCalPrecios(true)
            const response = await fetchDataGetRet(
                "/instrumentos/corporativos/calculadora-precios",
                " al obtener calculadora de precios",
                {
                    dFechaVal: selectDate,
                    sEmisora: selectedEmisora,
                    sSerie: selectedSerie,
                    sTv: selectedTv})

            setLoadingCalPrecios(false)
            if(response)
            {
                setPrecios(response)
                setIsModalOpenCalPrecio(true)
            }
        }
    }

    const handleUpdateCalculator = async (url: string, messageError: string)=> {
        setLoadingSaveCalculator(true)
        let data: { [key: string]: any } = {
            sUser: userEncoded(),
            sTv: selectedTv,
            sEmisora: selectedEmisora,
            sSerie: selectedSerie,
            n_tipo_instrumento: formValues["n_tipo_instrumento"]
        };
        if(selectDate) {
            data.d_fecha_val = selectDate;
        }
        await fetchDataGetSave(
            url,
            messageError,
            data
        )
        setLoadingSaveCalculator(false)
    }

    const handleModalCloseTasas = () => {
        setIsModalOpenCalTasas(false);
    }

    const handleModalClosePrecio = () => {
        setIsModalOpenCalPrecio(false);
    }

    const selectedValues = {
        selectedEmisora,
        selectedTv,
        selectedSerie,
    };

    return {
        instrument,
        isDivVisible,
        isAnimatingOut,
        loadingSaveCalculator,
        loadingCalPrecios,
        loadingCalTasas,
        isSelectDate,
        isModalOpenCalTasas,
        isModalOpenCalPrecio,
        selectDate,
        precios,
        tasasInt,
        handleModalCloseTasas,
        handleModalClosePrecio,
        handleDate,
        handleCalTasasInt,
        handleCalPrecio,
        handleUpdateCalculator,
        toggleDivVisibility,
        selectedValues
    }
}