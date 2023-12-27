import React from "react";
import {TvEmiSerieProps} from "../../../../model";
import {SeriesNew, TvEmiSeriesDefault, TvEmiSeriesNew} from "./components";

export const TvEmiSerieOptions = (props : TvEmiSerieProps) => {
    let Component;

    if (props.isNewSerie) {
        Component = SeriesNew;
    } else if (props.isNewFormInst) {
        Component = TvEmiSeriesNew;
    } else {
        Component = TvEmiSeriesDefault;
    }

    return <Component {...props} />;
}