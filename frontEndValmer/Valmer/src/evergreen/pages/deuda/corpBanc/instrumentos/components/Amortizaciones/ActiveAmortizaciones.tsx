import React from "react";
import { Amortizaciones } from "./Amortizaciones";

export const ActiveAmortizaciones = ({activePanel} : any) => {
    return (
        <div className={`flex flex-wrap bg-slate-50 animate__animated ${activePanel ? "animate__fadeIn" : ""}`}>
            <div
                className="container-form animate__animated animate__fadeIn"
            >
                <Amortizaciones/>
            </div>
        </div>
    )
}