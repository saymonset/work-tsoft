import {ProgramasFormHeader} from "./components/ProgramasFormHeader";
import {ProgramasForm} from "./components/ProgramasForm";
import {TitleDate} from "../../../../shared";
import React from "react";


export const Programas = () => {
    return (
        <>
            <TitleDate title="Calificaciones Programas"/>

            <ProgramasFormHeader/>

            <div className={`mt-4 mb-14 container-flex animate__animated animate__fadeIn`}>
                <div className="container-form">
                    <ProgramasForm/>
                </div>
            </div>
        </>
    )
}