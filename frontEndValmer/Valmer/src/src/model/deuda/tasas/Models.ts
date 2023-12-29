export type FormFieldsType = { [key: string]: string[] };
export type FormsType = { [key: string]: { [key: string]: string } };

export type FormName = 'formTasHistPriCet' | 'formTaHiFoGu' | 'formTasaTIIE' | 'formTaTreYi5y' | 'formTaFoGu'
    | 'formFonGuBanx' | 'formEuribor' | 'formTaSwa5Y6M3M' | 'formTaFoBa' | 'formBancBanx' | 'formTaIruFeFu'
    | 'formTermSofr' | 'formTiieFon1d' | 'formUsdSofr' | 'formIpcMexbol' | 'formTIIEF' | 'formAjustSofr';

export interface TasasConsultaResponse {
    n_euribor: string;
    n_euribor6m: string;
    n_fbanc: string;
    n_fguber28: string;
    n_fguber91: string;
    n_cetes28: string;
    n_cetes91: string;
    n_cetes182: string;
    n_cetes364: string;
    n_fguber28d: string;
    n_fguber91d: string;
    n_fguber: string;
    n_tiie28: string;
    n_tiie91: string;
    n_tiie182: string;
    n_tsofr1M: string;
    n_tsofr3M: string;
    n_tsofr6M: string;
    n_tsofr12M: string;
    n_tiie_fondeo_1d: string;
    n_treasury_5y: string;
    n_apertura_guber: string;
    n_cierre_guber: string;
    n_min_guber: string;
    n_max_guber: string;
    n_pond_guber: string;
    n_apertura_banc: string;
    n_cierre_banc: string;
    n_min_banc: string;
    n_max_banc: string;
    n_pond_banc: string;
    n_swap_5y_6m_3m: string;
    n_irusd_fed_funds: string;
    n_sofr: string;
    n_ipc_mexbol: string;
    n_adjtsofr1W: string;
    n_adjtsofr1M: string;
    n_adjtsofr2M: string;
    n_adjtsofr3M: string;
    n_adjtsofr6M: string;
    n_adjtsofr12M: string;
    n_ind_tiief_nat: string;
    n_ind_tiief_banc: string;
    n_tiief_28_acum: string;
    n_tiief_91_acum: string;
    n_tiief_182_acum: string;
}