import React from "react";
import {handleFileChange} from "../../../../../../../utils";
import {ButtonContent} from "../../../../../../../shared";
import {useChargeFile} from "./hooks";

export const ChargeFile = () => {

   const {
       selectedFile,
       fileWallet,
       loading,
       handleCharge,
       setFileBase64,
       setSelectedFile
   } = useChargeFile()

    return (
        <>
            <div className="flex flex-col items-center bg-cyan-700 px-1 text-slate-50 w-1/2 mx-auto">
                <span>Carga instrumentos carga</span>
            </div>

            <div className="mt-4 mb-44 flex justify-center w-full mx-auto">
                <div className="flex items-center my-3 h-full justify-center mr-2">
                    <label htmlFor="file-upload" className="relative cursor-pointer
                                                        bg-cyan-700 hover:bg-green-700 text-white py-2 px-4 rounded">
                        <span className="text-sm">Examinar...</span>
                        <input id="file-upload" type="file"
                               className="hidden"
                               accept=".csv"
                               onChange={(e) =>
                                   handleFileChange(e, setFileBase64, setSelectedFile)}
                        />
                    </label>
                    <span className="py-2 px-4 text-cyan-700">
                                                    {selectedFile?.name ?? fileWallet.nombreCompleto
                                                        ?? 'Ningún archivo seleccionado.'}
                                                    </span>
                </div>
                <div className="flex items-center my-3 h-full justify-center">
                    <button
                        className="btn btn-primary"
                        onClick={handleCharge}>
                        <ButtonContent name="Carga" loading={loading}/>
                    </button>
                </div>
            </div>
        </>
    );
};