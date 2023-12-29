import {CalificacionesForm, CargaForm, InstrumentoForm} from "../Forms";
import React, { useEffect } from "react";

export const  ActiveInstrumentos = ({requeridos, activePanel, activeForm, handleButtonClickForm} : any) => {

    useEffect(() => {
        if(activeForm === "cupones" || activeForm === "flujos")
        {
            handleButtonClickForm("instrumento");
        }
    }, [activePanel]);

    return (
        <div className={`container-flex animate__animated ${activePanel ? "animate__fadeIn" : ""}`}>
            {/** Activa Formulario Instrumento  */}
            <div
                className={`acordeon-header ${activeForm === "instrumento"
                    ? "acordeon-header-active"
                    : "acordeon-header-inactive"
                }`}
            >
                <button
                    className="acordeon-button"
                    onClick={() => {
                        handleButtonClickForm("instrumento");
                    }}
                >
                    <i
                        className={`acordeon-button-icon ${activeForm === "instrumento" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span>Instrumento</span>
                </button>
            </div>
            {activeForm === "instrumento" && (
                <div
                    className={`acordeon-body ${activeForm === "instrumento"
                        ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <InstrumentoForm requeridos={requeridos} />
                </div>
            )}

            {/** Activa Formulario Calificaciones */}
            <div
                className={`acordeon-header ${activeForm === "calificaciones"
                    ? "acordeon-header-active"
                    : "acordeon-header-inactive"
                }`}
            >
                <button className="acordeon-button"
                        onClick={() => {
                            handleButtonClickForm("calificaciones")
                        }}
                >
                    <i
                        className={`acordeon-button-icon ${activeForm === "calificaciones" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span>Calificaciones</span>
                </button>
            </div>
            {activeForm === "calificaciones" && (
                <div
                    className={`acordeon-body ${activeForm === "calificaciones" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <CalificacionesForm/>
                </div>
            )}

            {/** Activa Formulario Carga */}
            <div
                className={`acordeon-header ${activeForm === "carga"
                    ? "acordeon-header-active"
                    : "acordeon-header-inactive"
                }`}>
                <button className="acordeon-button"
                        onClick={() => {
                            handleButtonClickForm("carga")
                        }}
                >
                    <i
                        className={`acordeon-button-icon ${activeForm === "carga" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span>Carga</span>
                </button>
            </div>
            {activeForm === "carga" && (
                <div
                    className={`acordeon-body ${activeForm === "carga" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <CargaForm/>
                </div>
            )}
        </div>
    )
}