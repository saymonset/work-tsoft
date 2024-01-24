import React from "react";
import {useHandleDataCorp, useTvEmiSerieDataCorp} from "./hooks";
import {BarLoader, MoonLoader} from "react-spinners"
import {ButtonContent, TvEmiSerieOptions} from "../../../../../../../../shared";
import {generateUUID, getCatalogs} from "../../../../../../../../utils";
import { useBigInput } from "../../../../../../../../utils";

export const InstrumentoForm = ({requeridos}: any) => {

      //  Achica o agranda el input del form cuando obtiene o deja el focus
      const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
        
    const {
        fieldRequiredCorp, triggerErase, catalog,
        loading, tv, loadingTv, selectedTv,
        selectedEmisora, selectedSerie, emisorasRef1,
        emisorasRef2, serieRef1, serieRef2,
        loadingEmisorasRef1, loadingSerieRef1, loadingEmisorasRef2,
        loadingSerieRef2, loadingEmisoras, loadingSerie,
        loadingConsultaData, emisora, serie,
        consultaData, isNewFormCorp, isNewSerieCorp,
        requiredTvCorp, requiredEmisoraCorp, requiredSerieCorp,
        handleClickTv, handleEmisora, handleSerie, handleTvRef1,
        handleEmiRef1, handleTvRef2, handleEmiRef2
    } = useTvEmiSerieDataCorp()


    const {
        isInterBonos,
        isFieldModified,
        loadingSubmitCorpRW,
        inputValueEval,
        checkboxValueEval,
        handleChange,
        handleNumericChange,
        handleCheckboxChange,
        handleSubmitRW,
        handleTvNewForm,
    } = useHandleDataCorp()

    const isOfertaPublica = checkboxValueEval("b_oferta_publica", consultaData)
    const isFiduciario = checkboxValueEval("b_fiduciario", consultaData)
    const isTipoOferta = checkboxValueEval("b_tipo_oferta", consultaData)
    const isEsg = checkboxValueEval("b_esg", consultaData)
    const isCanje = checkboxValueEval("b_canje", consultaData)

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

    if (triggerErase) {
        return (
            <div className="flex justify-center items-center h-full"></div>
        );
    }

    return (
        <>
            <form>
                <div className="px-3">
                    <TvEmiSerieOptions isNewFormInst={isNewFormCorp}
                                       isNewSerie={isNewSerieCorp}
                                       loadingConsultaData={loadingConsultaData}
                                       loadingEmisoras={loadingEmisoras}
                                       loadingSerie={loadingSerie}
                                       selectedTv={selectedTv}
                                       selectedEmisora={selectedEmisora}
                                       selectedSerie={selectedSerie}
                                       requiredTv={requiredTvCorp}
                                       requiredEmisora={requiredEmisoraCorp}
                                       requiredSerie={requiredSerieCorp}
                                       tv={tv}
                                       emisora={emisora}
                                       serie={serie}
                                       isFieldModified={isFieldModified}
                                       consultaData={consultaData}
                                       requeridos={requeridos}
                                       inputValueEval={inputValueEval}
                                       handleChange={handleChange}
                                       handleTvNewForm={handleTvNewForm}
                                       handleClickTv={handleClickTv}
                                       handleEmisora={handleEmisora}
                                       handleSerie={handleSerie}/>
                    <div className="form-cols-6 ">
                        <div className="form-select">
                            <select name="n_tipo_instrumento"
                                    value={consultaData?.body?.n_tipo_instrumento ?? ''}
                                    onChange={handleChange}
                                    ref={requeridos.n_tipo_instrumento}
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
                            {fieldRequiredCorp.n_tipo_instrumento && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tipo Instrumento</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select name="n_familia_instrumento"
                                    value={consultaData?.body?.n_familia_instrumento ?? ''}
                                    onChange={handleChange}
                                    ref={requeridos.n_familia_instrumento}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'FAMILIA_INSTRUMENTO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_familia_instrumento">
                                Familia Instrumento
                            </label>
                            {fieldRequiredCorp.n_familia_instrumento && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Familia Instrumento</span>
                            )}
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_corto_plazo"
                                checked={checkboxValueEval("b_corto_plazo", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_corto_plazo">
                                Corto Plazo
                            </label>
                        </div>
                    
                        <div className="form-select">
                            <select name="n_tipo_mercado"
                                    value={consultaData?.body?.n_tipo_mercado ?? ''}
                                    onChange={handleChange}
                                    ref={requeridos.n_tipo_mercado}
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
                            {fieldRequiredCorp.n_tipo_mercado && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tipo Mercado</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select name="n_pais"
                                    value={consultaData?.body?.n_pais ?? ''}
                                    onChange={handleChange}
                                    ref={requeridos.n_pais}
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
                            {fieldRequiredCorp.n_pais && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido País</span>
                            )}
                        </div>
                  
                        <div className="form-select">
                            <select name="n_moneda"
                                    value={consultaData?.body?.n_moneda ?? ''}
                                    onChange={handleChange}
                                    ref={requeridos.n_moneda}
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
                            {fieldRequiredCorp.n_moneda && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Moneda</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_isin"
                                value={consultaData?.body?.s_isin ?? ''}
                                onChange={handleChange}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('s_isin')}
                                onBlur={handleBlur}
                                style={sendStyle('s_isin')}
                            />
                            <label htmlFor="s_isin">
                                ISIN
                            </label>
                        </div>
                        <div className="form-select">
                            <select name="n_pais_riesgo"
                                    value={consultaData?.body?.n_pais_riesgo ?? ''}
                                    onChange={handleChange}
                                    ref={requeridos.n_pais_riesgo}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'PAIS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_pais_riesgo">
                                País de Riesgo
                            </label>
                            {fieldRequiredCorp.n_pais_riesgo && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido País de Riesgo</span>
                            )}
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_rnv"
                                checked={checkboxValueEval("b_rnv", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_rnv">
                                RNV
                            </label>
                        </div>
                    </div>
                </div>

                <div className="px-3">
                    <div className="form-title">
                        <span>Características</span>
                    </div>
                    <div className="form-cols-6">
                        <div className="form-select">
                            <select name="n_emisor"
                                    value={consultaData?.body?.n_emisor ?? ''}
                                    onChange={handleChange}
                                    ref={requeridos.n_emisor}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'EMISOR').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_emisor">
                                Emisor
                            </label>
                            {fieldRequiredCorp.n_emisor && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Emisor</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_num_fideicomiso"
                                value={consultaData?.body?.s_num_fideicomiso ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('s_num_fideicomiso')}
                                onBlur={handleBlur}
                                style={sendStyle('s_num_fideicomiso')}
                            />
                            <label htmlFor="s_num_fideicomiso">
                                Número Fideicomiso
                            </label>
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_fiduciario"
                                checked={checkboxValueEval("b_fiduciario", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_fiduciario">
                                Fiduciario
                            </label>
                        </div>
                        {isFiduciario && (
                            <div className="form-select">
                                <select name="n_fiduciario"
                                        value={consultaData?.body?.n_fiduciario ?? ''}
                                        onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, "EMISOR").map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_fiduciario">
                                    Fiduciario
                                </label>
                            </div>
                        )}
                    
                        <div className="form-select">
                            <select name="n_representante_comun"
                                    value={consultaData?.body?.n_representante_comun ?? ''}
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'REPRESENTANTE_COMUN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_representante_comun">
                                Representante Común
                            </label>
                        </div>
                        <div className="form-select">
                            <select name="n_agente_colocador"
                                    value={consultaData?.body?.n_agente_colocador ?? ''}
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'AGENTE_COLOCADOR').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_agente_colocador">
                                Agente Colocador
                            </label>
                        </div>
                    
                        <div className="form-select">
                            <select
                                name="n_sector"
                                value={consultaData?.body?.n_sector ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'SECTOR').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_sector">
                                Sector
                            </label>
                        </div>
                        <div className="form-select">
                            <select name="n_clasificacion_sectorial"
                                    value={consultaData?.body?.n_clasificacion_sectorial ?? ''}
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CLASIFICACION_SECTORIAL').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_clasificacion_sectorial">
                                Clas Sectorial
                            </label>
                        </div>
                   
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_emision"
                                value={consultaData?.body?.d_fecha_emision ?? ''}
                                onChange={handleChange}
                                placeholder=""
                                required
                                ref={requeridos.d_fecha_emision}
                            />
                            <label htmlFor="d_fecha_emision">
                                Fecha Emisión
                            </label>
                            {fieldRequiredCorp.d_fecha_emision && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Fecha Emisión</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_plazo"
                                value={consultaData?.body?.n_plazo ?? ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                placeholder=""
                                required
                                ref={requeridos.n_plazo}
                                onFocus={() => handleFocus('n_plazo')}
                                onBlur={handleBlur}
                                style={sendStyle('n_plazo')}
                            />
                            <label htmlFor="n_plazo">
                                Plazo
                            </label>
                            {fieldRequiredCorp.n_plazo && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Plazo</span>
                            )}
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_vto"
                                value={consultaData?.body?.d_fecha_vto ?? ''}
                                onChange={handleChange}
                                placeholder=""
                                required
                                ref={requeridos.d_fecha_vto}
                            />
                            <label htmlFor="d_fecha_vto">
                                Fecha Vencimiento
                            </label>
                            {fieldRequiredCorp.d_fecha_vto && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Fecha Vencimiento</span>
                            )}
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_vto_estimada"
                                value={consultaData?.body?.d_fecha_vto_estimada ?? ''}
                                onChange={handleChange}
                                ref={requeridos.d_fecha_vto_estimada}
                            />
                            <label htmlFor="d_fecha_vto_estimada">
                                Fecha Vencimiento Estimada
                            </label>
                            {fieldRequiredCorp.d_fecha_vto_estimada && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Fecha Vencimiento Estimada</span>
                            )}
                        </div>
                  
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_valor_nominal"
                                value={consultaData?.body?.n_valor_nominal ?? ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                ref={requeridos.n_valor_nominal}
                                onFocus={() => handleFocus('n_valor_nominal')}
                                onBlur={handleBlur}
                                style={sendStyle('n_valor_nominal')}
                            />
                            <label htmlFor="n_valor_nominal">
                                Valor Nominal
                            </label>
                            {fieldRequiredCorp.n_valor_nominal && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Valor Nominal</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_prima"
                                value={consultaData?.body?.n_prima ?? ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                ref={requeridos.n_prima}
                                onFocus={() => handleFocus('n_prima')}
                                onBlur={handleBlur}
                                style={sendStyle('n_prima')}
                            />
                            <label htmlFor="n_prima">
                                Prima
                            </label>
                            {fieldRequiredCorp.n_prima && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Prima</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_isr"
                                value={consultaData?.body?.n_isr ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_isr')}
                                onBlur={handleBlur}
                                style={sendStyle('n_isr')}
                            />
                            <label htmlFor="n_isr">
                                ISR
                            </label>
                        </div>
                    
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_sobretasa"
                                value={consultaData?.body?.n_sobretasa ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_sobretasa')}
                                onBlur={handleBlur}
                                style={sendStyle('n_sobretasa')}
                            />
                            <label htmlFor="n_sobretasa">
                                Sobre Tasa|
                            </label>
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_callable"
                                checked={checkboxValueEval("b_callable", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_callable">
                                Callable
                            </label>
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_mbs"
                                checked={checkboxValueEval("b_mbs", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_mbs">
                                MBS
                            </label>
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_tipo_oferta"
                                checked={checkboxValueEval("b_tipo_oferta", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_tipo_oferta">
                                Tipo Oferta
                            </label>
                        </div>
                        {isTipoOferta && (
                            <div className="form-select">
                                <select
                                    name="n_tipo_oferta"
                                    value={consultaData?.body?.n_tipo_oferta ?? ''}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, "TIPO_OFERTA").map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_tipo_oferta">
                                    Tipo Oferta
                                </label>
                            </div>
                        )}
                    
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_canje"
                                checked={checkboxValueEval("b_canje", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_canje">
                                Canje
                            </label>
                        </div>
                        {isCanje && (
                            <>
                                <div className="form-date">
                                    <input
                                        type="date"
                                        name="d_fecha_canje"
                                        value={consultaData?.body?.d_fecha_canje ?? ''}
                                        onChange={handleChange}
                                    />
                                    <label htmlFor="d_fecha_canje">Fecha Canje</label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_instrumento_canje"
                                        value={consultaData?.body?.s_instrumento_canje ?? ''}
                                        onChange={handleChange}
                                        onFocus={() => handleFocus('s_instrumento_canje')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_instrumento_canje')}
                                    />
                                    <label htmlFor="s_instrumento_canje">Instrumento Canje</label>
                                </div>
                            </>
                        )}
                   
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_esg"
                                checked={checkboxValueEval("b_esg", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_esg">
                                ESG
                            </label>
                        </div>
                        {isEsg && (
                            <div className="form-select">
                                <select
                                    name="n_familia_esg"
                                    value={consultaData?.body?.n_familia_esg ?? ''}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, "FAMILIA_ESG").map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_familia_esg">
                                    Familia ESG
                                </label>
                            </div>
                        )}
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_quasi_sob"
                                checked={checkboxValueEval("b_quasi_sob", consultaData)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor="b_quasi_sob">
                                Quasi Soberano
                            </label>
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_oferta_publica"
                                checked={checkboxValueEval("b_oferta_publica", consultaData)}
                                onChange={handleCheckboxChange}
                                ref={requeridos.b_oferta_publica}
                            />
                            <label htmlFor="b_oferta_publica">
                                Oferta pública
                            </label>
                            {fieldRequiredCorp.b_oferta_publica && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Oferta pública</span>
                            )}
                        </div>
                        {isOfertaPublica && (
                            <div className="form-select">
                                <select
                                    name="s_inscrito_bolsa"
                                    value={consultaData?.body?.s_inscrito_bolsa ?? ''}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    <option value="BIVA">BIVA</option>
                                    <option value="BMV">BMV</option>
                                    <option value="NINGUNA">NINGUNA</option>
                                </select>
                                <label htmlFor="s_inscrito_bolsa">
                                    Inscrito en Bolsa
                                </label>
                                {fieldRequiredCorp.s_inscrito_bolsa && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Inscrito en Bolsa</span>
                                )}
                            </div>
                        )}
                   
                        <div className="form-select">
                            <select
                                name="n_crv_descuento"
                                value={consultaData?.body?.n_crv_descuento ?? ''}
                                onChange={handleChange}
                                ref={requeridos.n_crv_descuento}
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
                            {fieldRequiredCorp.n_crv_descuento && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Curva Descuento</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_tasa_referencia"
                                value={consultaData?.body?.n_tasa_referencia ?? ''}
                                onChange={handleChange}
                                ref={requeridos.n_tasa_referencia}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CRV_REFERENCIA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_tasa_referencia">
                                Tasa Referencia
                            </label>
                            {fieldRequiredCorp.n_tasa_referencia && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tasa Referencia</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_tipo_tasa"
                                value={consultaData?.body?.n_tipo_tasa ?? ''}
                                onChange={handleChange}
                                ref={requeridos.n_tipo_tasa}
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
                            {fieldRequiredCorp.n_tipo_tasa && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tipo Tasa</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_nomb_tasa"
                                value={consultaData?.body?.n_nomb_tasa ?? ''}
                                onChange={handleChange}
                                disabled
                                onFocus={() => handleFocus('n_nomb_tasa')}
                                onBlur={handleBlur}
                                style={sendStyle('n_nomb_tasa')}
                            />
                            <label htmlFor="n_nomb_tasa">
                                Nomb. Tasa
                            </label>
                        </div>
                  
                        <div className="form-select">
                            <select
                                name="n_convencion_dias"
                                value={consultaData?.body?.n_convencion_dias ?? ''}
                                onChange={handleChange}
                                ref={requeridos.n_convencion_dias}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CONVENCION_DIAS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_convencion_dias">
                                Convención Días
                            </label>
                            {fieldRequiredCorp.n_convencion_dias && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Convención Días</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_composicion_yield"
                                value={consultaData?.body?.n_composicion_yield ?? ''}
                                onChange={handleChange}
                                ref={requeridos.n_composicion_yield}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'COMPOSICION_YIELD').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_composicion_yield">
                                Composición Yield
                            </label>
                            {fieldRequiredCorp.n_composicion_yield && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Composición Yield</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_tipo_deuda"
                                value={consultaData?.body?.n_tipo_deuda ?? ''}
                                onChange={handleChange}
                                ref={requeridos.n_tipo_deuda}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'TIPO_DEUDA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_tipo_deuda">
                                Tipo Deuda
                            </label>
                            {fieldRequiredCorp.n_tipo_deuda && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tipo Deuda</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_calendario"
                                value={consultaData?.body?.n_calendario ?? ''}
                                onChange={handleChange}
                                ref={requeridos.n_calendario}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CALENDARIO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calendario">
                                Calendario
                            </label>
                            {fieldRequiredCorp.n_calendario && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Calendario</span>
                            )}
                        </div>
                    
                        <div className="form-select">
                            <select
                                name="n_forma_cotizacion"
                                value={consultaData?.body?.n_forma_cotizacion ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'FORMA_COTIZACION').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_forma_cotizacion">
                                Forma Cotización
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_tipo_calculo"
                                value={consultaData?.body?.n_tipo_calculo ?? ''}
                                onChange={handleChange}
                                ref={requeridos.n_tipo_calculo}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'TIPO_CALCULO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_tipo_calculo">
                                Tipo Calculo
                            </label>
                            {fieldRequiredCorp.n_tipo_calculo && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tipo Calculo</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_referencia_mercado"
                                value={consultaData?.body?.n_referencia_mercado ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'REFERENCIA_MERCADO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_referencia_mercado">
                                Referencia Mercado
                            </label>


                            {isInterBonos && (
                                <>
                                    <div className="mt-12 border p-4 shadow-sm">
                                        <div className="flex items-center mb-4">
                                            <div className="font-bold text-lg pr-4">1</div>
                                            <div className="flex-grow">
                                                <div className="grid grid-cols-3 gap-4">
                                                    <div className="form-select">
                                                        <select
                                                            name="s_tv_ref_1"
                                                            value={consultaData?.body?.s_tv_ref_1 ?? '...'}
                                                            onChange={(e) => {
                                                                handleChange(e)
                                                                handleTvRef1()
                                                            }}
                                                        >
                                                            <option value="default">...</option>
                                                            <option value="M">M</option>
                                                            <option value="S">S</option>
                                                        </select>
                                                        <label htmlFor="s_tv_ref_1">
                                                            TV
                                                        </label>
                                                        {fieldRequiredCorp.s_tv_ref_1 && (
                                                            <span
                                                                className="fontError animate__animated animate__fadeIn">
                                                                    Campo requerido Tv 1</span>
                                                        )}
                                                    </div>
                                                    <div className="form-select">
                                                        <select
                                                            name="s_emisora_ref_1"
                                                            value={consultaData?.body?.s_emisora_ref_1 ?? '...'}
                                                            onChange={(e) => {
                                                                handleChange(e)
                                                                handleEmiRef1()
                                                            }}
                                                        >
                                                            <option value="default">...</option>
                                                            {emisorasRef1?.map((item) => (
                                                                <option key={generateUUID()} value={item}>
                                                                    {item}
                                                                </option>
                                                            ))}
                                                        </select>
                                                        {loadingEmisorasRef1 &&
                                                            <BarLoader className="mt-2" color="#059669" width={80}/>}
                                                        <label htmlFor="s_emisora_ref_1">
                                                            Emisora
                                                        </label>
                                                        {fieldRequiredCorp.s_emisora_ref_1 && (
                                                            <span
                                                                className="fontError animate__animated animate__fadeIn">
                                                                    Campo requerido Emisora 1</span>
                                                        )}
                                                    </div>
                                                    <div className="form-select">
                                                        <select
                                                            name="s_serie_ref_1"
                                                            value={consultaData?.body?.s_serie_ref_1 ?? '...'}
                                                            onChange={handleChange}
                                                        >
                                                            <option value="default">...</option>
                                                            {serieRef1?.map((item) => (
                                                                <option key={generateUUID()} value={item}>
                                                                    {item}
                                                                </option>
                                                            ))}
                                                        </select>
                                                        {loadingSerieRef1 &&
                                                            <BarLoader className="mt-2" color="#059669" width={80}/>}
                                                        <label htmlFor="s_serie_ref_1">
                                                            Serie
                                                        </label>
                                                        {fieldRequiredCorp.s_serie_ref_1 && (
                                                            <span
                                                                className="fontError animate__animated animate__fadeIn">
                                                                    Campo requerido Serie 1</span>
                                                        )}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="border p-4 shadow-sm">
                                        <div className="flex items-center mb-4">
                                            <div className="font-bold text-lg pr-4">2</div>
                                            <div className="flex-grow">
                                                <div className="grid grid-cols-3 gap-4">
                                                    <div className="form-select">
                                                        <select
                                                            name="s_tv_ref_2"
                                                            value={consultaData?.body?.s_tv_ref_2 ?? '...'}
                                                            onChange={(e) => {
                                                                handleChange(e)
                                                                handleTvRef2()
                                                            }}
                                                        >
                                                            <option value="default">...</option>
                                                            <option value="M">M</option>
                                                            <option value="S">S</option>
                                                        </select>
                                                        <label htmlFor="s_tv_ref_2">
                                                            TV
                                                        </label>
                                                        {fieldRequiredCorp.s_tv_ref_2 && (
                                                            <span
                                                                className="fontError animate__animated animate__fadeIn">
                                                                    Campo requerido Tv 2</span>
                                                        )}
                                                    </div>
                                                    <div className="form-select">
                                                        <select
                                                            name="s_emisora_ref_2"
                                                            value={consultaData?.body?.s_emisora_ref_2 ?? '...'}
                                                            onChange={(e) => {
                                                                handleChange(e)
                                                                handleEmiRef2()
                                                            }}
                                                        >
                                                            <option value="default">...</option>
                                                            {emisorasRef2?.map((item) => (
                                                                <option key={generateUUID()} value={item}>
                                                                    {item}
                                                                </option>
                                                            ))}
                                                        </select>
                                                        {loadingEmisorasRef2 &&
                                                            <BarLoader className="mt-2" color="#059669" width={80}/>}
                                                        <label htmlFor="s_emisora_ref_2">
                                                            Emisora
                                                        </label>
                                                        {fieldRequiredCorp.s_emisora_ref_2 && (
                                                            <span
                                                                className="fontError animate__animated animate__fadeIn">
                                                                    Campo requerido Emisora 2</span>
                                                        )}
                                                    </div>
                                                    <div className="form-select">
                                                        <select
                                                            name="s_serie_ref_2"
                                                            value={consultaData?.body?.s_serie_ref_2 ?? '...'}
                                                            onChange={handleChange}
                                                        >
                                                            <option value="default">...</option>
                                                            {serieRef2?.map((item) => (
                                                                <option key={generateUUID()} value={item}>
                                                                    {item}
                                                                </option>
                                                            ))}
                                                        </select>
                                                        {loadingSerieRef2 &&
                                                            <BarLoader className="mt-2" color="#059669" width={80}/>}
                                                        <label htmlFor="s_serie_ref_2">
                                                            Serie
                                                        </label>
                                                        {fieldRequiredCorp.s_serie_ref_2 && (
                                                            <span
                                                                className="fontError animate__animated animate__fadeIn">
                                                                    Campo requerido Serie 2</span>
                                                        )}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </>
                            )}

                        </div>
                        <div className="form-select">
                            <select
                                name="n_status"
                                value={consultaData?.body?.n_status ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'STATUS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_status">
                                Status
                            </label>
                        </div>
                    
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_titulos_iniciales"
                                value={consultaData?.body?.n_titulos_iniciales ?? ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                ref={requeridos.n_titulos_iniciales}
                                onFocus={() => handleFocus('n_titulos_iniciales')}
                                onBlur={handleBlur}
                                style={sendStyle('n_titulos_iniciales')}
                            />
                            <label htmlFor="n_titulos_iniciales">
                                Títulos Iniciales
                            </label>
                            {fieldRequiredCorp.n_titulos_iniciales && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Títulos Iniciales</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_titulos_circulacion"
                                value={consultaData?.body?.n_titulos_circulacion ?? ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                ref={requeridos.n_titulos_circulacion}
                                onFocus={() => handleFocus('n_titulos_circulacion')}
                                onBlur={handleBlur}
                                style={sendStyle('n_titulos_circulacion')}
                            />
                            <label htmlFor="n_titulos_circulacion">
                                Títulos Actuales
                            </label>
                            {fieldRequiredCorp.n_titulos_circulacion && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Títulos Actuales</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_monto_emitido"
                                value={consultaData?.body?.n_monto_emitido ?? ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                ref={requeridos.n_monto_emitido}
                                onFocus={() => handleFocus('n_monto_emitido')}
                                onBlur={handleBlur}
                                style={sendStyle('n_monto_emitido')}
                            />
                            <label htmlFor="n_monto_emitido">
                                Monto Emi.
                            </label>
                            {fieldRequiredCorp.n_monto_emitido && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Monto Emi</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_monto_circulacion"
                                value={consultaData?.body?.n_monto_circulacion ?? ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                ref={requeridos.n_monto_circulacion}
                                onFocus={() => handleFocus('n_monto_circulacion')}
                                onBlur={handleBlur}
                                style={sendStyle('n_monto_circulacion')}
                            />
                            <label htmlFor="n_monto_circulacion">
                                Monto Cir.
                            </label>
                            {fieldRequiredCorp.n_monto_circulacion && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Monto Cir</span>
                            )}
                        </div>
                    
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_tasa_mercado"
                                value={consultaData?.body?.n_tasa_mercado ?? ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                onFocus={() => handleFocus('n_tasa_mercado')}
                                onBlur={handleBlur}
                                style={sendStyle('n_tasa_mercado')}
                            />
                            <label htmlFor="n_tasa_mercado">
                                Tasa Mercado
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_tasa_descuento"
                                value={consultaData?.body?.n_tasa_descuento ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_tasa_descuento')}
                                onBlur={handleBlur}
                                style={sendStyle('n_tasa_descuento')}
                            />
                            <label htmlFor="n_tasa_descuento">
                                Tasa Descuento
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_rendimiento"
                                value={consultaData?.body?.n_rendimiento ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_rendimiento')}
                                onBlur={handleBlur}
                                style={sendStyle('n_rendimiento')}
                            />
                            <label htmlFor="n_rendimiento">
                                Rendimiento
                            </label>
                        </div>
                   
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_precio_mercado"
                                value={consultaData?.body?.n_precio_mercado ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_precio_mercado')}
                                onBlur={handleBlur}
                                style={sendStyle('n_precio_mercado')}
                            />
                            <label htmlFor="n_precio_mercado">
                                Precio Mercado
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_precio_mercado_24h"
                                value={consultaData?.body?.n_precio_mercado_24h ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_precio_mercado_24h')}
                                onBlur={handleBlur}
                                style={sendStyle('n_precio_mercado_24h')}
                            />
                            <label htmlFor="n_precio_mercado_24h">
                                Precio Mercado 24
                            </label>
                        </div>
                  
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_intereses_md"
                                value={consultaData?.body?.n_intereses_md ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_intereses_md')}
                                onBlur={handleBlur}
                                style={sendStyle('n_intereses_md')}
                            />
                            <label htmlFor="n_intereses_md">
                                Intereses MD
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_intereses_24h"
                                value={consultaData?.body?.n_intereses_24h ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_intereses_24h')}
                                onBlur={handleBlur}
                                style={sendStyle('n_intereses_24h')}
                            />
                            <label htmlFor="n_intereses_24h">
                                Intereses 24
                            </label>
                        </div>
                    </div>
                </div>
            </form>
            <form onSubmit={handleSubmitRW}>
                <div className="px-3">
                    <div className="form-title">
                        <span>RW</span>
                    </div>
                    <div className="form-cols-6">
                        <div className="form-select">
                            <select
                                name="n_theo_model"
                                value={consultaData?.body?.n_theo_model ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'THEO_MODEL').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_theo_model">
                                Theorical Model
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_market_model"
                                value={consultaData?.body?.n_market_model ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'MARKET_MODEL-CORPO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_market_model">
                                Market Model
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_curve_index"
                                value={consultaData?.body?.n_curve_index ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CURVE_INDEX-CORPO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_curve_index">
                                Curve Index
                            </label>
                        </div>
                 
                        <div className="form-select">
                            <select
                                name="n_reset_rule"
                                value={consultaData?.body?.n_reset_rule ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'BUSINESS_RESET_RULE').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_reset_rule">
                                Reset Rule
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_busdayrule"
                                value={consultaData?.body?.n_busdayrule ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'BUSINESS_RESET_RULE').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_busdayrule">
                                Business Day Rule
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_fixed_coupon_date"
                                value={consultaData?.body?.n_fixed_coupon_date ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_fixed_coupon_date')}
                                onBlur={handleBlur}
                                style={sendStyle('n_fixed_coupon_date')}
                            />
                            <label htmlFor="n_fixed_coupon_date">
                                Fixed Coupon Date
                            </label>
                        </div>
                
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_credit_spread_curve"
                                value={consultaData?.body?.s_credit_spread_curve ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('s_credit_spread_curve')}
                                onBlur={handleBlur}
                                style={sendStyle('s_credit_spread_curve')}
                            />
                            <label htmlFor="s_credit_spread_curve">
                                Credit Spread Curve
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_oddfirstcoupon"
                                value={consultaData?.body?.s_oddfirstcoupon ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('s_oddfirstcoupon')}
                                onBlur={handleBlur}
                                style={sendStyle('s_oddfirstcoupon')}
                            />
                            <label htmlFor="s_oddfirstcoupon">
                                Odd First Coupon
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_oddlastcoupon"
                                value={consultaData?.body?.s_oddlastcoupon ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('s_oddlastcoupon')}
                                onBlur={handleBlur}
                                style={sendStyle('s_oddlastcoupon')}
                            />
                            <label htmlFor="s_oddlastcoupon">
                                Odd Last Coupon
                            </label>
                        </div>
                   
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_lastregcou"
                                value={consultaData?.body?.d_lastregcou ?? ''}
                                onChange={handleChange}
                            />
                            <label htmlFor="d_lastregcou">
                                Last Reg Coupon
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="b_runscfphase"
                                value={consultaData?.body?.b_runscfphase ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                <option value="0"> Falso</option>
                                <option value="1"> Verdadero</option>
                            </select>
                            <label htmlFor="b_runscfphase">
                                Runscfphase
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="b_couponpro"
                                value={consultaData?.body?.b_couponpro ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                <option value="0"> Falso</option>
                                <option value="1"> Verdadero</option>
                            </select>
                            <label htmlFor="b_couponpro">
                                Coupon Prorated
                            </label>
                        </div>
                     
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_coupgenmthd"
                                value={consultaData?.body?.s_coupgenmthd ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('s_coupgenmthd')}
                                onBlur={handleBlur}
                                style={sendStyle('s_coupgenmthd')}
                            />
                            <label htmlFor="s_coupgenmthd">
                                Coupon Generation Method
                            </label>
                        </div>

                        
                    </div>
                </div>
                <div className='mt-0 flex justify-end px-3'>
                    <button className='btn'>
                        <ButtonContent name={"Guardar RW"} loading={loadingSubmitCorpRW}></ButtonContent>
                    </button>
                </div>
            </form>
        </>
    );
}