import React from 'react'

interface PaginationProps {
    position: number
    lastRecord: number
    totalRecords: number
    numRecords: number
    goToPage: (numPosition: number) => void
}

export const Pagination: React.FC<PaginationProps> = ({ position, lastRecord, totalRecords, numRecords, goToPage }) => {

    const totalPages = Math.ceil(totalRecords/numRecords)
    const page = Math.floor(position/numRecords) + 1
    const nextPage = position + numRecords
    const prevPage = position - numRecords

    return (
        <div className='-mt-4 text-xs flex justify-between'>
            <div>
                Mostrando del {position + 1} al {lastRecord < totalRecords ? lastRecord : totalRecords} de {totalRecords} registros
            </div>
            <div className='paginador'>
                <button 
                    className={`${position <= 0 ? "disabled" : ""}`}
                    onClick={() => goToPage(prevPage)}
                >
                    Anterior
                </button>
                {totalRecords !== 0 && (
                    <span className='mx-2'>Página {page} de {totalPages}</span>
                )}
                <button 
                    className={`${totalRecords === 0 || totalPages === page 
                        ? "disabled"
                        : ""}`
                    }
                    onClick={() => goToPage(nextPage)}
                >
                    Siguiente
                </button>
            </div>
        </div>
    )
}