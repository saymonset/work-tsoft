import {getCatalogs} from "../../../../../utils";
import { Catalogo, RespDerivadosDef } from "../../../../../model"

interface FormColateralSOFRProps {
    consultaDataDerDef: RespDerivadosDef
    catalog: Catalogo[]
    handleChange: (e: React.ChangeEvent<HTMLSelectElement>) => void
}

export const FormColateralSOFR = (props: FormColateralSOFRProps) => {

    return (
        <div className="animate__animated animate__fadeIn">
            <div className="form-cols-2">
                <div className="form-select">
                    <select
                        name="n_crv_descuento_cu_sofr"
                        value={props.consultaDataDerDef?.body?.n_crv_descuento_cu_sofr || 'default'}
                        onChange={props.handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_descuento_cu_sofr">Crv Descuento CU</label>
                </div>
                <div className="form-select">
                    <select
                        name="n_crv_descueto_cu_sorf_e"
                        value={props.consultaDataDerDef?.body?.n_crv_descuento_cu_sofr_e || 'default'}
                        onChange={props.handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_descueto_cu_sofr_e">Crv Descuento CU Entrega</label>
                </div>
            </div>
            <div className="form-cols-2">
                <div className="form-select">
                    <select
                        name="n_crv_foranea_cu_sofr"
                        value={props.consultaDataDerDef?.body?.n_crv_foranea_cu_sofr || 'default'}
                        onChange={props.handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_foranea_cu_sofr">Crv Foranea CU</label>
                </div>
                <div className="form-select">
                    <select
                        name="n_crv_foranea_cu_sofr_e"
                        value={props.consultaDataDerDef?.body?.n_crv_foranea_cu_sofr_e || 'default'}
                        onChange={props.handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_foranea_cu_sofr_e">Crv Descuento Foranea CU Entrega</label>
                </div>
            </div>
            <div className="form-cols-2">
                <div className="form-select">
                    <select
                        name="n_crv_index_cu_sofr"
                        value={props.consultaDataDerDef?.body?.n_crv_index_cu_sofr || 'default'}
                        onChange={props.handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CURVE_INDEX').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_index_cu_sofr">Crv Index CU</label>
                </div>
                <div className="form-select">
                    <select
                        name="n_crv_index_cu_sofr_e"
                        value={props.consultaDataDerDef?.body?.n_crv_index_cu_sofr_e || 'default'}
                        onChange={props.handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CURVE_INDEX').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_index_cu_sofr_e">Crv Index CU Entrega</label>
                </div>
            </div>
        </div>
    )
}