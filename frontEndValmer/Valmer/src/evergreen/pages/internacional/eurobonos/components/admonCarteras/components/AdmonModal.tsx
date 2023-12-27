import {ButtonContent, Modal} from "../../../../../../../shared";
import React from "react";

interface AdmonModalProps {
    isOpen: boolean
    loading: boolean
    cliente: string
    envioSFTP: number
    envioEmail: number
    handleCloseModal: () => void
    handleInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    handleCheckboxChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    handleSave: () => Promise<void>;
}
export const AdmonModal = ({
                               isOpen,
                               loading,
                               cliente,
                               envioSFTP,
                               envioEmail,
                               handleCloseModal,
                               handleInputChange,
                               handleCheckboxChange,
                               handleSave
}: AdmonModalProps) => {

    return (
        <Modal isOpen={isOpen} onClose={handleCloseModal} title="" modalSize={"small"}>
            <div className="bg-cyan-700 px-1 text-slate-50">
                <span>Nuevo cliente</span>
            </div>

            <div className="mt-4 grid grid-cols-2 gap-4">
                <div className="relative z-0 my-3 col-span-2">
                    <input
                        name="cliente"
                        type="text"
                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                            border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
                            focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                        value={cliente}
                        onChange={handleInputChange}
                        placeholder=""
                    />
                    <label htmlFor="cliente"
                        className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                            transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                            peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                            peer-focus:scale-75 peer-focus:-translate-y-6"
                    >
                        Cliente
                    </label>
                </div>

                <div className="flex items-center my-3 h-full">
                    <input
                        type="checkbox"
                        className="w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded focus:ring-cyan-700
                            dark:focus:ring-cyan-700 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700
                            dark:border-gray-600"
                        name="b_sftp"
                        checked={envioSFTP === 1}
                        onChange={handleCheckboxChange}
                    />
                    <label htmlFor="b_sftp"
                        className="ml-2 text-sm font-medium text-gray-300 dark:text-gray-900"
                    >
                        Envio SFTP
                    </label>
                </div>

                <div className="flex items-center my-3 h-full">
                    <input
                        type="checkbox"
                        className="w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded focus:ring-cyan-700
                            dark:focus:ring-cyan-700 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700
                            dark:border-gray-600"
                        name="b_email"
                        checked={envioEmail === 1}
                        onChange={handleCheckboxChange}
                    />
                    <label htmlFor="b_email"
                        className="ml-2 text-sm font-medium text-gray-300 dark:text-gray-900"
                    >
                        Envio Email
                    </label>
                </div>

                <div className="relative z-0 my-3 col-span-2">
                    <button
                        className="w-full bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3"
                        onClick={handleSave}
                    >
                        <ButtonContent name="Guardar" loading={loading}/>
                    </button>
                </div>
            </div>
        </Modal>
    )
}