import {fieldsToValidateGub, InstrumentosType, IsFieldModifiedFvDdGobIns} from "../../../model";
import {Dispatch} from "redux";
import {updateFieldRequiredGub} from "../../../redux";
import {showAlert} from "../../Utils";

export const validGubField = async (
    formValues: InstrumentosType,
    dispatch: Dispatch,
    fieldRequired: IsFieldModifiedFvDdGobIns
) => {
    for (const field of fieldsToValidateGub) {
        if (isInvalidField(formValues[field.name], field.defaultValue)) {
            updateFieldAsInvalid(dispatch, fieldRequired, field.name);
            return false;
        }
    }

    if (formValues.n_tipo_instrumento !== "4") {
        const additionalFields = ["n_tasa_referencia", "n_frecuencia_cupon", "n_tipo_promedio", "n_tasa", "n_tasa_24h"];
        for (const fieldName of additionalFields) {
            if (isInvalidField(formValues[fieldName], fieldName === "n_tasa_referencia" ? 'default' : '')) {
                await showAlert("warning", "Error", `Si el instrumento No es Zero Coupon debe llevar ${fieldName}`);
                updateFieldAsInvalid(dispatch, fieldRequired, fieldName);
                return false;
            }
        }
    } else {
        dispatch(updateFieldRequiredGub({ ...fieldRequired, ["n_tasa_referencia"]: false }));
    }

    return true;
};

const isInvalidField = (fieldValue: any, defaultValue: string) => !fieldValue || fieldValue === defaultValue;

const updateFieldAsInvalid = (dispatch: Dispatch, fieldRequired: IsFieldModifiedFvDdGobIns, fieldName: string) => {
    dispatch(updateFieldRequiredGub({ ...fieldRequired, [fieldName]: true }));
};