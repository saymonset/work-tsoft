import {ConsultaDataEditCauClient, ResponseApiEditCauClient} from "../model";

export const DataCatMexico = [
        {
                text: 'AGENTE_COLOCADOR',
                columns: [
                        { name: 'N_AGENTE_COLOCADOR', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'BOLSA_COTIZACION',
                columns: [
                        { name: 'N_BOLSA_COTIZACION', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'BUSINESS_RESET_RULE',
                columns: [
                        { name: 'N_BUSINESS_RESET_RULE', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CALENDARIO',
                columns: [
                        { name: 'N_CALENDARIO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CLASIFICACION_SECTORIAL',
                columns: [
                        { name: 'N_CLASIFICACION_SECTORIAL', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'COMPOSICION_YIELD',
                columns: [
                        { name: 'N_COMPOSICION_YIELD', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CONVENCION_DIAS',
                columns: [
                        { name: 'N_CONVENCION_DIAS', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CRV_DESCUENTO',
                columns: [
                        { name: 'N_CRV_DESCUENTO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'S_NOMALGO', type: 'input' },
                        { name: 'N_ID_CONSAR', type: 'input' }
                ]
        },
        {
                text: 'CRV_REFERENCIA',
                columns: [
                        { name: 'N_CRV_REFERENCIA', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CURVE_INDEX',
                columns: [
                        { name: 'N_CURVE_INDEX', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'S_NOMALGO', type: 'input' }
                ]
        },
        {
                text: 'EMISOR',
                columns: [
                        { name: 'N_EMISOR', field: "id", order: 1, type: 'input' },
                        { name: 'S_NOMCORTO', field: "Nombre Corto", order: 3, type: 'input', isReadOnly: true },
                        { name: 'S_DESCRIPCION', field: "Descripcion", order: 5, type: 'input' },
                        { name: 'N_TIPO_EMISOR', field: "Tipo Emisor", order: 2, type: 'select', catalog: "TIPO_EMISOR" },
                        { name: 'N_SECTOR', field: "Sector", order: 4, type: 'select', catalog: "SECTOR-NOMCORTO" },
                        { name: 'S_NOMCORTO_R', field: "Nombre Corto Regimen", order: 6, type: 'input' }
                ]
        },
        {
                text: 'FAMILIA_INSTRUMENTO',
                columns: [
                        { name: 'N_FAMILIA_INSTRUMENTO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'FECHAS_INSCRIPCION',
                columns: [
                        { name: 'N_ID_EMISOR', type: 'input' },
                        { name: 'S_EMISOR', type: 'input' },
                        { name: 'D_FECHA_RNV', type: 'input' },
                        { name: 'D_FECHA_BMV', type: 'input' }
                ]
        },
        {
                text: 'FORMA_COTIZACION',
                columns: [
                        { name: 'N_FORMA_COTIZACION', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'MARKET_MODEL',
                columns: [
                        { name: 'N_MARKET_MODEL', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'MONEDA',
                columns: [
                        { name: 'N_MONEDA', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAIS',
                columns: [
                        { name: 'N_PAIS', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_PAIS_CTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'RAZON_SOCIAL',
                columns: [
                        { name: 'N_RAZON_SOCIAL', field: "id", order: 1,  type: 'input' },
                        { name: 'S_DESCRIPCION', field: "Descripcion", order: 3,  type: 'input' },
                        { name: 'N_TIPO_RAZON_SOCIAL', field: "Tipo Razon Social", order: 2,  type: 'select', catalog: "tipo-deuda"}
                ]
        },
        {
                text: 'REFERENCIA_MERCADO',
                columns: [
                        { name: 'N_REFERENCIA_MERCADO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'REPRESENTANTE_COMUN',
                columns: [
                        { name: 'N_REPRESENTANTE_COMUN', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'SECTOR',
                columns: [
                        { name: 'N_SECTOR', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'SECTOR_ECO',
                columns: [
                        { name: 'N_SECTOR_ECO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'STATUS',
                columns: [
                        { name: 'N_STATUS', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'SUBRAMO',
                columns: [
                        { name: 'N_SUBRAMO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'THEO_MODEL',
                columns: [
                        { name: 'N_THEO_MODEL', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'TIPO',
                columns: [
                        { name: 'N_TIPO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'TIPO_INSTRUMENTO',
                columns: [
                        { name: 'N_TIPO_INSTRUMENTO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'TIPO_MERCADO',
                columns: [
                        { name: 'N_TIPO_MERCADO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'TIPO_RIESGO',
                columns: [
                        { name: 'N_TIPO_RIESGO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'TIPO_TASA',
                columns: [
                        { name: 'N_TIPO_TASA', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'TIPO_TRAC',
                columns: [
                        { name: 'N_ID', type: 'input' },
                        { name: 'S_TV', type: 'input' },
                        { name: 'S_EMISORA', type: 'input' },
                        { name: 'S_SERIE', type: 'input' },
                        { name: 'S_INSTRUMENTO', type: 'input' },
                        { name: 'S_ISIN', type: 'input' },
                        { name: 'S_TIPO', type: 'select', catalog: "tipo-trac" },
                        { name: 'B_CONSAR', type: 'select', catalog: "consar" }
                ]
        },
        {
                text: 'TIPO_VALOR',
                columns: [
                        { name: 'N_TV', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_MERCADO', type: 'input' }
                ]
        }
];

export const DataCatCalif = [
        {
                text: 'BEST',
                columns: [
                        { name: 'N_CALIF_BEST', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_CALIF', type: 'select', catalog: "TIPO_CALIF"},
                        { name: 'N_VALOR', type: 'input' },
                        { name: 'N_PLAZO_CALIF', type: 'select', catalog: "PLAZO_CALIF"},
                        { name: 'S_HOMOLOGACION', type: 'input' },
                        { name: 'N_NIVEL_REGIMEN', type: 'input' }
                ]
        },
        {
                text: 'BOLSA_EMISION',
                columns: [
                        { name: 'N_BOLSA_EMISION', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'COMISION_VALORES',
                columns: [
                        { name: 'N_COMISION_VALORES', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'DBRS',
                columns: [
                        { name: 'N_CALIF_DBRS', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_CALIF', type: 'select', catalog: "TIPO_CALIF"},
                        { name: 'N_VALOR', type: 'input' },
                        { name: 'N_PLAZO_CALIF', type: 'select', catalog: "PLAZO_CALIF"},
                        { name: 'S_HOMOLOGACION', type: 'input' },
                        { name: 'N_NIVEL_REGIMEN', type: 'input' }
                ]
        },
        {
                text: 'FITCH',
                columns: [
                        { name: 'N_CALIF_FITCH', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_CALIF', type: 'select', catalog: "TIPO_CALIF"},
                        { name: 'N_VALOR', type: 'input' },
                        { name: 'N_PLAZO_CALIF', type: 'select', catalog: "PLAZO_CALIF"},
                        { name: 'S_HOMOLOGACION', type: 'input' },
                        { name: 'N_NIVEL_REGIMEN', type: 'input' }
                ]
        },
        {
                text: 'HR',
                columns: [
                        { name: 'N_CALIF_HR', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_CALIF', type: 'select', catalog: "TIPO_CALIF"},
                        { name: 'N_VALOR', type: 'input' },
                        { name: 'N_PLAZO_CALIF', type: 'select', catalog: "PLAZO_CALIF"},
                        { name: 'S_HOMOLOGACION', type: 'input' },
                        { name: 'N_NIVEL_REGIMEN', type: 'input' }
                ]
        },
        {
                text: 'MOODY',
                columns: [
                        { name: 'N_CALIF_MOODY', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_CALIF', type: 'select', catalog: "TIPO_CALIF"},
                        { name: 'N_VALOR', type: 'input' },
                        { name: 'N_PLAZO_CALIF', type: 'select', catalog: "PLAZO_CALIF"},
                        { name: 'S_HOMOLOGACION', type: 'input' },
                        { name: 'N_NIVEL_REGIMEN', type: 'input' }
                ]
        },
        {
                text: 'PLAZO_CALIF',
                columns: [
                        { name: 'N_PLAZO_CALIF', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'SP',
                columns: [
                        { name: 'N_CALIF_SP', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_CALIF', type: 'select', catalog: "TIPO_CALIF"},
                        { name: 'N_VALOR', type: 'input' },
                        { name: 'N_PLAZO_CALIF', type: 'select', catalog: "PLAZO_CALIF"},
                        { name: 'S_HOMOLOGACION', type: 'input' },
                        { name: 'N_NIVEL_REGIMEN', type: 'input' }
                ]
        },
        {
                text: 'TIPO_PAPEL',
                columns: [
                        { name: 'N_TIPO_PAPEL', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'VERUM',
                columns: [
                        { name: 'N_CALIF_VERUM', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_CALIF', type: 'select', catalog: "TIPO_CALIF"},
                        { name: 'N_VALOR', type: 'input' },
                        { name: 'N_PLAZO_CALIF', type: 'select', catalog: "PLAZO_CALIF"},
                        { name: 'S_HOMOLOGACION', type: 'input' },
                        { name: 'N_NIVEL_REGIMEN', type: 'input' }
                ]
        }
];

export const DataCatPanama = [
        {
                text: 'PAN_BASE_CALCULO',
                columns: [
                        { name: 'N_BASE_CALCULO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_BUSINESS_DAY_RULE',
                columns: [
                        { name: 'N_BUSINESS_DAY_RULE', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_CLASE',
                columns: [
                        { name: 'N_CLASE', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_CURVA_DESC',
                columns: [
                        { name: 'N_CURVA_DESC', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_EMISOR',
                columns: [
                        { name: 'N_EMISOR', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'S_CODIGO', type: 'input' }
                ]
        },
        {
                text: 'PAN_FITCH',
                columns: [
                        { name: 'N_FITCH', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_FORM_COTIZACION',
                columns: [
                        { name: 'N_FORM_COTIZACION', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_FRECUENCIA_CUPON',
                columns: [
                        { name: 'N_FRECUENCIA_CUPON', type: 'input' },
                        { name: 'N_PLAZO_CUPON', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_MONEDA',
                columns: [
                        { name: 'N_MONEDA', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_MOODY',
                columns: [
                        { name: 'N_MOODY', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_PAIS',
                columns: [
                        { name: 'N_PAIS', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_SECTOR',
                columns: [
                        { name: 'N_SECTOR', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_SP',
                columns: [
                        { name: 'N_SP', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_STATUS',
                columns: [
                        { name: 'N_STATUS', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_THEO_MODEL',
                columns: [
                        { name: 'N_THEO_MODEL', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'S_MODEL_PRECIO', type: 'input' }
                ]
        },
        {
                text: 'PAN_TIPO_INSTRUMENTO',
                columns: [
                        { name: 'N_TIPO_INSTRUMENTO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'PAN_TIPO_MERCADO',
                columns: [
                        { name: 'N_TIPO_MERCADO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        }
];

export const DataCatCostaRica = [
        {
                text: 'CR_BASE_CALCULO',
                columns: [
                        { name: 'N_BASE_CALCULO', type: 'input', isReadOnly: true },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_CLASIFICACION_RIESGO',
                columns: [
                        { name: 'N_CLASIFICACION_RIESGO', type: 'input', isReadOnly: true },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_COUPON_GEN_METHOD',
                columns: [
                        { name: 'N_COUPON_GEN_METHOD', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_CURVE_INDEX',
                columns: [
                        { name: 'N_CURVE_INDEX', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_CVE_CUSTODIA',
                columns: [
                        { name: 'N_CVE_CUSTODIA', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_DISCOUNT_CURVE',
                columns: [
                        { name: 'N_DISCOUNT_CURVE', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_EDO_INSTRUMENTO',
                columns: [
                        { name: 'N_EDO_INSTRUMENTO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_EMISOR',
                columns: [
                        { name: 'N_EMISOR', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_FITCH',
                columns: [
                        { name: 'N_FITCH', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_TIPO_CALIF', type: 'input' },
                        { name: 'N_NIVEL_CALIF', type: 'input' }
                ]
        },
        {
                text: 'CR_FORMA_COTIZACION',
                columns: [
                        { name: 'N_FORMA_COTIZACION', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_HOMO_VALMER_PIP',
                columns: [
                        { name: 'N_ID', type: 'input' },
                        { name: 'S_ID_VALMER', type: 'input' },
                        { name: 'S_ID_PIP', type: 'input' }
                ]
        },
        {
                text: 'CR_MODO_NEG',
                columns: [
                        { name: 'N_MODO_NEG', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_MONEDA',
                columns: [
                        { name: 'N_MONEDA', type: 'input' },
                        { name: 'S_ISIN', type: 'input' },
                        { name: 'S_COD', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_ODD_COUPON',
                columns: [
                        { name: 'N_ODD_COUPON', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_PAIS',
                columns: [
                        { name: 'N_PAIS', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_PERIODICIDAD',
                columns: [
                        { name: 'N_PERIODICIDAD', type: 'input' },
                        { name: 'N_PLAZO_CUPON', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_RESET_RULE',
                columns: [
                        { name: 'N_RESET_RULE', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_SCR',
                columns: [
                        { name: 'N_SCR', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_PLAZO_CALIF', type: 'input' },
                        { name: 'N_NIVEL_CALIF', type: 'input' }
                ]
        },
        {
                text: 'CR_TASA_FLUCTUANTE',
                columns: [
                        { name: 'N_TASA_FLUCTUANTE', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_THEORETICAL_MODEL',
                columns: [
                        { name: 'N_THEORETICAL_MODEL', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'S_MKT_MODEL', type: 'input' }
                ]
        },
        {
                text: 'CR_TIPO_ACCION',
                columns: [
                        { name: 'N_TIPO_ACCION', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_TIPO_EMISION',
                columns: [
                        { name: 'N_TIPO_EMISION', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_TIPO_PRIVILEGIO',
                columns: [
                        { name: 'N_TIPO_PRIVILEGIO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_TIPO_TASA',
                columns: [
                        { name: 'N_TIPO_TASA', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_TIPO_TITULO',
                columns: [
                        { name: 'N_TIPO_TITULO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CR_TIPO_VALOR',
                columns: [
                        { name: 'N_TIPO_VALOR', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        }
];

export const DataCatCau = [
        {
                text: 'CAU_ACCESOS',
                columns: [
                        { name: 'N_ACCESO', type: 'input' },
                        { name: 'S_ACCESO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'CAU_AREA',
                columns: [
                        { name: 'N_AREA', type: 'input' },
                        { name: 'S_AREA', type: 'input' }
                ]
        },
        {
                text: 'CAU_CLIENTE',
                columns: [
                        { name: 'CLIENTE', type: 'input' },
                        { name: 'EMPRESA', type: 'input' },
                        { name: 'NOMBRE', type: 'input' },
                        { name: 'USUARIO', type: 'input' },
                        { name: 'PASSWORD', type: 'input' }
                ]
        },
        {
                text: 'CAU_EMPRESA',
                columns: [
                        { name: 'N_EMPRESA', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_CALLE', type: 'input' },
                        { name: 'S_COLONIA', type: 'input' },
                        { name: 'S_CP', type: 'input' },
                        { name: 'S_CIUDAD', type: 'input' },
                        { name: 'S_ESTADO', type: 'input' }
                ]
        },
        {
                text: 'CAU_ENCARGADO',
                columns: [
                        { name: 'N_ENCARGADO', type: 'input' },
                        { name: 'S_ENCARGADO', type: 'input' }
                ]
        },
        {
                text: 'CAU_ESCALAMIENTO',
                columns: [
                        { name: 'N_SERVICIO', type: 'select', catalog: "CAU_SERVICIOS"},
                        { name: 'S_SERVICIO', type: 'input', DisabledFieldForm: true},
                        { name: 'S_JEFE', field: "N_JEFE", type: 'select', catalog: "SEG_USUARIOS" },
                        { name: 'N_AVISO1', field: "N_AVISO 1", columnName: 'N_AVISO1(horas)', type: 'input', isReadOnly: false, isReadOnlyEdit: false  },
                        { name: 'N_AVISO2', field: "N_AVISO 2", columnName: 'N_AVISO2(horas)', type: 'input' , isReadOnly: false, isReadOnlyEdit: false }
                ]
        },
        {
                text: 'CAU_SECTOR',
                columns: [
                        { name: 'N_SECTOR', type: 'input' },
                        { name: 'S_SECTOR', type: 'input' }
                ]
        },
        {
                text: 'CAU_SERVICIOS',
                columns: [
                        { name: 'N_SERVICIO', type: 'input' },
                        { name: 'S_AREA', columnName: 'N_AREA', field: "N_AREA", type: 'select', catalog: "CAU_AREA"},
                        { name: 'S_SERVICIO', type: 'input' }
                ]
        },
        {
                text: 'CAU_STATUS',
                columns: [
                        { name: 'N_STATUS', type: 'input' },
                        { name: 'S_STATUS', type: 'input' }
                ]
        },
        {
                text: 'CAU_STATUS_CLI',
                columns: [
                        { name: 'N_STATUS', type: 'input' },
                        { name: 'S_STATUS', type: 'input' }
                ]
        },
        {
                text: 'CAU_USUARIOS',
                columns: [
                        { name: 'N_USUARIO', type: 'select', catalog: "SEG_USUARIOS" },
                        { name: 'S_NOMBRE', type: 'input', DisabledFieldForm: true },
                        { name: 'S_SERVICIO', columnName: 'N_SERVICIO', type: 'select', catalog: "CAU_SERVICIOS" },
                        { name: 'S_ENCARGADO', columnName: 'N_ENCARGADO', type: 'select', catalog: "CAU_ENCARGADO" }
                ]
        }
];

export const DataCauLevels = [
        { name: "Nivel I", value: "n_nivel1" },
        { name: "ASP Riesgos", value: "n_asp" },
        { name: "Benchmarks", value: "n_bench"  },
        { name: "Base Corporativa", value: "n_basecorp"  },
        { name: "Nivel II", value: "n_nivel2"  },
        { name: "Escenarios", value: "n_escenarios"  },
        { name: "Betas", value: "n_betas"  },
        { name: "Vector Fecha Valor", value: "n_vecfecval"  },
        { name: "Nivel III", value: "n_nivel3"  },
        { name: "Niveles de Mercado", value: "n_nivmer"  },
        { name: "Vector INT'L Afores", value: "n_vecintafor"  },
        { name: "Base Calificaciones", value: "n_basecalif"  },
        { name: "Nivel IV", value: "n_nivel4"  },
        { name: "Indices (cerrado)", value: "n_indices"  },
        { name: "Notas sobre Indices", value: "n_notasind"  },
        { name: "Motor Personalizado Benchmarks (cerrado)", value: "n_motorbench"  },
        { name: "Nivel V", value: "n_nivel5"  },
        { name: "Notas Estructuradas", value: "n_notasest"  },
        { name: "Volatilidades", value: "n_volati"  },
        { name: "Indices de Rotacion", value: "n_indrot"  },
        { name: "Curvas", value: "n_curvas"  },
        { name: "Portafolio&Historicos", value: "n_portafolio"  },
        { name: "Base Eurobonos", value: "n_baseeur"  },
        { name: "CAU", value: "n_cau"  },
        { name: "Benchmarks limitado (cerrado)", value: "n_benchlim"  },
        { name: "Vector Aforado", value: "n_vecafor"  },
        { name: "Vector Promedio Aforado (abierto)", value: "n_vecpromafor"  }
];

export const requiredFieldsEditCauClient = [
        { key: 'N_CLIENTE'},
        { key: 'S_NOMBRE'},
        { key: 'S_PUESTO'},
        { key: 'S_TELEFONO'},
        { key: 'S_EMAIL'},
        { key: 'S_USUARIO'},
        { key: 'S_PASSWORD'},
        { key: 'S_AREA'},
        { key: 'S_FAX'},
        { key: 'N_HITS'},
        { key: 'D_FECHA'},
];

export const transformToResponseEditCauClient = (data: ResponseApiEditCauClient): ConsultaDataEditCauClient => {
        const cliente = data.body.registros[0];

        return {
                n_emp: "0",
                n_cli: "0",
                N_CLIENTE: cliente.n_cliente.toString(),
                N_EMPRESA: cliente.n_empresa.toString(),
                S_NOMBRE: cliente.s_nombre,
                S_PUESTO: cliente.s_puesto,
                S_TELEFONO: cliente.s_telefono,
                S_EMAIL: cliente.s_email,
                S_USUARIO: cliente.s_usuario,
                S_PASSWORD: cliente.s_password,
                S_AREA: cliente.s_area,
                N_SECTOR: cliente.n_sector.toString(),
                S_FAX: cliente.s_fax,
                N_HITS: cliente.n_hits.toString(),
                D_FECHA: cliente.d_fecha,
                N_STATUS: cliente.n_status.toString(),
                ...data.body.niveles_servicio
        };
};


export const DataCatArchivos = [
        {
                text: 'ARCHIVOS_CAT_CAMBIO_CMTI',
                columns: [
                        { name: 'S_TV_EMI', type: 'input', isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'S_TV', type: 'input', isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'S_EMISORA', type: 'input', isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'N_CMTI', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_CMAC',
                columns: [
                        { name: 'S_CALIFICACION', type: 'input', isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'N_CMAC', type: 'input', isReadOnly: false, isReadOnlyEdit: false  },
                        { name: 'S_CALIFICADORA', type: 'input' , isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'S_TV', type: 'input', isReadOnly: false, isReadOnlyEdit: false  }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_CMPP',
                columns: [
                        { name: 'S_CLASIFICACION', type: 'input', isReadOnly: false, isReadOnlyEdit: true  },
                        { name: 'N_PLAZO_MIN', type: 'input', isReadOnly: false, isReadOnlyEdit: false  },
                        { name: 'N_PLAZO_MAX', type: 'input', isReadOnly: false, isReadOnlyEdit: false  },
                        { name: 'N_CMPP', type: 'input' , isReadOnly: false, isReadOnlyEdit: false }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_CMRM',
                columns: [
                        { name: 'N_CMRM', type: 'input', isReadOnly: true, isReadOnlyEdit: true },
                        { name: 'N_MINIMO', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'N_MAXIMO', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_CMTI',
                columns: [
                        { name: 'S_TIPO_VALOR', type: 'input', isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'S_TIPO_INSTRUMENTO', type: 'input', isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'N_CMTI', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_DESCRIPCION',
                columns: [
                        { name: 'N_DESCRIPCION', type: 'input', isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'S_DESCRIPCION', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_EMI_SCOTIA',
                columns: [
                        { name: 'S_TV_EMI', type: 'input', isReadOnly: false, isReadOnlyEdit: true  },
                        { name: 'S_TV', type: 'input', isReadOnly: false , isReadOnlyEdit: true },
                        { name: 'S_EMISORA', type: 'input', isReadOnly: false, isReadOnlyEdit: true  }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_FONDOS',
                columns: [
                        { name: 'S_INSTRUMENTO', type: 'input', isReadOnly: false, isReadOnlyEdit: true  },
                        { name: 'S_TV', type: 'input' , isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'S_EMISORA', type: 'input' , isReadOnly: false, isReadOnlyEdit: true },
                        { name: 'S_SERIE', type: 'input', isReadOnly: false, isReadOnlyEdit: true  }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_FONDOS_CMPP',
                columns: [
                        { name: 'S_EMISORA', type: 'input', isReadOnly: false, isReadOnlyEdit: true  },
                        { name: 'S_HORIZONTE', type: 'input', isReadOnly: false, isReadOnlyEdit: false  },
                        { name: 'N_CMPP', type: 'input', isReadOnly: false, isReadOnlyEdit: false  },
                        { name: 'N_DXV', type: 'input', isReadOnly: false, isReadOnlyEdit: false  }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_FONDOS_DESC',
                columns: [
                        { name: 'S_INSTRUMENTO', type: 'input', isReadOnly: false, isReadOnlyEdit: true  },
                        { name: 'S_DESCRIPCION', type: 'input', isReadOnly: false, isReadOnlyEdit: false  }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_SUB_CMRM',
                columns: [
                        { name: 'S_DESCRIPCION', type: 'input', isReadOnly: false , isReadOnlyEdit: true },
                        { name: 'N_CMRM', type: 'input', isReadOnly: false , isReadOnlyEdit: false },
                        { name: 'S_TV', type: 'input', isReadOnly: false, isReadOnlyEdit: false  }
                ]
        },
        {
                text: 'ARCHIVOS_CAT_TV_SIN_CALIF',
                columns: [
                        { name: 'S_TV', type: 'input', isReadOnly: false, isReadOnlyEdit: true }
                ]
        },
        {
                text: 'CAT_INSTR_EMISOR_FIRA',
                columns: [
                        { name: 'N_ID', type: 'input', isReadOnly: true, isReadOnlyEdit: true },
                        { name: 'N_EMISOR', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_INSTRUMENTO', type: 'input', isReadOnly: true, isReadOnlyEdit: true }
                ]
        },
        {
                text: 'CAT_MISMO_EMISOR_FIRA',
                columns: [
                        { name: 'N_EMISOR', type: 'input', isReadOnly: true, isReadOnlyEdit: true  },
                        { name: 'S_EMISOR', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ]
        },
        {
                text: 'PROBABILIDAD_INC_FIRA',
                columns: [
                        { name: 'N_ID', type: 'input', isReadOnly: true, isReadOnlyEdit: true  },
                        { name: 'D_FECHA', type: 'input', isReadOnly: true, isReadOnlyEdit: true  },
                        { name: 'S_FONDO', type: 'input', isReadOnly: true, isReadOnlyEdit: true  },
                        { name: 'N_EMISOR', type: 'input', isReadOnly: true, isReadOnlyEdit: true  },
                        { name: 'N_PROBABILIDAD', type: 'input', isReadOnly: false, isReadOnlyEdit: false  }
                ]
        }
];

export const DataCatBrokers = [
        {
                text: 'BROKER_CAT_BANCARIO',
                columns: [
                        { name: 'N_PLAZO', type: 'input' },
                        { name: 'N_B1', type: 'input' },
                        { name: 'N_B2', type: 'input' },
                        { name: 'N_B3', type: 'input' },
                        { name: 'N_B4', type: 'input' }
                ]
        },
        {
                text: 'BROKER_CAT_BORHIS',
                columns: [
                        { name: 'N_ID', type: 'input' },
                        { name: 'S_TV', type: 'input' },
                        { name: 'S_EMISORA', type: 'input' }
                ]
        },
        {
                text: 'BROKER_CAT_ID_CORP',
                columns: [
                        { name: 'N_ID_BROKER', type: 'input' },
                        { name: 'S_ID_BROKER', type: 'input' },
                        { name: 'S_EMISORA', type: 'input' },
                        { name: 'S_SERIE', type: 'input' }
                ]
        },
        {
                text: 'BROKER_CAT_ID_INSTR',
                columns: [
                        { name: 'N_ID_BROKER', type: 'input' },
                        { name: 'S_ID_BROKER', type: 'input' },
                        { name: 'S_EMI_BROKER', type: 'input' },
                        { name: 'S_EMI_VALMER', type: 'input' },
                        { name: 'S_TV', type: 'input' }
                ]
        },
        {
                text: 'BROKER_CAT_INSTR_ESP',
                columns: [
                        { name: 'N_ID_INSTR', type: 'input' },
                        { name: 'S_INSTRUMENTO', type: 'input' }
                ]
        },
        {
                text: 'BROKER_CAT_PLAZOS',
                columns: [
                        { name: 'N_ID_PLAZO', type: 'input' },
                        { name: 'S_EMISORA', type: 'input' },
                        { name: 'N_PLAZO_FINAL', type: 'input' },
                        { name: 'N_RANGO', type: 'input' },
                        { name: 'S_TV', type: 'input' }
                ]
        },
        {
                text: 'BROKER_CAT_REPO_BANC',
                columns: [
                        { name: 'N_PLAZO', type: 'input' },
                        { name: 'N_REPOB1', type: 'input' },
                        { name: 'N_REPOB2', type: 'input' },
                        { name: 'N_REPOB3', type: 'input' },
                        { name: 'N_REPOB4', type: 'input' }
                ]
        },
        {
                text: 'BROKER_CONFIG_ARCHIVOS',
                columns: [
                        { name: 'N_ID_ARCHIVO', type: 'input' },
                        { name: 'N_ID_BROKER', type: 'select', catalog: "" },
                        { name: 'S_IP_FTP', type: 'input' },
                        { name: 'S_TIPO_FTP', type: 'select', catalog: "tipo-ftp"},
                        { name: 'S_USER_FTP', type: 'input' },
                        { name: 'S_PWD_FTP', type: 'input' },
                        { name: 'S_DIR_FTP', type: 'input' },
                        { name: 'S_ARCH_FTP', type: 'input' },
                        { name: 'S_OPERACION', type: 'select', catalog: "tipo-operacion" },
                        { name: 'B_PRELIMINAR', type: 'select', catalog: "bandera" },
                        { name: 'B_DEFINITIVO', type: 'select', catalog: "bandera" },
                        { name: 'S_NOMBRE_ARCHIVO', type: 'input' },
                        { name: 'N_CAMBIO_HORA_ARCH', type: 'input' },
                        { name: 'S_NOM_PROCESO', type: 'input' },
                        { name: 'S_PERIODO_DEPOSITO', type: 'input' }
                ]
        },
        {
                text: 'BROKER_ELECTRONICO',
                columns: [
                        { name: 'N_ID_BROKER', type: 'input' },
                        { name: 'S_NOMBRE_BROKER', type: 'input' }
                ]
        },
        {
                text: 'BROKER_FESTIVOS_USA',
                columns: [
                        { name: 'N_FECHA_USA', type: 'input' },
                        { name: 'D_FECHA_USA', type: 'input' }
                ]
        },
        {
                text: 'BROKER_FIDEICOMISOS',
                columns: [
                        { name: 'N_ID_INSTR', type: 'input' },
                        { name: 'S_INSTRUMENTO', type: 'input' },
                        { name: 'S_CLAVE_FIDE', type: 'input' }
                ]
        }
];


export const DataPerfilInstrumento = [
        {
                text: 'PERFIL_INSTRUMENTO_ADRS',
                columns: [
                        { name: 'S_INSTR_ADR', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_INSTR_REF', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ],
                edit: true
        },
        {
                text: 'PERFIL_INSTRUMENTO_CAT_FONDOS',
                columns: [
                        { name: 'N_CLASIFICACION', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_DESCRIPCION', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ],
                edit: true
        },
        {
                text: 'PERFIL_INSTRUMENTO_CLAS_FONDOS',
                columns: [
                        { name: 'S_TV', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_EMISORA', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'N_CLASIFICACION', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ],
                edit: true
        },
        {
                text: 'PERFIL_INSTRUMENTO_FONDO_SERIE',
                columns: [
                        { name: 'S_TV', type: 'input', isReadOnly: false },
                        { name: 'S_EMISORA', type: 'input', isReadOnly: false },
                        { name: 'S_SERIE', type: 'input', isReadOnly: false }
                ],
                edit: true
        },
        {
                text: 'PERFIL_INSTRUMENTO_INST_EXC',
                columns: [
                        { name: 'S_TV', type: 'input', isReadOnly: false, isReadOnlyEdit: false  },
                        { name: 'B_PERFIL', type: 'input' , isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'B_LIQUIDEZ', type: 'input' , isReadOnly: false, isReadOnlyEdit: false }
                ],
                edit: true
        },
        {
                text: 'PERFIL_INSTRUMENTO_REGLAS',
                columns: [
                        { name: 'S_TV', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_MERCADO', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_DXV', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_RIESGO_CREDITO', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_CALIFICACION', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_MERCADO_SECUNDARIO', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_PAIS_INSTRUMENTO', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_PAIS_EMISOR', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_DISTRIBUCION', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_CALIF_CUSTODIO', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_TIPO_PAPEL', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_DURACION_MAC', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_BURSATILIDAD', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_RIESGO_LIQUIDEZ', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_VOLATILIDAD', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_RIESGO_MERCADO', type: 'input', isReadOnly: false, isReadOnlyEdit: false },
                        { name: 'S_MODELO_INSTR', type: 'input', isReadOnly: false, isReadOnlyEdit: false }
                ],
                edit: true
        },
        {
                text: 'PERFIL_INSTRUMENTO_SCOTIA',
                columns: [
                        { name: 'N_TIPO_CATALOGO', type: 'input', isReadOnly: false },
                        { name: 'S_TV', type: 'input', isReadOnly: false },
                        { name: 'S_EMISORA', type: 'input', isReadOnly: false },
                        { name: 'S_SERIE', type: 'input', isReadOnly: false },
                        { name: 'S_CODIGO_TECH', type: 'input' , isReadOnly: false}
                ],
                edit: true
        }
];

export const DataCatBaseAccionaria = [
        {
                text: 'BACC_CATEGORIA',
                columns: [
                        { name: 'N_CATEGORIA', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_DOMICILIO',
                columns: [
                        { name: 'N_DOMICILIO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_EMISOR',
                columns: [
                        { name: 'N_EMISOR', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' }
                ]
        },
        {
                text: 'BACC_FOCO_GEOGRAFICO',
                columns: [
                        { name: 'N_FOCO_GEOGRAFICO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_FRECUENCIA_DVD',
                columns: [
                        { name: 'N_FRECUENCIA_DVD', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_MDO_CORTO',
                columns: [
                        { name: 'N_MDO_CORTO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_MDO_LARGO',
                columns: [
                        { name: 'N_MDO_LARGO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_PAIS_MDO',
                columns: [
                        { name: 'N_PAIS_MDO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_SUBRAMO_ING',
                columns: [
                        { name: 'N_SUBRAMO_ING', type: 'input' },
                        { name: 'S_DESCRIPCION', columnName: "N_CLASIFICACION_SECTORIAL", field: "N_CLASIFICACION_SECTORIAL", type: 'select', catalog: "CLASIFICACION_SECTORIAL", columnIndex: 1},
                        { name: 'S_DESCR', field: "S_DESCRIPCION", type: 'input' }
                ]
        },
        {
                text: 'BACC_TIPO_DERECHO',
                columns: [
                        { name: 'N_TIPO_DERECHO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_TIPO_DIVIDENDO',
                columns: [
                        { name: 'N_TIPO_DIVIDENDO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_TIPO_FONDO',
                columns: [
                        { name: 'N_TIPO_FONDO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_TIPO_INSTRUMENTO',
                columns: [
                        { name: 'N_TIPO_INSTRUMENTO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        },
        {
                text: 'BACC_TIPO_PAGO',
                columns: [
                        { name: 'N_TIPO_PAGO', type: 'input' },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_NOMCORTO_ING', type: 'input' }
                ]
        }
];

export const DataCatClassSec = [
        {
                text: 'INEGI_DIVISION',
                columns: [
                        { name: 'N_INEGI_DIVISION', type: 'input', isReadOnly: true },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' },
                        { name: 'N_INEGI_SECTOR', type: 'select', catalog: "INEGI_SECTOR"}
                ]
        },
        {
                text: 'INEGI_GRUPO',
                columns: [
                        { name: 'N_INEGI_GRUPO', type: 'input', isReadOnly: true },
                        { name: 'S_NOMCORTO', type: 'input' },
                        { name: 'S_DESCRIPCION', type: 'input' }
                ]
        },
        {
                text: 'INEGI_RAMO',
                columns: [
                        { name: 'N_INEGI_RAMO', type: 'input', isReadOnly: true },
                        { name: 'S_NOMCORTO', type: 'input' , isReadOnly: false},
                        { name: 'S_DESCRIPCION', type: 'input', isReadOnly: false },
                        { name: 'N_INEGI_DIVISION', type: 'select', catalog: "INEGI_SECTOR"}
                ]
        },
        {
                text: 'INEGI_SECTOR',
                columns: [
                        { name: 'N_INEGI_SECTOR', type: 'input', isReadOnly: true },
                        { name: 'S_NOMCORTO', type: 'input', isReadOnly: false },
                        { name: 'S_DESCRIPCION', type: 'input' , isReadOnly: false}
                ]
        },
        {
                text: 'INEGI_SUBGRUPO',
                columns: [
                        { name: 'N_INEGI_SUBGRUPO', type: 'input', isReadOnly: true },
                        { name: 'S_NOMCORTO', type: 'input', isReadOnly: false },
                        { name: 'S_DESCRIPCION', type: 'input', isReadOnly: false }
                ]
        },
];

