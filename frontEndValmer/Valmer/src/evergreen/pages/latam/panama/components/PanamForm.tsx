import {getCatalogs, useBigInput} from "../../../../../utils";
import React from "react";
import {PanamaFormProps} from "../../../../../model";

export const PanamForm = (data: PanamaFormProps) => {
        //  Achica o agranda el input del form cuando obtiene o deja el focus
        const {  handleFocus,
            handleBlur,
            sendStyle} = useBigInput();
    return (
        <div className="form form-mb-x mt-1 animate__animated animate__fadeIn ">
            <form>
                <div className="form-cols-3 flex items-start ">
                    <div className="form-cols-1 col-span-2">
                        <span className="form-title">Características</span>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_tipo_instrumento_edit"
                                        id="n_tipo_instrumento_edit"
                                        value={data.consultaData?.body?.info_bd?.n_tipo_instrumento_edit || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_tipo_instrumento_edit}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'TIPO_INSTRUMENTO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_tipo_instrumento_edit">
                                    Tipo Instrumento
                                </label>
                                {data.isFieldRequiredLatPanama.n_tipo_instrumento_edit && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Tipo Instrumento
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date form-date-my">
                                <input type="date"
                                       name="d_fecha_emision"
                                       id="d_fecha_emision"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_emision || ""}
                                       onChange={data.handleChange}
                                       ref={data.refReqLatPanama.d_fecha_emision}
                                />
                                <label htmlFor="d_fecha_emision">
                                    Emisión
                                </label>
                                {data.isFieldRequiredLatPanama.d_fecha_emision && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Emisión
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_frecuencia_cupon"
                                        id="n_frecuencia_cupon"
                                        value={data.consultaData?.body?.info_bd?.n_frecuencia_cupon || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_frecuencia_cupon}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'FRECUENCIA_CUPON').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_frecuencia_cupon">
                                    Frecuencia Cupón
                                </label>
                                {data.isFieldRequiredLatPanama.n_frecuencia_cupon && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Frecuencia Cupón
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date form-date-my">
                                <input type="date"
                                       name="d_fecha_liquidacion"
                                       id="d_fecha_liquidacion"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_liquidacion || ""}
                                       onChange={data.handleChange}
                                       ref={data.refReqLatPanama.d_fecha_liquidacion}
                                />
                                <label htmlFor="d_fecha_liquidacion">
                                    Fecha Liquidación
                                </label>
                                {data.isFieldRequiredLatPanama.d_fecha_liquidacion && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Fecha Liquidación
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_tipo_mercado"
                                        id="n_tipo_mercado"
                                        value={data.consultaData?.body?.info_bd?.n_tipo_mercado || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_tipo_mercado}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'TIPO_MERCADO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="tipoMercado">Tipo Mercado</label>
                                {data.isFieldRequiredLatPanama.n_tipo_mercado && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Tipo Mercado
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date form-date-my">
                                <input type="date"
                                       name="d_fecha_vencimiento"
                                       id="d_fecha_vencimiento"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_vencimiento || ""}
                                       onChange={data.handleChange}
                                       ref={data.refReqLatPanama.d_fecha_vencimiento}
                                />
                                <label htmlFor="d_fecha_vencimiento">
                                    Fecha Vencimiento
                                </label>
                                {data.isFieldRequiredLatPanama.d_fecha_vencimiento && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Fecha Vencimiento
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_clase"
                                        id="n_clase"
                                        value={data.consultaData?.body?.info_bd?.n_clase || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_clase}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'CLASE').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_clase">Clase</label>
                                {data.isFieldRequiredLatPanama.n_clase && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Clase
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date form-date-my">
                                <input type="date"
                                       name="d_fecha_inicio_cupon"
                                       id="d_fecha_inicio_cupon"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_inicio_cupon || ""}
                                       onChange={data.handleChange}
                                       ref={data.refReqLatPanama.d_fecha_inicio_cupon}
                                />
                                <label htmlFor="d_fecha_inicio_cupon">
                                    Fecha Inicio Cupón
                                </label>
                                {data.isFieldRequiredLatPanama.d_fecha_inicio_cupon && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Fecha Inicio Cupón
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_sector"
                                        id="n_sector"
                                        value={data.consultaData?.body?.info_bd?.n_sector || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_sector}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'SECTOR').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_sector">Sector</label>
                                {data.isFieldRequiredLatPanama.n_sector && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Sector
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-date form-date-my">
                                <input type="date"
                                       name="d_fecha_vto_cupon"
                                       id="d_fecha_vto_cupon"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_vto_cupon || ""}
                                       onChange={data.handleChange}
                                       ref={data.refReqLatPanama.d_fecha_vto_cupon}
                                />
                                <label htmlFor="d_fecha_vto_cupon">
                                    Fecha Fin Cupón
                                </label>
                                {data.isFieldRequiredLatPanama.d_fecha_vto_cupon && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Fecha Fin Cupón
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_curva_desc"
                                        id="n_curva_desc"
                                        value={data.consultaData?.body?.info_bd?.n_curva_desc || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_curva_desc}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'CURVA_DESC').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_curva_desc">
                                    Curva Descuento
                                </label>
                                {data.isFieldRequiredLatPanama.n_curva_desc && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Curva Descuento
                                    </span>
                                )}
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
                                       ref={data.refReqLatPanama.n_plazo}
                                       onFocus={() => handleFocus('n_plazo')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_plazo')}
                                />
                                <label htmlFor="n_plazo">
                                    Plazo
                                </label>
                                {data.isFieldRequiredLatPanama.n_plazo && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Plazo
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_moneda"
                                        id="n_moneda"
                                        value={data.consultaData?.body?.info_bd?.n_moneda || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_moneda}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'MONEDA').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_moneda">
                                    Moneda
                                </label>
                                {data.isFieldRequiredLatPanama.n_moneda && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Moneda
                                    </span>
                                )}
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
                                       ref={data.refReqLatPanama.n_monto_colocado}
                                       onFocus={() => handleFocus('n_monto_colocado')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_monto_colocado')}
                                />
                                <label htmlFor="n_monto_colocado">
                                    Monto Colocado
                                </label>
                                {data.isFieldRequiredLatPanama.n_monto_colocado && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Monto Colocado
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_theo_model"
                                        id="n_theo_model"
                                        value={data.consultaData?.body?.info_bd?.n_theo_model || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_theo_model}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'THEO_MODEL').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_theo_model">
                                    Theorical Model
                                </label>
                                {data.isFieldRequiredLatPanama.n_theo_model && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Theorical Model
                                    </span>
                                )}
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
                                       ref={data.refReqLatPanama.n_valor_nominal}
                                       onFocus={() => handleFocus('n_valor_nominal')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_valor_nominal')}
                                />
                                <label htmlFor="n_valor_nominal">
                                    Valor Nominal
                                </label>
                                {data.isFieldRequiredLatPanama.n_valor_nominal && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Valor Nominal
                                    </span>
                                )}
                            </div>
                            <div className="form-input">
                                <input type="text"
                                       name="n_precio_colocacion"
                                       id="n_precio_colocacion"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_precio_colocacion || ""}
                                       onChange={data.handleChange}
                                       onFocus={() => handleFocus('n_precio_colocacion')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_precio_colocacion')}
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
                                       ref={data.refReqLatPanama.n_tasa}
                                       onFocus={() => handleFocus('n_tasa')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_tasa')}
                                />
                                <label htmlFor="tasa">
                                    Tasa
                                </label>
                                {data.isFieldRequiredLatPanama.n_tasa && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Tasa
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_base_calculo"
                                        id="n_base_calculo"
                                        value={data.consultaData?.body?.info_bd?.n_base_calculo || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_base_calculo}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'BASE_CALCULO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_base_calculo">
                                    Base Cálculo
                                </label>
                                {data.isFieldRequiredLatPanama.n_base_calculo && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Base Cálculo
                                    </span>
                                )}
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
                                       ref={data.refReqLatPanama.n_sobretasa}
                                       onFocus={() => handleFocus('n_sobretasa')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_sobretasa')}
                                />
                                <label htmlFor="prima">Prima</label>
                                {data.isFieldRequiredLatPanama.n_sobretasa && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Prima
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_status"
                                        id="n_status"
                                        value={data.consultaData?.body?.info_bd?.n_status || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_status}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'STATUS').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_status">
                                    Status
                                </label>
                                {data.isFieldRequiredLatPanama.n_status && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Status
                                    </span>
                                )}
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
                                       ref={data.refReqLatPanama.s_isin}
                                       onFocus={() => handleFocus('s_isin')}
                                       onBlur={handleBlur}
                                       style={sendStyle('s_isin')}
                                />
                                <label htmlFor="s_isin">
                                    Isin
                                </label>
                                {data.isFieldRequiredLatPanama.s_isin && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Isin
                                    </span>
                                )}
                            </div>
                            <div className="form-date form-date-my">
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
                                       ref={data.refReqLatPanama.n_precio}
                                       onFocus={() => handleFocus('n_precio')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_precio')}
                                />
                                <label htmlFor="n_precio">
                                    Precio
                                </label>
                                {data.isFieldRequiredLatPanama.n_precio && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Precio
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_form_cotizacion"
                                        id="n_form_cotizacion"
                                        value={data.consultaData?.body?.info_bd?.n_form_cotizacion || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_form_cotizacion}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'forma_cotizacion').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_form_cotizacion">
                                    Forma Cotización
                                </label>
                                {data.isFieldRequiredLatPanama.n_form_cotizacion && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Forma Cotización
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_coupon_gen_met"
                                        id="n_coupon_gen_met"
                                        value={data.consultaData?.body?.info_bd?.n_coupon_gen_met || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_coupon_gen_met}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'COUPON_GEN_MET').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_coupon_gen_met">
                                    Generación Cupón
                                </label>
                                {data.isFieldRequiredLatPanama.n_coupon_gen_met && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Generación Cupón
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_odd_last_coupon"
                                        id="n_odd_last_coupon"
                                        value={data.consultaData?.body?.info_bd?.n_odd_last_coupon || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_odd_last_coupon}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'ODD_COUPON').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_odd_last_coupon">
                                    Odd Last Coupon
                                </label>
                                {data.isFieldRequiredLatPanama.n_odd_last_coupon && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Odd Last Coupon
                                    </span>
                                )}
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
                                       ref={data.refReqLatPanama.n_fixed_coupon_date}
                                       onFocus={() => handleFocus('n_fixed_coupon_date')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_fixed_coupon_date')}
                                />
                                <label htmlFor="n_fixed_coupon_date">
                                    Fixed Coupon Date
                                </label>
                                {data.isFieldRequiredLatPanama.n_fixed_coupon_date && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Fixed Coupon Date
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_odd_first_coupon"
                                        id="n_odd_first_coupon"
                                        value={data.consultaData?.body?.info_bd?.n_odd_first_coupon || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_odd_first_coupon}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'ODD_COUPON').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_odd_first_coupon">Odd First Coupon</label>
                                {data.isFieldRequiredLatPanama.n_odd_first_coupon && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Odd First Coupon
                                    </span>
                                )}
                            </div>
                        </div>

                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_pais"
                                        id="n_pais"
                                        value={data.consultaData?.body?.info_bd?.n_pais || "default"}
                                        onChange={data.handleChange}
                                        ref={data.refReqLatPanama.n_pais}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'PAIS').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_pais">País</label>
                                {data.isFieldRequiredLatPanama.n_pais && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido País
                                    </span>
                                )}
                            </div>
                            <div className="form-select">
                                <select name="n_business_day_rule"
                                        id="n_business_day_rule"
                                        value={data.consultaData?.body?.info_bd?.n_business_day_rule || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="default">...</option>
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
                                        ref={data.refReqLatPanama.n_emisor}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'EMISOR').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_emisor">
                                    Emisor
                                </label>
                                {data.isFieldRequiredLatPanama.n_emisor && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Emisor
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="form-cols-2 -my-3">
                            <div className="form-select">
                                <select name="n_fitch"
                                        id="n_fitch"
                                        value={data.consultaData?.body?.info_bd?.n_fitch || "default"}
                                        onChange={data.handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'FITCH').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="fitch">Fitch</label>
                            </div>
                            <div className="form-date form-date-my">
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
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'MOODY').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_moody">Moody</label>
                            </div>
                            <div className="form-date form-date-my">
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
                                    <option value="default">...</option>
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
                            <div className="form-date form-date-my">
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
                            <div className="form-date form-date-my">
                                <input type="date"
                                       name="d_fecha_ingreso_titulo"
                                       id="d_fecha_ingreso_titulo"
                                       value={data.consultaData?.body?.info_bd?.d_fecha_ingreso_titulo || ""}
                                       onChange={data.handleChange}
                                       ref={data.refReqLatPanama.d_fecha_ingreso_titulo}
                                />
                                <label htmlFor="d_fecha_ingreso_titulo">
                                    Fecha Ingreso Título
                                </label>
                                {data.isFieldRequiredLatPanama.d_fecha_ingreso_titulo && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Fecha Ingreso Título
                                    </span>
                                )}
                            </div>
                            <div className="form-date form-date-my">
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
                                        ref={data.refReqLatPanama.n_crv_index}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(data.catalog, 'CRV_INDEX').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_crv_index">
                                    Curve Index
                                </label>
                                {data.isFieldRequiredLatPanama.n_crv_index && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Curve Index
                                    </span>
                                )}
                            </div>
                            <div className="form-input">
                                <input type="text"
                                       name="n_soy"
                                       id="n_soy"
                                       placeholder=""
                                       value={data.consultaData?.body?.info_bd?.n_soy || ""}
                                       onChange={data.handleChange}
                                       onFocus={() => handleFocus('n_soy')}
                                       onBlur={handleBlur}
                                       style={sendStyle('n_soy')}
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
                                           onFocus={() => handleFocus('currency')}
                                           onBlur={handleBlur}
                                           style={sendStyle('currency')}
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
                                           disabled
                                           onFocus={() => handleFocus('notional')}
                                           onBlur={handleBlur}
                                           style={sendStyle('notional')}
                                           />
                                    <label htmlFor="notional">Notional</label>
                                </div>
                            </div>
                            <div className="form-cols-1 -my-3">
                                <div className="form-date form-date-my">
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
                                <div className="form-date form-date-my">
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
                                           disabled
                                           onFocus={() => handleFocus('Discount Courve')}
                                           onBlur={handleBlur}
                                           style={sendStyle('Discount Courve')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('term')}
                                           onBlur={handleBlur}
                                           style={sendStyle('term')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('couponGenerationMethod')}
                                           onBlur={handleBlur}
                                           style={sendStyle('couponGenerationMethod')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('stateProcedure')}
                                           onBlur={handleBlur}
                                           style={sendStyle('stateProcedure')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('couponRate')}
                                           onBlur={handleBlur}
                                           style={sendStyle('couponRate')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('spotPrice')}
                                           onBlur={handleBlur}
                                           style={sendStyle('spotPrice')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('couponProrated')}
                                           onBlur={handleBlur}
                                           style={sendStyle('couponProrated')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('businessDayRule')}
                                           onBlur={handleBlur}
                                           style={sendStyle('businessDayRule')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('spreadOverYield')}
                                           onBlur={handleBlur}
                                           style={sendStyle('spreadOverYield')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('theoricalModel')}
                                           onBlur={handleBlur}
                                           style={sendStyle('theoricalModel')}
                                           />
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
                                           disabled
                                           onFocus={() => handleFocus('marketModel')}
                                           onBlur={handleBlur}
                                           style={sendStyle('marketModel')}
                                           />
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