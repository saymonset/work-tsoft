import React, { useEffect, useState } from 'react'
import { CatUser, Catalogo, InfoCauId } from '../../../../../model'
import { fetchDataPost, getCatalogs, userEncoded } from '../../../../../utils'
import { BarLoader } from 'react-spinners'
import { AsignarCau } from './AsignarCau'
import { ButtonContent } from '../../../../../shared'

interface DatosCauProps {
    cau: string
    catalog: Catalogo[]
    data: InfoCauId
    loading: boolean
    catUsr?: CatUser[]
    loadingCatUsr?: boolean
    setData?: React.Dispatch<React.SetStateAction<InfoCauId>>
}

export const DatosCau: React.FC<DatosCauProps> = ({ 
    cau, catalog, data, loading, catUsr, loadingCatUsr, setData 
}) => {

    const [loadingSave, setLoadingSave] = useState<boolean>(false)
    const [url, setUrl] = useState<string>("")

    useEffect(() => {
        if (cau === "abierto") {
            setUrl("/cau/abiertos/actualiza-status")
        } else {
            setUrl("/cau/modificados/actualiza-status")
        }
    }, [])


    const getLabel = (cau: string): string => {
        if(cau === 'modificacion') {
            return "SECTOR"
        } else {
            return "AREA"
        }
    }

    const handleChange = (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target
        const values = {...data, [name]: value}
        console.log(values)
        if (setData) { setData(values)}
    }

    const saveData = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setLoadingSave(true)
        await fetchDataPost(
            url,
            " al actualizar status",
            data,
            {s_user: userEncoded()}
        )
        setLoadingSave(false)
    }

    return (
        <div className='form-col-1'>
            <div className="form-col-1 mb-4">
                {loading && <BarLoader className="mt-2" color="#059669" width={200} />}
                <div className="form-title">Datos</div>
                <div className="form-cols-2">
                    <div className="form-input">
                        <input 
                            type="text"
                            id="n_folio"
                            name="n_folio"
                            disabled
                            value={data.n_folio ?? ''}
                        />
                        <label htmlFor="n_folio">FOLIO</label>
                    </div>
                    <button className="btn my-3" onClick={saveData} type='button'>
                        <ButtonContent name='Grabar' loading={loadingSave} />
                    </button>
                </div>
                {cau === "abiertos" && catUsr &&(
                    <AsignarCau 
                        data={data}
                        catUsr={catUsr}
                        loading={loadingCatUsr} 
                    />
                )}
                <div className="form-input w-1/2">
                    <input type="text" 
                        id="n_area"
                        name="n_area"
                        disabled
                        value={data.n_area ?? ''}
                    />
                    <label htmlFor="n_area">{getLabel(cau)}</label>
                </div>
                {cau === "modificacion" ? (
                    <div className="form-select w-2/3">
                        <select 
                            name="n_servicio" 
                            id="n_servicio"
                            value={data.n_servicio ?? ''}
                            onChange={handleChange}
                        >
                            <option value="0">...</option>
                            {getCatalogs(catalog, "VALMER_PAGINA.CAU_SERVICIOS").map((item) => (
                                <option key={item[0]} value={item[0]}>
                                    {item[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_servicio">SERVICIO</label>
                    </div>
                ) : (
                    <div className="form-input w-2/3">
                        <input type="text" 
                            id="n_servicio"
                            name="n_servicio"
                            disabled
                            value={data.n_servicio ?? ''}
                        />
                        <label htmlFor="n_servicio">SERVICIO</label>
                    </div>
                )}
                <div className="form-input">
                    <input type="text" 
                        id="n_empresa"
                        name="n_empresa"
                        disabled
                        value={data.n_empresa ?? ''}
                    />
                    <label htmlFor="n_empresa">EMPRESA</label>
                </div>
                <div className="form-input">
                    <input type="text" 
                        id="s_nombre"
                        name="s_nombre"
                        disabled
                        value={data.s_nombre ?? ''}
                    />
                    <label htmlFor="s_nombre">NOMBRE</label>
                </div>
                <div className="form-input">
                    <input type="email" 
                        id="s_correo"
                        name="s_correo"
                        disabled
                        value={data.s_correo ?? ''}
                    />
                    <label htmlFor="s_correo">CORREO</label>
                </div>
                {cau !== "cierre" && (
                    <div className="form-input">
                        <input type="text" 
                            id="s_telefono"
                            name="s_telefono"
                            disabled
                            value={data.s_telefono ?? ''}
                        />
                        <label htmlFor="s_telefono">TELEFONO</label>
                    </div>
                )}
                <div className="form-text-area">
                    <textarea 
                        name="s_descripcion" 
                        id="s_descripcion" 
                        disabled
                        cols={30} 
                        rows={3}
                        value={data.s_descripcion ?? ''}
                    >
                    </textarea>
                    <label htmlFor="s_descripcion">DESCRIPCION</label>
                </div>
                {cau === "cierre" ? (
                    <div className="form-input">
                        <input type="email"
                            id='s_atendido'
                            name='s_atendido'
                            value={data.s_atendio ?? ''}
                        />
                        <label htmlFor="s_atendido">ATENDIDO</label>
                    </div>
                ) : (
                    <div className="flex text-xs my-2">
                        <span className='mr-2'>ARCHIVO ADJUNTO</span>
                        <a href={`/CAU_DOCS/${data.archivo}`} 
                            className='text-cyan-700 hover:text-green-700'
                        >
                            { data.archivo }
                        </a>
                    </div>
                )}
                <div className="form-select w-1/3">
                    <select 
                        name="n_status" 
                        id="n_status"
                        value={data.n_status ?? ''}
                        onChange={handleChange}
                    >
                        <option value="0">...</option>
                        {getCatalogs(catalog, "CAU_STATUS").map((item) => (
                            <option key={item[0]} value={item[0]}>
                                {item[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_status">STATUS</label>
                </div>
                {data.n_status === '3' && (
                    <>
                        <div className="form-date w-1/4">
                            <input type="date" 
                                name="d_fecha_estimada" 
                                id="d_fecha_estimada"
                                value={data.d_fecha_estimada ?? ''}
                                onChange={handleChange}
                            />
                            <label htmlFor="d_fecha_estimada">FECHA ESTIMADA</label>
                        </div>
                        <div className="form-text-area">
                            <textarea 
                                name="s_observaciones" 
                                id="s_observaciones" 
                                cols={30} 
                                rows={3}
                                value={data.s_observaciones ?? ''}
                                onChange={handleChange}
                            >
                            </textarea>
                            <label htmlFor="s_observaciones">OBSERVACIONES</label>
                        </div> 
                    </> 
                )}
            </div>
        </div>
    )
}