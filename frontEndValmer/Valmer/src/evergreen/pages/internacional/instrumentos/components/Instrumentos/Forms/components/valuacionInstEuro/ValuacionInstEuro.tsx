import {TitleDate, ValuacionForm} from "../../../../../../../../../shared";
import React from "react";

export const ValuacionInstEuro = () => {
    return (
        <>
            <TitleDate title="Valuación Instrumentos Eurobonos"/>
            <ValuacionForm mercado="eurobonos" />
        </>
    )
}