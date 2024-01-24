import {generateUUID} from "../../../../../../utils";
import {BarLoader} from "react-spinners";
import {CostaRicaHeaderProps} from "../../../../../../model";
import {ButtonContent} from "../../../../../../shared";

export const CostaRicaFormHeader = (data: CostaRicaHeaderProps) => {

    return (
        <div className="form form-my mt-1 animate__animated animate__fadeIn">
            <form>
                <div className="form-cols-1-gap-1">
                    <div className="form-cols-1">
                        <div className="flex justify-end pr-2">
                            <button
                                className="btn"
                                type='button'
                                onClick={data.handleNuevo}
                            >
                                <span>Nuevo</span>
                            </button>
                            <button
                                className="btn"
                                onClick={data.handleSave}
                            >
                                <ButtonContent name={"Guardar"} loading={data.loadingSave}/>
                            </button>
                            <button
                                className="btn"
                                onClick={data.handleErase}
                            >
                                <span>Limpiar</span>
                            </button>
                            {data.activeNuevo && (
                                <button
                                    className="btn animate__animated animate__fadeIn"
                                    type='button'
                                    onClick={data.handleCancel}
                                >
                                    <span>Cancelar</span>
                                </button>
                            )}
                        </div>
                    </div>
                    <span className="form-title mb-2">Instrumento</span>
                    {data.activeNuevo && (
                        <div className="form-cols-4 -my-3 animate__animated animate__fadeIn">
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="s_emisor"
                                    id="s_emisor"
                                    value={data.selectedEmisor}
                                    onChange={data.handleEmisor}
                                    ref={data.requeridos.s_emisor}
                                />
                                <label htmlFor="s_emisor">EMISOR</label>
                                {data.isFieldRequired.s_emisor && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido EMISOR
                                    </span>
                                )}
                            </div>
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="s_nemo_instr"
                                    id="s_nemo_instr"
                                    value={data.selectedNemo}
                                    onChange={data.handleNemo}
                                    ref={data.requeridos.s_nemo_instr}
                                />
                                <label htmlFor="s_nemo_instr">NEMO INSTRUMENTO</label>
                                {data.isFieldRequired.s_nemo_instr && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido NEMO INSTRUMENTO
                                    </span>
                                )}
                            </div>
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="s_serie"
                                    id="s_serie"
                                    value={data.selectedSerie}
                                    onChange={data.handleSerie}
                                    ref={data.requeridos.s_serie}
                                />
                                <label htmlFor="s_serie">SERIE</label>
                                {data.isFieldRequired.s_serie && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido SERIE
                                    </span>
                                )}
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="inactivas"
                                    value={data.checkboxValue}
                                    onChange={data.handleCheckboxChange}
                                />
                                <label htmlFor="inactivas">Inactivas</label>
                            </div>
                        </div>
                    )}
                    {!data.activeNuevo && (
                        <div className="form-cols-4 -my-3 animate__animated animate__fadeIn">

                            <div className="form-select">
                                <select
                                    name="s_emisor"
                                    id="s_emisor"
                                    value={data.selectedEmisor}
                                    onChange={data.handleEmisor}
                                    ref={data.requeridos.s_emisor}
                                >
                                    <option value="">...</option>
                                    {data.emisor?.map((item: any) => (
                                        <option key={generateUUID()} value={item}>
                                            {item}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="s_emisor">EMISOR</label>
                                {data.isFieldRequired.s_emisor && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido EMISOR
                                    </span>
                                )}
                            </div>

                            <div className="form-select">
                                <select
                                    name="s_nemo_instr"
                                    id="s_nemo_instr"
                                    value={data.selectedNemo}
                                    onChange={data.handleNemo}
                                    ref={data.requeridos.s_nemo_instr}
                                >
                                    <option value="">...</option>
                                    {data.nemoInstrumento?.map((item) => (
                                        <option key={generateUUID()} value={item}>
                                            {item}
                                        </option>
                                    ))}
                                </select>
                                {data.loadingNemoInst && <BarLoader className="mt-2" color="#059669" width={200}/>}
                                <label htmlFor="s_nemo_inst">NEMO INSTRUMENTO</label>
                                {data.isFieldRequired.s_nemo_instr && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido NEMO INSTRUMENTO
                                    </span>
                                )}
                            </div>

                            <div className="form-select">
                                <select
                                    name="s_serie"
                                    id="s_serie"
                                    value={data.selectedSerie}
                                    onChange={data.handleSerie}
                                    ref={data.requeridos.s_serie}
                                >
                                    <option value="">...</option>
                                    {data.serie?.map((item) => (
                                        <option key={generateUUID()} value={item}>
                                            {item}
                                        </option>
                                    ))}
                                </select>
                                {data.loadingSerie && <BarLoader className="mt-2" color="#059669" width={200}/>}
                                <label htmlFor="s_serie">SERIE</label>
                                {data.isFieldRequired.s_serie && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido SERIE
                                    </span>
                                )}
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="inactivas"
                                    value={data.checkboxValue}
                                    onChange={data.handleCheckboxChange}
                                />
                                <label htmlFor="inactivas">Inactivas</label>
                            </div>
                        </div>
                    )}
                </div>
            </form>
            {/* <div className="line"></div> */}
        </div>
    )
}