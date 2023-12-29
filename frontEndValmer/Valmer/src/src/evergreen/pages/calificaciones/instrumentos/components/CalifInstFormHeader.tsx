import { BarLoader } from "react-spinners";
import { FormInstProps } from "../../../../../model";
import { getCatalogs } from "../../../../../utils";
import { OptTvEmiSerie } from "./OptTvEmiSerie";

export const CalifInstFormHeader = (props: FormInstProps) => {

    if (!props.catalog?.length) {
        return (
            <div className="flex justify-center item-center h-full">
                No hay información
            </div>
        )
    }

    return (
        <div className="form mt-4 animate__animated animate__fadeIn">
            {props.loadingConsultaData && <BarLoader className="my-2" color="#059669" width={500}/>}
            <form>
                <div className="form-cols-5">
                    <OptTvEmiSerie {...props} />
                    <div className="form-select">
                        <select
                            name="s_pais"
                            id="s_pais"
                            onChange={props.handleChange}
                            value={props.consultaData.s_pais || ""}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'PAIS').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_pais">País</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="s_moneda"
                            id="s_moneda"
                            onChange={props.handleChange}
                            value={props.consultaData.s_moneda || ""}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'MONEDA').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_moneda">Moneda</label>
                    </div>
                </div>
                <div className="form-cols-1">
                    <div className="form-select">
                        <select
                            name="s_emisor"
                            id="s_emisor"
                            onChange={props.handleChange}
                            value={props.consultaData.s_emisor || ""}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'EMISOR').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_emisor">Emisor</label>
                    </div>
                </div>
                <div className="form-cols-5">
                    <div className="form-select">
                        <select
                            name="s_tipo_papel"
                            id="s_tipo_papel"
                            onChange={props.handleChange}
                            value={props.consultaData.s_tipo_papel || ""}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'TIPO_PAPEL').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_tipo_papel">Tipo Papel</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="s_com_val"
                            id="s_com_val"
                            onChange={props.handleChange}
                            value={props.consultaData.s_com_val || ""}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'COMISION_VALORES').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_com_val">Comisión Valores</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="s_bolsa_emi"
                            id="s_bolsa_emi"
                            onChange={props.handleChange}
                            value={props.consultaData.s_bolsa_emi || ""}
                        >
                            <option value="">...</option>
                            {getCatalogs(props.catalog, 'BOLSA_EMISION').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_bolsa_emi">Bolsa Emisión</label>
                    </div>
                    <div className="form-date">
                        <input
                            type="date"
                            name="d_fec_ins"
                            id="d_fec_ins"
                            onChange={props.handleChange}
                            value={props.consultaData.d_fec_ins || ""}
                        />
                        <label htmlFor="d_fec_ins">Fecha Inscripción</label>
                    </div>
                    {!props.isFondos && (
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_subasta"
                                id="d_fecha_subasta"
                                onChange={props.handleChange}
                                value={props.consultaData.d_fecha_subasta || ""}
                            />
                            <label htmlFor="d_fecha_subasta">Fecha Subasta</label>
                        </div>
                    )}
                </div>
            </form>
        </div>
    )
}