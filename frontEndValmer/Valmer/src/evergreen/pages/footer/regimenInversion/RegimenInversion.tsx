import { ButtonContent, TitleDate } from "../../../../shared";
import { HocRestricted } from "../../restrictedAccess";
import { BarLoader, MoonLoader } from "react-spinners";
import { useRegInvHandleData } from "./hooks/useRegInvHandleData";
import { Instrumento } from "./components/Instrumento";
import { Caracteristicas } from "./components/Caracteristicas";
import { Informacion } from "./components/Informacion";

export const RegimenInversion = () => {
    const title = "Regimen de Inversion"

    const {
        loading,
        selectedTv,
        handleClickTv,
        tvRegInv,
        selectedEmisora,
        handleEmisora,
        emisorasRegInv,
        loadingEmisoras,
        selectedSerie,
        handleSerie,
        serieRegInv,
        loadingSerie,
        handleChange,
        catalog,
        consultaDataRegInv,
        loadingConsultaData,
        loadingCatalog,
        handleSave,
        loadingBtn,
        handleCheckbox,
        spanEnabled
    } = useRegInvHandleData();

    if (loading || loadingCatalog || !catalog.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <HocRestricted title={title} view={title}>
            <TitleDate title={title}/>

            <form>
                <div>
                    <div className="flex flex-row justify-end">
                        <button className="btn" onClick={handleSave}>
                            <ButtonContent name="Guardar" loading={loadingBtn}/>
                        </button>
                    </div>
                    {loadingConsultaData && <BarLoader className="ml-2 w-full mt-2 mb-2" color="#059669" width={500}/>}
                    <div className={"animate__animated animate__fadeIn"}>
                        <div className="form col-span-3">
                        <Instrumento 
                            selectedTv={selectedTv}
                            handleClickTv={handleClickTv}
                            tvRegInv={tvRegInv}
                            selectedEmisora={selectedEmisora}
                            handleEmisora={handleEmisora}
                            emisorasRegInv={emisorasRegInv}
                            loadingEmisoras={loadingEmisoras}
                            selectedSerie={selectedSerie}
                            handleSerie={handleSerie}
                            serieRegInv={serieRegInv}
                            loadingSerie={loadingSerie}
                        />
                        <Caracteristicas 
                                consultaDataRegInv={consultaDataRegInv}
                                handleChange={handleChange}
                                catalog={catalog}
                                handleCheckbox={handleCheckbox}
                                spanEnabled={spanEnabled}
                        />
                        <Informacion 
                                consultaDataRegInv={consultaDataRegInv}
                                handleChange={handleChange}
                                catalog={catalog}
                                handleCheckbox={handleCheckbox}
                                spanEnabled={spanEnabled}
                        />
                        </div>
                    </div>
                </div>
            </form>
        </HocRestricted>
    )
}