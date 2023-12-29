import {useEffect, useRef, useState} from "react";

export const useDropMenu = () => {
    const [showOptions, setShowOptions] = useState(false);
    const buttonRef = useRef<HTMLButtonElement>(null);

    useEffect(() => {
        // Función que se ejecuta cuando se hace clic fuera del dropdown
        const handleClickOutside = (event: MouseEvent) => {
            if (buttonRef.current && !buttonRef.current.contains(event.target as Node)) {
                setShowOptions(false);
            }
        };
        // Agregar event listener al document
        document.addEventListener("click", handleClickOutside);

        // Eliminar event listener al desmontar el componente
        return () => {
            document.removeEventListener("click", handleClickOutside);
        };
    }, [buttonRef]);

    const handleButtonClick = () => {
        setShowOptions(!showOptions);
    };

    return {
        buttonRef,
        showOptions,
        handleButtonClick
    }
}