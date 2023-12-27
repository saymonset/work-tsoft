import { FormInstProps } from "../../../../../model";
import { DefaultCalif } from "./DefaultCalif";
import { NewInstr } from "./NewInstr";
import { NewSerie } from "./NewSerie";

export const OptTvEmiSerie = (props: FormInstProps) => {
    let Component;

    if (props.isNewSerie)
    {
        Component = NewSerie;
    }
    else if (props.isNewInst)
    {
        Component = NewInstr;
    }
    else
    {
        Component = DefaultCalif;
    }

    return <Component {...props} />;
}