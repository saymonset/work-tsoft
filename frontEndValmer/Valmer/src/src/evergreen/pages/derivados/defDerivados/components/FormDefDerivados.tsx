import React, { useState } from "react"
import { useDataTableDef, useDerDefHandleData } from "./hooks"
import { MoonLoader } from "react-spinners"
import { TvEmiDefOptions } from "./TvEmiDefOptions"
import { CatalogoDef } from "./CatalogoDef"
import { ModalVencimientos } from "./ModalVencimientos"
import { FormDefDerivadosProps } from "../../../../../model";
import { CaracteristicasDerivados } from "./CaracteristicasDerivados"

export const FormDefDerivados = (props: FormDefDerivadosProps) => {

    const {
        loading,
        loadingDcsCurveUnd,
        loadingDefDer,
        loadingUnderlying,
        catalog,
        catalogDefDer,
        catalogUnderlying,
        catalogDcsCurveUnd,
        loadingConsultaData,
        loadingEmisoras,
        emisora,
        tv,
        loadingTv,
        serieOp,
        loadingSerie,
        tipoTv,
        handleTv,
        handleEmisora,
        handleChange,
        handleCheckChange,
        checkEval,
        handleSelectedInst
    } = useDerDefHandleData(props)

    const { loadingDataTable, dataTableDef } = useDataTableDef()

    const [colateralFF, setColateralFF] = useState(true)

    const [colateralSOFR, setColateralSOFR] = useState(true)

    const checkedColateralSOFR = () => {
        setColateralSOFR(!colateralSOFR)
    }

    const checkedColateralFF = () => {
        setColateralFF(!colateralFF)
    }

    if (loadingTv || loading || loadingDcsCurveUnd || loadingDefDer || loadingSerie ||
        loadingUnderlying || loadingDataTable || !catalog.length || !catalogDcsCurveUnd.length
        || !catalogDefDer.length || !catalogUnderlying.length || !dataTableDef.body) {
        return (
            <div className="flex justify-center items-center h-full">
                {loadingTv || loading || loadingDcsCurveUnd || loadingDefDer || loadingUnderlying || loadingSerie ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        )
    }

    return (
        <>
            <div className="form animate__animated animate__fadeIn">
                {props.isTabla &&
                    <CatalogoDef
                        data={dataTableDef.body}
                        handleSelectedInst={handleSelectedInst}
                    />
                }
                <form>
                    <TvEmiDefOptions
                        isNew={props.isNew}
                        tv={tv}
                        loadingConsultaData={loadingConsultaData}
                        loadingEmisoras={loadingEmisoras}
                        emisora={emisora}
                        selectedTv={props.selectedTv}
                        selectedEmisora={props.selectedEmisora}
                        consultaData={props.consultaDataDerDef}
                        requiredTv={props.requiredTv}
                        requiredEmisora={props.requiredEmisora}
                        requeridosDefDer={props.requeridosDefDer}
                        handleTv={handleTv}
                        handleEmisora={handleEmisora}
                        handleChange={handleChange}
                    />
                    <CaracteristicasDerivados
                        consultaDataDerDef={props.consultaDataDerDef}
                        requeridosDefDer={props.requeridosDefDer}
                        fieldRequiredDefDerivados={props.fieldRequiredDefDerivados}
                        handleChange={handleChange}
                        catalog={catalog}
                        catalogUnderlying={catalogUnderlying}
                        catalogDcsCurveUnd={catalogDcsCurveUnd}
                        checkEval={checkEval}
                        handleCheckChange={handleCheckChange}
                        colateralFF={colateralFF}
                        checkedColateralFF={checkedColateralFF}
                        colateralSOFR={colateralSOFR}
                        checkedColateralSOFR={checkedColateralSOFR}
                        catalogDefDer={catalogDefDer}
                    />
                </form>
            </div>

            <ModalVencimientos
                isOpen={props.isModalVenc}
                tv={props.selectedTv}
                emisora={props.selectedEmisora}
                serieOp={serieOp}
                tipoTv={tipoTv}
                triggerVenc={props.triggerTablaVenc}
                setTriggerVenc={props.setTriggerTablaVenc}
                onClose={props.closeModalVenc}
            />
        </>
    )
}