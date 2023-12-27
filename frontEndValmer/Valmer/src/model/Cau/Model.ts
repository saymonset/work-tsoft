export interface ResponseDataTableCau {
    status: number
    body: DataTableCau | DataTableHist
}

export interface DataTableCau {
    total_registros: number
    total_paginas: number
    registros: DataTableCauAbiertos[]
}

export interface DataTableHist {
    [key: string]: DataTableCauHist
}

export interface DataTableCauAbiertos {
    n_folio: number
    n_area: string
    n_servicio: string
    n_empresa: string
    n_status: string
    d_fecha: string
    s_hora?: string
    d_fecha_cierre?: string
    s_hora_cierre?: string
    email?: string
}

export interface DataTableCauHist {
    Folio: string
    Area: string
    Servicio: string
    Empresa: string
    Status: string
    Fecha: string
    Cliente: string
    Telefono: string
    Correo: string
    Descripcion: string
    Archivo: string
}

export interface FvDataHist {
    n_status: string
    d_fecha_inicio: string
    d_fecha_fin: string
}

export interface InfoCauId {
    n_folio: number
    n_area: string
    n_servicio: string
    n_empresa: string
    n_status: string
    s_descripcion: string
    n_serv_aux: number
    s_nombre: string
    s_correo: string
    s_telefono: string
    d_fecha_estimada: string
    s_observaciones: string
    archivo: string
    n_usuario_asig: number
    s_usuario_asig: string
    s_atendio?: string
}

export interface PassExpInfo {
    ftp: string
    fecha_exp: string
    dias: string
    fuente: string
}

export interface HomeGraphics {
    peticiones: number
    area: string
    mes: string
}

export interface InstGraphics {
    peticiones: number
    empresa: string
}

export interface AreaGraphics {
    peticiones: number
    area?: string
    cliente?: string
    servicio?: string
}

export interface CatUser {
    n_usuario: number
    s_nombre: string
    s_email: string
}