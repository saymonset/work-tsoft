import {Catalogo, RegistroEdit} from "../model";

export const procesarEvaluate = (nameCatalog: string, request: RegistroEdit, catalogStatic: Catalogo[]) => {
    switch (nameCatalog.toLowerCase()) {
        case 'cau_escalamiento':
            procesarCatalogoCauEscalamiento(request, catalogStatic);
            break;
        case 'cau_servicios':
            procesarCatalogoCauServicios(request, catalogStatic);
            break;
        case 'cau_usuarios':
            procesarCatalogoCauUsuarios(request, catalogStatic);
            break;
    }
}

const procesarCatalogoCauEscalamiento = (request: RegistroEdit, catalogStatic: Catalogo[]): void => {
    const catalogoObj = catalogStatic.find(catalogo => catalogo.catalogo === "CAU_SERVICIOS");
    if (catalogoObj && request['n_servicio']) {
        const sServicioValue = catalogoObj.registros[request['n_servicio']];

        if (sServicioValue) {
            request['s_servicio'] = sServicioValue;
        }
    }

    const sJefeNumber = request['s_jefe'];
    const catalogoJefe = catalogStatic.find(catalogo => catalogo.catalogo === "SEG_USUARIOS");
    if (catalogoJefe && sJefeNumber) {
        const sJefeValue = catalogoJefe.registros[sJefeNumber];
        if (sJefeValue) {
            request['n_jefe'] = sJefeNumber;
            request['s_jefe'] = sJefeValue;
        }
    }
};

const procesarCatalogoCauServicios = (request: RegistroEdit, catalogStatic: Catalogo[]): void => {
    const sArea = request['s_area'];

    const catalogoObj = catalogStatic.find(catalogo => catalogo.catalogo === "CAU_AREA");

    if (catalogoObj && sArea) {
        const sAreaValue = catalogoObj.registros[sArea];

        if (sAreaValue) {
            request['n_area'] = sArea;
            request['s_area'] = sAreaValue;
        }
    }
};

const procesarCatalogoCauUsuarios = (request: RegistroEdit, catalogStatic: Catalogo[]): void => {
    procesarServicio(request, catalogStatic);
    procesarEncargado(request, catalogStatic);
    asignarNombreUsuario(request, catalogStatic);
};

const procesarServicio = (request: RegistroEdit, catalogStatic: Catalogo[]): void => {
    const catalogoServicio = catalogStatic.find(catalogo => catalogo.catalogo === "CAU_SERVICIOS");
    if (!catalogoServicio) return;

    const sServicio = request['s_servicio'];
    const nServicio = esNumero(sServicio) ? sServicio : buscarClavePorValor(catalogoServicio.registros, sServicio);

    if (nServicio && catalogoServicio.registros[nServicio]) {
        request['n_servicio'] = nServicio;
        request['s_servicio'] = catalogoServicio.registros[nServicio];
    }
};

const procesarEncargado = (request: RegistroEdit, catalogStatic: Catalogo[]): void => {
    const catalogoEncargado = catalogStatic.find(catalogo => catalogo.catalogo === "CAU_ENCARGADO");
    if (!catalogoEncargado) return;

    const sEncargado = request['s_encargado'];
    const nEncargado = esNumero(sEncargado) ? sEncargado : buscarClavePorValor(catalogoEncargado.registros, sEncargado);

    if (nEncargado && catalogoEncargado.registros[nEncargado]) {
        request['n_encargado'] = nEncargado;
        request['s_encargado'] = catalogoEncargado.registros[nEncargado];
    }
};

const asignarNombreUsuario = (request: RegistroEdit, catalogStatic: Catalogo[]): void => {
    const catalogoNombre = catalogStatic.find(catalogo => catalogo.catalogo === "SEG_USUARIOS");
    if (!catalogoNombre || !request['n_usuario']) return;

    const sNombreValue = catalogoNombre.registros[request['n_usuario']];
    if (sNombreValue) {
        request['s_nombre'] = sNombreValue;
    }
};

const buscarClavePorValor = (registros: { [key: string]: string }, valor: string): string | undefined => {
    return Object.keys(registros).find(key => registros[key] === valor);
};

const esNumero = (valor: any): boolean => {
    return !isNaN(parseInt(valor)) && isFinite(valor);
};

export const validRowClick = (nameCatalog: string,
                              catalogStatic: Catalogo[],
                              registro: { id: string; [key: string]: string }) => {
    if (nameCatalog === "BACC_SUBRAMO_ING") {

        const catalogoObj = catalogStatic.find(catalogo => catalogo.catalogo === "clase-sectorial");

        console.log(catalogoObj)

        if (catalogoObj && registro['s_clasificacion_sectorial']) {
            const sectorialID = Object.keys(catalogoObj.registros).find(key =>
                catalogoObj.registros[key] === registro['s_clasificacion_sectorial']
            );

            console.log(registro['s_clasificacion_sectorial'])
            console.log(sectorialID)

            if (sectorialID) {
                registro['n_clasificacion_sectorial'] = sectorialID;
                delete registro['s_clasificacion_sectorial'];
            }
            else
            {
                registro['n_clasificacion_sectorial'] = "default";
            }
        }
    }
}

const buildIdFunctions: Record<string, (req: {[key: string]: string }) => string> = {
    PERFIL_INSTRUMENTO_REGLAS: (req) => `${req['s_tv']}_${req['s_mercado']}`,
    PERFIL_INSTRUMENTO_INST_EXC: (req) => req['s_tv'],
    PERFIL_INSTRUMENTO_ADRS: (req) => req['s_instr_adr'],
    PERFIL_INSTRUMENTO_CAT_FONDOS: (req) => req['n_clasificacion'],
    PERFIL_INSTRUMENTO_CLAS_FONDOS: (req) => `${req['s_tv']}_${req['s_emisora']}`,
    PERFIL_INSTRUMENTO_FONDO_SERIE: (req) => `${req['s_tv']}_${req['s_emisora']}_${req['s_serie']}`,
    PERFIL_INSTRUMENTO_SCOTIA: (req) => `${req['n_tipo_catalogo']}_${req['s_tv']}`
};

export const buildIdPerfiles = (request: {[key: string]: string }, nameCatalog: string): string => {
    const buildFunction = buildIdFunctions[nameCatalog];
    return buildFunction ? buildFunction(request) : "";
};


