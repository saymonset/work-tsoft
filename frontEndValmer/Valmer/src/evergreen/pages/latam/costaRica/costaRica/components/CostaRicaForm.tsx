import {BarLoader} from "react-spinners";
import {getCatalogs} from "../../../../../../utils";
import React from "react";
import {CostaRicaFormProps} from "../../../../../../model";
import { useBigInput } from "../../../../deuda/tasas/components/forms/hooks/useBigInput";

export const CostaRicaForm = ({
                                  loadingConsultaInfo,
                                  consultaData,
                                  mergeInstrumentos,
                                  handleChange,
                                  isFieldRequired,
                                  requeridos,
                                  catalog}: CostaRicaFormProps) => {

        //  Achica o agranda el input del form cuando obtiene o deja el focus
        const {  handleFocus,
            handleBlur,
            sendStyle} = useBigInput();                                
    const infoRwObject = consultaData?.body?.info_rw?.Acciones?.[mergeInstrumentos];
    const hasInfoRwData = infoRwObject && Object.keys(infoRwObject).length > 0;

    return (
        <>
            {loadingConsultaInfo && <BarLoader className="w-full ml-2 mt-2" color="#059669" width={500} />}

            <div className="form mt-2 animate__animated animate__fadeIn">
                <form>
                    <div className="form-cols-3 flex items-start">
                        <div className="form-cols-1 col-span-2">
                            <span className="form-title">Características</span>
                            <div className="form-cols-2 -my-3">
                                <div className="form-date  form-date-my">
                                    <input
                                        type="date"
                                        name="d_fecha_emision"
                                        value={consultaData?.body?.info_bd?.d_fecha_emision ?? ""}
                                        onChange={handleChange}
                                    />
                                    <label htmlFor="d_fecha_emision">Fecha Emisión</label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_isin"
                                        value={consultaData?.body?.info_bd?.s_isin ?? ""}
                                        onChange={handleChange}
                                        ref={requeridos.s_isin}
                                        onFocus={() => handleFocus('s_isin')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_isin')}
                                    />
                                    <label htmlFor="s_isin">Isin</label>
                                    {isFieldRequired.s_isin && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Isin
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-date form-date-my">
                                    <input
                                        type="date"
                                        name="d_fecha_vencimiento"
                                        value={consultaData?.body?.info_bd?.d_fecha_vencimiento ?? ""}
                                        onChange={handleChange}
                                    />
                                    <label htmlFor="d_fecha_vencimiento">Fecha Vencimiento</label>
                                </div>
                                <div className="form-select">
                                    <select name="n_tipo_tasa"
                                            value={consultaData?.body?.info_bd?.n_tipo_tasa ?? "default"}
                                            onChange={handleChange}
                                            ref={requeridos.n_tipo_tasa}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'TIPO_TASA').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_tipo_tasa">
                                        Tipo Tasa
                                    </label>
                                    {isFieldRequired.n_tipo_tasa && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Tipo Tasa
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-date form-date-my">
                                    <input
                                        type="date"
                                        name="d_fec_ini_cupon"
                                        value={consultaData?.body?.info_bd?.d_fec_ini_cupon ?? ""}
                                        onChange={handleChange}
                                    />
                                    <label htmlFor="fechaInicioCupon">Fecha Inicio Cupón</label>
                                </div>
                                <div className="form-select">
                                    <select name="n_periodicidad"
                                            id="n_periodicidad"
                                            value={consultaData?.body?.info_bd?.n_periodicidad ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'PERIODICIDAD').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="periodicidad">
                                        Periodicidad
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-date form-date-my">
                                    <input
                                        type="date"
                                        name="d_fecha_pago_interes"
                                        value={consultaData?.body?.info_bd?.d_fecha_pago_interes ?? ""}
                                        onChange={handleChange}
                                    />
                                    <label htmlFor="fechaPagoInteres">Fecha Pago Interés</label>
                                </div>
                                <div className="form-select">
                                    <select name="n_tipo_accion"
                                            value={consultaData?.body?.info_bd?.n_tipo_accion ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'TIPO_ACCION').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="tipoAccion">
                                        Tipo Acción
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_calendar_adjust"
                                            value={consultaData?.body?.info_bd?.n_calendar_adjust ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'calendar').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_calendar_adjust">Calendar Adjust</label>
                                </div>
                                <div className="form-select">
                                    <select name="n_tipo_merc"
                                            value={consultaData?.body?.info_bd?.n_tipo_merc ?? "default"}
                                            onChange={handleChange}
                                            ref={requeridos.n_tipo_merc}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'TIPO_MERCADO').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_tipo_merc">
                                        Tipo Mercado
                                    </label>
                                    {isFieldRequired.n_tipo_merc && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Tipo Mercado
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_coupon_gm"
                                            value={consultaData?.body?.info_bd?.n_coupon_gm ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'COUPON_GEN_METHOD').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_coupon_gm">
                                        Coupon Gen Method
                                    </label>
                                </div>
                                <div className="form-select">
                                    <select name="n_subfamilia"
                                            value={consultaData?.body?.info_bd?.n_subfamilia ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'SUBFAMILIA').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_subfamilia">
                                        Subfamilia
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_fixed_coupon_date"
                                        value={consultaData?.body?.info_bd?.n_fixed_coupon_date ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_fixed_coupon_date')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_fixed_coupon_date')}
                                    />
                                    <label htmlFor="n_fixed_coupon_date">
                                        Fixed Coupon Date
                                    </label>
                                </div>
                                <div className="form-select">
                                    <select name="n_tipo_emision"
                                            value={consultaData?.body?.info_bd?.n_tipo_emision ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'TIPO_EMISION').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_tipo_emision">
                                        Tipo Emisión
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-date form-date-my">
                                    <input
                                        type="date"
                                        name="d_last_reg_coup_date"
                                        value={consultaData?.body?.info_bd?.d_last_reg_coup_date ?? ""}
                                        onChange={handleChange}
                                    />
                                    <label htmlFor="d_last_reg_coup_date">
                                        Last Regular Coupon Date
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_last_reset_rate"
                                        value={consultaData?.body?.info_bd?.n_last_reset_rate ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_last_reset_rate')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_last_reset_rate')}
                                    />
                                    <label htmlFor="n_last_reset_rate">
                                        Last Reset Rate
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_odd_first_coupon"
                                            value={consultaData?.body?.info_bd?.n_odd_first_coupon ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'ODD_COUPON').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_odd_first_coupon">
                                        Odd First Coupon
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_premio"
                                        value={consultaData?.body?.info_bd?.n_premio ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_premio')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_premio')}
                                    />
                                    <label htmlFor="n_premio">
                                        Premio
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_odd_last_coupon"
                                            value={consultaData?.body?.info_bd?.n_odd_last_coupon ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'ODD_COUPON').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_odd_last_coupon">
                                        Odd Last Coupon
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_sobretasa"
                                        value={consultaData?.body?.info_bd?.n_sobretasa ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_sobretasa')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_sobretasa')}
                                    />
                                    <label htmlFor="n_sobretasa">
                                        Sobretasa
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="s_coupon_prorated"
                                            value={consultaData?.body?.info_bd?.s_coupon_prorated ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'coupon-prorated').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="s_coupon_prorated">
                                        Coupon Prorated
                                    </label>
                                </div>
                                <div className="form-select">
                                    <select name="n_discount_curve"
                                            value={consultaData?.body?.info_bd?.n_discount_curve ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'DISCOUNT_CURVE').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="discountCurve">Discount Curve</label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_moneda"
                                            value={consultaData?.body?.info_bd?.n_moneda ?? "default"}
                                            onChange={handleChange}
                                            ref={requeridos.n_moneda}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'MONEDA').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_moneda">
                                        Moneda
                                    </label>
                                    {isFieldRequired.n_moneda && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Moneda
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_curve_index"
                                            value={consultaData?.body?.info_bd?.n_curve_index ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'CURVE_INDEX').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_curve_index">
                                        Curve Index
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_plazo"
                                        value={consultaData?.body?.info_bd?.n_plazo ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_plazo')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_plazo')}
                                    />
                                    <label htmlFor="n_plazo">
                                        Plazo
                                    </label>
                                </div>
                                <div className="form-select">
                                    <select name="n_edo_instrumento"
                                            value={consultaData?.body?.info_bd?.n_edo_instrumento ?? "default"}
                                            onChange={handleChange}
                                            ref={requeridos.n_edo_instrumento}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'EDO_INSTRUMENTO').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_edo_instrumento">
                                        Estado Instrumento
                                    </label>
                                    {isFieldRequired.n_edo_instrumento && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Estado Instrumento
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_monto_colocado"
                                        value={consultaData?.body?.info_bd?.n_monto_colocado ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_monto_colocado')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_monto_colocado')}
                                        ref={requeridos.n_monto_colocado}
                                    />
                                    <label htmlFor="n_monto_colocado">
                                        Monto Colocado
                                    </label>
                                    {isFieldRequired.n_monto_colocado && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Monto Colocado
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_pais"
                                            value={consultaData?.body?.info_bd?.n_pais ?? "default"}
                                            onChange={handleChange}
                                            ref={requeridos.n_pais}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'PAIS').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="pais">
                                        País
                                    </label>
                                    {isFieldRequired.n_pais && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido País
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_monto_aprobado"
                                        value={consultaData?.body?.info_bd?.n_monto_aprobado ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_monto_aprobado')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_monto_aprobado')}
                                        ref={requeridos.n_monto_aprobado}
                                    />
                                    <label htmlFor="n_monto_aprobado">
                                        Monto Aprobado
                                    </label>
                                    {isFieldRequired.n_monto_aprobado && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Monto Aprobado
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_emisor"
                                            value={consultaData?.body?.info_bd?.n_emisor ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'EMISOR').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="emisor">Emisor</label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_tasa_minima"
                                        value={consultaData?.body?.info_bd?.n_tasa_minima ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_tasa_minima')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_tasa_minima')}
                                    />
                                    <label htmlFor="n_tasa_minima">
                                        Tasa Mínima
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_valor_nominal"
                                        value={consultaData?.body?.info_bd?.n_valor_nominal ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_valor_nominal')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_valor_nominal')}
                                    />
                                    <label htmlFor="n_valor_nominal">
                                        Valor Nominal
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_tasa_maxima"
                                        value={consultaData?.body?.info_bd?.n_tasa_maxima ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_tasa_maxima')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_tasa_maxima')}
                                    />
                                    <label htmlFor="n_tasa_maxima">
                                        Tasa Máxima
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_spot_price"
                                        value={consultaData?.body?.info_bd?.n_spot_price ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_spot_price')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_spot_price')}
                                    />
                                    <label htmlFor="n_spot_price">
                                        Spot Price
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_clasif_general"
                                            value={consultaData?.body?.info_bd?.n_clasif_general ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'CLASIF_GENERAL').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_clasif_general">
                                        Clasif General
                                    </label>
                                </div>
                                <div className="form-select">
                                    <select name="n_base_calculo"
                                            value={consultaData?.body?.info_bd?.n_base_calculo ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'BASE_CALCULO').map((column) => (
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
                                <div className="form-select">
                                    <select name="n_tipo_diversificacion"
                                            value={consultaData?.body?.info_bd?.n_tipo_diversificacion ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'TIPO_DIVERSIFICACION').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="tipoDiversif">
                                        Tipo Diversificación
                                    </label>
                                </div>
                                <div className="form-select">
                                    <select name="n_theoretical_model"
                                            value={consultaData?.body?.info_bd?.n_theoretical_model ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'THEORETICAL_MODEL').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_theoretical_model">
                                        Theorical Model
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_tipo_valor"
                                            value={consultaData?.body?.info_bd?.n_tipo_valor ?? "default"}
                                            onChange={handleChange}
                                            ref={requeridos.n_tipo_valor}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'TIPO_VALOR').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_tipo_valor">Tipo Valor</label>
                                    {isFieldRequired.n_tipo_valor && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Tipo Valor
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_market_model"
                                            value={consultaData?.body?.info_bd?.n_market_model ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog,'MARKET_MODEL').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="marketModel">
                                        Market Model
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_tipo_fondo"
                                            id="n_tipo_fondo"
                                            disabled={true}>
                                        <option value="default">...</option>
                                    </select>
                                    <label htmlFor="tipoFondo">Tipo de Fondo</label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_factor_impuesto"
                                        value={consultaData?.body?.info_bd?.n_factor_impuesto ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_factor_impuesto')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_factor_impuesto')}
                                    />
                                    <label htmlFor="factorImpuesto">
                                        Factor Impuesto
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_tasa_cupon_piso"
                                        value={consultaData?.body?.info_bd?.n_tasa_cupon_piso ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_tasa_cupon_piso')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_tasa_cupon_piso')}
                                    />
                                    <label htmlFor="n_tasa_cupon_piso">
                                        Tasa de Cupón Piso
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="n_tasa_cupon_techo"
                                        value={consultaData?.body?.info_bd?.n_tasa_cupon_techo ?? ""}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('n_tasa_cupon_techo')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_tasa_cupon_techo')}
                                    />
                                    <label htmlFor="n_tasa_cupon_techo">Tasa de Cupón Techo</label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-date form-date-my">
                                    <input
                                        type="date"
                                        name="d_fec_pri_dia_val"
                                        value={consultaData?.body?.info_bd?.d_fec_pri_dia_val ?? ""}
                                        onChange={handleChange}
                                    />
                                    <label htmlFor="d_fec_pri_dia_val">
                                        Fecha Incorporación
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div className="form-cols-1">
                            <span className="form-title">RiskWatch</span>

                            {hasInfoRwData && (
                                <>
                                    <div className="form-cols-1 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="Common Stock"
                                                   value={consultaData?.body?.info_rw?.Acciones?.
                                                       [mergeInstrumentos]?.
                                                       ["Common Stock"] ?? ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('CommonStock')}
                                                   onBlur={handleBlur}
                                                   style={sendStyle('CommonStock')}
                                            />
                                            <label htmlFor="Common Stock">
                                                Common Stock
                                            </label>
                                        </div>
                                    </div>
                                    <div className="form-cols-1 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="Name"
                                                   value={consultaData?.body?.info_rw?.Acciones?.
                                                       [mergeInstrumentos]?.
                                                       ["Name"] ?? ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('Name')}
                                                   onBlur={handleBlur}
                                                   style={sendStyle('Name')}
                                                   />
                                            <label htmlFor="name">
                                                Name
                                            </label>
                                        </div>
                                    </div>
                                    <div className="form-cols-1 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="Spot Price"
                                                   value={consultaData?.body?.info_rw?.Acciones?.
                                                       [mergeInstrumentos]?.
                                                       ["Spot Price"] ?? ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('Spot Price')}
                                                   onBlur={handleBlur}
                                                   style={sendStyle('Spot Price')}
                                                   />
                                            <label htmlFor="Spot Price">
                                                Spot Price
                                            </label>
                                        </div>
                                    </div>
                                    <div className="form-cols-1 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="*Theoretical Model"
                                                   value={consultaData?.body?.info_rw?.Acciones?.
                                                       [mergeInstrumentos]?.
                                                       ["*Theoretical Model"] ?? ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('Theoretical Model')}
                                                   onBlur={handleBlur}
                                                   style={sendStyle('Theoretical Model')}
                                                   />
                                            <label htmlFor="*Theoretical Model">
                                                Theorical Model
                                            </label>
                                        </div>
                                    </div>
                                    <div className="form-cols-1 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="Currency"
                                                   value={consultaData?.body?.info_rw?.Acciones?.
                                                       [mergeInstrumentos]?.
                                                       ["Currency"] ?? ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('Currency')}
                                                   onBlur={handleBlur}
                                                   style={sendStyle('Currency')}
                                                   />
                                            <label htmlFor="Currency">
                                                Currency
                                            </label>
                                        </div>
                                    </div>
                                    <div className="form-cols-1 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="Calendar Adjust"
                                                   value={consultaData?.body?.info_rw?.Acciones?.
                                                       [mergeInstrumentos]?.
                                                       ["Calendar Adjust"] ?? ""}
                                                   readOnly/>
                                            <label htmlFor="Calendar Adjust">
                                                Calendar Adjust
                                            </label>
                                        </div>
                                    </div>
                                </>
                            )}


                            <span className="form-title">Calificaciones</span>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_fitch"
                                            value={consultaData?.body?.info_bd?.n_fitch ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'FITCH').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_fitch">Fitch CR</label>
                                </div>
                                <div className="form-select">
                                    <select name="s_fitch_riesgomerc"
                                            value={consultaData?.body?.info_bd?.s_fitch_riesgomerc ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'FITCH_RiesgoMerc').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="s_fitch_riesgomerc">
                                        Fitch RiesgoMerc CR
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_scr"
                                            value={consultaData?.body?.info_bd?.n_scr ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'SCR').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_scr">SCR CR</label>
                                </div>
                                <div className="form-select">
                                    <select name="n_scr_riesgomerc"
                                            value={consultaData?.body?.info_bd?.n_scr_riesgomerc ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'SCR_RiesgoMerc').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_scr_riesgomerc">SCR RiesgoMerc CR</label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_pcr"
                                            value={consultaData?.body?.info_bd?.n_pcr ?? "default"}
                                            onChange={handleChange}
                                    >
                                    <option value="default">...</option>
                                        {getCatalogs(catalog, 'PCR').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_pcr">PCR CR</label>
                                </div>
                                <div className="form-select">
                                    <select name="s_pcr_riesgomerc"
                                            value={consultaData?.body?.info_bd?.s_pcr_riesgomerc ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'PCR_RiesgoMerc').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="s_pcr_riesgomerc">PCR RiesgoMerc CR</label>
                                </div>
                            </div>
                            <div className="line"></div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_sp_i"
                                            value={consultaData?.body?.info_bd?.n_sp_i ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'SP_I').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_sp_i">S P & I</label>
                                </div>
                                <div className="form-date form-date-my">
                                    <input type="date"
                                           name="d_fecha_sp_i"
                                           value={consultaData?.body?.info_bd?.d_fecha_sp_i ?? ""}
                                           onChange={handleChange}
                                    />
                                    <label htmlFor="d_fecha_sp_i">
                                        Fecha S P & I
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_moodys_i"
                                            value={consultaData?.body?.info_bd?.n_moodys_i ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'MOODYS_I').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_moodys_i">Moodys I</label>
                                </div>
                                <div className="form-date form-date-my">
                                    <input type="date"
                                           name="d_fecha_moodys_i"
                                           value={consultaData?.body?.info_bd?.d_fecha_moodys_i ?? ""}
                                           onChange={handleChange}
                                    />
                                    <label htmlFor="d_fecha_moodys_i">
                                        Fecha Moodys I
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2 -my-3">
                                <div className="form-select">
                                    <select name="n_fitch_i"
                                            value={consultaData?.body?.info_bd?.n_fitch_i ?? "default"}
                                            onChange={handleChange}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'FITCH_I').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_fitch_i">
                                        Fitch I
                                    </label>
                                </div>
                                <div className="form-date form-date-my">
                                    <input type="date"
                                           name="d_fecha_fitch_i"
                                           value={consultaData?.body?.info_bd?.d_fecha_fitch_i ?? "default"}
                                           onChange={handleChange}
                                    />
                                    <label htmlFor="d_fecha_fitch_i">
                                        Fecha Fitch I
                                    </label>
                                </div>
                            </div>
                            <span className="form-title">Factor de Liquidez</span>
                            <div className="form-cols-1 -my-3">
                                <div className="form-input">
                                    <input type="text"
                                           name="n_fac_liqui"
                                           value={consultaData?.body?.info_bd?.n_fac_liqui ?? "default"}
                                           readOnly
                                           onFocus={() => handleFocus('n_fac_liqui')}
                                           onBlur={handleBlur}
                                           style={sendStyle('n_fac_liqui')}
                                                   />
                                    <label htmlFor="n_fac_liqui">
                                        Factor Liquidez
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </>
    )
}