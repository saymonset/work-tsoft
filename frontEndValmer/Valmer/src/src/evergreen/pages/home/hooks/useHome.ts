import {useRestHorarios} from "./useRestHorarios";
import {useRestHorariosBarras} from "./useRestHorariosBarras";
import dayjs from "dayjs";
import {useRestCau} from "./useRestCau";

export const useHome = () => {

    const {horarios, loadingHorarios} = useRestHorarios()
    const {horariosBarras, loadingBarras} = useRestHorariosBarras()
    const {cau, loadingCau} = useRestCau()

    const previousDate = () => {
        return dayjs().subtract(1, 'day').format('DD/MM/YYYY')
    }

    const userName = localStorage.getItem('user');


    return {
        loadingHorarios,
        loadingBarras,
        horarios,
        horariosBarras,
        cau,
        loadingCau,
        previousDate,
        userName
    }

}