export interface LoginRequest {
    body: Login
}

interface Login
{
    nIdUsuario: number
    sNombre: string
    sUser: string
    sPass: string
    sArea: string
    sMail: string
    sTel: string
    sPuesto: string
    nEmpleado: any
    nStatus: number
    nIntentos: number
    userEncoded: string
}