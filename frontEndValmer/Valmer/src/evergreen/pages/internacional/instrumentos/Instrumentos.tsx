import {ButtonContent, TitleDate} from "../../../../shared";
import {Panel} from "./panel";
import {HocRestricted} from "../../restrictedAccess";
import {Link} from "react-router-dom";
import {CalModalEuro} from "./components";
import {useInstrumentos} from "./hooks";

export const Instrumentos = () => {
    const title : string = "Instrumentos Internacionales";

    const {
        loadingSubmit,
        instrument,
        isModalOpen,
        loading,
        calPrecios,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        requeridosInter,
        handleModalClose,
        handleCalculator,
        handleClickNew,
        handleLimpiarClick,
        handleSubmit
    } = useInstrumentos()

    return (
        <HocRestricted title={title} view={title}>
            <TitleDate title={title +"jajaj"}/>

            <div className="flex justify-end pr-2">
                <Link to="/internacional/valuacion" className="btn">
                    <span>Valuación</span>
                </Link>
                <button
                    className="btn"
                    onClick={handleClickNew}
                >
                    <span>Nuevo</span>
                </button>
                <button
                    className="btn"
                    onClick={handleSubmit}
                >
                    <ButtonContent name={"Guardar"} loading={loadingSubmit}/>
                </button>
                <button
                    className="btn"
                    onClick={handleLimpiarClick}
                >
                    <span>Limpiar</span>
                </button>
                <button
                    className="btn"
                    onClick={handleCalculator}
                >
                    <i className="fa fa-calculator"></i>
                    <ButtonContent name=" Calculadora" loading={loading}/>
                </button>
            </div>

            <Panel requeridos={requeridosInter} />

            <CalModalEuro isModalOpen={isModalOpen}
                          instrument={instrument}
                          calPrecios={calPrecios}
                          selectedTv={selectedTv}
                          selectedEmisora={selectedEmisora}
                          selectedSerie={selectedSerie}
                          handleModalClose={handleModalClose} />
        </HocRestricted>
    );
};
