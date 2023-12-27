export interface BDBody {
    BD: string,
    horario: string,
}

export interface GraphCau {
    name: string;
    value: number;
}

export interface Cau {
    num: number;
    descripcion: string;
}

export interface ResponseCau {
    status: number;
    body: Cau[];
}

export interface HorarioBarra {
    name: string;
    Definitivo?: number;
    Gubernamental?: number;
    Definitivo_Notas_PV?: number;
    Preliminar?: number;
    Def_Notas_Estruct?: number;
}

export interface ResponseDataHome {
    status: number;
    body: Insumo[];
}

export interface Insumo {
    idInsumo: number;
    fecha: string;
    insumo: string;
    hora: string;
}