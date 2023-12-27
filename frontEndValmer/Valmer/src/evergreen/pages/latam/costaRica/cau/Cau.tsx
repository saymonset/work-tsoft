import React from 'react'
import {TitleDate} from "../../../../../shared"
import {MoonLoader} from "react-spinners";
import {useCau} from "./hooks";
import {CauForm, CauHeader, CauTable} from "./components";

export const Cau = () => {

    const {
        status, selectedEnterprise, queryFolio,
        tableOpened, catalog,
        isEdit, loadingSave,
        loadingCatalogo, loadingInitSol,
        loadingOpened, loadingClosed,
        loadingModifies, loadingFolio,
        handleClickFolio, handleEnterprise,
        handleStatus, handleChange,
        handleSave, eraseFilters,
        handleClickFolioCerrados
    } = useCau();

    if (loadingInitSol || loadingCatalogo || !catalog.length) {
        return (
            <div className="mt-24 flex justify-center items-center h-full">
                {loadingInitSol || loadingCatalogo ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <>
            <TitleDate title={"CAU Costa Rica - " + status}/>

            <CauHeader
                catalog={catalog}
                selectedEnterprise={selectedEnterprise}
                eraseFilters={eraseFilters}
                handleEnterprise={handleEnterprise}
                handleStatus={handleStatus}/>
            
            
            <CauTable
                status={status}
                loadingOpened={loadingOpened}
                loadingClosed={loadingClosed}
                loadingModifies={loadingModifies}
                tableOpened={tableOpened}
                handleClickFolio={handleClickFolio}
                handleClickFolioCerrados={handleClickFolioCerrados}/>


            <CauForm
                loadingSave={loadingSave}
                loadingFolio={loadingFolio}
                isEdit={isEdit}
                catalog={catalog}
                queryFolio={queryFolio}
                handleChange={handleChange}
                handleSave={handleSave}
                status={status}/>
        </>
    )
}