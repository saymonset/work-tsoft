import { TitleDate } from "../../../../shared"
import React from 'react';
import {MoonLoader} from "react-spinners";
import {usePanama} from "./hooks";
import {PanamaHeader, PanamForm} from "./components";

export const Panama = () => {

    const {
        checkboxValues,
        consultaData,
        activeNuevo,
        nemoTecnico,
        selectedNemoTecnico,
        loading,
        loadingSave,
        loadingConsultaData,
        loadingNemo,
        catalog,
        isFieldRequiredLatPanama,
        refReqLatPanama,
        //setSelectedNemoTecnico,
        handleNewNemo,
        handleCheckboxChange,
        handleSelectNemo,
        handleNuevo,
        handleCancel,
        handleSave,
        handleChange
    } = usePanama()


    if (loading || loadingNemo || catalog.length === 0) {
        return (
            <div className="mt-24 flex justify-center items-center h-full">
                {loading || loadingNemo ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <>
            <TitleDate title="Panamá"/>

            
            <PanamaHeader activeNuevo={activeNuevo}
                          selectedNemoTecnico={selectedNemoTecnico}
                          loadingConsultaData={loadingConsultaData}
                          loadingSave={loadingSave}
                          consultaData={consultaData}
                          catalog={catalog}
                          nemoTecnico={nemoTecnico}
                          checkboxValues={checkboxValues}
                          isFieldRequiredLatPanama={isFieldRequiredLatPanama}
                          refReqLatPanama={refReqLatPanama}
                          handleNewNemo={handleNewNemo}
                          handleNuevo={handleNuevo}
                          handleCancel={handleCancel}
                          handleSelectNemo={handleSelectNemo}
                          handleCheckboxChange={handleCheckboxChange}
                          handleChange={handleChange}
                          handleSave={handleSave}/>

            <PanamForm catalog={catalog}
                       consultaData={consultaData}
                       selectedNemoTecnico={selectedNemoTecnico}
                       isFieldRequiredLatPanama={isFieldRequiredLatPanama}
                       refReqLatPanama={refReqLatPanama}
                       handleChange={handleChange}/>
        </>
    )
}