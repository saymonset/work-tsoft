import { BarLoader } from "react-spinners"
import { TvEmiSerieProps } from "../../../../../../../../../model"
import { generateUUID } from "../../../../../../../../../utils"

export const TvEmiSerieDefault = (propsDef: TvEmiSerieProps) => {

    const handleTv = (event: any) => {
        propsDef.handleClickTv(event)
        propsDef.handleChange(event)
    }

    const handleEmi = (event: any) => {
        propsDef.handleEmisora(event)
        propsDef.handleChange(event)
    }

    const handleSerie = (event: any) => {
        propsDef.handleSerie(event)
        propsDef.handleChange(event)
    }

    return (
        <>
            <div className="form-select">
                <select 
                    name="s_tv" 
                    id="s_tv"
                    value={propsDef.selectedTv}
                    onChange={handleTv}
                >
                    <option value="default">...</option>
                    {propsDef.tv?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                <label
                    htmlFor="s_tv"
                >
                    TV
                </label>
                {propsDef.requiredTv && (
                    <span className="fontError animate__animated animate__fadeIn">Campo requerido sTv</span>
                )}
            </div>
            <div className="form-select">
                <select 
                    name="s_emisora" 
                    id="s_emisora"
                    value={propsDef.selectedEmisora}
                    onChange={handleEmi}
                >
                    <option value="default">...</option>
                    {propsDef.emisora?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                {propsDef.loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={200} />}
                <label 
                    htmlFor="s_emisora"
                >
                    EMISORA
                </label>
                {propsDef.requiredEmisora && (
                    <span className="fontError animate__animated animate__fadeIn">Campo requiredo s_emisora</span>
                )}
            </div>
            <div className="form-select">
                <select 
                    name="s_serie" 
                    id="s_serie"
                    value={propsDef.selectedSerie}
                    onChange={handleSerie}
                >
                    <option value="default">...</option>
                    {propsDef.serie?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                {propsDef.loadingSerie && <BarLoader className="mt-2" color="#059669" width={200} />}
                <label 
                    htmlFor="s_serie"
                >
                    SERIE
                </label>
                {propsDef.requiredSerie && (
                    <span className="fontError animate__animated animate__fadeIn">Campo requerido s_serie</span>
                )}
            </div>
        </>
    )
}