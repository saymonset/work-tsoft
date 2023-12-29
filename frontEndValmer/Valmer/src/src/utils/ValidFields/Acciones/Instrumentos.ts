import { Dispatch } from "redux";
import { Acciones, fieldToValidateAccionesInst, IsFieldRequiredAccInst, RequeridosAcc } from "../../../model";
import { updateRequiredFieldAccInst } from "../../../redux";
import { focusElement } from "../../Utils";

export const validAccionesInstField = async (
    formValues: Acciones,
    dispatch: Dispatch,
    fieldRequired: IsFieldRequiredAccInst,
    requeridos: RequeridosAcc
) => {

    for (const field of fieldToValidateAccionesInst) {
        if (isInvalidField(formValues[field.name], field.defaultValue)) {
            updateFieldAsInvalid(dispatch, fieldRequired, field.name);
            focusElement(field.name, requeridos[field.name]);
            return false;
        }
    }

    return true;
};

const isInvalidField = (fieldValue: any, defaultValue: string) => !fieldValue || fieldValue === defaultValue || fieldValue == 0;

const updateFieldAsInvalid = (dispatch: Dispatch, fieldRequired: IsFieldRequiredAccInst, fieldName: string) => {
    dispatch(updateRequiredFieldAccInst({ ...fieldRequired, [fieldName]: true }));
};