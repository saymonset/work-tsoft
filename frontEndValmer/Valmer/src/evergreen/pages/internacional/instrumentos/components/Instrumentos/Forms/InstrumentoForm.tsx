import { BarLoader, MoonLoader } from "react-spinners";
import { getCatalogs } from "../../../../../../../utils";
import { TvEmiSerieOptions, ClientesActuales } from "./components";
import { useTvEmiSerie, useHandleData} from "./hooks";
import { getCatalogsSorted } from "./hooks/useGetCatalogSorted";
import { useBigInput } from "../../../../../deuda/tasas/components/forms/hooks/useBigInput";

export const InstrumentoForm = ({requeridos}: any) => {

     //  Achica o agranda el input del form cuando obtiene o deja el focus
     const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
        
    const {
        fieldRequiredInternacional,
        triggerErase,
        consultaData,
        catalog,
        loading,
        catalogCalif,
        loadingCalif,
        tv,
        loadingTv,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        emisora,
        serie,
        isNewInterForm,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        handleClickTv,
        handleEmisora,
        handleSerie
    } = useTvEmiSerie()

    const {
        isDisabledRnv,
        isFieldModified,
        inputValueEval,
        checkboxValueEval,
        handleChange,
        handleTvNewForm,
        handleCheckboxChange,
        isChecked
    } = useHandleData({requeridos})

    if (loading || loadingCalif || loadingTv || !catalog.length || !catalogCalif.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading || loadingCalif || loadingTv ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
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
        <form>
            <div className="form-cols-5">
                <div className="form col-span-3 px-3">
                    {loadingConsultaData && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500} />}
                    <div className="form-title">
                        <span>Instrumento</span>
                    </div>
                    <div className="form-cols-4">
                        <TvEmiSerieOptions
                            isNewFormInst={isNewInterForm}
                            isNewSerie={false}
                            loadingConsultaData={loadingConsultaData}
                            loadingEmisoras={loadingEmisoras}
                            loadingSerie={loadingSerie}
                            selectedTv={selectedTv}
                            selectedEmisora={selectedEmisora}
                            selectedSerie={selectedSerie}
                            requiredTv={requiredTv}
                            requiredEmisora={requiredEmisora}
                            requiredSerie={requiredSerie}
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
                            handleSerie={handleSerie}
                        />
                        <div className="form-check">
                            <input
                                type="checkbox"
                                id="n_inact"
                                name="n_inact"
                                checked={checkboxValueEval('n_inact', consultaData)}
                                onChange={(e) => handleCheckboxChange(e)}
                            />
                            <label
                                htmlFor="n_inact"
                            >
                                Inactivas
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-3">
                        <div className="form-select">
                            <select
                                id="s_tipo_instr"
                                name="s_tipo_instr"
                                value = {consultaData?.body?.s_tipo_instr ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'TIPO_INSTRUMENTO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_tipo_instr">
                                Tipo Instrumento
                            </label>
                            {fieldRequiredInternacional.s_tipo_instr && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Tipo Instrumento</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                id="s_tipo_merc"
                                name="s_tipo_merc"
                                value= {consultaData?.body?.s_tipo_merc ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'TIPO_MERCADO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_tipo_merc">
                                Tipo Mercado
                            </label>
                            {fieldRequiredInternacional.s_tipo_merc && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Tipo Mercado</span>
                            )}
                        </div>
                    {/* </div>
                    <div className="form-cols-2"> */}
                        <div className="form-select">
                            <select
                                id="s_pais"
                                name="s_pais"
                                value = {consultaData?.body?.s_pais ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'PAIS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_pais">
                                País
                            </label>
                            {fieldRequiredInternacional.s_pais && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido País</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                id="n_pais_riesgo"
                                name="n_pais_riesgo"
                                value = {consultaData?.body?.n_pais_riesgo ?? ''}
                                onChange={handleChange}
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
                        </div>
                    {/* </div>
                    <div className="form-cols-2"> */}
                        <div className="form-select">
                            <select
                                id="s_subfamilia"
                                name="s_subfamilia"
                                value={consultaData?.body?.s_subfamilia ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'SUB_FAMILIA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_subfamilia">
                                Sub Familia
                            </label>
                            {fieldRequiredInternacional.s_subfamilia && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Sub Familia</span>
                            )}
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                id="b_rnv"
                                name="b_rnv"
                                checked={checkboxValueEval('b_rnv', consultaData)}
                                onChange={(e) => handleCheckboxChange(e)}
                                disabled={isDisabledRnv}
                            />
                            <label
                                htmlFor="b_rnv"
                            >
                                RNV
                            </label>
                        </div>
                    </div>
                    <div className="form-title form-mb">
                        <span>Caracteristicas</span>
                    </div>
                    <div className="form-cols-3">
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fec_emi"
                                id="d_fec_emi"
                                value = {consultaData?.body?.d_fec_emi ?? ''}
                                onChange={handleChange}
                            />
                            <label
                                htmlFor="d_fec_emi"
                            >
                                Fecha Emisión
                            </label>
                            {fieldRequiredInternacional.d_fec_emi && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Fecha Emisión</span>
                            )}
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fec_venc"
                                id="d_fec_venc"
                                placeholder=""
                                value = {consultaData?.body?.d_fec_venc ?? ''}
                                onChange={handleChange}
                            />
                            <label
                                htmlFor="d_fec_venc"
                            >
                                Fecha Vencimiento
                            </label>
                            {fieldRequiredInternacional.d_fec_venc && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Fecha Vencimiento</span>
                            )}
                        </div>
                    {/* </div>
                    <div className="form-cols-2"> */}
                        <div className="form-select">
                            <select
                                id="s_moneda"
                                name="s_moneda"
                                value = {consultaData?.body?.s_moneda ?? ''}
                                onChange = {handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'MONEDA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_moneda">
                                Moneda
                            </label>
                            {fieldRequiredInternacional.s_moneda && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Moneda</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_val_nom"
                                id="n_val_nom"
                                placeholder=""
                                required
                                value = {consultaData?.body?.n_val_nom ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('n_val_nom')}
                                onBlur={handleBlur}
                                style={sendStyle('n_val_nom')}
                            />
                            <label
                                htmlFor="n_val_nom"
                            >
                                Valor Nominal
                            </label>
                            {fieldRequiredInternacional.n_val_nom && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Valor Nominal</span>
                            )}
                        </div>
                    {/* </div>
                    <div className="form-cols-2"> */}
                        <div className="form-select">
                            <select
                                id="s_tasa_ref"
                                name="s_tasa_ref"
                                value={consultaData?.body?.s_tasa_ref ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CRV_REFERENCIA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_tasa_ref">
                                Tasa Referencia
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                id="s_crv_desc"
                                name="s_crv_desc"
                                value = {consultaData?.body?.s_crv_desc ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CRV_DESCUENTO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_crv_desc">
                                Curva Descuento
                            </label>
                            {fieldRequiredInternacional.s_crv_desc && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Curva Descuento</span>
                            )}
                        </div>
                    {/* </div>
                    <div className="form-cols-2"> */}
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_isin"
                                id="s_isin"
                                placeholder=""
                                required
                                value = {consultaData?.body?.s_isin ?? ''}
                                onChange={handleChange}
                                onFocus={() => handleFocus('s_isin')}
                                onBlur={handleBlur}
                                style={sendStyle('s_isin')}
                            />
                            <label
                                htmlFor="s_isin"
                            >
                                ISIN
                            </label>
                            {fieldRequiredInternacional.s_isin && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido ISIN</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_isin_v"
                                id="s_isin_v"
                                placeholder=""
                                required
                                value={consultaData?.body?.s_isin_v ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('isinVinculado')}
                                onBlur={handleBlur}
                                style={sendStyle('isinVinculado')}
                            />
                            <label
                                htmlFor="isinVinculado"
                            >
                                ISIN Vinculado
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                className="mt-1"
                                name="s_emisor"
                                placeholder=""
                                value= {consultaData?.body?.s_emisor ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'EMISOR').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_emisor">
                                Emisor
                            </label>
                            {fieldRequiredInternacional.s_emisor && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Emisor</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                id="n_status"
                                name="n_status"
                                value = {consultaData?.body?.n_status ?? ''}
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
                            {fieldRequiredInternacional.n_status && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Status</span>
                            )}
                        </div>
                    </div>
                    <div className="form-cols-2">
                   
                        <div className="form-select">
                            <select
                                id="s_met_calc"
                                name="s_met_calc"
                                value = {consultaData?.body?.s_met_calc ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CONVENCION_DIAS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_met_calc">
                                Método Cálculo
                            </label>
                            {fieldRequiredInternacional.s_met_calc && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Método Cálculo</span>
                            )}
                        </div>
                    {/* </div>
                    <div className="form-cols-1"> */}
                        <div className="form-select">
                            <select
                                id="n_calendario"
                                name="n_calendario"
                                value = {consultaData?.body?.n_calendario ?? ''}
                                onChange={handleChange}
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
                            {fieldRequiredInternacional.n_calendario && (
                                <span className="fontError animate_animated animate_fadeIn">Campo requerido Calendario</span>
                            )}
                        </div>
                    </div>
                    <div className="form-title">
                        <span>Instrumento Deuda</span>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_amort_ant"
                                id="d_fecha_amort_ant"
                                placeholder=""
                                required
                                value={consultaData?.body?.d_fecha_amort_ant ?? ''}
                                onChange={(e) => handleChange(e)}
                            />
                            <label
                                htmlFor="d_fecha_amort_ant"
                            >
                                Fecha Amort. Ant.
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_prima"
                                id="n_prima"
                                placeholder=""
                                required
                                value={consultaData?.body?.n_prima ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_prima')}
                                onBlur={handleBlur}
                                style={sendStyle('n_prima')}
                            />
                            <label
                                htmlFor="n_prima"
                            >
                                Prima
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_rendimiento"
                                id="n_rendimiento"
                                placeholder=""
                                required
                                value={consultaData?.body?.n_rendimiento ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_rendimiento')}
                                onBlur={handleBlur}
                                style={sendStyle('n_rendimiento')}
                            />
                            <label
                                htmlFor="n_rendimiento"
                            >
                                Rendimiento
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_sobretasa"
                                id="n_sobretasa"
                                placeholder=""
                                required
                                value={consultaData?.body?.n_sobretasa ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_sobretasa')}
                                onBlur={handleBlur}
                                style={sendStyle('n_sobretasa')}
                            />
                            <label
                                htmlFor="n_sobretasa"
                            >
                                Sobretasa
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_prec_merc"
                                id="n_prec_merc"
                                placeholder=""
                                required
                                value={consultaData?.body?.n_prec_merc ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_prec_merc')}
                                onBlur={handleBlur}
                                style={sendStyle('n_prec_merc')}
                            />
                            <label
                                htmlFor="n_prec_merc"
                            >
                                Precio Mercado
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                id="s_frm_coti"
                                name="s_frm_coti"
                                value={consultaData?.body?.s_frm_coti ?? ''}
                                onChange={(e) => handleChange(e)}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'FORMA_COTIZACION').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_frm_coti">
                                Forma Cotización
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_val_nom_a"
                                id="n_val_nom_a"
                                placeholder=""
                                required
                                value={consultaData?.body?.n_val_nom_a ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_val_nom_a')}
                                onBlur={handleBlur}
                                style={sendStyle('n_val_nom_a')}
                            />
                            <label
                                htmlFor="n_val_nom_a"
                            >
                                Valor Nominal Act
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_mont_circ"
                                id="n_mont_circ"
                                placeholder=""
                                required
                                value={consultaData?.body?.n_mont_circ ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_mont_circ')}
                                onBlur={handleBlur}
                                style={sendStyle('n_mont_circ')}
                            />
                            <label
                                htmlFor="n_mont_circ"
                            >
                                Monto en Circulación
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                id="s_tipo"
                                name="s_tipo"
                                value= {consultaData?.body?.s_tipo ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'TIPO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_tipo">
                                Tipo
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                id="s_bolsa_cot"
                                name="s_bolsa_cot"
                                value={consultaData?.body?.s_bolsa_cot ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'BOLSA_COTIZACION').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_bolsa_cot">
                                Bolsa Cotización
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                id="s_sector"
                                name="s_sector"
                                value={consultaData?.body?.s_sector ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'SECTOR_ECO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_sector">
                                Sector
                            </label>
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                id="b_precio_teorico"
                                name="b_precio_teorico"
                                checked={checkboxValueEval('b_precio_teorico', consultaData)}
                                onChange={(e) => handleCheckboxChange(e)}
                            />
                            <label
                                htmlFor="b_precio_teorico"
                            >
                                Precio Teórico
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-cols-2">
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    id="b_esg"
                                    name="b_esg"
                                    checked={checkboxValueEval('b_esg', consultaData)}
                                    onChange={(e) => handleCheckboxChange(e)}
                                />
                                <label
                                    htmlFor="b_esg"
                                >
                                    ESG
                                </label>
                            </div>
                            {isChecked &&
                                <div className="form-select">
                                    <select
                                        id="n_familia_esg"
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
                            }
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                id="b_quasi_sob"
                                name="b_quasi_sob"
                                checked={checkboxValueEval('b_quasi_sob', consultaData)}
                                onChange={(e) => handleCheckboxChange(e)}
                            />
                            <label
                                htmlFor="b_quasi_sob"
                            >
                                Quasi Soberano
                            </label>
                        </div>
                    </div>
                    <div className="form-title">
                        Calificaciones
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                id="n_fitch"
                                name="n_fitch"
                                value={consultaData?.body?.n_fitch ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogsSorted(catalogCalif, 'FITCH').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_fitch">
                                Fitch
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_fitch"
                                id="d_fecha_fitch"
                                value={consultaData?.body?.d_fecha_fitch ?? ''}
                                onChange={handleChange}
                            />
                            <label
                                htmlFor="fechaFitch"
                            >
                                Fecha Fitch
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                id="n_sp"
                                name="n_sp"
                                value={consultaData?.body?.n_sp ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogsSorted(catalogCalif, 'SP').map((column) => (
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
                            <input
                                type="date"
                                name="d_fecha_sp"
                                id="d_fecha_sp"
                                value={consultaData?.body?.d_fecha_sp ?? ''}
                                onChange={handleChange}
                            />
                            <label
                                htmlFor="d_fecha_sp"
                            >
                                Fecha S&P
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                id="n_moody"
                                name="n_moody"
                                value={consultaData?.body?.n_moody ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogsSorted(catalogCalif, 'MOODY').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_moody">
                                Moody
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_moody"
                                id="d_fecha_moody"
                                value={consultaData?.body?.d_fecha_moody ?? ''}
                                onChange={handleChange}
                            />
                            <label
                                htmlFor="d_fecha_moody"
                            >
                                Fecha Moody
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-2">
                        <div className="form-select">
                            <select
                                id="n_hr"
                                name="n_hr"
                                value={consultaData?.body?.n_hr ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogCalif, 'HR').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label
                                htmlFor="hr"
                            >
                                HR
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_hr"
                                id="d_fecha_hr"
                                value={consultaData?.body?.d_fecha_hr ?? ''}
                                onChange={handleChange}
                            />
                            <label
                                htmlFor="d_fecha_hr"
                            >
                                Fecha HR
                            </label>
                        </div>
                    </div>
                    <div className="form-title">
                        Contribuidores
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_precio_bloomberg"
                                id="n_precio_bloomberg"
                                placeholder=""
                                value={consultaData?.body?.n_precio_bloomberg ?? ''}
                                onChange={(e) => handleChange(e)}
                                disabled={true}
                                onFocus={() => handleFocus('n_precio_blomberg')}
                                onBlur={handleBlur}
                                style={sendStyle('n_precio_blomberg')}
                            />
                            <label
                                htmlFor="n_precio_blomberg"
                            >
                                Bloomberg
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_precio_markit"
                                id="n_precio_markit"
                                placeholder=""
                                value={consultaData?.body?.n_precio_markit ?? ''}
                                onChange={(e) => handleChange(e)}
                                disabled={true}
                                onFocus={() => handleFocus('n_precio_markit')}
                                onBlur={handleBlur}
                                style={sendStyle('n_precio_markit')}
                            />
                            <label
                                htmlFor="n_precio_markit"
                            >
                                Markit
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_precio_reuters"
                                id="n_precio_reuters"
                                placeholder=""
                                value={consultaData?.body?.n_precio_reuters ?? ''}
                                onChange={(e) => handleChange(e)}
                                disabled={true}
                                onFocus={() => handleFocus('n_precio_reuters')}
                                onBlur={handleBlur}
                                style={sendStyle('n_precio_reuters')}
                            />
                            <label
                                htmlFor="n_precio_reuters"
                            >
                                Refinitiv
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="b_precio_sucio"
                                id="b_precio_sucio"
                                checked={checkboxValueEval('b_precio_sucio', consultaData)}
                                onChange={(e) => handleCheckboxChange(e)}
                            />
                            <label
                                htmlFor="b_precio_sucio"
                            >
                                Precio Sucio
                            </label>
                        </div>
                    </div>

                    {selectedSerie && selectedSerie !== "..." && (<ClientesActuales/>)}
                </div>


                <div className="form col-span-2">
                    <div className="form-title">Risk Watch</div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="n_theo_model"
                                id="n_theo_model"
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
                            <label htmlFor="n_theo_model"
                            >
                                Theorical Model
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="n_market_model"
                                id="n_market_model"
                                value={consultaData?.body?.n_market_model ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'MARKET_MODEL').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label
                                htmlFor="n_market_model"
                            >
                                Market Model
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="n_curve_index"
                                id="n_curve_index"
                                value={consultaData?.body?.n_curve_index ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'CURVE_INDEX-INTERNACIONAL').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label
                                htmlFor="n_curve_index"
                            >
                                Curve Index
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_fixed_coupon_date"
                                id="n_fixed_coupon_date"
                                placeholder=""
                                value={consultaData?.body?.n_fixed_coupon_date ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_fixed_coupon_date')}
                                onBlur={handleBlur}
                                style={sendStyle('n_fixed_coupon_date')}
                            />
                            <label
                                htmlFor="n_fixed_coupon_date"
                            >
                                Fixed Coupon Date
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_credit_spread_curve"
                                id="s_credit_spread_curve"
                                placeholder=""
                                value={consultaData?.body?.s_credit_spread_curve ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('s_credit_spread_curve')}
                                onBlur={handleBlur}
                                style={sendStyle('s_credit_spread_curve')}
                            />
                            <label
                                htmlFor="s_credit_spread_curve"
                            >
                                Credit Spread Date
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_oddfirstcoupon"
                                id="s_oodFirstcoupon"
                                placeholder=""
                                value={consultaData?.body?.s_oddfirstcoupon ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('s_oddfirstcoupon')}
                                onBlur={handleBlur}
                                style={sendStyle('s_oddfirstcoupon')}
                            />
                            <label
                                htmlFor="s_oddfirstcoupon"
                            >
                                Odd First Coupon
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_oddlastcoupon"
                                id="s_oddlastcoupon"
                                placeholder=""
                                value={consultaData?.body?.s_oddlastcoupon ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('s_oddlastcoupon')}
                                onBlur={handleBlur}
                                style={sendStyle('s_oddlastcoupon')}
                            />
                            <label
                                htmlFor="s_oddlastcoupon"
                            >
                                Odd Last Coupon
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_lastregcou"
                                id="d_lastregcou"
                                value={consultaData?.body?.d_lastregcou ?? ''}
                                onChange={handleChange}
                            />
                            <label
                                htmlFor="d_lastregcou"
                            >
                                Last Reg Coupon
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="n_busdayrule"
                                id="n_busdayrule"
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
                            <label
                                htmlFor="n_busdayrule"
                            >
                                Business Day Rule
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="b_runscfphase"
                                id="b_runscfphase"
                                value={consultaData?.body?.b_runscfphase ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'runscfphase').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label
                                htmlFor="b_runscfphase"
                            >
                                Runscfphase
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="b_couponpro"
                                id="b_couponpro"
                                value={consultaData?.body?.b_couponpro ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'coupon-prorated').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label
                                htmlFor="b_couponpro"
                            >
                                Coupon Prorated
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="s_coupgenmthd"
                                id="s_coupgenmthd"
                                value={consultaData?.body?.s_coupgenmthd ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'copuon-generation-method').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label
                                htmlFor="s_coupgenmthd"
                            >
                                Coupon Generation Method
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="n_reset_rule"
                                id="n_reset_rule"
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
                            <label
                                htmlFor="n_reset_rule"
                            >
                                Reset Rule
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_last_reset_rate"
                                id="n_last_reset_rate"
                                placeholder=""
                                value={consultaData?.body?.n_last_reset_rate ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_last_reset_rate')}
                                onBlur={handleBlur}
                                style={sendStyle('n_last_reset_rate')}
                            />
                            <label
                                htmlFor="n_last_reset_rate"
                            >
                                Last Reset Rate
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_last_reset_rate_24h"
                                id="n_last_reset_rate_24h"
                                placeholder=""
                                value={consultaData?.body?.n_last_reset_rate_24h ?? ''}
                                onChange={(e) => handleChange(e)}
                                onFocus={() => handleFocus('n_last_reset_rate_24h')}
                                onBlur={handleBlur}
                                style={sendStyle('n_last_reset_rate_24h')}
                            />
                            <label
                                htmlFor="n_last_reset_rate_24h"
                            >
                                Last Reset Rate 24H
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-select">
                            <select
                                name="b_not_equal"
                                id="b_not_equal"
                                value={consultaData?.body?.b_not_equal ?? ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog, 'notional-equals-current-balance').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label
                                htmlFor="b_not_equal"
                            >
                                Notional Equals Current Balance
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    );
}