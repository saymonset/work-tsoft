import { TvEmiSerieProps } from "../../../../../../../../../model";
import { TvEmiSerieDefault } from "./TvEmiSerieDefault";
import { TvEmiSerieNew } from "./TvEmiSerieNew";

export const TvEmiSerieOptions = (props: TvEmiSerieProps) => {
    let Component;

    if (props.isNewFormInst) {
        Component = TvEmiSerieNew;
    } else {
        Component = TvEmiSerieDefault;
    }

    return <Component {...props} />;
}