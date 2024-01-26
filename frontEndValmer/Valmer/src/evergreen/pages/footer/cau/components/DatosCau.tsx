import React, { useState, useRef } from 'react'
import { CatUser, Catalogo, InfoCauId, DownloadFile } from '../../../../../model'
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

const GetFile : React.FC<DownloadFile> = ({ contenido, nombre }) => {
    const enlaceRef = useRef<HTMLAnchorElement>(null);
    const downloadFile = () => {
        console.log("contenido", contenido);
        if (contenido && enlaceRef.current) {
            const byteCharacters = atob(contenido);
            const byteNumbers = new Array(byteCharacters.length);
      
            for (let i = 0; i < byteCharacters.length; i++) {
              byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
      
            const byteArray = new Uint8Array(byteNumbers);
            const blob = new Blob([byteArray], { type: 'application/octet-stream' });
      
            const url = URL.createObjectURL(blob);
            enlaceRef.current.href = url;
            enlaceRef.current.download = nombre;
            enlaceRef.current.click();

            URL.revokeObjectURL(url);
        }
    };
    return (
        <>
            <span onClick={downloadFile} className='text-cyan-700 hover:text-green-700 cursor-pointer'>
              {nombre}
            </span>
            <a ref={enlaceRef} style={{ display: 'none' }} />
        </>
    );
};

export const DatosCau: React.FC<DatosCauProps> = ({ 
    cau, catalog, data, loading, catUsr, loadingCatUsr, setData 
}) => {
    
    const [loadingSave, setLoadingSave] = useState<boolean>(false)

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
        if (setData) { setData(values)}
    }

    const convertNumberToString = (obj: any): any => {
        const newObj: { [key: string]: any } = {};
        for (const key in obj) {
            if (obj.hasOwnProperty(key)) {
                newObj[key] = typeof obj[key] === 'number' ? obj[key].toString() : obj[key];
            }
        }
        return newObj;
    }


    const saveData = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setLoadingSave(true)

        let url;

        if (cau === "abiertos")
        {
            url = "/cau/abiertos/actualiza-status"
        }
        else if (cau === "cierre")
        {
            url = "/cau/cerrados/actualiza-status"
        }
        else
        {
            url = "/cau/modificados/actualiza-status"
        }

        // Convierte los números a strings
        const dataAsString = convertNumberToString(data);

        await fetchDataPost(
            url,
            " al actualizar status",
            dataAsString,
            {s_user: userEncoded()}
        )
        setLoadingSave(false)
    }

    return (
        <div className='form-col-1'>
            <div className="form-col-1 mb-4 mt-1">
                {loading && <BarLoader className="mt-2" color="#059669" width={200} />}
                <div className="form-title">Datos</div>
                <div className="form-cols-2">
                    <div className="form-input">
                        <input 
                            type="text"
                            name="n_folio"
                            disabled
                            value={data.n_folio ?? ''}
                        />
                        <label htmlFor="n_folio">FOLIO</label>
                    </div>
                    {(cau === "modificacion" || data.muestra_boton_grabar) && (
                        <button className="btn my-3" onClick={saveData} type='button'>
                            <ButtonContent name='Grabar' loading={loadingSave} />
                        </button>
                    )}
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
                            name="n_servicio"
                            disabled
                            value={data.n_servicio ?? ''}
                        />
                        <label htmlFor="n_servicio">SERVICIO</label>
                    </div>
                )}
                <div className="form-input">
                    <input type="text"
                        name="n_empresa"
                        disabled
                        value={data.n_empresa ?? ''}
                    />
                    <label htmlFor="n_empresa">EMPRESA</label>
                </div>
                <div className="form-input">
                    <input type="text"
                        name="s_nombre"
                        disabled
                        value={data.s_nombre ?? ''}
                    />
                    <label htmlFor="s_nombre">NOMBRE</label>
                </div>
                <div className="form-input">
                    <input type="email"
                        name="s_correo"
                        disabled
                        value={data.s_correo ?? ''}
                    />
                    <label htmlFor="s_correo">CORREO</label>
                </div>
                {cau !== "cierre" && (
                    <div className="form-input">
                        <input type="text"
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
                            name='s_atendido'
                            value={data.s_atendio ?? ''}
                        />
                        <label htmlFor="s_atendido">ATENDIDO</label>
                    </div>
                ) : (
                    <div className="flex text-xs my-2">
                        <span className='mr-2'>ARCHIVO ADJUNTO</span>
                        <GetFile contenido={data.descarga_archivo?.contenido} nombre={data.archivo} />
                    </div>
                )}
                <div className="form-select w-1/3">
                    <select 
                        name="n_status"
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
                                value={data.d_fecha_estimada ?? ''}
                                onChange={handleChange}
                            />
                            <label htmlFor="d_fecha_estimada">FECHA ESTIMADA</label>
                        </div>
                        <div className="form-text-area">
                            <textarea 
                                name="s_observaciones"
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