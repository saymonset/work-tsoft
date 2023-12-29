import { BarLoader } from "react-spinners"
import { FormInstProps } from "../../../../../model"
import { generateUUID } from "../../../../../utils"

export const NewSerie = (props: FormInstProps) => {
    return (
        <>
            <div className="form-select">
                <select 
                    name="s_tv" 
                    id="s_tv"
                    onChange={props.handleTv}
                    value={props.selectedTv || ""}
                >
                    <option value="default">...</option>
                    {props.tv?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                <label htmlFor="s_tv">TV</label>
            </div>
            <div className="form-select">
                <select 
                    name="s_emisora" 
                    id="s_emisora"
                    onChange={props.handleEmisora}
                    value={props.selectedEmisora || ""}
                >
                    <option value="default">...</option>
                    {props.emisoras?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                {props.loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={180} />}
                <label htmlFor="s_emisora">EMISORA</label>
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
            </div>
        </>
    )
}