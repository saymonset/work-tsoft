
import { useHandleCalc } from "./useHandleCalc"
import { useDispatch, useSelector } from "react-redux"
import { ConsultaData, ResponseConsultaDataCorp, keysToKeepCorp } from "../../../../../../../../model"
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import React, { useState } from "react"
import { updateConsultaDataCorp } from "../../../../../../../../redux"
import { fetchDataPost, userEncoded } from "../../../../../../../../utils"
import { useInputConfig } from "./useInputConfigs";

export const useInfoFinanciera = () => {

  const {
    factorVal,
    derCobro,
    activoNC,
    efectivo,
    garantias,
    montoCirc,
    vnaAct,
    der35Porcent,
    recAnc,
    ac,
    acRec,
    recGarantias,
    porcentVNA,
    precioNuevo,
    setFactorVal,
    setDerCobro,
    setActivoNC,
    setEfectivo,
    setGarantias,
    setMontoCirc,
    setVnaAct,
    handleInputChange,
  } = useHandleCalc()

  const inputConfigs = useInputConfig();

  const consultaData = useSelector((state: RootState<any, any, any>) =>
    state.consultaDataCorp) as unknown as ResponseConsultaDataCorp;

  const dispatch = useDispatch();

  const [loadingSubmit, setLoadingSubmit] = useState(false)

  const handleChange = <T extends HTMLInputElement | HTMLSelectElement>(e: React.ChangeEvent<T>) => {
    const { name, value } = e.target;

    const config = inputConfigs[name];

    let updatedData: ResponseConsultaDataCorp;

    if (config) {
      const calculatedValues = config.calculate(value, consultaData.body);

      updatedData = {
        body: {
          ...consultaData.body,
          [name]: value,
          ...calculatedValues,
        },
      };
    } else {
      updatedData = {
        body: {
          ...consultaData.body,
          [name]: value,
        },
      };
    }

    dispatch(updateConsultaDataCorp(updatedData));
  };

  const checkboxValueEval = (fieldName: string,
    consultaData: ConsultaData | undefined) => {
    return consultaData?.body?.[fieldName] !== undefined
      ? consultaData?.body?.[fieldName] === '1'
      : consultaData?.body?.[fieldName] === '0'
  }

  const handleSubmitFinanciera = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    setLoadingSubmit(true)

    const filteredData = Object.fromEntries(
      keysToKeepCorp.map(key => [key, consultaData.body[key] || ""])
    );

    await fetchDataPost("/instrumentos/corporativos/guarda-info-financiera",
      "al intentar guardar datos",
      filteredData,
      { s_user: userEncoded() })
    setLoadingSubmit(false)
  }

  return {
    consultaData,
    loadingSubmit,
    factorVal,
    derCobro,
    activoNC,
    efectivo,
    garantias,
    montoCirc,
    vnaAct,
    der35Porcent,
    recAnc,
    ac,
    acRec,
    recGarantias,
    porcentVNA,
    precioNuevo,
    setFactorVal,
    setDerCobro,
    setActivoNC,
    setEfectivo,
    setGarantias,
    setMontoCirc,
    setVnaAct,
    handleInputChange,
    handleChange,
    checkboxValueEval,
    handleSubmitFinanciera
  }
}