import {getCatalogs} from "../../../../../utils";
import React from "react";
import {PanamaFormProps} from "../../../../../model";

export const PanamForm = (data: PanamaFormProps) => {
    return (
        <div className="form mt-4 animate__animated animate__fadeIn">
            <form>
                <div className="form-cols-3 flex items-start">
                    <div className="form-cols-1 col-span-2">
                        <span className="form-title">Características</span>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_tipo_instrumento_edit"
                                        id="n_tipo_instrumento_edit"
                                        value={data.consultaData?.body?.info_bd?.n_tipo_instrumento_edit || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'TIPO_INSTRUMENTO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_tipo_instrumento_edit">
                                    Tipo Instrumento
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date">
                                <input type="date"
                                       name="d_fecha_emision"
                                       id="d_fecha_emision"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_emision || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_fecha_emision">
                                    Emisión
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_frecuencia_cupon"
                                        id="n_frecuencia_cupon"
                                        value={data.consultaData?.body?.info_bd?.n_frecuencia_cupon || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'FRECUENCIA_CUPON').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_frecuencia_cupon">
                                    Frecuencia Cupón
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date">
                                <input type="date"
                                       name="d_fecha_liquidacion"
                                       id="d_fecha_liquidacion"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_liquidacion || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_fecha_liquidacion">
                                    Fecha Liquidación
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_tipo_mercado"
                                        id="n_tipo_mercado"
                                        value={data.consultaData?.body?.info_bd?.n_tipo_mercado || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'TIPO_MERCADO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="tipoMercado">Tipo Mercado</label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date">
                                <input type="date"
                                       name="d_fecha_vencimiento"
                                       id="d_fecha_vencimiento"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_vencimiento || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_fecha_vencimiento">
                                    Fecha Vencimiento
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_clase"
                                        id="n_clase"
                                        value={data.consultaData?.body?.info_bd?.n_clase || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'CLASE').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_clase">Clase</label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date">
                                <input type="date"
                                       name="d_fecha_inicio_cupon"
                                       id="d_fecha_inicio_cupon"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_inicio_cupon || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_fecha_inicio_cupon">
                                    Fecha Inicio Cupón
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_sector"
                                        id="n_sector"
                                        value={data.consultaData?.body?.info_bd?.n_sector || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'SECTOR').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_sector">Sector</label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date">
                                <input type="date"
                                       name="d_fecha_vto_cupon"
                                       id="d_fecha_vto_cupon"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_vto_cupon || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_fecha_vto_cupon">
                                    Fecha Fin Cupón
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_curva_desc"
                                        id="n_curva_desc"
                                        value={data.consultaData?.body?.info_bd?.n_curva_desc || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'CURVA_DESC').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_curva_desc">
                                    Curva Descuento
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-input">
                                <input type="text"
                                       name="n_plazo"
                                       id="n_plazo"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_plazo || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="n_plazo">
                                    Plazo
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_moneda"
                                        id="n_moneda"
                                        value={data.consultaData?.body?.info_bd?.n_moneda || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'MONEDA').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_moneda">
                                    Moneda
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-input">
                                <input type="text"
                                       name="n_monto_colocado"
                                       id="n_monto_colocado"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_monto_colocado || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="n_monto_colocado">
                                    Monto Colocado
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_theo_model"
                                        id="n_theo_model"
                                        value={data.consultaData?.body?.info_bd?.n_theo_model || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'THEO_MODEL').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_theo_model">
                                    Theorical Model
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-input">
                                <input type="text"
                                       name="n_valor_nominal"
                                       id="n_valor_nominal"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_valor_nominal || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="n_valor_nominal">
                                    Valor Nominal
                                </label>
                            </div>
                            <div className="form-input">
                                <input type="text"
                                       name="n_precio_colocacion"
                                       id="n_precio_colocacion"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_precio_colocacion || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="n_precio_colocacion">
                                    Precio Colocación
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-input">
                                <input type="text"
                                       name="n_tasa"
                                       id="n_tasa"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_tasa || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="tasa">
                                    Tasa
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_base_calculo"
                                        id="n_base_calculo"
                                        value={data.consultaData?.body?.info_bd?.n_base_calculo || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'BASE_CALCULO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_base_calculo">
                                    Base Cálculo
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-input">
                                <input type="text"
                                       name="n_sobretasa"
                                       id="n_sobretasa"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_sobretasa || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="prima">Prima</label>
                            </div>
                            <div className="form-select">
                                <select name="n_status"
                                        id="n_status"
                                        value={data.consultaData?.body?.info_bd?.n_status || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'STATUS').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_status">
                                    Status
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-input">
                                <input type="text"
                                       name="s_isin"
                                       id="s_isin"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.s_isin || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="s_isin">
                                    Isin
                                </label>
                            </div>
                            <div className="form-date">
                                <input type="date"
                                       name="d_fecha_amort_ant"
                                       id="d_fecha_amort_ant"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_amort_ant || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_fecha_amort_ant">
                                    Fecha Amort. Ant.
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-input">
                                <input type="text"
                                       name="n_precio"
                                       id="n_precio"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_precio || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="n_precio">
                                    Precio
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_form_cotizacion"
                                        id="n_form_cotizacion"
                                        value={data.consultaData?.body?.info_bd?.n_form_cotizacion || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'forma_cotizacion').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_form_cotizacion">
                                    Forma Cotización
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_coupon_gen_met"
                                        id="n_coupon_gen_met"
                                        value={data.consultaData?.body?.info_bd?.n_coupon_gen_met || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'COUPON_GEN_MET').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_coupon_gen_met">
                                    Generación Cupón
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_odd_last_coupon"
                                        id="n_odd_last_coupon"
                                        value={data.consultaData?.body?.info_bd?.n_odd_last_coupon || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'ODD_COUPON').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_odd_last_coupon">
                                    Odd Last Coupon
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-input">
                                <input type="text"
                                       name="n_fixed_coupon_date"
                                       id="n_fixed_coupon_date"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_fixed_coupon_date || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="n_fixed_coupon_date">
                                    Fixed Coupon Date
                                </label>
                            </div>
                            <div className="form-select">
                                <select name="n_odd_first_coupon"
                                        id="n_odd_first_coupon"
                                        value={data.consultaData?.body?.info_bd?.n_odd_first_coupon || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'ODD_COUPON').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_odd_first_coupon">Odd First Coupon</label>
                            </div>
                        </div>

                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_pais"
                                        id="n_pais"
                                        value={data.consultaData?.body?.info_bd?.n_pais || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'PAIS').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_pais">País</label>
                            </div>
                            <div className="form-select">
                                <select name="n_business_day_rule"
                                        id="n_business_day_rule"
                                        value={data.consultaData?.body?.info_bd?.n_business_day_rule || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'BUSINESS_DAY_RULE').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_business_day_rule">
                                    Business Day Rule
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-1 -my-3">
                            <div className="form-select">
                                <select name="n_emisor"
                                        id="n_emisor"
                                        value={data.consultaData?.body?.info_bd?.n_emisor || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'EMISOR').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_emisor">
                                    Emisor
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_fitch"
                                        id="n_fitch"
                                        value={data.consultaData?.body?.info_bd?.n_fitch || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'FITCH').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="fitch">Fitch</label>
                            </div>
                            <div className="form-date">
                                <input type="date"
                                       name="d_fitch"
                                       id="d_fitch"
                                       value={data.consultaData?.body?.info_bd?.d_fitch || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_fitch">
                                    Fecha Fitch
                                </label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_moody"
                                        id="n_moody"
                                        value={data.consultaData?.body?.info_bd?.n_moody || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'MOODY').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_moody">Moody</label>
                            </div>
                            <div className="form-date">
                                <input type="date"
                                       name="d_moody"
                                       id="d_moody"
                                       value={data.consultaData?.body?.info_bd?.d_moody || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_moody">Fecha Moody</label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_sp"
                                        id="n_sp"
                                        value={data.consultaData?.body?.info_bd?.n_sp || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'FITCH').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_sp">
                                    S&P
                                </label>
                            </div>
                            <div className="form-date">
                                <input type="date"
                                       name="d_sp"
                                       id="d_sp"
                                       value={data.consultaData?.body?.info_bd?.d_sp || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_sp">Fecha S&P</label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date">
                                <input type="date"
                                       name="d_fecha_ingreso_titulo"
                                       id="d_fecha_ingreso_titulo"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_ingreso_titulo || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_fecha_ingreso_titulo">
                                    Fecha Ingreso Título
                                </label>
                            </div>
                            <div className="form-date">
                                <input type="date"
                                       name="d_last_reg_coup_date"
                                       id="d_last_reg_coup_date"
                                       value={data.consultaData?.body?.info_bd?.d_last_reg_coup_date || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="d_last_reg_coup_date">Last Regular Coupon Date</label>
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_crv_index"
                                        id="n_crv_index"
                                        value={data.consultaData?.body?.info_bd?.n_crv_index || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'CRV_INDEX').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_crv_index">
                                    Curve Index
                                </label>
                            </div>
                            <div className="form-input">
                                <input type="text"
                                       name="n_soy"
                                       id="n_soy"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_soy || ""}
                                       onChange={data.handleChange}
                                />
                                <label htmlFor="n_soy">SOY</label>
                            </div>
                        </div>
                    </div>

                    {(data.consultaData && Object.keys(data.consultaData).length > 0) && (
                        <div className="form-cols-1">
                            <span className="form-title">RiskWatch</span>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="currency"
                                           id="currency"
                                           placeholder=""
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.Currency ?? ''}
                                           disabled
                                    />
                                    <label htmlFor="currency">Currency</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="notional"
                                           id="notional"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.Notional ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="notional">Notional</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-date">
                                    <input type="date"
                                           name="issueDate"
                                           id="issueDate"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Issue Date"] ?? ''}
                                           disabled/>
                                    <label htmlFor="issueDate">Issue Date</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-date">
                                    <input type="date"
                                           name="maturityDate"
                                           id="maturityDate"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Maturity Date"] ?? ''}
                                           disabled/>
                                    <label htmlFor="maturityDate">Maturity Date</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="Discount Courve"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Discount Courve"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="Discount Courve">Discount Curve</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="term"
                                           id="term"
                                           placeholder=""
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.Term ?? ''}
                                           disabled/>
                                    <label htmlFor="term">Term</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="couponGenerationMethod"
                                           id="couponGenerationMethod"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Coupon Generation Method"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="couponGenerationMethod">Coupon Generation Method</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="stateProcedure"
                                           id="stateProcedure"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["State Procedure"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="stateProcedure">State Procedure</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="couponRate"
                                           id="couponRate"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Coupon Rate"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="couponRate">Coupon Rate</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="spotPrice"
                                           id="spotPrice"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Spot Price"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="spotPrice">Spot Price</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="couponProrated"
                                           id="couponProrated"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Coupon Prorated"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="couponProrated">Coupon Prorated</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="businessDayRule"
                                           id="businessDayRule"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Business Day Rule"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="businessDayRule">Business Day Rule</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="spreadOverYield"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Spread Over Yield"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="spreadOverYield">Spread Over Yield</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="theoricalModel"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Theorical Model"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="theoricalModel">Theorical Model</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="marketModel"
                                           id="marketModel"
                                           value={data.consultaData?.body?.info_rw?.
                                               ["Fixed Rate Bond"]?.
                                               [data.selectedNemoTecnico]?.["Market Model"] ?? ''}
                                           placeholder=""
                                           disabled/>
                                    <label htmlFor="marketModel">Market Model</label>
                                </div>
                            </div>
                        </div>
                    )}
                </div>
            </form>
        </div>
    );
};