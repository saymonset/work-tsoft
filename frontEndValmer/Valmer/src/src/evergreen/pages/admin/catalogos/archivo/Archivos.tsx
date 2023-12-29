import {ShowEditCatalog} from "../../../../../shared";
import {DataCatArchivos} from "../../../../../utils";
import React from "react";

export const Archivos = () => {
    return (
        <ShowEditCatalog titleName="Catálogo Archivos" DataCatalog={DataCatArchivos}/>
    )
};