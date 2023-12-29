import { ButtonContent, TitleDate } from '../../../../../shared'
import { HocRestricted } from '../../../restrictedAccess'
import { MoonLoader } from 'react-spinners'
import { CalifInstForm, CalifInstFormHeader } from '../components'
import { FormInstProps } from '../../../../../model'
import { useEffect } from 'react'

export const General = (props: FormInstProps) => {

    useEffect(() => {
        props.handleLimpiar()
        props.setIsFondos(false)
    }, [])
    
    const title = "Calificaciones Instrumentos"

    return (
        <HocRestricted title={title} view={title} >
            <TitleDate title={title} />

            <div className="form-cols-1">
                <div className="flex justify-end pr-2">
                    <button
                        className="btn"
                        onClick={props.handleNuevo}
                    >
                        <span>Nuevo Instr</span>
                    </button>
                    <button
                        className="btn"
                        onClick={props.handleGuardar}
                    >
                        <ButtonContent name='Guardar' loading={props.loadingSave} />
                    </button>
                    <button
                        className="btn"
                        onClick={props.handleNuevaSerie}
                    >
                        <span>Nueva Serie</span>
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