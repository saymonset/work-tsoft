import {handleFileChange} from "../../../../../../../utils";
import {ButtonContent} from "../../../../../../../shared";
import React from "react";
import {useChargeClientWallet} from "./hooks";

export const ChargeClientWallet = () => {

   const {
       selectedFile,
       fileContributor,
       loading,
       setFileBase64,
       setSelectedFile,
       handleCharge
   } = useChargeClientWallet()

    return (
        <td className="mt-10 flex flex-col items-center">

            <div className="bg-cyan-700 px-1 text-slate-50 w-1/2 mx-auto">
                <span>Carga Cartera Clientes</span>
            </div>

            <div className="flex justify-center w-full mx-auto">
                <div className="flex items-center my-3 h-full justify-center mr-2">
                    <label htmlFor="file-upload"
                           className="relative cursor-pointer
                                       bg-cyan-700 hover:bg-green-700 text-white py-2 px-4 rounded">
                        <span className="text-sm">Examinar...</span>
                        <input id="file-upload"
                               type="file"
                               className="hidden"
                               accept=".txt,.csv,.xlsx"
                               onChange={(e) =>
                                   handleFileChange(e, setFileBase64, setSelectedFile)}
                        />
                    </label>
                    <span className="py-2 px-4 text-cyan-700">
                                {selectedFile?.name ??
                                    fileContributor.nombreCompleto ??
                                    'Ningún archivo seleccionado.'}</span>
                </div>
                <div className="flex items-center my-3 h-full justify-center">
                    <button
                        className="btn btn-primary"
                        onClick={handleCharge}>
                        <ButtonContent name="Carga Cartera" loading={loading}/>
                    </button>
                </div>
            </div>
        </td>
    );
};