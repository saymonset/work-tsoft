import {useDerHandleData} from "./hooks";
import {MoonLoader} from "react-spinners";
import {TvEmiSerieDer} from "./components";
import {generateUUID, getCatalogs} from "../../../../../utils";
import {ButtonContent} from "../../../../../shared";
import React from "react";
import { useBigInput } from "../../../deuda/tasas/components/forms/hooks/useBigInput";

interface TeoricosChicagoProps {
    isChicago: boolean,
    sMercado: string,
    queryDataUrl: string
    urlSaveData: string
}
export const DerivadosForm = ({isChicago, sMercado, queryDataUrl, urlSaveData}: TeoricosChicagoProps) => {

      //  Achica o agranda el input del form cuando obtiene o deja el focus
      const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
    const {
        tv,
        loadingPersist,
        loading,
        loadingTv,
        catalog,
        consultaDataDer,
        fieldRequiredDerivados,
        requeridosDer,
        setConsultaDataDer,
        handleChange,
        handleRowClick,
        handleSubmit
    } = useDerHandleData({sMercado: sMercado, urlSaveData: urlSaveData})

    const isValidNumber = (value: any) => {
        return !isNaN(parseFloat(value)) && isFinite(value);
    }
    
    if (loading || loadingTv || !catalog.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading || loadingTv ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="form-cols-5">
                <div className="form col-span-3">

                    <TvEmiSerieDer tv={tv}
                                   sMercado={sMercado}
                                   urlConsultaData={queryDataUrl}
                                   requeridos={requeridosDer}
                                   handleChange={handleChange}
                                   setConsultaDataDer={setConsultaDataDer}/>

                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                name="n_tipo_instrumento"
                                value={consultaDataDer?.body?.n_tipo_instrumento  || 'default'}
                                onChange={handleChange}
                                ref={requeridosDer.n_tipo_instrumento}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'TIPO_INSTRUMENTO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_tipo_instrumento">
                                Tipo Instrumento 
                            </label>
                            {fieldRequiredDerivados.n_tipo_instrumento && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Tipo Instrumento</span>
                            )}
                        </div>

                        { sMercado !== "TEORICOS" && (
                            <>
                                <div className="form-select">
                                    <select
                                        name="n_tipo_mercado"
                                        value={consultaDataDer?.body?.n_tipo_mercado ?? 'default'}
                                        onChange={handleChange}
                                        ref={requeridosDer.n_tipo_mercado}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'TIPO_MERCADO').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_tipo_mercado">
                                        Tipo Mercado
                                    </label>
                                    {fieldRequiredDerivados.n_tipo_mercado && (
                                        <span className="fontError animate__animated animate__fadeIn">Campo requerido Tipo Mercado</span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select
                                        name="n_pais"
                                        value={consultaDataDer?.body?.n_pais ?? 'default'}
                                        onChange={handleChange}
                                        ref={requeridosDer.n_pais}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'PAIS').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_pais">
                                        País
                                    </label>
                                    {fieldRequiredDerivados.n_pais && (
                                        <span className="fontError animate__animated animate__fadeIn">Campo requerido País</span>
                                    )}
                                </div>
                            </>
                        )}

                        <div className="form-select">
                            <select
                                name="n_fam_instrumento"
                                value={consultaDataDer?.body?.n_fam_instrumento || 'default'}
                                onChange={handleChange}
                                ref={requeridosDer.n_fam_instrumento}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'FAMILIA_INSTRUMENTO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_fam_instrumento">
                                Familia Instrumento
                            </label>
                            {fieldRequiredDerivados.n_fam_instrumento && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Familia Instrumento</span>
                            )}
                        </div>
                    </div>
                    <div className="form-title">
                        <span>Caracteristicas</span>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-date form-date-my">
                            <input
                                type="date"
                                name="d_fecha_vto"
                                value={consultaDataDer?.body?.d_fecha_vto || ''}
                                onChange={handleChange}
                            />
                            <label htmlFor="d_fecha_vto">
                                Fecha Vencimiento
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_moneda"
                                value={consultaDataDer?.body?.n_moneda || 'default'}
                                onChange={handleChange}
                                ref={requeridosDer.n_moneda}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'MONEDA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_moneda">
                                Moneda
                            </label>
                            {fieldRequiredDerivados.n_moneda && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Moneda</span>
                            )}
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                name="n_crv_descuento"
                                value={consultaDataDer?.body?.n_crv_descuento || 'default'}
                                onChange={handleChange}
                                ref={requeridosDer.n_crv_descuento}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CRV_DESCUENTO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_crv_descuento">
                                Curva Descuento
                            </label>
                            {fieldRequiredDerivados.n_crv_descuento && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Curva Descuento</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_valor_nominal"
                                value={consultaDataDer?.body?.n_valor_nominal || ""}
                                onChange={handleChange}
                                ref={requeridosDer.n_valor_nominal}
                                placeholder=""
                                onFocus={() => handleFocus('n_valor_nominal')}
                                onBlur={handleBlur}
                                style={sendStyle('n_valor_nominal')}
                            />
                            <label htmlFor="n_valor_nominal">
                                Valor Nominal
                            </label>
                            {fieldRequiredDerivados.n_valor_nominal && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Valor Nominal</span>
                            )}
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                name="n_composicion_yield"
                                value={consultaDataDer?.body?.n_composicion_yield || 'default'}
                                onChange={handleChange}
                                ref={requeridosDer.n_composicion_yield}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'COMPOSICION_YIELD').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_composicion_yield">
                                Composicion Yield
                            </label>
                            {fieldRequiredDerivados.n_composicion_yield && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Composicion Yield</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_tipo_tasa"
                                value={consultaDataDer?.body?.n_tipo_tasa || 'default'}
                                onChange={handleChange}
                                ref={requeridosDer.n_tipo_tasa}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'TIPO_TASA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_tipo_tasa">
                                Tipo Tasa
                            </label>
                            {fieldRequiredDerivados.n_tipo_tasa && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Tipo Tasa</span>
                            )}
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                name="n_convencion_dias"
                                value={consultaDataDer?.body?.n_convencion_dias || 'default'}
                                onChange={handleChange}
                                ref={requeridosDer.n_convencion_dias}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CONVENCION_DIAS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_convencion_dias">
                                Convencion Dias
                            </label>
                            {fieldRequiredDerivados.n_convencion_dias && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Convencion Dias</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_calendario"
                                value={consultaDataDer?.body?.n_calendario || 'default'}
                                onChange={handleChange}
                                ref={requeridosDer.n_calendario}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CALENDARIO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="curvaDescuento">
                                Calendario
                            </label>
                            {fieldRequiredDerivados.n_calendario && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido Calendario</span>
                            )}
                        </div>
                        {sMercado === "TEORICOS" && (
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="n_tasa_cupon"
                                    value={consultaDataDer?.body?.n_tasa_cupon ?? ""}
                                    onChange={handleChange}
                                    placeholder=""
                                    ref={requeridosDer.n_tasa_cupon}
                                    onFocus={() => handleFocus('n_tasa_cupon')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tasa_cupon')}
                                />
                                <label htmlFor="n_tasa_cupon">
                                    Tasa Cupón
                                </label>
                                {fieldRequiredDerivados.n_tasa_cupon && (
                                    <span className="fontError animate__animated animate__fadeIn">Campo requerido Tasa Cupón</span>
                                )}
                            </div>
                        )}
                    </div>
                    { isChicago && (
                        <>
                            <div className="form-title">
                                <span>Instrumentos Listados</span>
                            </div>
                            <div className="mb-8 flex flex-col mt-3">
                                <table className="table">
                                    <thead className="thead">
                                    <tr>
                                        <th>CONSECUTIVO</th>
                                        <th>FECHA VTO</th>
                                        <th>PRECIO HOY</th>
                                        <th>PRECIO AYER</th>
                                        <th>MONTO EXPOS HOY</th>
                                        <th>MONTO EXPOS AYER</th>
                                    </tr>
                                    </thead>
                                    <tbody className="tbody">
                                    {
                                        consultaDataDer.body?.LIST && consultaDataDer.body.LIST.length > 0 &&
                                        consultaDataDer.body.LIST.map((item) => {
                                            return(
                                                <tr key={generateUUID()} className="tr">
                                                    <td>
                                                        <button
                                                            onClick={() => handleRowClick(item)}
                                                            onKeyDown={(e) => {
                                                                if (e.key === 'Enter' || e.key === ' ')
                                                                {
                                                                    handleRowClick(item);
                                                                    e.preventDefault();
                                                                }
                                                            }}
                                                            tabIndex={0}
                                                        >
                                                            { item.property }
                                                        </button>
                                                    </td>
                                                    <td>{ item.data.FEC_VTO }</td>
                                                    <td>{isValidNumber(item.data.PRECIO_HOY) ? item.data.PRECIO_HOY : '0'}</td>
                                                    <td>{isValidNumber(item.data.PRECIO_AYER) ? item.data.PRECIO_AYER : '0'}</td>
                                                    <td>{isValidNumber(item.data.MONTO_EXPOS_HOY) ? item.data.MONTO_EXPOS_HOY : '0'}</td>
                                                    <td>{isValidNumber(item.data.MONTO_EXPOS_AYER) ? item.data.MONTO_EXPOS_AYER : '0'}</td>
                                                </tr>
                                            )
                                        })
                                    }
                                    </tbody>
                                </table>
                            </div>

                            <div className="form-cols-2">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_consec"
                                        value={consultaDataDer?.body?.s_consec ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('s_consec')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_consec')}
                                    />
                                    <label htmlFor="s_consec">
                                        Consecutivo
                                    </label>
                                </div>
                                <div className="form-date">
                                    <input
                                        type="date"
                                        name="consec_fecha_vto"
                                        value={consultaDataDer?.body?.consec_fecha_vto ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                    />
                                    <label htmlFor="consec_fecha_vto">
                                        Fecha Vencimiento
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="consec_precio_hoy"
                                        value={consultaDataDer?.body?.consec_precio_hoy ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('consec_precio_hoy')}
                                        onBlur={handleBlur}
                                        style={sendStyle('consec_precio_hoy')}
                                    />
                                    <label htmlFor="consec_precio_hoy">
                                        Precio Hoy
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="consec_precio_ayer"
                                        value={consultaDataDer?.body?.consec_precio_ayer ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('consec_precio_ayer')}
                                        onBlur={handleBlur}
                                        style={sendStyle('consec_precio_ayer')}
                                    />
                                    <label htmlFor="consec_precio_ayer">
                                        Precio Ayer
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="consec_monto_ex_h"
                                        value={consultaDataDer?.body?.consec_monto_ex_h ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('consec_monto_ex_h')}
                                        onBlur={handleBlur}
                                        style={sendStyle('consec_monto_ex_h')}
                                    />
                                    <label htmlFor="consec_monto_ex_h">
                                        Monto Exposición Hoy
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="consec_monto_ex_a"
                                        value={consultaDataDer?.body?.consec_monto_ex_a ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('consec_monto_ex_a')}
                                        onBlur={handleBlur}
                                        style={sendStyle('consec_monto_ex_a')}
                                    />
                                    <label htmlFor="consec_monto_ex_a">
                                        Monto Exposición Ayer
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-1">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="consec_under_fin_ent"
                                        value={consultaDataDer?.body?.consec_under_fin_ent ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('consec_under_fin_ent')}
                                        onBlur={handleBlur}
                                        style={sendStyle('consec_under_fin_ent')}
                                    />
                                    <label htmlFor="consec_under_fin_ent">
                                        Underlying Fin Ent
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-1">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="consec_compon_weights"
                                        value={consultaDataDer?.body?.consec_compon_weights ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('consec_compon_weights')}
                                        onBlur={handleBlur}
                                        style={sendStyle('consec_compon_weights')}
                                    />
                                    <label htmlFor="consec_compon_weights">
                                        Component Weights
                                    </label>
                                </div>
                            </div>
                        </>
                    )}
                </div>
                <div className="form col-span-2">
                    <div className="form-title">Risk Watch</div>

                    {sMercado === "TEORICOS" ? (
                        <>
                            <div className="form-select">
                                <select
                                    name="n_busdayrule"
                                    value={consultaDataDer?.body?.n_busdayrule ?? "default"}
                                    onChange={handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(catalog,'BUSINESS_RESET_RULE').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_busdayrule">
                                    Business Day Rule
                                </label>
                            </div>
                            <div className="form-select">
                                <select
                                    name="b_couponpro"
                                    value={consultaDataDer?.body?.b_couponpro ?? "default"}
                                    onChange={handleChange}
                                >
                                    <option value="">...</option>
                                    <option value="0">False</option>
                                    <option value="1">True</option>
                                </select>
                                <label htmlFor="b_couponpro">
                                    Coupon Pro
                                </label>
                            </div>
                            <div className="form-cols-1">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_coupgenmthd"
                                        value={consultaDataDer?.body?.s_coupgenmthd ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('s_coupgenmthd')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_coupgenmthd')}
                                    />
                                    <label htmlFor="s_coupgenmthd">
                                        Coup Generation Method
                                    </label>
                                </div>
                            </div>
                        </>

                    ) : (
                        <>
                            <div className="form-cols-1">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_dsc_curve_und"
                                        value={consultaDataDer?.body?.s_dsc_curve_und ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('s_dsc_curve_und')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_dsc_curve_und')}
                                    />
                                    <label htmlFor="s_dsc_curve_und">
                                        Discount Curve Und
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-1">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_foreign_curve"
                                        value={consultaDataDer?.body?.s_foreign_curve ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('s_foreign_curve')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_foreign_curve')}
                                    />
                                    <label htmlFor="s_foreign_curve">
                                        Foreign Curve
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-1">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_volatilidad"
                                        value={consultaDataDer?.body?.s_volatilidad ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        onFocus={() => handleFocus('s_volatilidad')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_volatilidad')}
                                    />
                                    <label htmlFor="s_volatilidad">
                                        Volatilidad
                                    </label>
                                </div>
                            </div>
                        </>
                    )}

                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="n_theo_model"
                                value={consultaDataDer?.body?.n_theo_model || "default"}
                                onChange={handleChange}
                            >
                                <option value="">...</option>
                                {getCatalogs(catalog,'THEO_MODEL').map((column) => (
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
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="n_market_model"
                                value={consultaDataDer?.body?.n_market_model || "default"}
                                onChange={handleChange}
                            >
                                <option value="">...</option>
                                {getCatalogs(catalog,'MARKET_MODEL').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_market_model">
                                Market Model
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div className="my-4 mt-12">
                <button className="btn">
                    <ButtonContent name="Guardar" loading={loadingPersist}/>
                </button>
            </div>
        </form>
    )
};