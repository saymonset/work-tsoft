import { DataTableDef } from "../../../../../../model";
import { generateUUID } from "../../../../../../utils";

interface TableDefProps {
    data: DataTableDef[]
    position: number
    lastRecord: number
    handleSelectedInst: (tv: string, emisora: string) => void
}

export const TableDef: React.FC<TableDefProps> = ({ data, position, lastRecord, handleSelectedInst }) => {
    const columns = [
        'S_CLAVE',
        'N_TAM_CONTRATO',
        'N_MONEDA',
        'N_TIPO_INSTRUMENTO',
        'N_CRV_DESCUENTO',
        'N_CRV_FORANEA',
        'N_CRV_REFERENCIA',
        'N_THEO_MODEL',
        'N_MKT_MODEL',
        'S_UNDERLYING',
        'S_TIPO_VALOR',
        'S_EMISORA',
        'N_TIPO_TASA',
        'N_COMP_YIELD',
        'S_DCS_CURVE_UND',
        'N_SETDAYRULE',
        'N_SUPERFICIE_VOLATILIDAD',
        'S_FB_UNDERLYINGS',
        'N_COMPONENT_WEIGHTS',
        'N_FACTOR_FILTRO_STRIKE',
        'N_SWP_RATE',
        'B_TV_EMI_VD',
        'B_TV_VD',
        'S_FUENTE',
        'B_RW',
        'S_FUTURE_QUOTE_UNITS',
    ];
    
    return (
        <>
            <div className="w-full max-h-64 overflow-scroll mt-3 -mb-4">
                <table className="table text-xs -mt-1">
                    <thead className="thead sticky top-0">
                        {columns.map((item) => (
                            <th className="px-2" key={generateUUID()}>{item}</th>
                        ))}
                    </thead>
                    <tbody className="tbody">
                        {data.slice(position, lastRecord).map((item) => (
                            <tr className="tr" key={generateUUID()}>
                                <td>
                                    <button 
                                        className="btn-link"
                                        onClick={() => handleSelectedInst(item.s_tipo_valor, item.s_emisora)}
                                    >
                                        {item.s_clave}
                                    </button>
                                </td>
                                <td>{item.n_tam_contrato}</td>
                                <td>{item.n_moneda}</td>
                                <td>{item.n_tipo_instrumento}</td>
                                <td>{item.n_crv_descuento}</td>
                                <td>{item.n_crv_foranea}</td>
                                <td>{item.n_crv_referencia}</td>
                                <td>{item.n_theo_model}</td>
                                <td>{item.n_mkt_model}</td>
                                <td>{item.s_underlying}</td>
                                <td>{item.s_tipo_valor}</td>
                                <td>{item.s_emisora}</td>
                                <td>{item.n_tipo_tasa}</td>
                                <td>{item.n_comp_yield}</td>
                                <td>{item.s_dcs_curve_und}</td>
                                <td>{item.n_setdayrule}</td>
                                <td>{item.n_superficie_volatilidad}</td>
                                <td>{item.s_fb_underlyings}</td>
                                <td>{item.n_component_weights}</td>
                                <td>{item.n_factor_filtro_strike}</td>
                                <td>{item.n_swp_rate}</td>
                                <td>{item.b_tv_emi_vd}</td>
                                <td>{item.b_tv_vd}</td>
                                <td>{item.s_fuente}</td>
                                <td>{item.b_rw}</td>
                                <td>{item.s_future_quote_units}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className="line"/>
        </>
    )
}