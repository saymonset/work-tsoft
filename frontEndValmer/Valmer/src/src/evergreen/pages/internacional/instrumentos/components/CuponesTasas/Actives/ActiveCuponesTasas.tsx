import {
    CuponesForm,
    FlujosHist,
    Flujos
} from '../Forms'
import React from 'react'

export const ActiveCuponesTasas = ({requeridos, activePanel, activeForm, handleButtonClickForm}: any) => {
    return (
        <div className={`container-flex animate__animated ${activePanel ? 'animate__fadeIn' : ''}`}>
            {/** Activa Formulario Cupones  */}
            <div
                className={`acordeon-header ${activeForm === "cupones"
                    ? "acordeon-header-active"
                    : "acordeon-header-inactive"
                }`}
            >
                <button
                    className="acordeon-button"
                    onClick={() => {
                        handleButtonClickForm("cupones");
                    }}
                >
                    <i
                        className={`acordeon-button-icon 
                                ${activeForm === "cupones" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span>Cupones</span>
                </button>
            </div>
            {activeForm === "cupones" && (
                <div
                    className={`acordeon-body 
                            ${activeForm === "cupones" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <CuponesForm requeridos={requeridos} />
                </div>
            )}

            {/** Activa Tabla Flujos Hist */}
            <div
                className={`acordeon-header ${activeForm === "flujosHist"
                    ? "acordeon-header-active"
                    : "acordeon-header-inactive"
                }`}
            >
                <button
                    className="acordeon-button"
                    onClick={() => {
                        handleButtonClickForm("flujosHist");
                    }}
                >
                    <i
                        className={`acordeon-button-icon 
                                ${activeForm === "flujosHist" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span>Flujos Hist</span>
                </button>
            </div>
            {activeForm === "flujosHist" && (
                <div
                    className={`acordeon-body 
                            ${activeForm === "flujosHist" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <FlujosHist requeridos={requeridos} />
                </div>
            )}

            {/** Activa Tabla Flujos */}
            <div
                className={`acordeon-header ${activeForm === "flujos"
                    ? "acordeon-header-active"
                    : "acordeon-header-inactive"
                }`}
            >
                <button
                    className="acordeon-button"
                    onClick={() => {
                        handleButtonClickForm("flujos");
                    }}
                >
                    <i
                        className={`acordeon-button-icon 
                                ${activeForm === "flujos" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span>Flujos</span>
                </button>
            </div>
            {activeForm === "flujos" && (
                <div
                    className={`acordeon-body 
                            ${activeForm === "flujos" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <Flujos requeridos={requeridos}/>
                </div>
            )}
        </div>
    )
}