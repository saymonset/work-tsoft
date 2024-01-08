import React, { useState } from "react";
import { generateUUID } from "../../../../../../utils";
import { TitleDate, EditCatalog } from "../../../../../../shared";
import { EditCauClient } from "./cauClient/EditCauClient";

interface Column {
    name: string;
    type: string;
}
export const ShowEditCau = ({ titleName, DataCatalog }: {
    titleName: string,
    DataCatalog: Array<{text: string, columns: Column[], edit?: boolean}>}) => {

    const [selectedOption, setSelectedOption] =
        useState<string | null>(null);
    const [nameCatalog, setNameCatalog] = useState<string>("");
    const [columns, setColumns] = useState<Column[]>([]);
    const [showTable, setShowTable] = useState(true);

    const handleClick = (text: string, columns: Column[]) => {
        setSelectedOption(text);
        setNameCatalog(text);
        setColumns(columns);
        setShowTable(false);
    };

    return (
        <>
            <TitleDate title={titleName} />

            {showTable && (
                <table className="text-center w-full">
                    <tbody>
                    {DataCatalog.map((item) => (
                        <tr key={generateUUID()}>
                            <td className="px-4 py-2 text-center text-blue-500 hover:text-blue-800">
                                <button onClick={() => handleClick(item.text, item.columns)}>
                                    {item.text}
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}

            {!showTable && (
                <div>
                    {selectedOption === "CAU_CLIENTE" ? (
                        <EditCauClient
                            nameCatalog={nameCatalog}
                            setShowTable={setShowTable}
                            setSelectedOption={setSelectedOption}
                        />
                    ) : (
                        <EditCatalog
                            nameCatalog={nameCatalog}
                            columns={columns}
                            setShowTable={setShowTable}
                        />
                    )}
                </div>
            )}
        </>
    );
};