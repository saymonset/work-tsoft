import { FormInstProps } from "../../../../../model"
import { generateUUID } from "../../../../../utils"

export const NewInstr = (props: FormInstProps) => {
    return (
        <>
            <div className="form-select">
                <select 
                    name="s_tv" 
                    id="s_tv"
                    onChange={props.handleChange}
                    value={props.consultaData.s_tv || ""}
                >
                    <option value="default">...</option>
                    {props.tv?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                <label htmlFor="s_tv">TV</label>
                {props.requiredTv && (
                    <span className="fontError animate__animated animate__fadeIn">
                        Campo requerido TV
                    </span>
                )}
            </div>
            <div className="form-input">
                <input 
                    type="text" 
                    id="s_emisora"
                    name="s_emisora"
                    onChange={props.handleChange}
                    value={props.consultaData.s_emisora || ""}
                />
                <label htmlFor="s_emisora">EMISORA</label>
                {props.requiredEmisora && (
                    <span className="fontError animate__animated animate__fadeIn">
                        Campo requerido EMISORA
                    </span>
                )}
            </div>
            <div className="form-input">
                <input 
                    type="text" 
                    id="s_serie"
                    name="s_serie"
                    onChange={props.handleChange}
                    value={props.consultaData.s_serie || ""}
                />
                <label htmlFor="s_serie">SERIE</label>
                {props.requiredSerie && (
                    <span className="fontError animate__animated animate__fadeIn">
                        Campo requerido SERIE
                    </span>
                )}
            </div>
        </>
    )
}