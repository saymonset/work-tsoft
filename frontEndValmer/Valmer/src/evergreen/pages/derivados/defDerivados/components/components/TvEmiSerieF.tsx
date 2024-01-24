import { FormValuesVenc } from "../../../../../../model"
import { ButtonContent } from "../../../../../../shared"

interface TvEmiSerieProps {
    tv: string
    emisora: string
    formValuesVenc: FormValuesVenc
    loading: boolean
    handleChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void
}

export const TvEmiSerieF = (props: TvEmiSerieProps) => {
    return (
        <>
            <div className="flex">
                <div className="mt-2 text-sm">
                    <label htmlFor="sTv">TV</label>
                    <input 
                        type="text" 
                        name="sTv" 
                        id="sTv" 
                        className="border-2 border-gray-400 w-9/12 ml-1"
                        value={props.tv}
                        disabled
                    />
                </div>
                <div className="mt-2 text-sm">
                    <label htmlFor="sEmisora">EMISORA</label>
                    <input 
                        type="text" 
                        name="sEmisora" 
                        id="sEmisora" 
                        className="border-2 border-gray-400 w-7/12 ml-1"
                        value={props.emisora}
                        disabled
                    />
                </div>
                <div className="mt-2 text-sm">
                    <label htmlFor="sSerie">SERIE</label>
                    <input 
                        type="text" 
                        name="sSerie" 
                        id="sSerie" 
                        className="border-2 border-gray-400 w-9/12 ml-1"
                        value={props.formValuesVenc.sSerie || ''}
                        onChange={(e) => props.handleChange(e)}
                    />
                </div>
            </div>
            <div className="flex mt-3">
                <div className="mt-2 text-sm w-2/3">
                    <label htmlFor="date_picker0">Fecha Vto</label>
                    <input 
                        type="date"
                        name="date_picker0" 
                        id="date_picker0" 
                        className="border-2 border-gray-400 ml-1"
                        value={props.formValuesVenc.date_picker0}
                        onChange={(e) => props.handleChange(e)}
                    />
                </div>
                <div className="text-right w-full">
                    <button 
                        className="btn"
                    >
                        <ButtonContent name="Guardar" loading={props.loading} />
                    </button>
                </div>
            </div>
        </>
    )
}