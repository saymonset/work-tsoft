import React from "react";

export const PanelButtonNav = ({activePanel, handleButtonClickPanel} : any) => {
    return (
        <>
            {/** Activa Panel Instrumento */}
            <button
                className={`panel-button 
              ${activePanel === "insumos"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("insumos")}
            >
                Insumos
            </button>

            {/** Activa Panel Cupones y Tasas */}
            <button
                className={`panel-button 
              ${activePanel === "contribuidores"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("contribuidores")}
            >
                Contribuidores
            </button>

            {/** Activa Panel Amortizaciones */}
            <button
                className={`panel-button 
              ${activePanel === "proceso"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("proceso")}
            >
                Proceso
            </button>

            {/** Activa Panel Información Financiera */}
            <button
                className={`panel-button 
              ${activePanel === "admonCarteras"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("admonCarteras")}
            >
                Admon. Carteras
            </button>
        </>
    )
}