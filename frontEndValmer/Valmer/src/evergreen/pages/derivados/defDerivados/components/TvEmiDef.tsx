import { BarLoader } from "react-spinners";
import { TvEmiDefProps } from "../../../../../model";
import { generateUUID } from "../../../../../utils";

export const TvEmiDef = (props: TvEmiDefProps) => {

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
            {props.loadingConsultaData && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500} />}

            <div className="form-title">Instrumento</div>
            <div className="form-cols-2">
                <div className="form-select">
                    <select 
                        name="s_tv" 
                        id="s_tv"
                        value={props.selectedTv}
                        onChange={handleClickTv}
                    >
                        <option value="default">...</option>
                        {props.tv?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="tv">TV</label>
                    {props.requiredTv && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido TV</span>
                    )}
                </div>
                <div className="form-select">
                    <select 
                        name="s_emisora" 
                        id="s_emisora"
                        value={props.selectedEmisora}
                        onChange={handleClickEmisora}
                    >
                        <option value="default">...</option>
                        {props.emisora?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>

                    {props.loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={150} />}
                    <label htmlFor="emisora">EMISORA</label>
                    {props.requiredEmisora && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido Emisora</span>
                    )}
                </div>
            </div>
        </>
    );
}