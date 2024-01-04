import { MutableRefObject } from "react";
import {Catalogo} from "../deuda";
import { InputOrSelect, SelectOrNull } from "../Models";

export interface RatingAgency {
    [agency: string]: Catalogo[];
}

export interface ApiResponseCalificaciones {
    status: number;
    body: RatingAgency;
}

export interface CalifProgramas {
    [key: string]: string;
    s_tv: string;
    s_emisora: string;
    n_plazo_calif: string;
    n_emisor: string;
    n_calif_fitch_n: string;
    n_calif_fitch_g: string;
    d_fec_fitch: string;
    n_calif_sp_n: string;
    n_calif_sp_g: string;
    d_fec_sp: string;
    n_calif_moody_n: string;
    n_calif_moody_g: string;
    d_fec_moody: string;
    n_calif_hr_n: string;
    n_calif_hr_g: string;
    d_fec_hr: string;
    n_calif_verum_n: string;
    n_calif_verum_g: string;
    d_fec_verum: string;
    n_calif_dbrs_n: string;
    n_calif_dbrs_g: string;
    d_fec_dbrs: string;
    n_calif_best_n: string;
    n_calif_best_g: string;
    d_fec_best: string;
    d_fecha: string;
}

export const DefaultValuesProgramas: CalifProgramas = {
    s_tv: "",
    s_emisora: "",
    n_plazo_calif: "",
    n_emisor: "",
    n_calif_fitch_n: "0",
    n_calif_fitch_g: "0",
    d_fec_fitch: "",
    n_calif_sp_n: "0",
    n_calif_sp_g: "0",
    d_fec_sp: "",
    n_calif_moody_n: "0",
    n_calif_moody_g: "0",
    d_fec_moody: "",
    n_calif_hr_n: "0",
    n_calif_hr_g: "0",
    d_fec_hr: "",
    n_calif_verum_n: "0",
    n_calif_verum_g: "0",
    d_fec_verum: "",
    n_calif_dbrs_n: "0",
    n_calif_dbrs_g: "0",
    d_fec_dbrs: "",
    n_calif_best_n: "0",
    n_calif_best_g: "0",
    d_fec_best: "",
    d_fecha: "",
  };

export interface CalifEmisoraData {
    [key: string]: any;
    n_emisor: string;
    n_pais: string;
    s_entidad: string;
    s_fitch_lp_n: string;
    s_fitch_cp_n: string;
    s_fitch_lp_g: string;
    s_fitch_cp_g: string;
    d_fec_fitch: string;
    s_evento_fitch: string;
    s_pdf_fitch: string;
    fitch_b64: string;
    s_sp_lp_n: string;
    s_sp_cp_n: string;
    s_sp_lp_g: string;
    s_sp_cp_g: string;
    d_fec_sp: string;
    s_evento_sp: string;
    s_pdf_sp: string;
    sp_b64: string;
    s_moody_lp_n: string;
    s_moody_cp_n: string;
    s_moody_lp_g: string;
    s_moody_cp_g: string;
    d_fec_moody: string;
    s_evento_moody: string;
    s_pdf_moody: string;
    moody_b64: string;
    s_hr_lp_n: string;
    s_hr_cp_n: string;
    s_hr_lp_g: string;
    s_hr_cp_g: string;
    d_fec_hr: string;
    s_evento_hr: string;
    s_pdf_hr: string;
    hr_b64: string;
    s_verum_lp_n: string;
    s_verum_cp_n: string;
    s_verum_lp_g: string;
    s_verum_cp_g: string;
    d_fec_verum: string;
    s_evento_verum: string;
    s_pdf_verum: string;
    verum_b64: string;
    s_dbrs_lp_n: string;
    s_dbrs_cp_n: string;
    s_dbrs_lp_g: string;
    s_dbrs_cp_g: string;
    d_fec_dbrs: string;
    s_evento_dbrs: string;
    s_pdf_dbrs: string;
    dbrs_b64: string;
    s_best_lp_n: string;
    s_best_cp_n: string;
    s_best_lp_g: string;
    s_best_cp_g: string;
    d_fec_best: string;
    s_evento_best: string;
    s_pdf_best: string;
    best_b64: string;
}

