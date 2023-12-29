import React from 'react'

interface FilterRegProps {
    numRegistros: number
    search: string
    handleSelectNum: (e: React.ChangeEvent<HTMLSelectElement>) => void
    handleSearch: (e: React.ChangeEvent<HTMLInputElement>) => void
}

export const FilterReg: React.FC<FilterRegProps> = ({ numRegistros, search, handleSelectNum, handleSearch }) => {
    return (
        <div className='-mb-2'>
            <span className='text-xs mr-1'>Mostrando</span>
            <select 
                className='text-xs p-1 border border-solid border-cyan-700'
                name="registros"
                id="registros"
                value={numRegistros}
                onChange={handleSelectNum}
            >
                <option value="10">10</option>
                <option value="15">15</option>
                <option value="25" selected>25</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>
            <span className='text-xs ml-1'>registros por página</span>
            <input 
                className='float-right border border-solid border-cyan-700 px-1'
                type="text"
                name="buscar"
                id="buscar"
                placeholder='Buscar'
                value={search}
                onChange={handleSearch}
            />
        </div>
    )
}