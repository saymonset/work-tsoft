import {generateUUID, getCatalogs} from "../../../../../../../../utils";
import { useBigInput } from "../../../../../../../../utils/useBigInput";
import {useDataCupones} from "../hooks"

export const CuponesForm = ({requeridos}: any) => {

      //  Achica o agranda el input del form cuando obtiene o deja el focus
      const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
    const {
        triggerErase,
        catalog,
        consultaData,
        handleChange
    } = useDataCupones({requeridos});

    const getFrequencyCoupon = () => {
        const frequencyCoupon = getCatalogs(catalog, 'FRECUENCIA_CUPON-GUBER').map((column) => column[1]);
        const frequencyFilters = frequencyCoupon.filter((option) => !option.includes("CADA") && option !== "BIMESTRAL");

        return frequencyFilters.map((option, index) => {
            let description;
            switch (option) {
                case "AL VENCIMIENTO":
                    description = "Maturity";
                    break;
                case "ANUAL":
                    description = "1-Year";
                    break;
                case "MENSUAL":
                    description = "1-Month";
                    break;
                case "SEMESTRAL":
                    description = "6-Months";
                    break;
                case "TRIMESTRAL":
                    description = "3-Months";
                    break;
                default:
                    description = option;
                    break;
            }
            return { value: index, label: `${option} - ${description}` };
        });
    };

    if (triggerErase) {
        return (
            <div className="flex justify-center items-center h-full"></div>
        );
    }

    return (
        <form>
            <div className="form">
                <div className="form-title">Cupones</div>
                <div className="form-cols-3">
                    <div className="form-date form-date-my">
                        <input
                            type="date"
                            name="d_fec_ini_c"
                            value={consultaData?.body?.d_fec_ini_c ?? ''}
                            onChange={handleChange}
                        />
                        <label htmlFor="d_fec_ini_c">Fecha Inicio Cupón</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="n_frec_cupon"
                            value={consultaData?.body?.n_frec_cupon ?? ''}
                            onChange={handleChange}
                        >
                            <option value="default">...</option>
                            {getFrequencyCoupon().map((option) => (
                                <option key={generateUUID()} value={option.value}>
                                    {option.label}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_frec_cupon">Forma de Pago</label>
                    </div>
                    <div className="form-date form-date-my">
                        <input
                            type="date"
                            name="d_fec_fin_c"
                            value={consultaData?.body?.d_fec_fin_c ?? ''}
                            onChange={handleChange}
                        />
                        <label htmlFor="fechaFinCupon">Fecha Fin Cupón</label>
                    </div>
                </div>
                <div className="form-cols-3">
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_p_cupon"
                            value={consultaData?.body?.n_p_cupon ?? ''}
                            onChange={handleChange}
                            onFocus={() => handleFocus('n_p_cupon')}
                            onBlur={handleBlur}
                            style={sendStyle('n_p_cupon')}
                        />
                        <label htmlFor="n_p_cupon">Periodo Cupón</label>
                    </div>
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_p_cupon_vig"
                            value={consultaData?.body?.n_p_cupon_vig ?? ''}
                            onChange={handleChange}
                            onFocus={() => handleFocus('n_p_cupon_vig')}
                            onBlur={handleBlur}
                            style={sendStyle('n_p_cupon_vig')}
                        />
                        <label htmlFor="n_p_cupon_vig">Periodo Cupón Vigente</label>
                    </div>
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_num_cupones"
                            value={consultaData?.body?.n_num_cupones ?? ''}
                            onChange={handleChange}
                            onFocus={() => handleFocus('n_num_cupones')}
                            onBlur={handleBlur}
                            style={sendStyle('n_num_cupones')}
                        />
                        <label htmlFor="n_num_cupones">Cupones</label>
                    </div>
                </div>
                <div className="form-cols-3">
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_tasa_cupon_md"
                            value={consultaData?.body?.n_tasa_cupon_md ?? ''}
                            onChange={handleChange}
                            onFocus={() => handleFocus('n_tasa_cupon_md')}
                            onBlur={handleBlur}
                            style={sendStyle('n_tasa_cupon_md')}
                        />
                        <label htmlFor="n_tasa_cupon_md">Tasa</label>
                    </div>
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_tasa_cupon_24"
                            value={consultaData?.body?.n_tasa_cupon_24 ?? ''}
                            onChange={handleChange}
                            onFocus={() => handleFocus('n_tasa_cupon_24')}
                            onBlur={handleBlur}
                            style={sendStyle('n_tasa_cupon_24')}
                        />
                        <label htmlFor="n_tasa_cupon_24h">Tasa Cupón 24</label>
                    </div>
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_num_cupon"
                            value={consultaData?.body?.n_num_cupon ?? ''}
                            onChange={handleChange}
                            onFocus={() => handleFocus('n_num_cupon')}
                            onBlur={handleBlur}
                            style={sendStyle('n_num_cupon')}
                        />
                        <label htmlFor="n_num_cupon">Cupón Vigente</label>
                    </div>
                </div>
                <div className="form-cols-3">
                    <div className="form-select">
                        <select
                            name="n_tipo_promedio"
                            value={consultaData?.body?.n_tipo_promedio ?? ''}
                            onChange={handleChange}
                        >
                            <option value="default">...</option>
                            {getCatalogs(catalog, 'TIPO_PROMEDIO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_tipo_promedio">Tipo Promedio</label>
                    </div>
                    <div className="form-select">
                        <select
                            name="n_calc_dias"
                            value={consultaData?.body?.n_calc_dias ?? ''}
                            onChange={handleChange}
                        >
                            <option value="default">...</option>
                            {getCatalogs(catalog, 'CALCULO_DIAS').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_calc_dias">Calc Días</label>
                    </div>
                    <div className="form-input">
                        <input
                            type="number"
                            name="n_dias_deter_tasa"
                            value={consultaData?.body?.n_dias_deter_tasa ?? ''}
                            onChange={handleChange}
                            onFocus={() => handleFocus('n_dias_deter_tasa')}
                            onBlur={handleBlur}
                            style={sendStyle('n_dias_deter_tasa')}
                        />
                        <label htmlFor="n_dias_deter_tasa">Días Determinación Tasa</label>
                    </div>
                </div>
                <div className="form-cols-3">
                    <div className="form-input">
                        <input
                            type="number"
                            name="n_redondeo_tasa_cupon"
                            value={consultaData?.body?.n_redondeo_tasa_cupon ?? ''}
                            onChange={handleChange}
                            onFocus={() => handleFocus('n_redondeo_tasa_cupon')}
                            onBlur={handleBlur}
                            style={sendStyle('n_redondeo_tasa_cupon')}
                        />
                        <label htmlFor="n_redondeo_tasa_cupon">Redondeo</label>
                    </div>
                </div>
            </div>
        </form>
    )
}