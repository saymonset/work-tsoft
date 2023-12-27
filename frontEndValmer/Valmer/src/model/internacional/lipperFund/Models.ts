export interface ResponseDataTableHead {
    status: number
    body: FileStatus
}

export interface FileStatus {
    [key: string]: string | FileDetails
}

export interface FileDetails {
    fecha_mod: string
    hora_mod: string
    lineas: string
    tamanio: string
}

export interface ConsultaLipper {
    status: number
    body: InstrumentoLipper[]
}

export interface FormValuesLipper {
    [key: string]: string
}

export interface InstrumentoLipper extends FormValuesLipper {
    rowNum: string
    instrumento: string
    isin: string
    ric: string
    status: string
    valida_bbva: string
    moneda: string
    precio_mercado: string
}
