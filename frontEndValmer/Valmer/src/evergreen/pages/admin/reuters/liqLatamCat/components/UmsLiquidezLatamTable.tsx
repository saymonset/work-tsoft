import React, { useState } from 'react';
import '../../table.css';
import { ILiqLatam } from '../../Models';
import { MoonLoader } from 'react-spinners';

interface Table {
  onOpenDelete: (e: React.MouseEvent<HTMLElement>) => void;
  onOpenEdit: (e: React.MouseEvent<HTMLElement>) => void;
  tableData: ILiqLatam;
  loadingData: boolean;
  totaPages: number;
  n_cbo_pais: number;
  textSearch: string;
  getDataTable: (pais: number, numRegistros: number, position: number, txt_buscar: string) => Promise<void>;
}

export const UmsLiquidezLatamTable: React.FC<Table> = ({
  onOpenDelete,
  onOpenEdit,
  tableData,
  loadingData,
  totaPages,
  n_cbo_pais,
  textSearch,
  getDataTable,
}) => {
  const [position, setPosition] = useState(0);
  const numRegistros = 14;

  const goToPage = (newPosition: number) => {
    setPosition(newPosition);
    getDataTable(n_cbo_pais, numRegistros, newPosition, textSearch);
  };

  if (loadingData || !tableData || !tableData.body.registros) {
    return (
      <div className="flex justify-center items-center h-[256px]">
        {loadingData ? (
          <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
        ) : (
          <div>No hay información</div>
        )}
      </div>
    );
  }

  let color = "bg-white";
  let prevIsin = "";

  return (
    <div className="max-h-screen overflow-y-scroll mb-5">
      <table className="table">
        <thead className='thead'>
          <tr>
            <th>ELIMINAR</th>
            <th>ISIN</th>
            <th>INSTRUMENTO</th>
            <th>RIC</th>
            <th>TIPO</th>
            <th>EDITAR</th>
          </tr>
        </thead>
        <tbody className='tbody'>
          {tableData.body.registros.map((registro, index) => {
            const instrumentosCount = tableData.body.registros.filter(reg => reg.isin === registro.isin).length;
            const rowspan = index === 0 || registro.isin !== prevIsin ? instrumentosCount : 0;

            if (prevIsin !== registro.isin) {
              if (color === "bg-white") {
                color = "bg-gray-300";
              } else {
                color = "bg-white";
              }
            }

            prevIsin = registro.isin;

            return (
              <tr key={index}>
                {rowspan > 0 && (
                  <td rowSpan={rowspan} className={color}>
                    {tableData.body.registros.some(reg => reg.isin === registro.isin) && (
                      <button
                        data-sisin={registro.isin}
                        data-ricformato={registro.s_formato}
                        data-instrumento={registro.s_instrumento}
                        data-sric={registro.s_ric}
                        data-stipo={registro.s_tipo}
                        onClick={onOpenDelete}
                      >
                        {tableData.body.registros.length > 1 && (
                          <i className="fa-regular fa-trash-can text-xl text-cyan-700"></i>
                        )}
                      </button>
                    )}
                  </td>
                )}
                {rowspan > 0 && <td rowSpan={rowspan} className={color}>{registro.isin}</td>}
                <td className={color}>{registro.s_instrumento}</td>
                <td className={color}>{registro.s_ric}</td>
                <td className={color}>{registro.s_tipo}</td>
                <td className={color}>
                  <button
                    data-sisin={registro.isin}
                    data-ricformato={registro.s_formato}
                    data-instrumento={registro.s_instrumento}
                    data-sric={registro.s_ric}
                    data-stipo={registro.s_tipo}
                    onClick={onOpenEdit}
                  >
                    {tableData.body.registros.length > 1 && (
                      <i className="fa fa-file-pen text-xl text-cyan-700"></i>
                    )}
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
      <div className="text-white bg-cyan-700 flex justify-center mt-10">
        <button
          className={`hover:text-black ${position === 0 ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none' : ''
            }`}
          onClick={() => goToPage(position - numRegistros)}
          disabled={position === 0}
        >
          Anterior
        </button>
        <span className="mx-2">
          Página {position / numRegistros + 1} de {totaPages}
        </span>
        <button
          className={`hover:text-black ${totaPages === 1
            ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none'
            : ''
            }`}
          onClick={() => goToPage(position + numRegistros)}
          disabled={position + numRegistros >= tableData.body.total_registros}
        >
          Siguiente
        </button>
      </div>
    </div>
  );
};
