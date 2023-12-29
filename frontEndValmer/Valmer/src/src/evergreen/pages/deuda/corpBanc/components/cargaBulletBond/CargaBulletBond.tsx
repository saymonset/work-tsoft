import {ButtonContent, TitleDate} from "../../../../../../shared";
import React from "react";
import {handleFileChange} from "../../../../../../utils";
import {useCargaBulletBond} from "./hooks";

export const CargaBulletBond = () => {

    const {
        handleCargaArchivo,
        setFileBase64,
        setSelectedFile,
        selectedFile,
        loading
    } = useCargaBulletBond()

    return (
        <>
            <TitleDate title="Instrumentos Bullet Bond"></TitleDate>

            <div className="px-3">
                <div className="bg-cyan-700 px-1 text-slate-50">
                    <span>Alta Masiva BulletBond</span>
                </div>
                <div className="mt-4 grid grid-cols-4 gap-4">
                    <div className="text-xs text-cyan-700 my-3">
                        <label htmlFor="file-upload" className="relative cursor-pointer bg-cyan-700 hover:bg-green-700
                            text-white py-2 px-4 rounded">
                            <span className="text-sm">Examinar...</span>
                            <input id="file-upload"
                                   type="file"
                                   className="hidden"
                                   accept=".txt, .csv, application/vnd.ms-excel,
                                   application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                                   onChange={(e) => handleFileChange(e,
                                       setFileBase64, setSelectedFile)} />
                        </label>
                        <span className="py-2 px-4 text-cyan-700">
                            {selectedFile?.name ?? 'Ningún archivo seleccionado.'}</span>
                    </div>

                    <div>
                        <button className="btn" onClick={handleCargaArchivo}>
                            <ButtonContent name="Carga instrumentos" loading={loading}/>
                        </button>
                        <button type='button'
                                className="bg-blue-300 border border-gray-500
                                hover:bg-blue-400 text-white rounded-full ml-1 text-xs px-2.5 py-1">
                            <i className="fa fa-info"></i>
                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}