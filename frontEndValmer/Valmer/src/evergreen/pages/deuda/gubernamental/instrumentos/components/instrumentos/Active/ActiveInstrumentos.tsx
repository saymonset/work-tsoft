import React, {useEffect} from "react";
import {CalificacionesForm, InstrumentoForm} from "../Forms";
import {LogBoxText} from "../../../../../../../../shared";

export const ActiveInstrumentos = ({requeridos, activePanel, activeForm, handleButtonClickForm} : any) => {

    useEffect(() => {
        if(activeForm === "cupones" || activeForm === "flujos")
        {
            handleButtonClickForm("instrumento");
        }
    }, []);

    return (
        <>
            <div className={`flex flex-wrap bg-slate-50 animate__animated ${activePanel ? "animate__fadeIn" : ""}`}>
                {/** Activa Formulario Instrumento  */}
                <div
                    className={`w-full py-1 px-2 border border-slate-300 ${activeForm === "instrumento"
                        ? "bg-slate-50 text-cyan-700"
                        : "bg-cyan-700 hover:bg-green-700 text-slate-50 mb-1"
                    }`}
                >
                    <button
                        className="w-full text-start"
                        onClick={() => {
                            handleButtonClickForm("instrumento");
                        }}
                    >
                        <i
                            className={`text-slate-800 ${activeForm === "instrumento" ? "fa fa-caret-down" : "fa fa-caret-right"
                            }`}
                        ></i>
                        <span className="ml-2">Instrumento</span>
                    </button>
                </div>
                {activeForm === "instrumento" && (
                    <div
                        className={`p-2 w-full border border-slate-300 mb-1 ${activeForm === "instrumento" ? 
                            "animate__animated animate__fadeIn animate__slow" : ""
                        }`}
                    >
                        <InstrumentoForm requeridos={requeridos} />
                    </div>
                )}

                {/** Activa Formulario Calificaciones */}
                <div
                    className={`w-full py-1 px-2 border border-slate-300 ${activeForm === "calificaciones"
                        ? "bg-slate-50 text-cyan-700"
                        : "bg-cyan-700 hover:bg-green-700 text-slate-50 mb-1"
                    }`}
                >
                    <button className="w-full text-start"
                            onClick={() => {
                                handleButtonClickForm("calificaciones")
                            }}
                    >
                        <i
                            className={`text-slate-800 ${activeForm === "calificaciones" ? "fa fa-caret-down" : "fa fa-caret-right"
                            }`}
                        ></i>
                        <span className="ml-2">Calificaciones</span>
                    </button>
                </div>
                {activeForm === "calificaciones" && (
                    <div
                        className={`p-2 w-full border border-slate-300 mb-1 ${
                            activeForm === 'calificaciones'
                                ? 'animate__animated animate__fadeIn'
                                : ''
                        }`}
                    >
                        <CalificacionesForm/>
                    </div>
                )}
            </div>

            <div className="mt-8">
                <LogBoxText logName={"log_guber"}/>
            </div>
        </>
    )
}