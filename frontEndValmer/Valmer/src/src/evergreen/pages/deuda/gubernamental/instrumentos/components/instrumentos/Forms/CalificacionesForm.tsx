import React from "react";
import {useCalificaciones} from "./hooks";
import {CalificacionesContent} from "../../../../../../../../shared";

export const CalificacionesForm = () => {

    const {
        loading,
        loadingSubmitCalif,
        catalog,
        consultaData,
        handleChange,
        handleSubmit,
        handleFileChange
    } = useCalificaciones()

    return (
        <CalificacionesContent
            consultaData={consultaData}
            loading={loading}
            loadingSubmitCalif={loadingSubmitCalif}
            catalog={catalog}
            handleChange={handleChange}
            handleSubmit={handleSubmit}
            handleFileChange={handleFileChange}
        ></CalificacionesContent>
    )
}

