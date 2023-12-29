import {CuponesForm, Flujos} from '../Forms';
import React, {useEffect} from "react";

export const ActiveCuponesTasas = ({requeridos, activePanel, activeForm, handleButtonClickForm} : any) => {

    useEffect(() => {
        return () => {
            handleButtonClickForm("cupones");
        };
    }, []);

    return (
        <div className={`container-flex animate__animated ${activePanel ? "animate__fadeIn" : ""}`}>
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
                        className={`acordeon-button-icon ${activeForm === "cupones" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span>Cupones</span>
                </button>
            </div>
            {activeForm === "cupones" && (
                <div
                    className={`acordeon-body ${activeForm === "cupones" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <CuponesForm requeridos={requeridos}/>
                </div>
            )}
            {/** Activa Tabla de Flujos  */}
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
                        className={`acordeon-button-icon ${activeForm === "flujos" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span>Flujos</span>
                </button>
            </div>
            {activeForm === "flujos" && (
                <div
                    className={`acordeon-body ${activeForm === "flujos" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <Flujos/>
                </div>
            )}
        </div>
    )
}