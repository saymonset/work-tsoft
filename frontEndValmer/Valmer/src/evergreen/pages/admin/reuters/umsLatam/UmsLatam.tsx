import { TitleDate, Modal } from '../../../../../shared';
import { UmsLatamTable } from './components/UmsLatamTable';
import { useUmsMexCat } from '../hooks/useUmsMexCat'

export const UmsLatam = () => {
    const {
        tableData,
        loadingData,
        totaPages,
        getDataTable,
        downloadCsvFile,
        textSearch,
        HandleChangeBuscar,
        handleOpenCarga,
        handleOpenDelete,
        handleOpenEdit,
        isOpenCarga,
        handleCloseCarga,
        isOpenEdit,
        handleButtonClick,
        handleCloseEdit,
        registro,
        handleChangeForm,
        isOpenDelete,
        handleCloseDelete,
        setOpenDelete,
        deletebyIsin,
        isinToDelete,
        setOpenEdit,
        saveDataUser
    } = useUmsMexCat();

    const handleSubmitForm = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setOpenEdit(false)
        await saveDataUser(registro, 9018)
        await getDataTable(12, 0, '', 9018)
    }

    const searchText = async () => {
        await getDataTable(12, 0, textSearch, 9018)
    }

    const deleteByISIN = async (isinToDelete: string | null) => {
        setOpenDelete(false);
        await deletebyIsin(9018, isinToDelete)
        await getDataTable(12, 0, '', 9018)
    }

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            searchText();
        }
    };

    return (
        <>
            <TitleDate title="Reuters UMS Latam" />

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
                            onClick={() => downloadCsvFile(9017)}
                        >
                            <i className="fa fa-download"></i>
                            <span className="ml-2">Descargar</span>
                        </button>
                    </div>
                </div>

                <UmsLatamTable onOpenDelete={handleOpenDelete} onOpenEdit={handleOpenEdit}
                    tableData={tableData} loadingData={loadingData}
                    totaPages={totaPages} getDataTable={getDataTable}
                />

                <Modal isOpen={isOpenCarga} onClose={handleCloseCarga} title="Cargar">
                    <div className="text-center">
                        ISIN | RIC | PROVEDOR
                    </div>
                    <form>
                        <div className="form-text-area">
                            <textarea name="carga" id="carga" placeholder=''></textarea>
                            <label htmlFor="carga">ISIN, RIC, PROVEDOR</label>
                        </div>
                        <div className="line" />
                        <div className="flex justify-end">
                            <button className="btn" onClick={handleButtonClick}>Carga</button>
                        </div>
                    </form>
                </Modal>

                <Modal isOpen={isOpenEdit} onClose={handleCloseEdit} title="Editar">
                    <form onSubmit={handleSubmitForm}>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="S_ISIN" id="isin" value={registro.S_ISIN} onChange={handleChangeForm} placeholder='' readOnly />
                                <label htmlFor="isin">Isin</label>
                            </div>
                        </div>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="S_RIC_FORMATO" id="ric" value={registro.S_RIC_FORMATO} onChange={handleChangeForm} placeholder='' />
                                <label htmlFor="ric">Ric</label>
                            </div>
                        </div>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="S_PROVEDOR" id="provedor" value={registro.S_PROVEDOR} onChange={handleChangeForm} placeholder='' />
                                <label htmlFor="provedor">Provedor</label>
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
                                onClick={() => handleOpenDelete(registro.S_ISIN)}
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
                        ¿Desea eliminar el isin {isinToDelete} de la bd?
                    </div>
                    <div className="line" />
                    <div className="flex justify-end">
                        <button className="btn"
                            onClick={() => deleteByISIN(isinToDelete)}
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