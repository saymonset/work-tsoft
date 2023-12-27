import React from "react";

export const PanelButtonNav = ({activePanel, handleButtonClickPanel} : any) => {
    return (
        <>
            {/** Activa Panel Instrumento */}
            <button
                className={`panel-button 
              ${activePanel === "instrumentos"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("instrumentos")}
            >
                Instrumentos
            </button>

            {/** Activa Panel Cupones y Tasas */}
            <button
                className={`panel-button 
              ${activePanel === "cuponesTasas"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("cuponesTasas")}
            >
                Cupones y Tasas
            </button>
        </>
    )
}