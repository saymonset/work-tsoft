import { BarLoader, MoonLoader } from "react-spinners";
import { useHandleDataUserWeb } from "../hooks"
import { getCatalogs } from "../../../../../utils";
import { ButtonContent } from "../../../../../shared";
import { UserBloqueo } from "./UserBloqueo";
import { UserChangePwd } from "./UserChangePwd";
import { ProductosTrial } from "./ProductosTrial";
import { HistoricoTrial } from "./HistoricoTrial";
import { GenerarArchivo } from "./GenerarArchivo";

export const UserWebForm = () => {

    const {
        catalogoInst, 
        loadingInst, 
        catNom, 
        loadingCatNom,
        catTipoUser,
        loadingTipoUser,
        catUri,
        loadingCatUri,
        loadingSector,
        selectedInstitucion,
        catSector,
        selectedSector,
        loadingInfo,
        selectedNombre,
        infoUser,
        loadingSave,
        selectedUri,
        loadingUri,
        uriInfo,
        liga,
        loadingGeneraArch,
        linkArchPermisos,
        triggerInfoTrial,
        triggerProducts,
        dataTable,
        dataUri,
        handleClickInstitucion,
        handleClickSector,
        handleClickNombre,
        handleChage,
        handleSaveUser,
        handleUri,
        handleProcesosPermisos,
        downloadProcesosPermisos,
        setTriggerInfoTrial,
        setTriggerProducts,
        setDataTable,
        searchDataUri
    } = useHandleDataUserWeb()

    if (loadingInst || !catalogoInst.length ||
        loadingTipoUser || !catTipoUser.body ||
        loadingCatUri || !catUri.body || !dataUri) {
        return (
            <div className="flex justify-center items-center h-full">
              {loadingInst || loadingTipoUser || loadingCatUri ? (
                  <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
              ) : (
                  <div>No hay información</div>
              )}
            </div>
        );
    }

    return (
        <>
            <div className="form-cols-3 -my-3">
                <div className="form-cols-2 col-span-2">
                    <span className="form-title col-span-2">Compañía</span>
                    <div className="form-select">
                        <select 
                            name="n_institucion"
                            id="n_institucion"
                            value={selectedInstitucion ?? ''}
                            onChange={handleClickInstitucion}
                        >
                            <option value="default">...</option>
                            {getCatalogs(catalogoInst, 'INSTITUCION').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="institucion">INSTITUCION</label>
                    </div>
                    <div className="form-select">
                        <select 
                            name="n_sector" 
                            id="n_sector"
                            value={selectedSector ?? ''}
                            onChange={handleClickSector}
                        >
                            <option value="0">...</option>
                            {catSector.map((item) => (
                                <option key={item.id_sector} value={item.id_sector}>
                                    {item.nombre}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="sector">SECTOR</label>
                        {loadingSector && <BarLoader className="mt-2" color="#059669" width={200} />}
                    </div>
                </div>
                <div className="form-cols-1 col-span-1">
                    <span className="form-title">Usuario</span>
                    <div className="form-select">
                        <select 
                            name="n_nombre" 
                            id="n_nombre"
                            value={selectedNombre}
                            onChange={handleClickNombre}
                        >
                            <option value="0">...</option>
                            {catNom.body && Object.entries(catNom.body).map((item) => (
                                <option key={item[0]} value={item[0]}>
                                    {item[1]}
                                </option>
                            ))}
                        </select>
                        {loadingCatNom && <BarLoader className="mt-2" color="#059669" width={200} />}
                        <label htmlFor="nombre">NOMBRE</label>
                    </div>
                </div>
            </div>
            <div className="flex mt-3">
                <button className="btn">RECARGAR</button>
                <button 
                    className="btn"
                    onClick={handleSaveUser}
                >
                    <ButtonContent name="Guardar" loading={loadingSave} />
                </button>
            </div>
            
            <div className="line"/>
            
            <div className="flex justify-center -mt-3">
                <div className="grid grid-rows-2 gap-4 w-2/5">
                    <span className="form-title w-full">Consulta Usuarios Proceso Permisos</span>
                    <div className="form-cols-3">
                        <span className="text-center mt-2">CONSULTA</span>
                        <button 
                            className="btn"
                            onClick={handleProcesosPermisos}
                        >
                            <ButtonContent name="Generar" loading={loadingGeneraArch} />
                        </button>
                        {linkArchPermisos && (
                            <button 
                                className="btn-link"
                                onClick={downloadProcesosPermisos}
                            >
                                Descargar Archivo
                            </button>
                        )}
                    </div>
                </div>
            </div>
            
            <div className="line"/>

            <div className="form-cols-2 -mt-3">
                <div className="form-cols-1">
                    {loadingInfo && <BarLoader className="mt-2" color="#059669" width={400} />}
                    <span className="form-title">Información</span>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input -my-3">
                            <input 
                                type="text" 
                                name="id_usuario" 
                                id="id_usuario"
                                value={selectedNombre}
                                disabled
                            />
                            <label htmlFor="id_usuario">ID_DATOS</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="id_institucion" 
                                id="id_institucion"
                                value={infoUser?.["n institucion"] ?? selectedInstitucion ?? ''}
                                disabled
                            />
                            <label htmlFor="id_institucion">ID_INSTITUCION</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="id_sector" 
                                id="id_sector"
                                value={infoUser?.n_sector ?? selectedSector ?? ''}
                                disabled
                            />
                            <label htmlFor="id_sector">ID_SECTOR</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-select">
                            <select 
                                name="n_tipo_usuario" 
                                id="n_tipo_usuario"
                                value={infoUser?.n_tipo_usuario ?? ''}
                                onChange={handleChage}
                            >
                                <option value="0">...</option>
                                {Object.entries(catTipoUser.body).map((item) => (
                                <option key={item[0]} value={item[0]}>
                                    {item[1]}
                                </option>
                            ))}
                            </select>
                            <label htmlFor="n_tipo_usuario">TIPO USUARIO</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="email" 
                                name="email" 
                                id="email"
                                value={infoUser?.email ?? ''}
                                onChange={handleChage}
                            />
                            <label htmlFor="email">EMAIL</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="contrasenia_vigente" 
                                id="contrasenia_vigente"
                                value={infoUser?.contrasenia_vigente ?? ''}
                                disabled
                            />
                            <label htmlFor="contrasenia_vigente">CONTRASEÑA VIGENTE</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="contrasenia_fecha_cambio" 
                                id="contrasenia_fecha_cambio"
                                value={infoUser?.contrasenia_fecha_cambio ?? ''}
                                disabled
                            />
                            <label htmlFor="contrasenia_fecha_cambio">CONTRASEÑA FECHA CAMBIO</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="token" 
                                id="token"
                                value={infoUser?.token ?? ''}
                                disabled
                            />
                            <label htmlFor="token">TOKEN</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="cuenta_activada" 
                                id="cuenta_activada"
                                value={infoUser?.cuenta_activada ?? ''}
                                disabled
                            />
                            <label htmlFor="cuenta_activada">CUENTA ACTIVADA</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="estatus" 
                                id="estatus"
                                value={infoUser?.estatus ?? ''}
                                disabled
                            />
                            <label htmlFor="estatus">ESTATUS</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="activo" 
                                id="activo"
                                value={infoUser?.activo ?? ''}
                                disabled
                            />
                            <label htmlFor="activo">ACTIVO</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="fecha_alta" 
                                id="fecha_alta"
                                value={infoUser?.fecha_alta ?? ''}
                                disabled
                            />
                            <label htmlFor="fecha_alta">FECHA ALTA</label>
                        </div>
                    </div>
                </div>
                <div className="form-cols-1 content-start">
                    <span className="form-title">Archivos Proceso Descarga</span>
                    {loadingUri && <BarLoader className="mt-2" color="#059669" width={400} />}
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input type="text" 
                                   name="searchUri" 
                                   id="searchUri"
                                   onChange={searchDataUri}
                            />
                            <label htmlFor="searchUri">Busca URI</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-select">
                            <select 
                                name="s_uri" 
                                id="s_uri"
                                value={selectedUri}
                                onChange={handleUri}
                            >
                                <option value="default">...</option>
                                {Object.entries(dataUri).map((item) => (
                                <option key={item[0]} value={item[0]}>
                                    {item[1]}
                                </option>
                            ))}
                            </select>
                            <label htmlFor="s_uri">URI</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="archivo" 
                                id="archivo"
                                value={uriInfo.archivo ?? ''}
                                disabled
                            />
                            <label htmlFor="archivo">ARCHIVO</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="id_proceso" 
                                id="id_proceso"
                                value={uriInfo.id_proceso ?? ''}
                                disabled
                            />
                            <label htmlFor="id_proceso">ID_PROCESO</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <input 
                                type="text" 
                                name="id_proceso_padre" 
                                id="id_proceso_padre"
                                value={uriInfo.id_proceso_padre ?? ''}
                                disabled
                            />
                            <label htmlFor="id_proceso_padre">ID_PROCESO_PADRE</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-text-area">
                            <textarea
                                className="max-h-80"
                                name="liga_descarga" 
                                id="liga_descarga" 
                                rows={5} 
                                placeholder="SELECCIONA UN USUARIO"
                                value={liga}
                                disabled
                            />
                            <label htmlFor="liga_descarga">LIGA_DESCARGA</label>
                        </div>
                    </div>
                </div>
            </div>
            <div className="form-cols-2">
                {(infoUser.cuenta_activada === "N" || infoUser.estatus === "B" || infoUser.activo === "N") && (
                    <UserBloqueo n_nombre = {selectedNombre} />
                )}
                {selectedNombre !== 0 && (
                    <UserChangePwd email={infoUser.email} />
                )}
            </div>

            <div className="line"/>

            <GenerarArchivo 
                title="Accesos Página"
                n_institucion={selectedInstitucion}
                n_nombre={selectedNombre}
                url="/admin-user-web/generar-archivo-accesos"
                is_hist={false}
            />

            <div className="line"/>
            
            <div className="form-cols-3">
                <ProductosTrial 
                    n_nombre={selectedNombre}
                    triggerProduct={triggerProducts}
                    setTriggerProduct={setTriggerProducts}
                    setDataTable={setDataTable}
                    setTriggerDataTable={setTriggerInfoTrial}
                />
                <HistoricoTrial 
                    n_nombre={selectedNombre} 
                    triggerInfo={triggerInfoTrial} 
                    dataTable={dataTable}
                    setTrigger={setTriggerInfoTrial}
                    setDataTable={setDataTable}
                />
            </div>

            <div className="line"/>

            <GenerarArchivo 
                title="Historico Trial Archivo"
                url="/admin-user-web/generar-archivo-hist-trial"
                is_hist={true}
            />
        </>
    )
}