import React from "react";
import {ButtonContent, TitleDate} from "../../../../../shared";
import {Panel} from "./Panel";
import {useEraseData} from "./hooks";
import { HocRestricted } from "../../../restrictedAccess";
import { Link } from "react-router-dom";
import { CalModal } from "./components/CalModal";
// import { CalModal } from "./components/CalModal";
export const Instrumentos = () => {

    const {
        loadingSubmit,
        requeridosGuber,
        handleSubmit,
        handleLimpiarClick,
        handleNuevoClick,
        handleNuevaSerieClick,
        handleCalculator,
        isModalOpen,
        handleModalClose

     } = useEraseData()

    const title = "Instrumentos Gubernamentales"

    return (

        <HocRestricted title={title} view={title}>

            <TitleDate title={title}/>

            <div className="flex justify-end pr-2">
                <button
                    className="btn"
                    onClick={handleNuevoClick}
                >
                    <span>Nuevo</span>
                </button>
                <button
                    className="btn"
                    onClick={handleSubmit}
                >
                    <ButtonContent name="Guardar" loading={loadingSubmit}/>
                </button>
                <button
                    className="btn"
                    onClick={handleNuevaSerieClick}
                >
                    <span>Nueva Serie</span>
                </button>
                <div>|</div>
                <Link to="/deuda/genera/si" className="btn">
                    <span>S.I.</span>
                </Link>
                <div>|</div>
                <button
                      className="btn"
                      onClick={handleCalculator}
                >
                    <i className="fa fa-calculator"></i>
                    <ButtonContent name=" Calculadora" loading={loadingSubmit}/>
                </button>
                <div>|</div>
                <button
                    className="btn"
                    onClick={handleLimpiarClick}
                >
                    <span>Limpiar</span>
                </button>
            </div>

            <Panel requeridos={requeridosGuber} />

            <CalModal isModalOpen={isModalOpen}
                          instrument={"instrument"}
                          selectedTv={"selectedTv"}
                          selectedEmisora={"selectedEmisora"}
                          selectedSerie={"selectedSerie"}
                          handleModalClose={handleModalClose} />
        </HocRestricted>
    )
}