export interface CalifInstData {
    [key: string]: string;
    n_calif_verum_g: string,
    n_calif_moody_g: string,
    s_bolsa_emi: string,
    s_com_val: string,
    n_calif_moody_n: string,
    n_calif_fitch_g: string,
    n_calif_fitch_n: string,
    s_tipo_papel: string,
    s_pdf_hr: string,
    s_pdf_verum: string,
    s_evento_hr: string,
    s_evento_fitch: string,
    s_moneda: string,
    n_calif_dbrs_g: string,
    s_pdf_sp: string,
    d_fec_moody: string,
    s_evento_moody: string,
    s_cusip: string,
    d_fec_venc: string,
    s_serie: string,
    d_fec_fitch: string,
    d_fec_dbrs: string,
    n_calif_dbrs_n: string,
    d_fec_verum: string,
    d_fecha_subasta: string,
    d_fec_sp: string,
    s_rep_comun: string,
    s_pdf_fitch: string,
    n_calif_sp_g: string,
    s_isin: string,
    s_pdf_best: string,
    s_evento_sp: string,
    s_agen_col: string,
    s_emisor: string,
    s_pdf_dbrs: string,
    n_calif_verum_n: string,
    s_tv: string,
    n_calif_hr_n: string,
    s_evento_dbrs: string,
    s_evento_verum: string,
    s_evento_best: string,
    n_calif_best_g: string,
    d_fec_best: string,
    s_emisora: string,
    d_fec_hr: string,
    n_calif_sp_n: string,
    s_pdf_moody: string,
    d_fec_ins: string,
    n_calif_hr_g: string,
    n_calif_best_n: string,
    s_pais: string,
    d_fecha_emision: string
  }

  export const ValuesNewInstr: CalifInstData = {
    n_calif_verum_g: "",
    n_calif_moody_g: "",
    s_bolsa_emi: "29",
    s_com_val: "29",
    n_calif_moody_n: "",
    n_calif_fitch_g: "",
    n_calif_fitch_n: "",
    s_tipo_papel: "",
    s_pdf_hr: "",
    s_pdf_verum: "",
    s_evento_hr: "",
    s_evento_fitch: "",
    s_moneda: "8",
    n_calif_dbrs_g: "",
    s_pdf_sp: "",
    d_fec_moody: "",
    s_evento_moody: "",
    s_cusip: "",
    d_fec_venc: "",
    s_serie: "",
    d_fec_fitch: "",
    d_fec_dbrs: "",
    n_calif_dbrs_n: "",
    d_fec_verum: "",
    d_fecha_subasta: "",
    d_fec_sp: "",
    s_rep_comun: "",
    s_pdf_fitch: "",
    n_calif_sp_g: "",
    s_isin: "",
    s_pdf_best: "",
    s_evento_sp: "",
    s_agen_col: "",
    s_emisor: "",
    s_pdf_dbrs: "",
    n_calif_verum_n: "",
    s_tv: "",
    n_calif_hr_n: "",
    s_evento_dbrs: "",
    s_evento_verum: "",
    s_evento_best: "",
    n_calif_best_g: "",
    d_fec_best: "",
    s_emisora: "",
    d_fec_hr: "",
    n_calif_sp_n: "",
    s_pdf_moody: "",
    d_fec_ins: "",
    n_calif_hr_g: "",
    n_calif_best_n: "",
    s_pais: "69",
    d_fecha_emision: ""
  }

  export interface CatalogoCalificadoras {
    status: number;
    body: {
        fitch: Catalogo[];
        "Standard & Poors": Catalogo[];
        Moodys: Catalogo[];
        Hr: Catalogo[];
        Verum: Catalogo[];
        DBRS: Catalogo[];
        BEST: Catalogo[];
    };
}

