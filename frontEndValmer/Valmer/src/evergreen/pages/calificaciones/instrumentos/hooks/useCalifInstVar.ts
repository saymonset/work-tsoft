import { useState } from "react"
import { CalifInstData, SectionType } from "../../../../../model"

export const useCalifInstVar = () => {

    const [tv, setTv] = useState<string[]>([])
    const [loadingTv, setLoadingTv] = useState(false)
    const [tvFondos, setTvFondos] = useState<string[]>([])
    const [loadingTvFondos, setLoadingTvFondos] = useState(false)
    const [selectedTv, setSelectedTv] = useState<string>("")

    const [selectedEmisora, setSelectedEmisora] = useState<string>("")
    const [emisoras, setEmisoras] = useState<string[]>([])
    const [loadingEmisoras, setLoadingEmisoras] = useState(false)
    const [triggerEmisora, setTriggerEmisora] = useState(false)
    const [selectedSerie, setSelectedSerie] = useState<string>("")
    const [series, setSeries] = useState<string[]>([])
    const [loadingSeries, setLoadingSeries] = useState(false)
    const [triggerSerie, setTriggerSerie] = useState(false)
    const [consultaData, setConsultaData] = useState<CalifInstData>({} as CalifInstData)
    const [loadingConsultaData, setLoadingConsultaData] = useState(false)
    const [triggerConsultaData, setTriggerConsultaData] = useState(false)

    const [loadingSave, setLoadingSave] = useState(false)

    const [isNewSerie, setIsNewSerie] = useState(false)
    const [isNewInstr, setIsNewInstr] = useState(false)
    const [isFondos, setIsFondos] = useState(false)

    const [section, setSection] = useState<SectionType | "">("")
    const [fileBase64, setFileBase64] = useState<string | null>(null)
    const [selectedFile, setSelectedFile] = useState<File | null>(null)
    
    return {
        isFondos,
        isNewInstr,
        isNewSerie,
        selectedTv,
        loadingTv,
        tv,
        loadingTvFondos,
        tvFondos,
        selectedEmisora,
        emisoras,
        loadingEmisoras,
        triggerEmisora,
        selectedSerie,
        series,
        loadingSeries,
        triggerSerie,
        consultaData,
        loadingConsultaData,
        triggerConsultaData,
        loadingSave,
        section,
        fileBase64,
        selectedFile,
        setSection,
        setFileBase64,
        setSelectedFile,
        setIsFondos,
        setIsNewInstr,
        setIsNewSerie,
        setLoadingSave,
        setSelectedTv,
        setTv,
        setLoadingTv,
        setTvFondos,
        setLoadingTvFondos,
        setSelectedEmisora,
        setEmisoras,
        setLoadingEmisoras,
        setTriggerEmisora,
        setSelectedSerie,
        setSeries,
        setLoadingSeries,
        setTriggerSerie,
        setConsultaData,
        setLoadingConsultaData,
        setTriggerConsultaData
    }
}