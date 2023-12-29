import {useState} from "react";

export const useActivePanelForm = () => {
    const [activePanel, setActivePanel] = useState("instrumentos");
    const [activeForm, setActiveForm] = useState("instrumento");

    const handleButtonClickPanel = (buttonId: string) => {
        setActivePanel(buttonId === activePanel ? "" : buttonId);
    };

    const handleButtonClickForm = (buttonId: string) => {
        setActiveForm(buttonId === activeForm ? "" : buttonId);
    };


    return {
        activePanel,
        activeForm,
        handleButtonClickPanel,
        handleButtonClickForm}
}