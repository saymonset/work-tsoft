import React, { useEffect, useState } from 'react';
import { CatUser, InfoCauId } from "../../../../../model";
import { BarLoader } from 'react-spinners';
import { fetchDataPostAct, userEncoded } from '../../../../../utils';
import { ButtonContent } from '../../../../../shared';

interface AsignarCauProps {
    data: InfoCauId;
    catUsr: CatUser[];
    loading: boolean | undefined;
}

export const AsignarCau: React.FC<AsignarCauProps> = ({ data, catUsr, loading }) => {

    const [userAsig, setUserAsig] = useState<number>(data.n_usuario_asig);
    const [asignadoValue, setAsignadoValue] = useState<string | undefined>(data.s_usuario_asig);
    const [triggerAsig, setTriggerAsig] = useState<boolean>(false);
    const [loadingAsig, setLoadingAsig] = useState<boolean>(false);

    useEffect(() => {
        try {
            if (triggerAsig) {
                setLoadingAsig(true);
                const newData = {
                    n_folio: data.n_folio.toString(),
                    n_usuario_asig: userAsig.toString(),
                    n_servicio: data.n_serv_aux.toString()
                };
                fetchDataPostAct(
                    "/cau/abiertos/asignar-cau",
                    "Asignado",
                    " al actualizar asignación",
                    newData,
                    { s_user: userEncoded() }
                ).then(() => {
                    setAsignadoValue(catUsr.find(item => item.n_usuario === userAsig)?.s_nombre || '');
                }).catch(error => {
                    console.log("Se produjo el siguiente error: " + error);
                }).finally(() => {
                    setLoadingAsig(false);
                    setTriggerAsig(false);
                });
            }
        } catch (error) {
            setLoadingAsig(false);
            setTriggerAsig(false);
            console.log("Se produjo el siguiente error: " + error);
        }
    }, [triggerAsig, userAsig, data.n_folio, data.n_serv_aux, catUsr]);

    useEffect(() => {
        if (data.s_usuario_asig !== undefined && data.s_usuario_asig !== '') {
            setAsignadoValue(data.s_usuario_asig);
            setUserAsig(data.n_usuario_asig);
        } else {
            setAsignadoValue('');
            setUserAsig(0);
        }
    }, [data.s_usuario_asig, data.n_folio, data.n_usuario_asig]);

    const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const user = parseInt(e.target.value);
        setUserAsig(user);
    };

    const handleAsignar = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setTriggerAsig(true);
    };

    return (
        <>
            <hr className="line" />
            <div className='form-cols-3'>
                <div className="form-input">
                    <input
                        type="text"
                        id="s_usuario_asig"
                        name="s_usuario_asig"
                        disabled
                        value={asignadoValue ?? ''}
                    />
                    <label htmlFor="s_usuario_asig">ASIGNADO</label>
                </div>
                {data.b_asignar === "1" &&
                    <>
                        <div className="form-select pt-1 pl-8 pr-8">
                            <select
                                name="n_usuario_asig"
                                id="n_usuario_asig"
                                value={userAsig ?? ''}
                                onChange={handleChange}
                            >
                                <option value="0">...</option>
                                {catUsr.map((item) => (
                                    <option
                                        key={item.n_usuario}
                                        value={item.n_usuario}
                                    >
                                        {item.s_nombre}
                                    </option>
                                ))}
                            </select>
                            {loading && <BarLoader className="mt-2" color="#059669" width={100} />}
                            <label htmlFor="n_usuarioa_asig">ASIGNAR</label>
                        </div>
                        <button
                            className="btn my-3 w-3/4"
                            onClick={handleAsignar}
                            disabled={loadingAsig}
                        >
                            <ButtonContent name='Asignar' loading={loadingAsig} />
                        </button>
                    </>
                }
            </div>
            <hr className="line" />
        </>
    );
};
