import {TitleDate} from "../../../../../shared";
import React from "react";
import {DerivadosForm} from "../components";

export const Teoricos = () => {
    return (
        <>
            <TitleDate title='Instrumentos Derivados - Teóricos'/>

            <div className={`container-flex animate__animated animate__fadeIn`}>
                <div className="container-form">
                    <DerivadosForm isChicago={false}
                                   sMercado={"TEORICOS"}
                                   queryDataUrl="/derivados/teoricos/consulta-info"
                                   urlSaveData=""
                    />
                </div>
            </div>
        </>
    )
}