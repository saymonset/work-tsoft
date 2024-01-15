import {InstrumentoForm} from "../Forms";

export const ActiveInstrumentos = ({requeridos, activePanel} : any) => {

    return (
        <div className={`container-flex animate__animated ${activePanel ? "animate__fadeIn" : ""}`}>
            <div className="container-form container-form-py">
                <InstrumentoForm requeridos={requeridos} />
            </div>
        </div>
    )
}