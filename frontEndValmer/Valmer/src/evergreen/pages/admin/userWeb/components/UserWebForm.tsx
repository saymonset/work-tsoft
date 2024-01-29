import { BarLoader, MoonLoader } from "react-spinners";
import { useHandleDataUserWeb } from "../hooks"
import {getCatalogs, useBigInput} from "../../../../../utils";
import {ButtonContent} from "../../../../../shared";
import {UserChangePwd} from "./UserChangePwd";
import {ProductosTrial} from "./ProductosTrial";
import {HistoricoTrial} from "./HistoricoTrial";
import {GenerarArchivo} from "./GenerarArchivo";
import React from "react";

export const UserWebForm = () => {

    //  Achica o agranda el input del form cuando obtiene o deja el focus
    const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
    const {
        catalogoInst, loadingInst,
        catNom, loadingCatNom,
        catTipoUser, loadingTipoUser,
        catUri, dataUri,loadingCatUri,
        loadingSector, selectedInstitucion,
        catSector, selectedSector,
        loadingInfo, selectedNombre,
        infoUser, loadingSave,
        selectedUri, loadingUri,
        uriInfo, liga,
        loadingGeneraArch, linkArchPermisos,
        triggerInfoTrial, triggerProducts,
        dataTable,
        unlockUser, handleClickInstitucion, handleClickSector,
        handleClickNombre, handleChage,
        handleSaveUser, handleUri,
        handleProcesosPermisos, downloadProcesosPermisos, setTriggerInfo,
        setTriggerInfoTrial, setTriggerProducts,
        setDataTable, searchDataUri
    } = useHandleDataUserWeb()

    const isLoading = loadingInst || loadingTipoUser || loadingCatUri || loadingUri;

    const isDataAvailable = catalogoInst.length && catTipoUser.body && catUri.body;

    if (isLoading) {
        return (
            <div className="mt-1 flex justify-center items-center h-full">
                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
            </div>
        );
    } else if (!isDataAvailable) {
        return (
            <div className="mt-1 flex justify-center items-center h-full">
                <div>No hay información</div>
            </div>
        );
    }

    return (
        <>
            <div className="form-cols-3 my-0">
                <div className="form-cols-2 col-span-2">
                    <span className="form-title col-span-2">Compañía</span>
                    <div className="form-select form-my0">
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
                    <div className="form-select form-my0">
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
                    <div className="form-select form-my0">
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
            <div className="flex mt-0">
                <button className="btn">RECARGAR</button>
                <button 
                    className="btn"
                    onClick={handleSaveUser}
                >
                    <ButtonContent name="Guardar" loading={loadingSave} />
                </button>
            </div>
            
            <div className="line-y-1"/>
            
            <div className="flex justify-center mt-1">
                <div className="grid grid-rows-2 gap-1 w-2/5">
                    <span className="form-title w-full">Consulta Usuarios Proceso Permisos</span>
                    <div className="form-cols-3">
                        <span className="text-center mt-1">CONSULTA</span>
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
            
            <div className="line line-y-1"/>

            <div className="form-cols-2 mt-1 ">
                <div className="form-cols-1 content-start ">
                    {loadingInfo && <BarLoader className="mt-1" color="#059669" width={400} />}
                    <span className="form-title ">Información</span>
                    <div className="form-cols-2 my-0">
                        <div className="form-input form-my0 my-0">
                            <input 
                                type="text" 
                                name="id_usuario" 
                                id="id_usuario"
                                value={selectedNombre}
                                disabled
                                onFocus={() => handleFocus('id_usuario')}
                                onBlur={handleBlur}
                                style={sendStyle('id_usuario')}
                            />
                            <label htmlFor="id_usuario">ID_DATOS</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="id_institucion" 
                                id="id_institucion"
                                value={infoUser?.["n institucion"] ?? selectedInstitucion ?? ''}
                                disabled
                                onFocus={() => handleFocus('id_institucion')}
                                onBlur={handleBlur}
                                style={sendStyle('id_institucion')}
                            />
                            <label htmlFor="id_institucion">ID_INSTITUCION</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="id_sector" 
                                id="id_sector"
                                value={infoUser?.n_sector ?? selectedSector ?? ''}
                                disabled
                                onFocus={() => handleFocus('id_sector')}
                                onBlur={handleBlur}
                                style={sendStyle('id_sector')}
                            />
                            <label htmlFor="id_sector">ID_SECTOR</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-select form-my0">
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
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="email" 
                                name="email" 
                                id="email"
                                value={infoUser?.email ?? ''}
                                onChange={handleChage}
                                onFocus={() => handleFocus('email')}
                                onBlur={handleBlur}
                                style={sendStyle('email')}
                            />
                            <label htmlFor="email">EMAIL</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="contrasenia_vigente" 
                                id="contrasenia_vigente"
                                value={infoUser?.contrasenia_vigente ?? ''}
                                disabled
                                onFocus={() => handleFocus('contrasenia_vigente')}
                                onBlur={handleBlur}
                                style={sendStyle('contrasenia_vigente')}
                            />
                            <label htmlFor="contrasenia_vigente">CONTRASEÑA VIGENTE</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="contrasenia_fecha_cambio" 
                                id="contrasenia_fecha_cambio"
                                value={infoUser?.contrasenia_fecha_cambio ?? ''}
                                disabled
                                onFocus={() => handleFocus('contrasenia_fecha_cambio')}
                                onBlur={handleBlur}
                                style={sendStyle('contrasenia_fecha_cambio')}
                            />
                            <label htmlFor="contrasenia_fecha_cambio">CONTRASEÑA FECHA CAMBIO</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="token" 
                                id="token"
                                value={infoUser?.token ?? ''}
                                disabled
                                onFocus={() => handleFocus('token')}
                                onBlur={handleBlur}
                                style={sendStyle('token')}
                            />
                            <label htmlFor="token">TOKEN</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="cuenta_activada" 
                                id="cuenta_activada"
                                value={infoUser?.cuenta_activada ?? ''}
                                disabled
                                onFocus={() => handleFocus('cuenta_activada')}
                                onBlur={handleBlur}
                                style={sendStyle('cuenta_activada')}
                            />
                            <label htmlFor="cuenta_activada">CUENTA ACTIVADA</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="estatus" 
                                id="estatus"
                                value={infoUser?.estatus ?? ''}
                                disabled
                                onFocus={() => handleFocus('estatus')}
                                onBlur={handleBlur}
                                style={sendStyle('estatus')}
                            />
                            <label htmlFor="estatus">ESTATUS</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="activo" 
                                id="activo"
                                value={infoUser?.activo ?? ''}
                                disabled
                                onFocus={() => handleFocus('activo')}
                                onBlur={handleBlur}
                                style={sendStyle('activo')}
                            />
                            <label htmlFor="activo">ACTIVO</label>
                        </div>
                    {/* </div>
                    <div className="form-cols-1 my-0"> */}
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="fecha_alta" 
                                id="fecha_alta"
                                value={infoUser?.fecha_alta ?? ''}
                                disabled
                                onFocus={() => handleFocus('fecha_alta')}
                                onBlur={handleBlur}
                                style={sendStyle('fecha_alta')}
                            />
                            <label htmlFor="fecha_alta">FECHA ALTA</label>
                        </div>
                    </div>
                </div>
                <div className="form-cols-1 content-start">
                    <span className="form-title">Archivos Proceso Descarga</span>
                    {loadingUri && <BarLoader className="mt-2" color="#059669" width={400} />}
                    <div className="form-cols-1 my-0">
                        <div className="form-input form-my0 ">
                            <input type="text" 
                                   name="searchUri" 
                                   id="searchUri"
                                   onChange={searchDataUri}
                                   onFocus={() => handleFocus('searchUri')}
                                    onBlur={handleBlur}
                                    style={sendStyle('searchUri')}
                            />
                            <label htmlFor="searchUri">Busca URI</label>
                        </div>
                    </div>
                    <div className="form-cols-1 my-0">
                        <div className="form-select form-my0">
                            <select 
                                name="s_uri" 
                                id="s_uri"
                                value={selectedUri}
                                onChange={handleUri}
                            >
                                <option value="default">...</option>
                                {dataUri && Object.entries(dataUri).map((item) => (
                                <option key={item[0]} value={item[0]}>
                                    {item[1]}
                                </option>
                            ))}
                            </select>
                            <label htmlFor="s_uri">URI</label>
                        </div>
                    </div>
                    <div className="form-cols-1 my-0">
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="archivo" 
                                id="archivo"
                                value={uriInfo.archivo ?? ''}
                                disabled
                                onFocus={() => handleFocus('archivo')}
                                onBlur={handleBlur}
                                style={sendStyle('archivo')}
                            />
                            <label htmlFor="archivo">ARCHIVO</label>
                        </div>
                    </div>
                    <div className="form-cols-1 my-0">
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="id_proceso" 
                                id="id_proceso"
                                value={uriInfo.id_proceso ?? ''}
                                disabled
                                onFocus={() => handleFocus('id_proceso')}
                                onBlur={handleBlur}
                                style={sendStyle('id_proceso')}
                            />
                            <label htmlFor="id_proceso">ID_PROCESO</label>
                        </div>
                    </div>
                    <div className="form-cols-1 my-0">
                        <div className="form-input form-my0 ">
                            <input 
                                type="text" 
                                name="id_proceso_padre" 
                                id="id_proceso_padre"
                                value={uriInfo.id_proceso_padre ?? ''}
                                disabled
                                onFocus={() => handleFocus('id_proceso_padre')}
                                onBlur={handleBlur}
                                style={sendStyle('id_proceso_padre')}
                            />
                            <label htmlFor="id_proceso_padre">ID_PROCESO_PADRE</label>
                        </div>
                    </div>
                    <div className="form-cols-1 my-0">
                        <div className="form-text-area">
                            <textarea
                                className="max-h-14"
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
                    <div className="form-cols-2 animate__animated animate__fadeIn flex items-center">
                        <div className="text-red-700 font-bold text-center">USUARIO BLOQUEADO</div>
                        <button
                            className="btn w-min m-0 p-0"
                            onClick={unlockUser}
                        >
                            DESBLOQUEAR
                        </button>
                    </div>
                )}
                {selectedNombre !== 0 && (
                    <UserChangePwd
                        email={infoUser.email}
                        setTriggerProducts={setTriggerProducts}
                        setTriggerInfoTrial={setTriggerInfoTrial}
                        setTriggerInfo={setTriggerInfo}
                    />
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

            <div className="line line-y-1"/>
            
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