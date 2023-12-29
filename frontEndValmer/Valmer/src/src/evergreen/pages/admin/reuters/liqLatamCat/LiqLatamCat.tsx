import React, { useEffect } from 'react';
import { TitleDate, Modal } from '../../../../../shared';
import { UmsLiquidezLatamTable } from './components/UmsLiquidezLatamTable';
import { useliqLatamCat } from '../hooks/useliqLatamCat'

export const LiqLatamCat = () => {

    const {
        tableData,
        loadingData,
        totaPages,
        getDataTable,
        downloadCsvFile,
        isOpenCarga,
        handleOpenCarga,
        handleCloseCarga,
        handleOpenDelete,
        isOpenDelete,
        handleCloseDelete,
        n_cbo_pais,
        isOpenEdit,
        handleCloseEdit,
        registro,
        HandleChangeBuscar,
        handleOpenEdit,
        searchText,
        HandleChangePais,
        handleChangeForm,
        handleSubmitForm,
        deleteByISIN,
        textSearch,
        handleKeyDown
    } = useliqLatamCat();

    useEffect(() => {
        if (n_cbo_pais > 0) {
            getDataTable(n_cbo_pais, 12, 0, textSearch)
        }
    }, [n_cbo_pais])

    return (
        <>
            <TitleDate title="Reuters Liquidez Latam" />

            <div className="animate__animated animate__fadeIn">
                <div className="flex justify-center">
                    <div className="form-input m-1 w-1/2">
                        <input
                            type="text"
                            name="buscar"
                            id="buscar"
                            value={textSearch}
                            onChange={HandleChangeBuscar}
                            onKeyDown={handleKeyDown}
                            placeholder=""
                        />
                        <label htmlFor="buscar">BUSCAR</label>
                    </div>
                    <div className="content-center grid m-1">
                        <button
                            className="btn"
                            type="button"
                            onClick={searchText}
                        >
                            <i className="fa fa-rotate"></i>
                        </button>
                    </div>
                    <div className="content-center grid m-1">
                        <button
                            className="btn"
                            type="button"
                            onClick={handleOpenCarga}
                        >
                            <i className="fa fa-file-circle-plus"></i>
                            <span className="ml-2">Cargar</span>
                        </button>
                    </div>
                    <div className="content-center grid m-1">
                        <button
                            className="btn"
                            type="button"
                            onClick={() => downloadCsvFile(n_cbo_pais)}
                        >
                            <i className="fa fa-download"></i>
                            <span className="ml-2">Descargar</span>
                        </button>
                    </div>
                </div>

                <div className='flex justify-center'>
                    <div className="form-select m-1 w-3/4">
                        <select name="n_cbo_pais" id="n_cbo_pais" onChange={HandleChangePais}>
                            <option value={0}>...</option>
                            <option value={1055}>Costa Rica</option>
                            <option value={1056}>Panamá</option>
                        </select>
                    </div>
                </div>

                <UmsLiquidezLatamTable
                    onOpenDelete={handleOpenDelete} onOpenEdit={handleOpenEdit}
                    tableData={tableData} loadingData={loadingData}
                    totaPages={totaPages} n_cbo_pais={n_cbo_pais} textSearch={textSearch} getDataTable={getDataTable}

                />

                <Modal isOpen={isOpenCarga} onClose={handleCloseCarga} title="Cargar">
                    <div className="text-center">
                        ISIN | INSTRUMENTO | RIC | TIPO
                    </div>
                    <form>
                        <div className="form-text-area">
                            <textarea name="carga" id="carga" placeholder=''></textarea>
                            <label htmlFor="carga">ISIN, INSTRUMENTO, RIC, TIPO</label>
                        </div>
                        <div className="line" />
                        <div className="flex justify-end">
                            <button className="btn">Carga</button>
                        </div>
                    </form>
                </Modal>

                <Modal isOpen={isOpenEdit} onClose={handleCloseEdit} title="Editar">
                    <form onSubmit={handleSubmitForm}>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="isin" id="isin" value={registro.isin} onChange={handleChangeForm} placeholder='' />
                                <label htmlFor="isin">Isin</label>
                            </div>
                        </div>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="s_instrumento" id="instrumento" value={registro.s_instrumento} onChange={handleChangeForm} placeholder='' />
                                <label htmlFor="instrumento">Instrumento</label>
                            </div>
                        </div>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="s_ric" id="ric" value={registro.s_ric} onChange={handleChangeForm} placeholder='' />
                                <label htmlFor="ric">Ric</label>
                            </div>
                        </div>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="s_tipo" id="tipo" value={registro.s_tipo} onChange={handleChangeForm} placeholder='' />
                                <label htmlFor="tipo">Tipo</label>
                            </div>
                        </div>
                        <div className="line" />
                        <div className="flex justify-end">
                            <button
                                className="btn"
                                type='button'
                                onClick={handleCloseEdit}
                            >
                                Cancelar
                            </button>
                            <button
                                className="btn"
                                type='button'
                                onClick={handleOpenDelete}
                            >
                                Eliminar
                            </button>
                            <button
                                className="btn"
                                type='submit'
                            >
                                Guardar
                            </button>
                        </div>
                    </form>
                </Modal>

                <Modal isOpen={isOpenDelete} onClose={handleCloseDelete} title="Desea Eliminar!!!">
                    <div>
                        ¿Desea eliminar el isin {registro.isin} de la bd ?
                    </div>
                    <div className="line" />
                    <div className="flex justify-end">
                        <button className="btn"
                            onClick={deleteByISIN}
                        >Continuar</button>
                        <button
                            className="btn"
                            onClick={handleCloseDelete}
                        >
                            Cancelar
                        </button>
                    </div>
                </Modal>
            </div>
        </>
    )
}