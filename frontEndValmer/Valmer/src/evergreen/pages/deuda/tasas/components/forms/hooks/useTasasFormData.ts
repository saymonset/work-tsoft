import {useState} from "react";
import {fetchDataPost} from "../../../../../../../utils";

export const useTasasFormData = () => {
    const [isOpenUdi, setIsOpenUdi] = useState(false)
    const [loadingUdi, setLoadingUdi] = useState(false)
    const [isTextArea, setIsTextArea] = useState(false)
    const [textUdi, setTextUdi] = useState("")


    const handleCloseUdi = () => {
        setIsOpenUdi(false)
    }

    const handleOpenUdi = () => {
        setIsOpenUdi(true)
    }

    const handleChargeUdi = async () => {
        if(textUdi.trim() != "")
        {
            setIsTextArea(false)
            setLoadingUdi(true)
            await fetchDataPost(
                "/tasas/carga-udis",
                " error al cargar udi",
                {data: textUdi})
            setLoadingUdi(false)
            setIsOpenUdi(false)
        }
        else
        {
            setIsTextArea(true)
        }
    }

    return {
        textUdi,
        isOpenUdi,
        loadingUdi,
        isTextArea,
        setTextUdi,
        handleCloseUdi,
        handleOpenUdi,
        handleChargeUdi}
}