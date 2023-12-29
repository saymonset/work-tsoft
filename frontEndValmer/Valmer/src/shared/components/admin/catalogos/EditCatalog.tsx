import React from "react";
import {PropsEdit,} from "../../../../model";
import {BarLoader, MoonLoader} from "react-spinners";
import {ButtonContent} from "../../ButtonContent";
import {useEditCatalog} from "./hooks";
import {getCatalogs} from "../../../../utils";
import {MemoizedColumn, MemoizedTableRow} from "./components";

export const EditCatalog = ({nameCatalog, setShowTable, columns, edit}: PropsEdit) => {

    const {
        isNew, selectRef,
        inputRefs, sortedColumns,
        loadingNomCorto,
        registros,
        loadingSave, loadingDelete,
        loadingNewId, loadingCatalogStatic,
        loadingCatalog, catalogs,
        catalogStatic, handleNew,
        handleRowClick, handleChange,
        handleSave, handleDelete,
        validInputValue, validSelectValue
    } = useEditCatalog({nameCatalog, columns})



    if (loadingCatalogStatic || loadingCatalog || !catalogs.length || !catalogStatic.length) {
        return (
            <div className="mt-24 flex justify-center items-center h-full">
                {loadingCatalog || loadingCatalogStatic ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <>
            <div className="form-cols-2 mb-4">
                <div className="flex justify-start pr-2">
                    <button className="btn" onClick={() => setShowTable(true)}>
                        <i className="mr-2 fa-solid fa-arrow-left"></i>
                        <span>Regresar</span>
                    </button>
                </div>

                <div className="flex justify-end pr-2">
                    <button className="btn"
                            onClick={handleSave}>
                        <ButtonContent name="Grabar" loading={loadingSave}/>
                    </button>
                    <button className="btn"
                            onClick={handleNew}>
                        <ButtonContent name="Nuevo" loading={loadingNewId}/>
                    </button>
                    <button className="btn"
                            onClick={handleDelete}>
                        <ButtonContent name="Borrar" loading={loadingDelete}/>
                    </button>
                </div>
            </div>

            <div className="flex mb-8">
                <div className="flex-1 mt-8 ml-8 text-center text-cyan-700 text-2xl font-semibold">
                    Catálogo {nameCatalog}
                </div>
            </div>

            <div className="flex flex-col">
                <div className="overflow-x-auto">
                    <div style={{ maxHeight: "500px", overflowY: "scroll" }}>
                        <table className="min-w-full">
                            <thead>
                            <tr>
                                {edit && (
                                    <th className="bg-cyan-700 text-white px-4 py-2">
                                        Editar
                                    </th>
                                )}
                                {columns.map(column => (
                                    <MemoizedColumn key={column.name} column={column} />
                                ))}
                            </tr>
                            </thead>
                            <tbody>
                            {registros.map((registro) => (
                                <MemoizedTableRow key={registro.id}
                                                  registro={registro}
                                                  columns={columns}
                                                  handleRowClick={handleRowClick}
                                                  edit={edit} />
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="mt-14 bg-cyan-700 h-8 w-full">
                    <div className="flex items-center justify-center h-full">
                        <span className="text-white text-lg font-bold">Datos</span>
                    </div>
                </div>

                <div className="ml-4 mr-10 form-cols-4">
                    <div className="mt-4 mb-4 relative z-0">
                        {sortedColumns.map(({
                                                name,
                                                field,
                                                type,
                                                catalog,
                                                isReadOnly,
                                                isReadOnlyEdit,
                                                columnIndex},
                                                index,
                                                 ) => {

                            const readOnly = isNew
                                ? isReadOnly ?? index === 0
                                : isReadOnlyEdit ?? index === 0;

                            return (
                                <div key={name}>
                                    {type === "input" ? (
                                            <div className="form-input">
                                                <input
                                                    ref={el => {
                                                        if (el) inputRefs.current[index] = el;
                                                    }}
                                                    className="mt-8"
                                                    type="text"
                                                    name={name.toLowerCase()}
                                                    value={validInputValue(name.toLowerCase())}
                                                    onChange={handleChange}
                                                    readOnly={readOnly}
                                                />
                                                <label htmlFor={name}>{field ?? name}</label>
                                                {name === "S_NOMCORTO" && loadingNomCorto && (
                                                    <BarLoader className="w-full mt-2 mb-2" color="#059669" width={80} />
                                                )}
                                            </div>
                                    ) : (
                                        <div className="form-select">
                                            <select ref={selectRef}
                                                    name={name.toLowerCase()}
                                                    className="mt-8 block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                                    border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                                    value={validSelectValue(name.toLowerCase(), catalog)}
                                                    onChange={handleChange}
                                            >
                                                <option value="default">...</option>
                                                {getCatalogs(catalogStatic, catalog).map((column) => (
                                                    <option key={column[columnIndex ?? 0]} value={column[columnIndex ?? 0]}>
                                                        {column[1]}
                                                    </option>
                                                ))}
                                            </select>
                                            <label htmlFor={name}>{field ?? name}</label>
                                        </div>
                                    )}
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>
        </>
    );
}