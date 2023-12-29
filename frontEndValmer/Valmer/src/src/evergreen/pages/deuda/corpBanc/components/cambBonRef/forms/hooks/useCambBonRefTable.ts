import {useState} from "react";
import {fetchDataPost} from "../../../../../../../../utils";
import {BodyBonosRef, ResponseBonosRef} from "../../../../../../../../model";

export const useCambBonRefTable = ({data, date}: {data: ResponseBonosRef, date: string}) => {

    const [param, setParam] = useState("");
    const [dataNew, setDataNew] = useState(data);
    const [loading, setLoading] = useState(false);

    const handleSave = async () => {
        setLoading(true);
        await fetchDataPost(
            "/instrumentos/corporativos/cambios-bonos-ref/actualiza-bonos-ref",
            " al guardar bonos referencia",
            null,
            {chk_instrumentos: param, d_fecha_consulta: date})
        setLoading(false);
    }

    const handleChangeChk = (e: any, item: BodyBonosRef, index: number) => {
        const isChecked = e.target.checked;
        const updatedItem = { ...item, data: { ...item.data, CAMBIO: isChecked ? "1" : "0" } };

        if (isChecked) {
            setParam(prevParam => {
                if (prevParam) {
                    return `${prevParam},${item.property}`;
                } else {
                    return item.property;
                }
            });
        } else {
            setParam(prevParam => prevParam.split(',').filter(p => p !== item.property).join(','));
        }

        setDataNew(prevData => {
            const newData = { ...prevData };
            newData.body[index] = updatedItem;
            return newData;
        });
    };

    const shouldDisableCheckbox = (item: BodyBonosRef) => {
        const fieldsToCheck = [
            item.property,
            item.data.BONO_AYER,
            item.data.BONO_HOY,
            item.data.DUR_INSTR,
            item.data.DUR_BONO_AYER,
            item.data.DUR_BONO_HOY,
            item.data.SOBRETASA
        ];

        return fieldsToCheck.some(field => field === "" || field === "NA");
    };

    return {dataNew, loading, shouldDisableCheckbox, handleSave, handleChangeChk}
}