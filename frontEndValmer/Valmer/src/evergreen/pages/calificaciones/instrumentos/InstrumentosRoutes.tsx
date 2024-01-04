import {Routes, Route} from 'react-router-dom'
import {General} from './general'
import {Fondos} from './fondos'
import { useCalifInstForm } from './hooks'

export const InstrumentosRoutes = () => {

    const {
        isFondos,
        isNewInstr,
        isNewSerie,
        loadingCatalog,
        catalog,
        loadingCatalogCalif,
        catalogCalif,
        selectedTv,
        tv,
        loadingTv,
        tvFondos,
        loadingTvFondos,
        selectedEmisora,
        emisoras,
        loadingEmisoras,
        selectedSerie,
        series,
        loadingSeries,
        consultaData,
        loadingConsultaData,
        loadingSave,
        isFieldReqCalifInst,
        refReqInst,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        setIsFondos,
        handleChange,
        handleTv,
        handleEmisora,
        handleSerie,
        handleNuevo,
        handleNuevaSerie,
        handleLimpiar,
        handleGuardar,
        handleChangeFile
    } = useCalifInstForm()

    return (
        <Routes>
            <Route path='/general/*' element={
                <General
                    isNewInst={isNewInstr}
                    isNewSerie={isNewSerie}
                    isFondos={isFondos}
                    loadingCatalog={loadingCatalog}
                    loadingCatalogCalif={loadingCatalogCalif}
                    catalog={catalog}
                    catalogCalif={catalogCalif}
                    selectedTv={selectedTv}
                    tv={tv}
                    loadingTv={loadingTv}
                    selectedEmisora={selectedEmisora}
                    emisoras={emisoras}
                    loadingEmisoras={loadingEmisoras}
                    selectedSerie={selectedSerie}
                    series={series}
                    loadingSeries={loadingSeries}
                    consultaData={consultaData}
                    loadingConsultaData={loadingConsultaData}
                    loadingSave={loadingSave}
                    isFieldReqCalifInst={isFieldReqCalifInst}
                    refReqInst={refReqInst}
                    requiredTv={requiredTv}
                    requiredEmisora={requiredEmisora}
                    requiredSerie={requiredSerie}
                    setIsFondos={setIsFondos}
                    handleChange={handleChange}
                    handleChangeFile={handleChangeFile}
                    handleTv={handleTv}
                    handleEmisora={handleEmisora}
                    handleSerie={handleSerie}
                    handleNuevo={handleNuevo}
                    handleNuevaSerie={handleNuevaSerie}
                    handleGuardar={handleGuardar}
                    handleLimpiar={handleLimpiar}
                />
            } />

            <Route path='/fondos/*' element={
                <Fondos
                    isNewInst={isNewInstr}
                    isNewSerie={isNewSerie}
                    isFondos={isFondos}
                    loadingCatalog={loadingCatalog}
                    loadingCatalogCalif={loadingCatalogCalif}
                    catalog={catalog}
                    catalogCalif={catalogCalif}
                    selectedTv={selectedTv}
                    tv={tvFondos}
                    loadingTv={loadingTvFondos}
                    selectedEmisora={selectedEmisora}
                    emisoras={emisoras}
                    loadingEmisoras={loadingEmisoras}
                    selectedSerie={selectedSerie}
                    series={series}
                    loadingSeries={loadingSeries}
                    consultaData={consultaData}
                    loadingConsultaData={loadingConsultaData}
                    loadingSave={loadingSave}
                    isFieldReqCalifInst={isFieldReqCalifInst}
                    refReqInst={refReqInst}
                    requiredTv={requiredTv}
                    requiredEmisora={requiredEmisora}
                    requiredSerie={requiredSerie}
                    setIsFondos={setIsFondos}
                    handleChange={handleChange}
                    handleChangeFile={handleChangeFile}
                    handleTv={handleTv}
                    handleEmisora={handleEmisora}
                    handleSerie={handleSerie}
                    handleNuevo={handleNuevo}
                    handleNuevaSerie={handleNuevaSerie}
                    handleGuardar={handleGuardar}
                    handleLimpiar={handleLimpiar}
                />
            } />
        </Routes>
    )
}