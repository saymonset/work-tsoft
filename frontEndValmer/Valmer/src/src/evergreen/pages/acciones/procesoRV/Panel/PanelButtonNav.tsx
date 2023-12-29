import React from "react";

export const PanelButtonNav = ({activePanel, handleButtonClickPanel} : any) => {
    return (
        <>
            <button
                className={`panel-button 
              ${activePanel === "caterv"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("caterv")}
            >
                CateRV
            </button>

            <button
                className={`panel-button 
              ${activePanel === "actualizabd"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("actualizabd")}
            >
                Actualiza BD
            </button>

            <button
                className={`panel-button 
              ${activePanel === "procesorv"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("procesorv")}
            >
                Proceso RV
            </button>

            <button
                className={`panel-button 
              ${activePanel === "salidas"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("salidas")}
            >
                Salidas
            </button>

            <button
                className={`panel-button 
              ${activePanel === "consultas"
                    ? "panel-button-active"
                    : "panel-button-inactive"
                }`}
                onClick={() => handleButtonClickPanel("consultas")}
            >
                Consultas
            </button>
        </>
    )
}