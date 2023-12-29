import React from 'react'
import {ButtonContent, Modal} from "../../../../../../../../shared";
import {SelectFileCorp} from "./components";
import {useCargaForm} from "./hooks";

export const CargaForm = () => {

    const {
        title,
        isModalOpen,
        loading,
        handleModalOpen,
        handleModalClose,
        handleCargaArchivo} = useCargaForm()

    return (
        <>
            <form>
                <div className="px-3">
                    <div className="grid grid-cols-4 gap-4">
                        <SelectFileCorp/>
                        <div>
                            <button className='bg-cyan-700 hover:bg-green-700 text-white py-2 px-4 rounded-md'
                                    onClick={handleCargaArchivo}>
                                <ButtonContent name={"Cargar/Validar"} loading={loading}></ButtonContent>
                            </button>
                            <button
                                type='button'
                                className="bg-blue-300 border border-gray-500 hover:bg-blue-400 text-white rounded-full ml-1 text-xs px-2.5 py-1"
                                onClick={handleModalOpen}
                            >
                                <i className="fa fa-info"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </form>
            <Modal isOpen={isModalOpen} onClose={handleModalClose} title={title}>
                <div className="grid grid-cols-1 mt-1">
                    <div className="p-1 bg-cyan-700 text-white text-center">
                        <span>Layout Carga</span>
                    </div>
                    <div className="flex flex-col text-sm mt-3">
                        <table className='border-separate border-spacing-1'>
                            <thead className='text-white bg-cyan-700'>
                                <tr>
                                    <th>Id</th>
                                    <th>Nombre</th>
                                    <th>Tipo de Dato</th>
                                </tr>
                            </thead>
                            <tbody className='text-center'>
                                <tr className='cursor-default even:bg-gray-300 hover:bg-cyan-600 hover:text-white'>
                                    <td>1</td>
                                    <td>INSTRUMENTO</td>
                                    <td>AN</td>
                                </tr>
                                <tr className='cursor-default even:bg-gray-300 hover:bg-cyan-600 hover:text-white'>
                                    <td>2</td>
                                    <td>TASACUPON</td>
                                    <td>N</td>
                                </tr>
                                <tr className='cursor-default even:bg-gray-300 hover:bg-cyan-600 hover:text-white'>
                                    <td>3</td>
                                    <td>FECHA INICIO CUPON</td>
                                    <td>AN</td>
                                </tr>
                                <tr className='cursor-default even:bg-gray-300 hover:bg-cyan-600 hover:text-white'>
                                    <td>4</td>
                                    <td>FECHA FIN CUPON</td>
                                    <td>AN</td>
                                </tr>
                                <tr className='cursor-default even:bg-gray-300 hover:bg-cyan-600 hover:text-white'>
                                    <td>5</td>
                                    <td>PLAZO</td>
                                    <td>N</td>
                                </tr>
                                <tr className='cursor-default even:bg-gray-300 hover:bg-cyan-600 hover:text-white'>
                                    <td>6</td>
                                    <td>VALOR NOMINAL</td>
                                    <td>N</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div className='flex justify-end mt-4 px-3'>
                        <button
                            className='bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md'
                            type='button'
                            onClick={handleModalClose}
                        >
                            <span>Cerrar</span>
                        </button>
                    </div>
                </div>
            </Modal>
        </>
    );
}