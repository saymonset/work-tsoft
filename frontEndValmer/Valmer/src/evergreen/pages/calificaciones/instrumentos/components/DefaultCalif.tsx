import { BarLoader } from "react-spinners"
import { FormInstProps } from "../../../../../model"
import { generateUUID } from "../../../../../utils"

export const DefaultCalif = (props: FormInstProps) => {
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
            {!props.isFondos && (
                <div className="form-select">
                    <select 
                        name="s_serie" 
                        id="s_serie"
                        onChange={props.handleSerie}
                        value={props.selectedSerie || ""}
                    >
                        <option value="default">...</option>
                        {props.series?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                    {props.loadingSeries && <BarLoader className="mt-2" color="#059669" width={180} />}
                    <label htmlFor="s_serie">SERIE</label>
                </div>
            )}
        </>
    )
}