import {FormFieldsType, FormName} from "../model";

export const formFields: FormFieldsType  = {
    formTasHistPriCet: ['n_cetes28', 'n_cetes91', 'n_cetes182', 'n_cetes364'],
    formTaHiFoGu: ['n_fguber28', 'n_fguber91'],
    formTasaTIIE: ['n_tiie28', 'n_tiie91', 'n_tiie182'],
    formTaTreYi5y: ['n_treasury_5y'],
    formTaFoGu: ['n_fguber28d', 'n_fguber91d', 'n_fguber'],
    formFonGuBanx: ['n_apertura_guber', 'n_min_guber', 'n_max_guber', 'n_cierre_guber', 'n_pond_guber'],
    formEuribor: ['n_euribor', 'n_euribor6m'],
    formTaSwa5Y6M3M: ['n_swap_5y_6m_3m'],
    formTaFoBa: ['n_fbanc'],
    formBancBanx: ['n_apertura_banc', 'n_min_banc', 'n_max_banc', 'n_cierre_banc', 'n_pond_banc'],
    formTaIruFeFu: ['n_irusd_fed_funds'],
    formTermSofr: ['n_tsofr1M', 'n_tsofr3M', 'n_tsofr6M', 'n_tsofr12M'],
    formTiieFon1d: ['n_tiie_fondeo_1d'],
    formUsdSofr: ['n_sofr'],
    formIpcMexbol: ['n_ipc_mexbol'],
    formTIIEF: ['n_ind_tiief_nat', 'n_ind_tiief_banc', 'n_tiief_28_acum', 'n_tiief_91_acum', 'n_tiief_182_acum'],
    formAjustSofr: ['n_adjtsofr1W', 'n_adjtsofr1M', 'n_adjtsofr2M', 'n_adjtsofr3M', 'n_adjtsofr6M', 'n_adjtsofr12M']
};

export const formEndpoint: Record<FormName, string> = {
    formTasHistPriCet: "/tasas/actualiza-tasas-cetes",
    formTaHiFoGu: "/tasas/actualiza-tasas-fondeo-guber",
    formTasaTIIE: "/tasas/actualiza-tasas-tiie",
    formTaTreYi5y: "/tasas/actualiza-tasas-treasury-yield-5y",
    formTaFoGu: "/tasas/actualiza-tasas-fondeo-guber-diario",
    formFonGuBanx: "/tasas/actualiza-tasas-fondeo-guber-banxico",
    formEuribor: "/tasas/actualiza-tasas-euribor",
    formTaSwa5Y6M3M: "/tasas/actualiza-tasas-swap-5y6m3m",
    formTaFoBa: "/tasas/actualiza-tasas-fondeo-bancario",
    formBancBanx: "/tasas/actualiza-tasas-fondeo-bancario-banxico",
    formTaIruFeFu: "/tasas/actualiza-tasas-irus-fed-funds",
    formTermSofr: "/tasas/actualiza-tasas-term-sofr",
    formTiieFon1d: "/tasas/actualiza-tasas-tiie-fondeo",
    formUsdSofr: "/tasas/actualiza-tasas-sofr",
    formIpcMexbol: "/tasas/actualiza-tasas-ipc-mexbol",
    formTIIEF: "/tasas/actualiza-tasas-tiie-fondeo-acum",
    formAjustSofr: "/tasas/actualiza-tasas-adj-sofr"
}