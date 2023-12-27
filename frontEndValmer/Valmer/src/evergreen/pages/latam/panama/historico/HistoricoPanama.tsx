import { ButtonContent, TitleDate } from '../../../../../shared';
import { usePanamaHistorico } from '../hooks';
import { generateUUID } from '../../../../../utils';
import { BarLoader } from 'react-spinners';

export const HistoricoPanama = () => {
    const {
        nemoTecnicoHistorico,
        loadingNemo,
        campos,
        handleRightArrowClick,
        handleLeftArrowClick,
        checkCampos,
        checkNemotecnicos,
        setCheckNemotecnicos,
        handleGeneraClick,
        setFechaInicio,
        setFechaFin,
        fechaInicio,
        fechaFin,
        nemotecnicoSeleccionado,
        setNemotecnicoSeleccionado,
        handleAllFieldsChange,
        downloadFilesFromUrl,
        loadingLogCsv,
        tabla,
        genera,
        loadingGenera,
        resetState,
        loadingCancelar,
        handleRegresar
    } = usePanamaHistorico();

    const renderLogContent = () => {
        if (tabla) {
            return (
                <div className="mt-8 flex flex-col items-center w-full">
                    <div className="w-1/8 mb-4">
                        <button
                            className="w-44 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3"
                            onClick={downloadFilesFromUrl}>
                            <ButtonContent name="Obtener Log CSV" loading={loadingLogCsv} />
                        </button>
                    </div>
                    <table className="table w-full">
                        <thead className="thead">
                            <tr>
                                <th>NEMOTECNICO</th>
                                <th>FECHA</th>
                                <th>BASE</th>
                                <th>CIERRE LIBRO</th>
                            </tr>
                        </thead>
                        <tbody className="tbody">
                            {tabla?.body?.map((fila) => (
                                <tr key={generateUUID()}>
                                    <td>{fila.s_nemotecnico}</td>
                                    <td>{fila.d_fecha}</td>
                                    <td>{fila.s_base}</td>
                                    <td>{fila.s_cierre_libro}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            );

        }
        else {
            return <p>No hay registros de log disponibles.</p>;
        }
    };

    return (
        <>
            <TitleDate title="Histórico Panamá" />

            <div className="form mt-4 animate__animated animate__fadeIn">
                <form>
                    <div className="form-cols-1">
                        <div className="flex justify-end pr-2">
                            <button className="btn" type='button' onClick={handleGeneraClick}>
                                <ButtonContent name="Genera" loading={loadingGenera} />
                            </button>
                            <button className="btn" type='button' onClick={resetState}>
                                <ButtonContent name="Cancelar" loading={loadingCancelar} />
                            </button>
                            <button className="btn animate__animated animate__fadeIn" type='button' onClick={handleRegresar}>
                                <span>Regresar</span>
                            </button>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <span className="form-title">Instrumento</span>
                        <div className="form-select animate__animated animate__fadeIn">
                            {loadingNemo &&
                                <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500} />
                            }
                            <select
                                name="nemotecnico"
                                id="nemotecnico"
                                value={nemotecnicoSeleccionado}
                                onChange={(e) => setNemotecnicoSeleccionado(e.target.value)}
                            >
                                <option value="">...</option>
                                {nemoTecnicoHistorico?.map((item) => (
                                    <option key={generateUUID()} value={item}>
                                        {item}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="nemotecnico">NEMOTECNICO</label>
                        </div>
                    </div>
                    <div className="form-cols-2 -my-3 mt-4">
                        <div className="form-date">
                            <input type="date"
                                name="fecha_inicio"
                                id="fecha_inicio"
                                value={fechaInicio}
                                onChange={(e) => setFechaInicio(e.target.value)}
                            />
                            <label htmlFor="fecha_inicio">
                                Fecha inicio
                            </label>
                        </div>
                        <div className="form-date">
                            <input type="date"
                                name="fecha_fin"
                                id="fecha_fin"
                                value={fechaFin}
                                onChange={(e) => setFechaFin(e.target.value)}
                            />
                            <label htmlFor="fecha_fin">
                                Fecha fin
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-1 gap-4">
                        <div className="border-gray-300 border my-5"></div>
                    </div>
                    <div className="form-cols-2 animate__animated animate__fadeIn">
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="allFields"
                                checked={checkCampos}
                                onChange={handleAllFieldsChange}
                            />
                            <label htmlFor="allFields">Todos los campos</label>
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                name="allNemotecnicos"
                                checked={checkNemotecnicos}
                                onChange={() => setCheckNemotecnicos(!checkNemotecnicos)}
                            />
                            <label htmlFor="allNemotecnicos">Todos los nemotecnicos</label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <span className="form-title">Campos</span>
                    </div>
                    <div className="form-cols-3 -my-3">
                        <div className="form-select animate__animated animate__fadeIn">
                            <select
                                multiple
                                name="campos"
                                id="campos"
                                className="h-52"
                            >
                                {campos?.body?.map((item) => {
                                    const key = Object.keys(item)[0];
                                    const value = item[key];
                                    return (
                                        <option key={generateUUID()} value={key}>
                                            {value}
                                        </option>
                                    )
                                })}
                            </select>
                        </div>
                        <div className="form-cols-1">
                            <div className="flex flex-col items-center justify-evenly">
                                <button className="btn w-1/4" onClick={handleRightArrowClick}>
                                    <ButtonContent name="->" loading={false} />
                                </button>
                                <button className="btn w-1/4" onClick={handleLeftArrowClick}>
                                    <ButtonContent name="<-" loading={false} />
                                </button>
                            </div>
                        </div>
                        <div className="form-select animate__animated animate__fadeIn">
                            <select
                                multiple
                                name="pickList"
                                id="pickList"
                                className="h-52"
                            >
                            </select>
                        </div>
                    </div>
                    <div>
                        { genera && renderLogContent() }
                    </div>
                </form>
            </div>
        </>
    );
};
