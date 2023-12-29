import { TvEmiDefProps } from "../../../../../model"
import { TvEmiDef } from "./TvEmiDef"
import { TvEmiDefNew } from "./TvEmiDefNew"

export const TvEmiDefOptions = (props: TvEmiDefProps) => {
    let Component

    if (props.isNew) {
        Component = TvEmiDefNew
    } else {
        Component = TvEmiDef
    }

    return <Component {...props} />
}