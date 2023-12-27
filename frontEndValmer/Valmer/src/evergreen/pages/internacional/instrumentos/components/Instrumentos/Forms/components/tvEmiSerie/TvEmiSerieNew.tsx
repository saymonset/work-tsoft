import { TvEmiSerieProps } from "../../../../../../../../../model"

export const TvEmiSerieNew = (propsDef: TvEmiSerieProps) => {
    return (
        <>
            <div className="form-input">
                <input 
                    type="text" 
                    name="s_tv" 
                    id="s_tv" 
                    placeholder=""
                    value={propsDef.consultaData?.body?.s_tv ?? ''}
                    onChange={(e) => {
                        e.target.value = e.target.value.replace(/\s/g, '').slice(0, 4).toUpperCase();
                        propsDef.handleChange(e);
                    }}                    
                />
                <label
                    htmlFor="s_tv"
                >
                    TV
                </label>
                {propsDef.requiredTv && (
                    <span className="fontError animate__animated animate__fadeIn">Campo requerido s_tv</span>
                )}
            </div>
            <div className="form-input">
                <input 
                    type="text" 
                    name="s_emisora" 
                    id="s_emisora" 
                    placeholder=""
                    value={propsDef.consultaData?.body?.s_emisora ?? ''}
                    onChange={(e) => {
                        e.target.value = e.target.value.replace(/\s/g, '').slice(0, 7).toUpperCase();
                        propsDef.handleChange(e);
                    }}  
                />
                <label 
                    htmlFor="s_emisora"
                >
                    EMISORA
                </label>
                {propsDef.requiredEmisora && (
                    <span className="fontError animate__animated animate__fadeIn">Campo requerido s_emisora</span>
                )}
            </div>
            <div className="form-input">
                <input 
                    type="text" 
                    name="s_serie" 
                    id="s_serie"
                    placeholder=""
                    value={propsDef.consultaData?.body?.s_serie ?? ''}
                    onChange={(e) => {
                        e.target.value = e.target.value.replace(/\s/g, '').slice(0, 6).toUpperCase();
                        propsDef.handleChange(e);
                    }}  
                />
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