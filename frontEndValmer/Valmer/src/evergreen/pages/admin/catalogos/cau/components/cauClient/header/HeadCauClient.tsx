import React, {useEffect, useState} from "react";
import {Catalogo, ResponseCat} from "../../../../../../../../model";
import {fetchDataGetRet, getCatalogs} from "../../../../../../../../utils";
import {BarLoader} from "react-spinners";


export const HeadCauClient = ({catalogCau}: {catalogCau: Catalogo[]}) => {

    const [loadingClient, setLoadingClient] = useState(false)
    const [triggerClientById, setTriggerClientById] = useState(false)
    const [clients, setClients] = useState<Record<string, string>>({})

    const [enterprise, setEnterprise] = useState("")

    useEffect(() => {
        const getClientById = async () => {

            setLoadingClient(true)

            try {

                const response: ResponseCat = await fetchDataGetRet(
                    "/catalogos/cau-cliente/consulta-clientes-empresa",
                    " al obtener catalogo clientes empresa",
                    {
                        n_emp: enterprise,
                    }
                );

                setClients(response.body)
                setTriggerClientById(false)

            } catch (error) {
                console.error('Error al obtener los catálogos:', error);
            }

            setLoadingClient(false)
        };

        getClientById().then();

    }, [triggerClientById]);

    const handleEnterprise = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setTriggerClientById(true)
        setEnterprise(e.target.value);
    };

    return (
        <div className="ml-4 mr- mt-10 form-cols-2">
            <div className="relative z-0">
                <select defaultValue="default"
                        name="n_emp"
                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                        onChange={handleEnterprise}
                >
                    <option value="default">...</option>
                    {getCatalogs(catalogCau, "CAU_EMPRESA").map((column) => (
                        <option key={column[0]} value={column[0]}>
                            {column[1]}
                        </option>
                    ))}
                </select>
                <label
                    htmlFor="n_emp"
                    className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                >
                    Empresa
                </label>
            </div>
            <div className="relative z-0">
                <select defaultValue="default"
                        name="n_cli"
                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                >
                    <option value="default">...</option>
                </select>
                <label
                    htmlFor="n_cli"
                    className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                >
                    Cliente
                </label>
                {loadingClient && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500}/>}
            </div>
        </div>
    )
}