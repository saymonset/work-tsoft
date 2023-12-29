import React from 'react'

interface PaginadorProps {
    posicion: number
    numRegistros: number
    totalRegistros: number
    goToPage: (numPosicion: number) => void
}

export const Paginador: React.FC<PaginadorProps> = ({ posicion, numRegistros, totalRegistros, goToPage }) => {

    const totalPages = Math.ceil(totalRegistros/numRegistros)
    const page = posicion/numRegistros + 1
    const nextPage = posicion + numRegistros
    const prevPage = posicion - numRegistros

    return (
        <div className='paginador -mt-4'>
            <button 
                className={`${posicion === 0 ? "disabled" : ""}`}
                onClick={() => goToPage(prevPage)}
            >
                Anterior
            </button>
            {totalRegistros !== 0 && (
                <span className='mx-2'>Página {page} de {totalPages}</span>
            )}
            <button 
                className={`${totalRegistros === 0 || totalPages === page 
                    ? "disabled"
                    : ""}`
                }
                onClick={() => goToPage(nextPage)}
            >
                Siguiente
            </button>
        </div>
    )
}