export interface FormInstProps {
    isNewInst: boolean;
    isNewSerie: boolean;
    isFondos: boolean;
    loadingCatalog: boolean;
    loadingCatalogCalif: boolean;
    catalog: Catalogo[];
    catalogCalif: CatalogoCalificadoras;
    selectedTv: string;
    tv: string[]
    loadingTv: boolean;
    selectedEmisora: string;
    emisoras: string[];
    loadingEmisoras: boolean;
    selectedSerie: string;
    series: string[];
    loadingSeries: boolean;
    consultaData: CalifInstData;
    loadingConsultaData: boolean;
    loadingSave: boolean;
    isFieldReqCalifInst: IsFieldReqCalifInst;
    refReqInst: RefReqInst;
    requiredTv: boolean;
    requiredEmisora: boolean;
    requiredSerie: boolean;
    setIsFondos: React.Dispatch<React.SetStateAction<boolean>>;
    handleChange: (e: React.ChangeEvent<HTMLInputElement | 
                                        HTMLSelectElement | 
                                        HTMLTextAreaElement>) => void;
    handleChangeFile: (e: React.ChangeEvent<HTMLInputElement>, section: string) => void;
    handleTv: (e: any) => void;
    handleEmisora: (e: any) => void;
    handleSerie: (e: any) => void;
    handleNuevo: () => void;
    handleNuevaSerie: () => void;
    handleGuardar: () => void;
    handleLimpiar: () => void;
}

export interface IsFieldReqCalifEmi {
    [key: string]: boolean;
    n_emisor: boolean;
    n_pais: boolean;
}

export interface RefReqEmi {
    [key: string]: React.MutableRefObject<SelectOrNull>;
    n_emisor: React.MutableRefObject<SelectOrNull>;
    n_pais: React.MutableRefObject<SelectOrNull>;
}

export const fieldToValidateCalifEmi = [
    { name: 'n_emisor', defaultValue: 'default' },
    { name: 'n_pais', defaultValue: 'default' }
]

export interface IsFieldReqCalifProg {
    [key: string]: boolean;
    s_tv: boolean;
    s_emisora: boolean;
    n_plazo_calif: boolean;
}

export interface RefReqProg {
    [key: string]: React.MutableRefObject<SelectOrNull>;
    s_tv: React.MutableRefObject<SelectOrNull>;
    s_emisora: React.MutableRefObject<SelectOrNull>;
    n_plazo_calif: React.MutableRefObject<SelectOrNull>;
}

export const fieldToValidateCalifProg = [
    { name: 's_emisora', defaultValue: 'default' },
    { name: 'n_plazo_calif', defaultValue: 'default' }
]

export interface IsFieldReqCalifInst {
    [key: string]: boolean;
    s_tv: boolean;
    s_emisora: boolean;
    s_serie: boolean;
    s_pais: boolean;
    s_moneda: boolean;
    s_emisor: boolean;
    s_tipo_papel: boolean;
    s_com_val: boolean;
    s_bolsa_emi: boolean;
}

export interface RefReqInst {
    [key: string]: React.MutableRefObject<InputOrSelect>;
    s_tv: React.MutableRefObject<InputOrSelect>;
    s_emisora: React.MutableRefObject<InputOrSelect>;
    s_serie: React.MutableRefObject<InputOrSelect>;
    s_pais: React.MutableRefObject<SelectOrNull>;
    s_moneda: MutableRefObject<SelectOrNull>;
    s_emisor: MutableRefObject<SelectOrNull>;
    s_tipo_papel: MutableRefObject<SelectOrNull>;
    s_com_val: MutableRefObject<SelectOrNull>;
    s_bolsa_emi: MutableRefObject<SelectOrNull>;
}

export const fieldToValidateCalifInst = [
    { name: 's_pais', defaultValue: 'default' },
    { name: 's_moneda', defaultValue: 'default' },
    { name: 's_emisor', defaultValue: 'default' },
    { name: 's_tipo_papel', defaultValue: 'default' },
    { name: 's_com_val', defaultValue: 'default' },
    { name: 's_bolsa_emi', defaultValue: 'default' }
]