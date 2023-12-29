import { combineReducers, configureStore, createReducer } from "@reduxjs/toolkit";
import {
    updateCheckActualiza1bActualizaBd,
    updateCheckDescargaCateRv,
    updateCheckFormateaCateRv,
    updateCheckGenerarSalidas, updateCheckInternacionalActualizaBd,
    updateCheckNacionalActualizaBd, updateCheckPrecalIntProcesoRv, updateCheckPrecalNacProcesoRv,
    updateCheckProduccionSalidas,
    updateFechaProcesoRV,
    updateIsShowLogBoxOuts,
    updateSelectedVectorRV,
    updateShowActualiza1BActBd,
    updateShowDescargaCateRv,
    updateShowFormateaCateRv,
    updateShowIndicadoConsultas,
    updateShowInternacionalActBd,
    updateShowNacionalActBd,
    updateShowProcesoRvLog
} from "./actions";
import dayjs from "dayjs";

const fechaProcesoRVReducer = createReducer<string>(
    dayjs().format('YYYY-MM-DD'),
    (builder) => {
        builder.addCase(updateFechaProcesoRV, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedVectorRVReducer = createReducer<string>(
    '',
    (builder) => {
        builder.addCase(updateSelectedVectorRV, (_state, action) => {
            return action.payload;
        });
    }
);

const IsShowLogBoxOutsReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsShowLogBoxOuts, (_state, action) => {
            return action.payload;
        });
    }
);

const ShowProcesoRvLogReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowProcesoRvLog, (_state, action) => {
            return action.payload;
        });
    }
);

const ShowIndicadoConsultasReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowIndicadoConsultas, (_state, action) => {
            return action.payload;
        });
    }
);

const ShowDescargaCateRvReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowDescargaCateRv, (_state, action) => {
            return action.payload;
        });
    }
);

const ShowFormateaCateRvReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowFormateaCateRv, (_state, action) => {
            return action.payload;
        });
    }
);

const ShowActualiza1BActBdReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowActualiza1BActBd, (_state, action) => {
            return action.payload;
        });
    }
);

const ShowNacionalActBdReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowNacionalActBd, (_state, action) => {
            return action.payload;
        });
    }
);

const ShowInternacionalActBdReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowInternacionalActBd, (_state, action) => {
            return action.payload;
        });
    }
);

const CheckFormateaCateRvReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckFormateaCateRv, (_state, action) => {
            return action.payload;
        });
    }
);

const CheckDescargaCateRvReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckDescargaCateRv, (_state, action) => {
            return action.payload;
        });
    }
);

const CheckInternacionalActualizaBdReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckInternacionalActualizaBd, (_state, action) => {
            return action.payload;
        });
    }
);

const CheckNacionalActualizaBdReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckNacionalActualizaBd, (_state, action) => {
            return action.payload;
        });
    }
);

const CheckActualiza1bActualizaBdReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckActualiza1bActualizaBd, (_state, action) => {
            return action.payload;
        });
    }
);

const CheckPrecalIntProcesoRvReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckPrecalIntProcesoRv, (_state, action) => {
            return action.payload;
        });
    }
);


const CheckPrecalNacProcesoRvReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckPrecalNacProcesoRv, (_state, action) => {
            return action.payload;
        });
    }
);

const CheckGenerarSalidasReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckGenerarSalidas, (_state, action) => {
            return action.payload;
        });
    }
);

const CheckProduccionSalidasReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateCheckProduccionSalidas, (_state, action) => {
            return action.payload;
        });
    }
);


const rootProcesoRV = combineReducers({
    fechaProcesoRV: fechaProcesoRVReducer,
    selectedVectorRV: selectedVectorRVReducer,
    IsShowLogBoxOuts: IsShowLogBoxOutsReducer,
    ShowProcesoRvLog: ShowProcesoRvLogReducer,
    ShowIndicadoConsultas: ShowIndicadoConsultasReducer,
    ShowDescargaCateRv: ShowDescargaCateRvReducer,
    ShowFormateaCateRv: ShowFormateaCateRvReducer,
    ShowActualiza1BActBd: ShowActualiza1BActBdReducer,
    ShowNacionalActBd: ShowNacionalActBdReducer,
    ShowInternacionalActBd: ShowInternacionalActBdReducer,
    CheckFormateaCateRv: CheckFormateaCateRvReducer,
    CheckDescargaCateRv: CheckDescargaCateRvReducer,
    CheckInternacionalActualizaBd: CheckInternacionalActualizaBdReducer,
    CheckNacionalActualizaBd: CheckNacionalActualizaBdReducer,
    CheckActualiza1bActualizaBd: CheckActualiza1bActualizaBdReducer,
    CheckPrecalIntProcesoRv: CheckPrecalIntProcesoRvReducer,
    CheckPrecalNacProcesoRv: CheckPrecalNacProcesoRvReducer,
    CheckGenerarSalidas: CheckGenerarSalidasReducer,
    CheckProduccionSalidas: CheckProduccionSalidasReducer
});

export const storeProcess = configureStore({
    reducer: rootProcesoRV
});