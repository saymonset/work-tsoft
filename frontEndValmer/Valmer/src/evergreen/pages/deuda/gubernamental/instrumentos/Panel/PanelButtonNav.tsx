import React from "react";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";

export const PanelButtonNav = ({activePanel, handleButtonClickPanel} : any) => {

    const isCuponesTasas =
        useSelector((state: RootState<any, any, any>) => state.cuponesTasas) as unknown as boolean;

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
            {isCuponesTasas && (
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
            )}
        </>
    )
}