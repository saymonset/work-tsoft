import { CargaArchivoContent } from "../../../../../../model"
import { ButtonContent } from "../../../../../../shared"
import { handleFileChange } from "../../../../../../utils"

interface CargaArchVencProps {
    selectedFile: File | null
    fileContributor: CargaArchivoContent
    loading: boolean
    loadingProcess: boolean
    setFileBase64: React.Dispatch<React.SetStateAction<string>>
    setSelectedFile: React.Dispatch<React.SetStateAction<File | null>>
    handleCharge: () => void
    handleProcess: (nombre:string | undefined) => void
    setIsHelp: React.Dispatch<React.SetStateAction<boolean>>
}

export const CargaArchVenc = (props: CargaArchVencProps) => {

    return (
        <>
            <div className="form-title">Carga Archivo</div>

            <label htmlFor="file-upload">Arch Vencimientos:</label>
            <div className="flex items-center h-full mr-2">
                <label htmlFor="file-upload"
                       className="relative cursor-pointer
                        bg-cyan-700 hover:bg-green-700 text-white py-1 px-4 rounded"
                >
                    <span className="text-sm">Examinar...</span>
                    <input id="file-upload"
                           type="file"
                           className="hidden"
                           accept=".txt,.csv,.xlsx"
                           onChange={(e) =>
                               handleFileChange(e,
                                   props.setFileBase64,
                                   props.setSelectedFile
                               )}
                    />
                </label>
                <span className="py-2 px-4 text-cyan-700">
                            {props.selectedFile?.name ??
                                props.fileContributor.nombreCompleto ??
                                'Ningún archivo seleccionado.'}
                    </span>
            </div>
            <div>
                <button
                    className="fa fa-circle-question text-cyan-700 cursor-pointer"
                    onMouseEnter={() => props.setIsHelp(true)}
                    onMouseLeave={() => props.setIsHelp(false)}
                />
            </div>

            <div className="flex justify-center">
                <span>(1)</span>
                <button
                    className="btn"
                    onClick={props.handleCharge}
                >
                    <ButtonContent name="CARGA ARC" loading={props.loading}/>
                </button>

                <span>(2)</span>
                <button
                    className="btn"
                    onClick={() => props.handleProcess(props.fileContributor.nombreCompleto)}
                >
                    <ButtonContent name="CARGA DB" loading={props.loadingProcess}/>
                </button>
            </div>
        </>
    )
}