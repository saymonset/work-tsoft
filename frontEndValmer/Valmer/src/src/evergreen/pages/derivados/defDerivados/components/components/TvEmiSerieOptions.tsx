import { components } from "react-select";
import { TvEmiSerieF } from "./TvEmiSerieF";
import { TvEmiSerieO } from "./TvEmiSerieO";
import { NewSerieO } from "./NewSerieO";
import { FormValuesVenc } from "../../../../../../model";

interface TvEmiSerieProps {
    tipoTv: string
    isNewSerie: boolean
    tv: string
    emisora: string
    serieOp: string[]
    formValuesVenc: FormValuesVenc
    loading: boolean
    setIsNewSerie: React.Dispatch<React.SetStateAction<boolean>>
    handleChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void
}

export const TvEmiSerieOptions = (props : TvEmiSerieProps) => {
    let Component;

    if (props.tipoTv === "O") {
        if (props.isNewSerie) {
            Component = NewSerieO
        } else {
            Component = TvEmiSerieO;
        }
    } else {
        Component = TvEmiSerieF;
    }

    return <Component {...props} />;
}