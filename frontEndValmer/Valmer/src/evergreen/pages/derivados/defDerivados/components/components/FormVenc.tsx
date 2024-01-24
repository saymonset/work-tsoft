import { TableDefVenc } from "../../../../../../model"
import { CargaArchVenc } from "./CargaArchVenc"
import { TvEmiSerieOptions } from "./TvEmiSerieOptions"
import { useHandleFormVenc } from "./hooks"
import React from "react";

interface FormVencProps {
    tv: string
    emisora: string
    serieOp: string[]
    tipoTv: string
    setIsHelp: React.Dispatch<React.SetStateAction<boolean>>
    setDataTable: React.Dispatch<React.SetStateAction<TableDefVenc[]>>
    openModal: () => void
}

export const FormVenc = (props: FormVencProps) => {

    const {
        isCargaOp,
        formValuesVenc,
        loadingSaveVenc,
        loadingCargaArch,
        selectedFile,
        fileVencimientos,
        loadingProcess,
        isNewSerie,
        setIsNewSerie,
        setFileBase64,
        setSelectedFile,
        handleCharge,
        handleOpenCargaOp,
        handleCloseCargaOp,
        handleChange,
        handleSubmitVenc,
        handleProcessArch
    } = useHandleFormVenc(props)

    return (
        <>
            <form onSubmit={handleSubmitVenc}>
                <div className="form-title">Instrumento</div>
                <TvEmiSerieOptions
                    tipoTv={props.tipoTv}
                    isNewSerie={isNewSerie}
                    tv={props.tv}
                    emisora={props.emisora}
                    serieOp={props.serieOp}
                    formValuesVenc={formValuesVenc}
                    loading={loadingSaveVenc}
                    setIsNewSerie={setIsNewSerie}
                    handleChange={handleChange}
                />
            </form>
            <div className="line"></div>
            <div className="form-cols-4">
                <div className="form-title col-span-3">Carga Operaciones</div>
                <div className="flex justify-end text-3xl mt-1 text-cyan-700">
                    <button className="mx-1" onClick={handleOpenCargaOp} aria-label="Abrir">
                        <span className="fa fa-sort-down"/>
                    </button>
                    <button className="mx-1 mt-4 -mb-4" onClick={handleCloseCargaOp} aria-label="Cerrar">
                        <span className="fa fa-sort-up"/>
                    </button>
                </div>
            </div>


            <div className={`grid gap-1 animate__animated ${isCargaOp ? "animate__fadeIn": "animate__fadeOut"}`}>

                <CargaArchVenc
                    selectedFile={selectedFile}
                    fileContributor={fileVencimientos}
                    loading={loadingCargaArch}
                    loadingProcess={loadingProcess}
                    setFileBase64={setFileBase64}
                    setSelectedFile={setSelectedFile}
                    handleCharge={handleCharge}
                    setIsHelp={props.setIsHelp}
                    handleProcess={handleProcessArch}
                />

                <div className="grid">
                    <div className="form-title">Pega Vtos</div>
                    <div className="text-center">
                        <button
                            className="btn mt-2 w-32"
                            onClick={props.openModal}
                        >
                            CARGA ARC
                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}