import React from 'react'

interface FilterTableProps {
    numReg: number
    search: string
    handleSelectNum: (e: React.ChangeEvent<HTMLSelectElement>) => void
    handleSearch: (e: React.ChangeEvent<HTMLInputElement>) => void
}

export const FilterTable: React.FC<FilterTableProps> = ({ numReg, search, handleSelectNum, handleSearch }) => {
    return (
        <div>
            <div className="mt-4 flex justify-between text-xs">
                <div>
                    <span>Mostrar </span>
                    <select 
                        className="border-solid border border-gray-400 pl-1"
                        name="show-entries" 
                        id="show-entries"
                        onChange={handleSelectNum}
                        value={numReg}
                    >
                        <option value="10">10</option>
                        <option value="15">15</option>
                        <option value="25">25</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                    <span> registros</span>
                </div>
                <div>
                    <span>Buscar </span>
                    <input 
                        className="border border-solid border-gray-400 px-1"
                        type="text" 
                        name="search" id="search"
                        onChange={handleSearch}
                        value={search}
                    />
                </div>
            </div>
        </div>
    )
}