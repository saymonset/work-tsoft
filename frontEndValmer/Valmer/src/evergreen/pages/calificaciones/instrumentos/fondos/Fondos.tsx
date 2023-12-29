import { ButtonContent, TitleDate } from "../../../../../shared"
import { CalifInstForm, CalifInstFormHeader } from "../components"
import { MoonLoader } from "react-spinners"
import { FormInstProps } from "../../../../../model"
import { HocRestricted } from "../../../restrictedAccess"
import { useEffect } from "react"

export const Fondos = (props: FormInstProps) => {

    useEffect(() => {
        props.handleLimpiar()
        props.setIsFondos(true)
    }, [])

    const title = "Calificaciones Fondos"

    return (
        <HocRestricted title={title} view={title} >
            <TitleDate title={title} />

            <div className="form-cols-1">
                <div className="flex justify-end pr-2">
                    <button
                        className="btn"
                        onClick={props.handleGuardar}
                    >
                        <ButtonContent name="Guardar" loading={props.loadingSave} />
                    </button>
                    <button
                        className="btn"
                        onClick={props.handleLimpiar}
                    >
                        <span>Limpiar</span>
                    </button>
                </div>
            </div>

            { props.loadingCatalog || !props.catalog.length || props.loadingCatalogCalif || !props.catalogCalif.body || props.loadingTv ?
                (
                <div className="flex justify-center item-center h-full">
                    {props.loadingCatalog || props.loadingCatalogCalif || props.loadingTv ? (
                        <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                    ) : (
                        <div>No hay información</div>
                    )}
                </div>
                ) : (
                    <>
                        <CalifInstFormHeader {...props} />
            
                        <div className="line"></div>
            
                        <CalifInstForm {...props} />
                    </>
                )
            }
        </HocRestricted>
    )
}