import { TvEmiDefProps } from "../../../../../model";

export const TvEmiDefNew = (props: TvEmiDefProps) => {

    const handleClickTv = (event: any) => {
        props.handleTv(event)
        props.handleChange(event)
    }

    const handleClickEmisora = (event: any) => {
        props.handleEmisora(event)
        props.handleChange(event)
    }

    return (
        <>
            <div className="form-title">Instrumento</div>
            <div className="form-cols-2">
                <div className="form-input">
                    <input 
                        type="text" 
                        name="s_tv" 
                        id="s_tv"
                        placeholder="" 
                        value={props.consultaData?.body?.s_tv || ''}
                        onChange={handleClickTv}
                    />
                    <label htmlFor="s_tv">TV</label>
                    {props.requiredTv && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido TV</span>
                    )}
                </div>
                <div className="form-input">
                    <input 
                        type="text" 
                        id="s_emisora"
                        name="s_emisora"
                        placeholder=""
                        value={props.consultaData?.body?.s_emisora || ''}
                        onChange={handleClickEmisora}
                    />
                    <label htmlFor="s_emisora">EMISORA</label>
                    {props.requiredEmisora && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido Emisora</span>
                    )}
                </div>
            </div>
        </>
    );
}