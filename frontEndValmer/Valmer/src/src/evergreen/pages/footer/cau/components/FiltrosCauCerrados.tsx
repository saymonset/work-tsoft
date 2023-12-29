import React from 'react'
import { Catalogo } from '../../../../../model'
import { getCatalogs } from '../../../../../utils'

interface FiltrosCerradosProps {
    catalog: Catalogo[]
    empresa: string
    servicio: string
    area: string
    setEmpresa: React.Dispatch<React.SetStateAction<string>>
    setServicio: React.Dispatch<React.SetStateAction<string>>
    setArea: React.Dispatch<React.SetStateAction<string>>
    setTriggerData: React.Dispatch<React.SetStateAction<boolean>>
}

export const FiltrosCauCerrados: React.FC<FiltrosCerradosProps> = ({ 
    catalog, empresa, servicio, area, setEmpresa, setServicio, setArea, setTriggerData
}) => {

    const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const {name, value} = e.target
        if (name === 'n_emp') {
            setEmpresa(value)
        } else if (name === 'n_serv') {
            setServicio(value)
        } else {
            setArea(value)
        }
        setTriggerData(true)
    }

    const handleLimp = () => {
        setArea("")
        setServicio("")
        setEmpresa("")
        setTriggerData(true)
    }

    return (
        <div className="form-cols-4">
            <div className="form-select">
                <select 
                    name="n_area_cat" 
                    id="n_area_cat"
                    value={area ?? ''}
                    onChange={handleChange}
                >
                    <option value="0">...</option>
                    {getCatalogs(catalog, "VALMER_PAGINA.VM_SEG_CAT_AREA").map((item) => (
                        <option key={item[0]} value={item[0]}>
                            {item[1]}
                        </option>
                    ))}
                </select>
                <label htmlFor="n_area_cat">AREA</label>
            </div>
            <div className="form-select">
                <select 
                    name="n_serv" 
                    id="n_serv"
                    value={servicio ?? ''}
                    onChange={handleChange}
                >
                    <option value="0">...</option>
                    {getCatalogs(catalog, "VALMER_PAGINA.CAU_SERVICIOS").map((item) => (
                        <option key={item[0]} value={item[0]}>
                            {item[1]}
                        </option>
                    ))}
                </select>
                <label htmlFor="n_serv">SERVICIO</label>
            </div>
            <div className="form-select">
                <select 
                    name="n_emp" 
                    id="n_emp"
                    value={empresa ?? ''}
                    onChange={handleChange}
                >
                    <option value="0">...</option>
                    {getCatalogs(catalog, "VALMER_PAGINA.VM_SEG_INSTITUCION").map((item) => (
                        <option key={item[0]} value={item[0]}>
                            {item[1]}
                        </option>
                    ))}
                </select>
                <label htmlFor="n_emp">EMPRESA</label>
            </div>
            <button 
                className='btn my-3'
                onClick={handleLimp}
            >
                Limpiar Filtros
            </button>
        </div>
    )
}