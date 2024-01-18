import React from 'react'
import { getCatalogs } from "../../../../../utils"
import { Catalogo, InfoCauId } from '../../../../../model'

interface ComboEmpresaProps {
    catalog: Catalogo[]
    empresa: string
    setEmpresa: React.Dispatch<React.SetStateAction<string>>
    setTriggerData: React.Dispatch<React.SetStateAction<boolean>>
    setNumRegistros: React.Dispatch<React.SetStateAction<number>>
    setPosicion: React.Dispatch<React.SetStateAction<number>>
    setSearch: React.Dispatch<React.SetStateAction<string>>
    setData: React.Dispatch<React.SetStateAction<InfoCauId>>
}

export const ComboEmpresa: React.FC<ComboEmpresaProps> = ({ catalog, empresa, setEmpresa, setTriggerData, setNumRegistros, setPosicion, setSearch, setData }) => {

    const handleLimp = () => {
        setEmpresa("")
        setNumRegistros(25)
        setPosicion(0)
        setSearch("")
        setTriggerData(true)
        setData({} as InfoCauId)
    }

    const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setEmpresa(e.target.value)
        setTriggerData(true)
    }
    
    return (
        <div className="form-cols-1">
            <div className="form-cols-4">
                <div className="form-select col-span-2">
                    <select 
                        name="n_emp"
                        id="n_emp"
                        value={empresa}
                        onChange={handleChange}
                    >
                        <option value="">...</option>
                        {getCatalogs(catalog, 'VALMER_PAGINA.VM_SEG_INSTITUCION').map((item) => (
                            <option value={item[0]} key={item[0]}>
                                {item[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_status">EMPRESA</label>
                </div>
                <div className="flex justify-center py-3">
                    <button 
                        className="btn w-full"
                        onClick={handleLimp}
                    >
                        LIMPIAR FILTROS
                    </button>
                </div>
            </div>
        </div>
    )
}