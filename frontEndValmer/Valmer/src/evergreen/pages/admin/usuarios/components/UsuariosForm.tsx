import { useBigInput } from '../../../../../utils';
import {IUserData} from '../Models'

 interface Props {
     datos : IUserData | null,
     handleChange : (e:  React.FormEvent<HTMLInputElement>) => void
 }
export const UsuariosForm = ({datos, handleChange} : Props) => {
        //  Achica o agranda el input del form cuando obtiene o deja el focus
        const {  handleFocus,
            handleBlur,
            sendStyle} = useBigInput();
    return (
        <div className="flex justify-center mt-3">
            <div className="form-cols-2 w-full items-start">
                <div className="form-cols-2">
                    <span className="form-title col-span-2">Características</span>
                    <div className="my-3">
                        <div className="form-input">
                            <input type="text" value={datos?.s_nombre} autoComplete="off"  name="s_nombre" id="s_nombre" placeholder="" onChange={handleChange} 
                            onFocus={() => handleFocus('s_nombre')}
                            onBlur={handleBlur}
                            style={sendStyle('s_nombre')}
                            />
                            <label htmlFor="s_nombre">Nombre</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-input">
                            <input type="text" name="s_usuario" autoComplete="off"  value = {datos?.s_usuario} id="s_usuario" placeholder="" onChange={handleChange}
                            onFocus={() => handleFocus('s_usuario')}
                            onBlur={handleBlur}
                            style={sendStyle('s_usuario')}
                            />
                            <label htmlFor="s_usuario">User</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-input">
                            <input type="password" name="s_pass" autoComplete="off"  id="s_pass" value = {datos?.s_pass} placeholder="" onChange={handleChange}
                            onFocus={() => handleFocus('s_pass')}
                            onBlur={handleBlur}
                            style={sendStyle('s_pass')}
                            />
                            <label htmlFor="s_pass">Password</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-input">
                            <input type="text" name="s_area" id="s_area" autoComplete="off" value= {datos?.s_area} placeholder="" onChange={handleChange}/>
                            <label htmlFor="s_area">Area</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-input">
                            <input type="email" name="s_mail" autoComplete="off"  id="s_mail" value = {datos?.s_mail} placeholder="" onChange={handleChange}
                            onFocus={() => handleFocus('s_mail')}
                            onBlur={handleBlur}
                            style={sendStyle('s_mail')}
                            />
                            <label htmlFor="s_mail">Mail</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-input">
                            <input type="text" name="s_tel" autoComplete="off" id="s_tel" value = {datos?.s_tel} placeholder="" onChange={handleChange}
                            onFocus={() => handleFocus('s_tel')}
                            onBlur={handleBlur}
                            style={sendStyle('s_tel')}
                            />
                            <label htmlFor="s_tel">Teléfono</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-input">
                            <input type="text" name="s_puesto" id="s_puesto"  autoComplete="off" value = {datos?.s_puesto} placeholder="" onChange={handleChange}
                            onFocus={() => handleFocus('s_puesto')}
                            onBlur={handleBlur}
                            style={sendStyle('s_puesto')}
                            />
                            <label htmlFor="s_puesto">Puesto</label>
                        </div>
                    </div>
                </div>
                <div className="form-cols-3 mb-6">
                    <span className="form-title col-span-3">Aplicaciones</span>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_guber" id="n_guber"  checked = {Number(datos?.n_guber) == 1}  onChange={handleChange}/>
                            <label htmlFor="n_guber">Gubernamental</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_corpo" id="n_corpo" checked = {Number(datos?.n_corpo) == 1} onChange={handleChange} />
                            <label htmlFor="n_corpo">Corporativos</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_inter" id="n_inter"  checked = {Number(datos?.n_inter) == 1} onChange={handleChange}/>
                            <label htmlFor="n_inter">Internacional</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_acci" id="n_acci"  checked = {Number(datos?.n_acci) == 1} onChange={handleChange}/>
                            <label htmlFor="n_acci">Accionario</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_calif" id="n_calif" checked = {Number(datos?.n_calif) == 1} onChange={handleChange}/>
                            <label htmlFor="n_calif">Calificaciones</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_tipcam" id="n_tipcam" checked = {Number(datos?.n_tipcam) == 1} onChange={handleChange} />
                            <label htmlFor="n_tipcam">Tipos de Cambio</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_panam" id="n_panam" checked = {Number(datos?.n_panam) == 1} onChange={handleChange}/>
                            <label htmlFor="n_panam">Panamá</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_catal" id="n_catal" checked = {Number(datos?.n_catal) == 1} onChange={handleChange}/>
                            <label htmlFor="n_catal">Catálogos México</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_usr" id="n_usr"  checked = {Number(datos?.n_usr) == 1} onChange={handleChange}/>
                            <label htmlFor="n_usr">Usuarios</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_cau" id="n_cau"  checked = {Number(datos?.n_cau) == 1} onChange={handleChange}/>
                            <label htmlFor="n_cau">CAU</label>
                        </div>
                    </div>
                    <div className="my-3"> 
                        <div className="form-check">
                            <input type="checkbox" name="n_catpan" id="n_catpan" checked = {Number(datos?.n_catpan) == 1} onChange={handleChange}/>
                            <label htmlFor="n_catpan">Catálogos Panamá</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_catcau" id="n_catcau" checked = {Number(datos?.n_catcau) == 1} onChange={handleChange}/>
                            <label htmlFor="n_catcau">Catálogos CAU</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_caumo" id="n_caumo" checked = {Number(datos?.n_caumo) == 1} onChange={handleChange} />
                            <label htmlFor="n_caumo">CAU Modificaciones</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_cr" id="n_cr" checked = {Number(datos?.n_cr) == 1} onChange={handleChange} />
                            <label htmlFor="n_cr">Costa Rica</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_catcal" id="n_catcal"  checked = {Number(datos?.n_catcal) == 1} onChange={handleChange} />
                            <label htmlFor="n_catcal">Catálogos Calificaciones</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_corptrc" id="n_corptrc" checked = {Number(datos?.n_corptrc) == 1} onChange={handleChange}/>
                            <label htmlFor="n_corptrc">Logs CORPOTRAC</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_envios" id="n_envios" checked = {Number(datos?.n_envios) == 1} onChange={handleChange}/>
                            <label htmlFor="n_envios">Envío Mail Clientes</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_hist_calif" id="n_hist_calif" checked = {Number(datos?.n_hist_calif) == 1} onChange={handleChange}/>
                            <label htmlFor="n_hist_calif">Hist. Calificaciones</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_hist_vec" id="n_hist_vec" checked = {Number(datos?.n_hist_vec) == 1} onChange={handleChange}/>
                            <label htmlFor="n_hist_vec">Hist. Vector</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_deriv" id="n_deriv" checked = {Number(datos?.n_deriv) == 1} onChange={handleChange}/>
                            <label htmlFor="n_deriv">Derivados</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_bench" id="n_bench" checked = {Number(datos?.n_bench) == 1} onChange={handleChange} />
                            <label htmlFor="n_bench">Benchmarks</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_reginv" id="n_reginv" checked = {Number(datos?.n_reginv) == 1} onChange={handleChange}/>
                            <label htmlFor="n_reginv">Regimen Inv</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_vmatriz" id="n_vmatriz" checked = {Number(datos?.n_vmatriz) == 1} onChange={handleChange} />
                            <label htmlFor="n_vmatriz">Valida Trans Matriz</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_broker" id="n_broker" checked = {Number(datos?.n_broker) == 1} onChange={handleChange}/>
                            <label htmlFor="n_broker">Brokers Electrónicos</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_notasdeuda" id="n_notasdeuda" checked = {Number(datos?.n_notasdeuda) == 1} onChange={handleChange}/>
                            <label htmlFor="n_notasdeuda">Notas Deuda</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_crv_der" id="n_crv_der" checked = {Number(datos?.n_crv_der) == 1} onChange={handleChange}/>
                            <label htmlFor="n_crv_der">Curva Derivados</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_corp_val" id="n_corp_val" checked = {Number(datos?.n_corp_val) == 1} onChange={handleChange} />
                            <label htmlFor="n_corp_val">Corp Val</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_betas" id="n_betas" checked = {Number(datos?.n_betas) == 1} onChange={handleChange}/>
                            <label htmlFor="n_betas">Betas Accionarias</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_pro_dev" id="n_pro_dev" checked = {Number(datos?.n_pro_dev) == 1} onChange={handleChange}/>
                            <label htmlFor="n_pro_dev">Proceso Derivados</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_users_web" id="n_users_web" checked = {Number(datos?.n_users_web) == 1} onChange={handleChange} />
                            <label htmlFor="n_users_web">Admin Usuarios Web</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_der_valrisk" id="n_der_valrisk" checked = {Number(datos?.n_der_valrisk) == 1} onChange={handleChange}/>
                            <label htmlFor="n_der_valrisk">Derivados ValRisk</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_tasas" id="n_tasas" checked = {Number(datos?.n_tasas) == 1} onChange={handleChange} />
                            <label htmlFor="n_tasas">Tasas</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_reuters" id="n_reuters" checked = {Number(datos?.n_reuters) == 1} onChange={handleChange} />
                            <label htmlFor="n_reuters">Reuters</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_notas_est" id="n_notas_est" checked = {Number(datos?.n_notas_est) == 1} onChange={handleChange} />
                            <label htmlFor="n_notas_est">Notas Estruct.</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_mensajeria" id="n_mensajeria" checked = {Number(datos?.n_mensajeria) == 1} onChange={handleChange} />
                            <label htmlFor="n_mensajeria">Mensajería</label>
                        </div>
                    </div>
                    <div className="my-3">
                        <div className="form-check">
                            <input type="checkbox" name="n_ctlr_int" id="n_ctlr_int" checked = {Number(datos?.n_ctlr_int) == 1} onChange={handleChange} />
                            <label htmlFor="n_ctlr_int">Control Int</label>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    )
}