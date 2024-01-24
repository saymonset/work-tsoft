import { useState } from 'react'

export const useBigInput = () => {

    const [focusedInput, setFocusedInput] = useState<string>("");

    const handleFocus = (inputName: string) => {
        setFocusedInput(inputName);
    }

    const handleBlur = () => {
        setFocusedInput("");
    }

    const sendStyle = (inputName: string)=>{
            if (!focusedInput || focusedInput !== inputName){
                return { paddingTop: '0rem', paddingBottom: '0rem' } 
            }
       
            return  {}
    }

  return {
    handleFocus,
    handleBlur,
    sendStyle
  }
}
