import {getCatalogs} from "../../../../../utils";
import {FormColateralFFProps} from "../../../../../model"
import React from "react";

export const FormColateralFF = (props: FormColateralFFProps) => {
    
    return (
        <div className="animate__animated animate__fadeIn">
            <div className="form-cols-2">
                <div className="form-select">
                    <select
                        name="n_crv_descuento_cu"
                        id="n_crv_descuento_cu"
                        value={props.consultaDataDerDef?.body?.n_crv_descuento_cu || 'default'}
                        onChange={(e) => props.handleChange(e)}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_descuento_cu">Crv Descuento CU</label>
                </div>
                <div className="form-select">
                    <select
                        name="n_crv_descuento_cu_e"
                        id="n_crv_descuento_cu_e"
                        value={props.consultaDataDerDef?.body?.n_crv_descuento_cu_e || 'default'}
                        onChange={(e) => props.handleChange(e)}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_descuento_cu_e">Crv Descuento CU Entrega</label>
                </div>
            </div>
            <div className="form-cols-2">
                <div className="form-select">
                    <select
                        name="n_crv_foranea_cu"
                        id="n_crv_foranea_cu"
                        value={props.consultaDataDerDef?.body?.n_crv_foranea_cu || 'default'}
                        onChange={(e) => props.handleChange(e)}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_foranea_cu">Crv Foranea CU</label>
                </div>
                <div className="form-select">
                    <select
                        name="n_crv_foranea_cu_e"
                        id="n_crv_foranea_cu_e"
                        value={props.consultaDataDerDef?.body?.n_crv_foranea_cu_e || 'default'}
                        onChange={(e) => props.handleChange(e)}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_foranea_cu_e">Crv Descuento Foranea CU Entrega</label>
                </div>
            </div>
            <div className="form-cols-2">
                <div className="form-select">
                    <select
                        name="n_crv_index_cu"
                        id="n_crv_index_cu"
                        value={props.consultaDataDerDef?.body?.n_crv_index_cu || 'default'}
                        onChange={(e) => props.handleChange(e)}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CURVE_INDEX').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_index_cu">Crv Index CU</label>
                </div>
                <div className="form-select">
                    <select
                        name="n_crv_index_cu_e"
                        id="n_crv_index_cu_e"
                        value={props.consultaDataDerDef?.body?.n_crv_index_cu_e || 'default'}
                        onChange={(e) => props.handleChange(e)}
                    >
                        <option value="default">...</option>
                        {getCatalogs(props.catalog, 'CURVE_INDEX').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_crv_index_cu_e">Crv Index CU Entrega</label>
                </div>
            </div>
        </div>
    )
}