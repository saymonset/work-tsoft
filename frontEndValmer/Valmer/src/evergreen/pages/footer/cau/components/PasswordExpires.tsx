import React from 'react'
import { PassExpInfo } from '../../../../../model'
import { BarLoader } from 'react-spinners'
import { generateUUID } from '../../../../../utils'

interface PasswordExpiresProps {
    data: PassExpInfo[]
    loading: boolean
    validaUser: boolean
    setTriggerData: React.Dispatch<React.SetStateAction<boolean>>
}

export const PasswordExpires: React.FC<PasswordExpiresProps> = ({ data, loading, validaUser, setTriggerData }) => {

    const validaDias = (valor: string): string => {
        const dia = parseInt(valor)
        if (dia < 10) {
            return "bg-red-600"
        } else if (dia < 15) {
            return "bg-orange-600"
        } else {
            return "bg-green-600"
        }
    }

    return (
        <div className="row-span-1">
            {loading && <BarLoader className="mt-2" color="#059669" width={200} />}
            <table className="table w-full">
                <thead className="thead">
                    <th>FTP</th>
                    <th>FECHA EXP</th>
                    <th>DIAS</th>
                    <th>FUENTE</th>
                </thead>
                <tbody className="tbody">
                    {data?.length > 0 && data.map((item) => {
                        return (
                            <tr className='tr' key={generateUUID()}>
                                <td>{ item.ftp }</td>
                                <td>{ item.fecha_exp }</td>
                                <td
                                    className={`${validaDias(item.dias)} text-white`}
                                >
                                    { item.dias }
                                </td>
                                <td>{ item.fuente }</td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
        </div>
    )
}