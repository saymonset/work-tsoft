import {useState} from 'react'
import { TitleDate, Modal } from "../../../../../shared"
import {UmsMexCatTable} from './components/UmsMexCatTable'
import {RegistrosUMSMexCat} from '../Models'
import { useUmsMexCat } from '../hooks/useUmsMexCat'

export const UmsMexCat = () => {

    const [isOpenCarga, setOpenCarga] = useState(false);
    const regInicial : RegistrosUMSMexCat = {
        S_ISIN: '',
        S_RIC_FORMATO: '',
        S_PROVEDOR: ''        
    }
    const [registro, setRegistro] = useState<RegistrosUMSMexCat>(regInicial)
    const [textSearch,setTextSearch] = useState('');
    const [isinToDelete, setIsinToDelete] = useState<string | null>(null);

    const {
        tableData,
        loadingData,
        totaPages,
        getDataTable,
        saveDataUser,
        deletebyIsin,
        downloadCsvFile
    } = useUmsMexCat()    

    const handleOpenCarga = () => {
        setOpenCarga(true);
    }

    const handleCloseCarga = () => {
        setOpenCarga(false);
    }

    const [isOpenDelete, setOpenDelete] = useState(false);

    const handleOpenDelete = (sisinToDelete: string) => {
        setIsinToDelete(sisinToDelete);
        setOpenDelete(true);
      };

    const handleCloseDelete = async () => {
        setOpenDelete(false);
    }

   const deleteByISIN =async (isinToDelete: string | null) => {
    setOpenDelete(false);
    await deletebyIsin(9017,isinToDelete)
    await getDataTable(12,0,'',9017)        
   }

    const [isOpenEdit, setOpenEdit] = useState(false); 

    const handleOpenEdit = (e: React.MouseEvent<HTMLElement>) => { 
        const sIsin: string | null = e.currentTarget.getAttribute("data-sisin")
        const sFormato : string | null =  e.currentTarget.getAttribute("data-ricformato")
        const sProveedor : string | null= e.currentTarget.getAttribute("data-proveedor")
        setOpenEdit(true);
        setRegistro({
            S_ISIN: sIsin ? sIsin : '' ,
            S_RIC_FORMATO: sFormato ?  sFormato : '',
            S_PROVEDOR: sProveedor ? sProveedor : ''            
        })
    }

    const handleChangeForm = (e:  React.FormEvent<HTMLInputElement>) => {
        e.preventDefault()
        const nameInput : string = e.currentTarget.name
        const value : string = e.currentTarget.value
        setRegistro( {...registro, [nameInput] : value} )
    }

   const handleSubmitForm = async (e: React.FormEvent<HTMLFormElement>) => {
     e.preventDefault()
     setOpenEdit(false)
     await saveDataUser(registro,9017)
     await getDataTable(12,0,textSearch,9017)
   }

   const HandleChangeBuscar = async(e: React.ChangeEvent<HTMLInputElement>) => {
      e.preventDefault()
      const text = e.currentTarget.value
      setTextSearch(text)
   }

   const searchText = async () => {
    await getDataTable(12,0,textSearch,9017)
   }
    const handleCloseEdit = () => {
        setOpenEdit(false);
    }

    return (
        <>
            <TitleDate title="Reuters UMS Mex"/>

            <div className="animate__animated animate__fadeIn">
                <div className="flex justify-center">
                    <div className="form-input m-1 w-1/2">
                        <input type="text" name="buscar" id="buscar" value={textSearch} onChange={HandleChangeBuscar} placeholder=""/>
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
                            onClick={()=> downloadCsvFile(9017)}
                        >
                            <i className="fa fa-download"></i>
                            <span className="ml-2">Descargar</span>
                        </button>
                    </div>
                </div>

                <UmsMexCatTable onOpenDelete={handleOpenDelete} onOpenEdit={handleOpenEdit}
                                 tableData={tableData}  loadingData = {loadingData} 
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
                        <div className="line"/>
                        <div className="flex justify-end">
                            <button className="btn">Carga</button>
                        </div>
                    </form>
                </Modal>

                <Modal isOpen={isOpenEdit} onClose={handleCloseEdit} title="Editar">
                    <form onSubmit={handleSubmitForm}>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="S_ISIN" id="isin" value={registro.S_ISIN} onChange={handleChangeForm} placeholder=''/>
                                <label htmlFor="isin">Isin</label>
                            </div>
                        </div>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="S_RIC_FORMATO" id="ric" value ={registro.S_RIC_FORMATO} onChange={handleChangeForm} placeholder=''/>
                                <label htmlFor="ric">Ric</label>
                            </div>
                        </div>
                        <div className="form-cols-1 -my-3">
                            <div className="form-input">
                                <input type="text" name="S_PROVEDOR" id="provedor" value={registro.S_PROVEDOR} onChange={handleChangeForm} placeholder=''/>
                                <label htmlFor="provedor">Provedor</label>
                            </div>
                        </div>
                        <div className="line"/>
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
                    <div className="line"/>
                    <div className="flex justify-end">
                        <button className="btn"
                                onClick={()=>deleteByISIN(isinToDelete)} 
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