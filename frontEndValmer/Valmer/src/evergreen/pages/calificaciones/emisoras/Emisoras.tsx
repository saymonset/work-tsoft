import {TitleDate} from "../../../../shared";
import React from "react";
import {HocRestricted} from "../../restrictedAccess";
import {EmisorasForm, EmisorasFormHeader} from "./components";

export const Emisoras = () => {

    const title = "Calificaciones Emisoras"

    return (
        <HocRestricted title={title} view={title}>
            <TitleDate title={title}/>

            <EmisorasFormHeader/>

            <div className={`mt-1 mb-14 container-flex animate__animated animate__fadeIn`}>
                <div className="container-form">
                    <EmisorasForm/>
                </div>
            </div>
        </HocRestricted>
    )
}