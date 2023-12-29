import { BarLoader } from "react-spinners";
import { generateUUID } from "../../../../../utils";

interface InstrumentoProps {
    selectedTv: string;
    handleClickTv: (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement>) => void;
    tvRegInv: string[];
    selectedEmisora: string;
    handleEmisora: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    emisorasRegInv: string[];
    loadingEmisoras: boolean;
    selectedSerie: string
    handleSerie: (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement>) => void;
    serieRegInv: string[];
    loadingSerie: boolean;
}

export const Instrumento = (
{
    selectedTv,
    handleClickTv,
    tvRegInv,
    selectedEmisora,
    handleEmisora,
    emisorasRegInv,
    loadingEmisoras,
    selectedSerie,
    handleSerie,
    serieRegInv,
    loadingSerie
}: InstrumentoProps) => {
    return (
        <>
        <div className="form-title">
            <span>Instrumento</span>
        </div>
        <div className="mt-4 form-cols-4">
            <div className="form-select">
                <select name="s_tv" value={selectedTv} onChange={handleClickTv}>
                    <option value="default">...</option>
                    {tvRegInv?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                <label htmlFor="s_tv">TV</label>
            </div>
            <div className="form-select">
                <select name="s_emisora" value={selectedEmisora} onChange={handleEmisora}>
                    <option value="default">...</option>
                    {emisorasRegInv?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                {loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={150}/>}
                <label htmlFor="s_emisora">Emisora</label>
            </div>
            <div className="form-select">
                <select name="s_serie" value={selectedSerie} onChange={handleSerie}>
                    <option value="default">...</option>
                    {serieRegInv?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                {loadingSerie && <BarLoader className="mt-2" color="#059669" width={150}/>}
                <label htmlFor="s_serie">SERIE</label>
            </div>
        </div>
        </>
    );
}