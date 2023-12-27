import { TitleDate } from "../../../../shared";
import { HocRestricted } from "../../restrictedAccess";
import { UserWebForm } from "./components/UserWebForm";

export const UserWeb = () => {

    const title = "Configuración Usuarios Web Valmer"
    
    return (
        <HocRestricted title={title} view={title} >
            <TitleDate title={title} />

            <form className="mb-10 animate__animated animate__fadeIn">
                <UserWebForm/>
            </form>

        </HocRestricted>
    )
}