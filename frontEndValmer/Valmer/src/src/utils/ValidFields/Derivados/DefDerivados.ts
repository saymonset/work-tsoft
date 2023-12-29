import { Dispatch } from "redux";
import { IsFieldModifiedFvDerivados, InstrumentosDerivadosType, RequeridosDerivados, fieldToValidateDefDerivados, RequeridosDefDerivados } from "../../../model";
import { updateFieldRequiredDerivados } from "../../../redux";
import { focusElement } from "../../Utils";

export const validDefDerivadosField = async (
    formValues: InstrumentosDerivadosType,
    dispatch: Dispatch,
    fieldRequired: IsFieldModifiedFvDerivados,
    requeridos: RequeridosDerivados | RequeridosDefDerivados
) => {

    for (const field of fieldToValidateDefDerivados) {
        if (isInvalidField(formValues[field.name], field.defaultValue)) {
            updateFieldAsInvalid(dispatch, fieldRequired, field.name);
            focusElement(field.name, requeridos[field.name]);
            return false;
        }
    }

    return true;
};

const isInvalidField = (fieldValue: any, defaultValue: string) => !fieldValue || fieldValue === defaultValue || fieldValue == 0;

const updateFieldAsInvalid = (dispatch: Dispatch, fieldRequired: IsFieldModifiedFvDerivados, fieldName: string) => {
    dispatch(updateFieldRequiredDerivados({ ...fieldRequired, [fieldName]: true }));
};