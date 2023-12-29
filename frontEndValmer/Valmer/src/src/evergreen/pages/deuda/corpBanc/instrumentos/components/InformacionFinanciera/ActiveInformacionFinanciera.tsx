import React from "react";
import { InformacionFinanciera } from "./InformacionFinanciera";

export const ActiveInformacionFinanciera = ({ activePanel }: any) => {
    return (
        <div className={`flex flex-wrap bg-slate-50 animate__animated ${activePanel ? "animate__fadeIn" : ""}`}>
            <div
                className="p-2 w-full border border-slate-300 mb-1 animate__animated animate__fadeIn"
            >
                <InformacionFinanciera/>
            </div>
        </div>
    )
}