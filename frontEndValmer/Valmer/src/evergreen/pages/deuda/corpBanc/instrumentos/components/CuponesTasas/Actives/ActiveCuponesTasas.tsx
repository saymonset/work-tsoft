import React, { useEffect } from "react";
import { CuponesForm, Cupones, Intercupon, Flujos } from '../Forms';

export const ActiveCuponesTasas = ({requeridos, activePanel, activeForm, handleButtonClickForm} : any) => {

    useEffect(() => {
        return () => {
            handleButtonClickForm("cupones");
        };
    }, [activePanel]);

    return (
        <div className={`flex flex-wrap bg-slate-50 animate__animated ${activePanel ? "animate__fadeIn" : ""}`}>
            {/** Activa Formulario Cupones  */}
            <div
                className={`w-full py-1 px-2 border border-slate-300 ${activeForm === "cupones"
                    ? "bg-slate-50 text-cyan-700"
                    : "bg-cyan-700 hover:bg-green-700 text-slate-50 mb-1"
                }`}
            >
                <button
                    className="w-full text-start"
                    onClick={() => {
                        handleButtonClickForm("cupones");
                    }}
                >
                    <i
                        className={`text-slate-800 ${activeForm === "cupones" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span className="ml-2">Cupones</span>
                </button>
            </div>
            {activeForm === "cupones" && (
                <div
                    className={`p-2 w-full border border-slate-300 mb-1 ${activeForm === "cupones" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <CuponesForm requeridos={requeridos} />
                </div>
            )}

            {/** Activa Tabla de Cupones  */}
            <div
                className={`w-full py-1 px-2 border border-slate-300 ${activeForm === "cuponesTable"
                    ? "bg-slate-50 text-cyan-700"
                    : "bg-cyan-700 hover:bg-green-700 text-slate-50 mb-1"
                }`}
            >
                <button
                    className="w-full text-start"
                    onClick={() => {
                        handleButtonClickForm("cuponesTable");
                    }}
                >
                    <i
                        className={`text-slate-800 ${activeForm === "cuponesTable" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span className="ml-2">Cupones</span>
                </button>
            </div>
            {activeForm === "cuponesTable" && (
                <div
                    className={`p-2 w-full border border-slate-300 mb-1 ${activeForm === "cupones" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <Cupones/>
                </div>
            )}

            {/** Activa Tabla de Intercupon  */}
            <div
                className={`w-full py-1 px-2 border border-slate-300 ${activeForm === "intercuponTable"
                    ? "bg-slate-50 text-cyan-700"
                    : "bg-cyan-700 hover:bg-green-700 text-slate-50 mb-1"
                }`}
            >
                <button
                    className="w-full text-start"
                    onClick={() => {
                        handleButtonClickForm("intercuponTable");
                    }}
                >
                    <i
                        className={`text-slate-800 ${activeForm === "intercuponTable" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span className="ml-2">Cupones Intercupón</span>
                </button>
            </div>
            {activeForm === "intercuponTable" && (
                <div
                    className={`p-2 w-full border border-slate-300 mb-1 ${activeForm === "intercuponTable" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <Intercupon/>
                </div>
            )}
            {/** Activa Tabla de Flujos  */}
            <div
                className={`w-full py-1 px-2 border border-slate-300 ${activeForm === "flujos"
                    ? "bg-slate-50 text-cyan-700"
                    : "bg-cyan-700 hover:bg-green-700 text-slate-50 mb-1"
                }`}
            >
                <button
                    className="w-full text-start"
                    onClick={() => {
                        handleButtonClickForm("flujos");
                    }}
                >
                    <i
                        className={`text-slate-800 ${activeForm === "flujos" ? "fa fa-caret-down" : "fa fa-caret-right"
                        }`}
                    ></i>
                    <span className="ml-2">Flujos</span>
                </button>
            </div>
            {activeForm === "flujos" && (
                <div
                    className={`p-2 w-full border border-slate-300 mb-1 ${activeForm === "flujos" ? "animate__animated animate__fadeIn" : ""
                    }`}
                >
                    <Flujos />
                </div>
            )}
        </div>
    )
}