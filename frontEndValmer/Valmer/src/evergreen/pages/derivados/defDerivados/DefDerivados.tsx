import { ButtonContent, TitleDate } from "../../../../shared"
import { FormDefDerivados } from "./components"
import { useHandleButtons } from "./hooks"

export const DefDerivados = () => {

    const {
        isNew,
        consultaDataDerDef,
        selectedTv,
        selectedEmisora,
        loadingGuardaDer,
        loadingActCaract,
        isTableCatalog,
        isButtonVenc,
        isModalVenc,
        triggerTablaVenc,
        fieldRequiredDefDerivados,
        requeridosDefDer,
        requiredTv,
        requiredEmisora,
        setTriggerTablaVenc,
        setSelectedTv,
        setSelectedEmisora,
        setConsultaDataDerDef,
        setIsButtonVenc,
        handleNew,
        handleGuardaDerivados,
        handleActualizaCaract,
        handleTablaCatalog,
        handleVenc,
        closeModalVenc
    } = useHandleButtons()

    const title = "Definición Instrumentos Derivados"

    return (
        <>
            <TitleDate title={title} />

            <div className="form-cols-2">
                <div className="flex justify-start pr-2">
                    <button 
                        className="btn"
                        onClick={handleTablaCatalog}
                    >
                        <span>Catálogo</span>
                    </button>
                </div>

                <div className="flex justify-end pr-2">
                    <button
                        className="btn"
                        onClick={handleNew}
                    >
                        <span>Nuevo</span>
                    </button>
                    <button
                        className="btn"
                        onClick={handleGuardaDerivados}
                    >
                        <ButtonContent name="Guardar Def Deriv" loading={loadingGuardaDer} />
                    </button>
                    <button
                        className="btn"
                        onClick={handleActualizaCaract}
                    >
                        <ButtonContent name="Actualizar Características" loading={loadingActCaract} />
                    </button>
                    <button
                        className={`btn animate__animated ${isButtonVenc ? "animate__fadeIn" : "animate__fadeOut"}`}
                        onClick={handleVenc}
                        disabled={!isButtonVenc}
                    >
                        <span>Vencimientos</span>
                    </button>
                </div>
            </div>

            <FormDefDerivados
                isNew={isNew}
                isTabla={isTableCatalog}
                isModalVenc={isModalVenc}
                triggerTablaVenc={triggerTablaVenc}
                consultaDataDerDef={consultaDataDerDef}
                selectedTv={selectedTv}
                selectedEmisora={selectedEmisora}
                requiredTv={requiredTv}
                requiredEmisora={requiredEmisora}
                fieldRequiredDefDerivados={fieldRequiredDefDerivados}
                requeridosDefDer={requeridosDefDer}
                setTriggerTablaVenc={setTriggerTablaVenc}
                setSelectedTv={setSelectedTv}
                setSelectedEmisora={setSelectedEmisora}
                setConsultaDataDerDef={setConsultaDataDerDef}
                setIsVencimiento={setIsButtonVenc}
                closeModalVenc={closeModalVenc}
            />
        </>
    )
}