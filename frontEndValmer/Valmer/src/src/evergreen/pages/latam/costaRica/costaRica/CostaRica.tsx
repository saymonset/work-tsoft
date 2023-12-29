import { TitleDate } from "../../../../../shared"
import React from "react";
import {MoonLoader} from "react-spinners";
import {CostaRicaForm, CostaRicaFormHeader} from "./components";
import {useCostaRica} from "./hooks";

export const CostaRica = () => {

    const {
        activeNuevo, consultaData,
        loadingConsultaInfo, loadingEmisor,
        loadingCatalogo, catalog,
        selectedEmisor, selectedNemo,
        selectedSerie, emisor,
        nemoInstrumento, serie,
        checkboxValue,loadingSave, loadingNemoInst,
        loadingSerie, setSelectedEmisor,
        setSelectedNemo, setSelectedSerie,
        handleChange, handleNuevo,
        handleCancel, handleEmisor,
        handleNemo, handleSerie,
        handleCheckboxChange, handleErase,
        handleSave
    } = useCostaRica()

    if (loadingEmisor || loadingCatalogo || !catalog.length) {
        return (
            <div className="mt-24 flex justify-center items-center h-full">
                {loadingEmisor || loadingCatalogo ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <>
            <TitleDate title="Costa Rica" />

            <CostaRicaFormHeader
                selectedEmisor={selectedEmisor} selectedNemo={selectedNemo}
                selectedSerie={selectedSerie} emisor={emisor}
                nemoInstrumento={nemoInstrumento} serie={serie}
                checkboxValue={checkboxValue} loadingNemoInst={loadingNemoInst}
                loadingSave={loadingSave} loadingSerie={loadingSerie}
                setSelectedEmisor={setSelectedEmisor} setSelectedNemo={setSelectedNemo}
                setSelectedSerie={setSelectedSerie} activeNuevo={activeNuevo}
                handleNuevo={handleNuevo} handleCancel={handleCancel}
                handleEmisor={handleEmisor} handleNemo={handleNemo}
                handleSerie={handleSerie} handleCheckboxChange={handleCheckboxChange}
                handleErase={handleErase} handleSave={handleSave}/>


            <CostaRicaForm
                catalog={catalog} consultaData={consultaData}
                mergeInstrumentos={`${selectedEmisor}_${selectedNemo}_${selectedSerie}`}
                loadingConsultaInfo={loadingConsultaInfo}
                handleChange={handleChange}
            />
        </>
    )
}