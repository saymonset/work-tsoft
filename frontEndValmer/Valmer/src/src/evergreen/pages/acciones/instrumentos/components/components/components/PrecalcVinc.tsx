import React from "react";
import {generateUUID} from "../../../../../../../utils";
import {BarLoader} from "react-spinners";
import {usePrecalcTvEmiSerie} from "./hooks";

export const PrecalcVinc = () => {

    const {
        tv,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        loadingTv,
        loadingEmisoras,
        loadingSerie,
        handleClickTv,
        handleEmisora,
        handleSerie
    } = usePrecalcTvEmiSerie()

    return (
        <div className="animate__animated animate__fadeIn">
            <div className="mt-8 grid grid-cols-1 gap-4">
                <div className="border-gray-300 border my-5"></div>
            </div>

            <div className="mb-8 form-title text-center">
                <span>Vinculados</span>
            </div>

            <div className="form-cols-3">

                <div className="form-select">
                    <select
                        name="s_tv_vin"
                        value={selectedTv}
                        onChange={handleClickTv}
                    >
                        <option value="default">...</option>
                        {tv?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>

                    {loadingTv && <BarLoader className="mt-2" color="#059669" width={80}/>}
                    <label>
                        TV
                    </label>
                </div>
                <div className="form-select">
                    <select
                        name="s_emisora_vin"
                        value={selectedEmisora}
                        onChange={handleEmisora}
                    >
                        <option value="default">...</option>
                        {emisora?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>

                    {loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={80}/>}
                    <label>
                        Emisora
                    </label>
                </div>
                <div className="form-select">
                    <select
                        name="s_serie_vin"
                        value={selectedSerie}
                        onChange={handleSerie}
                    >
                        <option value="default">...</option>
                        {serie?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>

                    {loadingSerie && <BarLoader className="mt-2" color="#059669" width={80}/>}
                    <label>
                        SERIE
                    </label>
                </div>
            </div>
        </div>
    );
};