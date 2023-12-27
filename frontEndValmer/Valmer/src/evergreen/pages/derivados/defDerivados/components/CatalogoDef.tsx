import React, { useEffect, useState } from "react";
import { DataTableDef } from "../../../../../model";
import { FilterTable, Pagination, TableDef } from "./components";

interface CatalogoDefProps {
    data: DataTableDef[]
    handleSelectedInst: (tv: string, emisora: string) => void
}

export const CatalogoDef: React.FC<CatalogoDefProps> = ({ data, handleSelectedInst }) => {
    
    const [numRecords, setNumRecords] = useState<number>(10)
    const [position, setPosition] = useState<number>(0)
    const [totalRecords, setTotalRecords] = useState<number>(data.length)
    const [search, setSearch] = useState<string>("")
    const [dataTable, setDataTable] = useState<DataTableDef[]>([])

    useEffect(() => {
        setDataTable(data)
    }, [])

    const lastRecord = position + numRecords

    const handleChangeReg = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const num_reg = parseInt(e.target.value)
        setNumRecords(num_reg)
    }

    const handleSearch = async (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearch(e.target.value)
        if (search) {

            const result = data?.filter(item => 
                Object.values(item).some(value => 
                    typeof value === 'string' && value.toLowerCase().includes(search.toLowerCase())
                )
            )

            const num = result.length
            setTotalRecords(num)
    
            setDataTable(result)
        }
    }

    const goToPage = (numPosition: number) => {
        setPosition(numPosition)
    }

    return (
        <>
            <FilterTable 
                numReg={numRecords}
                search={search}
                handleSelectNum={handleChangeReg}
                handleSearch={handleSearch}
            />
            <TableDef 
                data={dataTable}
                position={position}
                lastRecord={lastRecord}
                handleSelectedInst={handleSelectedInst}
            />
            <Pagination
                position={position}
                lastRecord={lastRecord}
                totalRecords={totalRecords}
                numRecords={numRecords}
                goToPage={goToPage}
            />
        </>
    )

}