
export type  RegistrosUMSMexCat = {
    S_ISIN: string
    S_RIC_FORMATO: string
    S_PROVEDOR: string
  }

export type RegistrosLiqLatam =  {
  id_reu_formato: number,
  s_formato: string
  s_ric: string
  isin: string
  s_tipo:string
  s_instrumento :string
}

export interface IUmsMexCat {
  status: number
  message?:string
  body: {
    total_registros: number
    total_paginas: number
    registros: RegistrosUMSMexCat[]
  }
}


export interface ILiqLatam {
  status: number
  message?:string
  body: {
    total_registros: number
    total_paginas: number
    registros: RegistrosLiqLatam[]
  }
}