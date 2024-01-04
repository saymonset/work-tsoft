import {Catalogo, RegistroEdit} from "../model";

export const procesarEvaluate = (nameCatalog: string, request: RegistroEdit, catalogStatic: Catalogo[]) => {
    switch (nameCatalog.toLowerCase()) {
        case 'bacc_subramo_ing':
            procesarCatalogoBaccSubramoIng(request);
            break;
        case 'cau_escalamiento':
            procesarCatalogoCauEscalamiento(request, catalogStatic);
            break;
        case 'cau_servicios':
            procesarCatalogoCauServicios(request, catalogStatic);
            break;
        case 'cau_usuarios':
            procesarCatalogoCauUsuarios(request, catalogStatic);
            break;
        case 'broker_cat_plazos':
            procesarCatalogoBrokerCatPlazos(request);
            break;
    }
}

const procesarCatalogoBrokerCatPlazos = (request: RegistroEdit): void => {
    const nRango = request['n_rango'];

    const partes = nRango.split('-');
    if (partes.length !== 2 || !esNumero(partes[0]) || !esNumero(partes[1])) {
        throw new Error("Formato inválido: n_rango debe ser del formato 'numero-numero'");
    }

    const numero1 = parseInt(partes[0], 10);
    const numero2 = parseInt(partes[1], 10);
    const resultado = numero1 - numero2;

    request['n_rango'] = resultado.toString();
};


const procesarCatalogoBaccSubramoIng = (request: RegistroEdit): void => {
    const sDescripcionValue = request['s_descripcion'];
    if (sDescripcionValue) {
        request['n_clasificacion_sectorial'] = sDescripcionValue;
    }
};

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
