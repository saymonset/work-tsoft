import {TitleDate} from "../../../../../shared";
import React from "react";
import {DerivadosForm} from "../components";

export const Chicago = () => {
    return (
        <>
            <TitleDate title='Instrumentos Derivados - Chicago'/>

            <div className={`container-flex animate__animated animate__fadeIn`}>
                <div className="container-form">
                    <DerivadosForm isChicago={true}
                                   sMercado={"DERIV_C"}
                                   queryDataUrl="/derivados/chicago/consulta-info"
                                   urlSaveData="/derivados/chicago/actualiza-info"
                    />
                </div>
            </div>
        </>
    )
}