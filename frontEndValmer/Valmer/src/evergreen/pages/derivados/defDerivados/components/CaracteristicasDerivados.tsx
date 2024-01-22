import { Catalogo, IsFieldModifiedFvDerivados, RequeridosDefDerivados, RespDerivadosDef } from "../../../../../model";
import { getCatalogs, getCatalogsNoOrder } from "../../../../../utils";
import { FormColateralFF } from "./FormColateralFF";
import { FormColateralSOFR } from "./FormColateralSOFR";

interface CaracteristicasDerivadosProps {
    consultaDataDerDef: RespDerivadosDef;
    requeridosDefDer: RequeridosDefDerivados;
    fieldRequiredDefDerivados: IsFieldModifiedFvDerivados;
    handleChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    catalog: Catalogo[];
    catalogUnderlying: Catalogo[];
    catalogDcsCurveUnd: Catalogo[];
    checkEval: (name: string) => boolean;
    handleCheckChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    colateralFF: boolean;
    checkedColateralFF: () => void;
    colateralSOFR: boolean;
    checkedColateralSOFR: () => void;
    catalogDefDer: Catalogo[];
}

export const CaracteristicasDerivados = (props: CaracteristicasDerivadosProps) => {
    return (
        <div className="form-cols-3">
            <div className="col-span-2">
                <div className="form-title">Características Derivados</div>
                <div className="form-cols-2">
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_tam_contrato"
                            id="n_tam_contrato"
                            placeholder=""
                            value={props.consultaDataDerDef?.body?.n_tam_contrato || ''}
                            onChange={(e) => props.handleChange(e)}
                        />
                        <label htmlFor="contrato">Contrato</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            id="n_moneda"
                            name="n_moneda"
                            value={props.consultaDataDerDef?.body?.n_moneda || 'default'}
                            onChange={(e) => props.handleChange(e)}
                            ref={props.requeridosDefDer.n_moneda}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'MONEDA').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_moneda">Moneda</label>
                        {props.fieldRequiredDefDerivados.n_moneda && (
                            <span className="fontError animate__animated animate__fadeIn">Campo requerido Moneda</span>
                        )}
                    </div>
                    <div className="form-select">
                        <select
                            name="n_moneda_e"
                            id="n_moneda_e"
                            value={props.consultaDataDerDef?.body?.n_moneda_e || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'MONEDA').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_moneda_e">Moneda Entrega</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_tipo_instrumento"
                            id="n_tipo_instrumento"
                            value={props.consultaDataDerDef?.body?.n_tipo_instrumento || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'TIPO_INSTRUMENTO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_tipo_instrumento">Tipo de Instrumento</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="n_tipo_instrumento_e"
                            id="n_tipo_instrumento_e"
                            value={props.consultaDataDerDef?.body?.n_tipo_instrumento_e || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'TIPO_INSTRUMENTO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_tipo_instrumento_e">Tipo Instrumento Entrega</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_crv_descuento"
                            id="n_crv_descuento"
                            value={props.consultaDataDerDef?.body?.n_crv_descuento || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_crv_descuento">Curva Descuento</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="n_crv_descuento_e"
                            id="n_crv_descuento_e"
                            value={props.consultaDataDerDef?.body?.n_crv_descuento_e || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_crv_descuento">Crv Descuento Entrega</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_crv_foranea"
                            id="n_crv_foranea"
                            value={props.consultaDataDerDef?.body?.n_crv_foranea || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_crv_foranea">Curva Foranea</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="n_crv_foranea_e"
                            id="n_crv_foranea_e"
                            value={props.consultaDataDerDef?.body?.n_crv_foranea_e || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_crv_foranea_e">Crv Descuento Foranea Entrega</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_crv_index"
                            id="n_crv_index"
                            value={props.consultaDataDerDef?.body?.n_crv_index || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'CURVE_INDEX').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_crv_index">Crv Index</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="n_crv_index_e"
                            id="n_crv_index_e"
                            value={props.consultaDataDerDef?.body?.n_crv_index_e || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'CURVE_INDEX').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_crv_index_e">Crv Index Entrega</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_crv_referencia"
                            id="n_crv_referencia"
                            value={props.consultaDataDerDef?.body?.n_crv_referencia || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'CRV_DESCUENTO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_crv_referencia">Curva Referencia</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_theo_model"
                            id="n_theo_model"
                            value={props.consultaDataDerDef?.body?.n_theo_model || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'THEO_MODEL').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_theo_model">Theorical Model</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_mkt_model"
                            id="n_mkt_model"
                            value={props.consultaDataDerDef?.body?.n_mkt_model || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'MARKET_MODEL').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_mkt_model">Market Model</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="s_underlying"
                            id="s_underlying"
                            value={props.consultaDataDerDef?.body?.s_underlying || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogsNoOrder(props.catalogUnderlying, 's_underlying').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_underlying">Underlying</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="s_underlying_sp"
                            id="s_underlying_sp"
                            value={props.consultaDataDerDef?.body?.s_underlying_sp || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogsNoOrder(props.catalogUnderlying, 's_underlying_sp').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_underlying_sp">Underlying SP</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_tipo_tasa"
                            id="n_tipo_tasa"
                            value={props.consultaDataDerDef?.body?.n_tipo_tasa || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'TIPO_TASA').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_tipo_tasa">Tipo Tasa</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_tipo_subyacente"
                            id="n_tipo_subyacente"
                            value={props.consultaDataDerDef?.body?.n_tipo_subyacente || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'TIPO_SUBYACENTE').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_tipo_subyacente">Tipo Subyacente</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_comp_yield"
                            id="n_comp_yield"
                            value={props.consultaDataDerDef?.body?.n_comp_yield || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'COMPOSICION_YIELD').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_comp_yield">Composición Yield</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="s_dcs_curve_und"
                            id="d_dcs_curve_und"
                            value={props.consultaDataDerDef?.body?.s_dcs_curve_und || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalogDcsCurveUnd, 'dcs-curve-und').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="d_dcs_curve_und">Dos_Curve_Und</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_setdayrule"
                            id="n_setdayrule"
                            value={props.consultaDataDerDef?.body?.n_setdayrule || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'BUSINESS_RESET_RULE').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_setdayrule">Set_Day_Rule</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_supervol"
                            id="n_supervol"
                            value={props.consultaDataDerDef?.body?.n_supervol || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'SUPERFICIE_VOLATILIDAD').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_supervol">Superifie Volatilidad</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="n_tipo_calculo_r"
                            id="n_tipo_calculo_r"
                            value={props.consultaDataDerDef?.body?.n_tipo_calculo_r || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'TIPO_CALCULO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_tipo_calculo_r">Tipo Calculo Recibe</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="n_tipo_calculo_e"
                            id="n_tipo_calculo_e"
                            value={props.consultaDataDerDef?.body?.n_tipo_calculo_e || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalog, 'TIPO_CALCULO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_tipo_calculo_e">Tipo Cálculo Entrega</label>
                    </div>
                </div>
                <div className="form-cols-2">
                    <div className="form-select">
                        <select
                            name="s_underlying_cu"
                            id="s_underlying_cu"
                            value={props.consultaDataDerDef?.body?.s_underlying_cu || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogsNoOrder(props.catalogUnderlying, 's_underlying_cu').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_underlying_cu">Underlying CU</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="s_underlying_sp_cu"
                            id="s_underlying_sp_cu"
                            value={props.consultaDataDerDef?.body?.s_underlying_sp_cu || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogsNoOrder(props.catalogUnderlying, 's_underlying_sp_cu').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="s_underlying_sp_cu">Underlying SP CU</label>
                    </div>
                </div>
                <div className="container-line">
                    <div className="line"></div>
                </div>
                <div className="form-cols-1">
                    <div className="form-input">
                        <input
                            type="text"
                            name="s_fb_underlyings"
                            id="s_fb_underlyings"
                            placeholder=""
                            value={props.consultaDataDerDef?.body?.s_fb_underlyings || ''}
                            onChange={(e) => props.handleChange(e)}
                        />
                        <label htmlFor="s_fb_underlyings">FBUnderlyings</label>
                    </div>
                </div>
                <div className="form-cols-1">
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_component_weights"
                            id="n_component_weights"
                            placeholder=""
                            value={props.consultaDataDerDef?.body?.n_component_weights || ''}
                            onChange={(e) => props.handleChange(e)}
                        />
                        <label htmlFor="n_component_weights">Component Weights</label>
                    </div>
                </div>
                <div className="container-line">
                    <div className="line"></div>
                </div>
                <div className="form-cols-1">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="b_filtro_strike"
                            id="b_filtro_strike"
                            checked={props.checkEval('b_filtro_strike')}
                            onChange={(e) => props.handleCheckChange(e)}
                        />
                        <label htmlFor="b_filtro_strike">Filtro Strike</label>
                    </div>
                </div>
                <div className="form-cols-1">
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_factor_filtro_strike"
                            id="n_factor_filtro_strike"
                            placeholder=""
                            value={props.consultaDataDerDef?.body?.n_factor_filtro_strike || ''}
                            onChange={(e) => props.handleChange(e)}
                        />
                        <label htmlFor="n_factor_filtro_strike">Factor Filtro Strike</label>
                    </div>
                </div>
                <div className="container-line">
                    <div className="line"></div>
                </div>
                <div className="form-cols-1">
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_swp_rate"
                            id="n_swp_rate"
                            placeholder=""
                            value={props.consultaDataDerDef?.body?.n_swp_rate || ''}
                            onChange={(e) => props.handleChange(e)}
                        />
                        <label htmlFor="n_swp_rate">SWP RATE</label>
                    </div>
                </div>
                <div className="container-line">
                    <div className="line"></div>
                </div>
                <div className="form-cols-1">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="b_base_ff"
                            id="b_base_ff"
                            checked={props.colateralFF}
                            onChange={props.checkedColateralFF}
                        />
                        <label htmlFor="b_base_ff">BASE COLATERAL FF</label>
                    </div>
                </div>
                {props.colateralFF && (
                    <FormColateralFF
                        consultaDataDerDef={props.consultaDataDerDef}
                        catalog={props.catalog}
                        handleChange={props.handleChange}
                    />
                )}
                <div className="form-cols-1">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="b_base_sofr"
                            id="b_base_sofr"
                            checked={props.colateralSOFR}
                            onChange={props.checkedColateralSOFR}
                        />
                        <label htmlFor="b_base_sofr">BASE COLATERAL SOFR</label>
                    </div>
                </div>
                {props.colateralSOFR && (
                    <FormColateralSOFR
                        consultaDataDerDef={props.consultaDataDerDef}
                        catalog={props.catalog}
                        handleChange={props.handleChange}
                    />
                )}
            </div>
            <div className="col-span-1">
                <div className="form-title">...</div>
                <div className="form-cols-1">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="b_tv_emi_vd"
                            id="b_tv_emi_vd"
                            checked={props.checkEval('b_tv_emi_vd')}
                            onChange={(e) => props.handleCheckChange(e)}
                        />
                        <label htmlFor="b_tv_emi_vd">TV_EMI VD</label>
                    </div>
                </div>
                <div className="form-cols-1">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="b_tv_vd"
                            id="b_tv_vd"
                            value={props.consultaDataDerDef?.body?.b_tv_vd}
                            checked={props.checkEval('b_tv_vd')}
                            onChange={(e) => props.handleCheckChange(e)}
                        />
                        <label htmlFor="b_tv_vd">TV_VD</label>
                    </div>
                </div>
                <div className="form-cols-1">
                    <div className="form-input">
                        <input
                            type="text"
                            name="s_fuente"
                            id="s_fuente"
                            placeholder=""
                            value={props.consultaDataDerDef?.body?.s_fuente || ''}
                            onChange={(e) => props.handleChange(e)}
                        />
                        <label htmlFor="s_fuente">Fuente</label>
                    </div>
                </div>
                <div className="form-cols-1">
                    <div className="form-select">
                        <select
                            name="b_rw"
                            id="b_rw"
                            value={props.consultaDataDerDef?.body?.b_rw || 'default'}
                            onChange={(e) => props.handleChange(e)}
                        >
                            <option value="default">...</option>
                            {getCatalogs(props.catalogDefDer, 'hrw').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="b_rw">RW</label>
                    </div>
                </div>
                <div className="form-cols-1">
                    <div className="form-input">
                        <input
                            type="text"
                            name="s_future_quote_units"
                            id="s_future_quote_units"
                            placeholder=""
                            value={props.consultaDataDerDef?.body?.s_future_quote_units || ''}
                            onChange={(e) => props.handleChange(e)}
                        />
                        <label htmlFor="s_future_quote_units">Future Quote Units</label>
                    </div>
                </div>
            </div>
        </div>
    );
}