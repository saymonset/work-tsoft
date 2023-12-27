export interface ResponseDataSubastas {
    status: number;
    body: {
        catalogos: CatalogoSubastas[];
    }
}
export interface SubastasProps {
    title: string;
    isSub: boolean
}
export interface SubFormProps {
    isSub: boolean;
}

export interface CatalogoSubastas {
    catalogo: string;
    listado: string[];
    registros: string[];
}

export interface ConsultaSubastas {
    status: number;
    body: Instrumento[];
}

export type ConsultaSubastasTransformArray = ConsultaSubastasTransform[];

export interface ConsultaSubastasTransform {
    [key: string]: Instrumento;
}

export interface Instrumento {
    d_fecha_subasta: string;
    s_instrumento: string;
    s_plazo: string;
    n_tasa: string;
    d_fecha_colocacion: string;
    d_fecha: string;
    s_instr: string;
    n_monto: string;
    [key: string]: string;
}

export const keysOfInstrumento: (keyof Instrumento)[] = [
    'd_fecha_subasta',
    's_plazo',
    'n_tasa',
    'd_fecha_colocacion',
    'd_fecha',
    's_instr',
    'n_monto'
];

export const initialValuesSub: Instrumento[] = [
    {
        "d_fecha_subasta": "",
        "s_instrumento": "tasa_obj",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "cete28",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "cete91",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "cete182",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "cete364",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "bonos3",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "bonos5",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "bonos10",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "bonos20",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "bonos30",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "udibonos3",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "udibonos5",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "udibonos10",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "udibonos20",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "udibonos30",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "inpcq",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "inpcm",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "cpp",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "sm",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "embi",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "euro",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "yen",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    }
]

export const initialVariableSubFlotantes: Instrumento[] = [
    {
        "d_fecha_subasta": "",
        "s_instrumento": "lf1",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "lf1",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "lf2",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "lf2",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "lf3",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "lf3",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "lf5",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "lf5",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "im3",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "im3",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "iq5",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "iq5",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "is7",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    },
    {
        "d_fecha_subasta": "",
        "s_instrumento": "is7",
        "s_plazo": "",
        "n_tasa": "",
        "d_fecha_colocacion": "",
        "d_fecha": "",
        "s_instr": "",
        "n_monto": ""
    }
]