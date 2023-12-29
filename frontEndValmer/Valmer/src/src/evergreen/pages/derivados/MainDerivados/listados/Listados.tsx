import { TitleDate } from "../../../../../shared"
import {DerivadosForm} from "../components";
import React from "react";

export const Listados = () => {
    return (
        <>
            <TitleDate title='Instrumentos Derivados - Listados'/>
            
            <DerivadosForm isChicago={true}
                           sMercado={"DERIV"}
                           queryDataUrl="/derivados/listados/consulta-info"
                           urlSaveData="/derivados/listados/actualiza-info"
            />
        </>
    )
}