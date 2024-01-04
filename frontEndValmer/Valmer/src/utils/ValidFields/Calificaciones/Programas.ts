import { Dispatch } from "redux";
import { AccCalifLatam, IsFieldReqCalifProg, RefReqAccCalifLatam, fieldToValidateCalifProg } from "../../../model";
import { focusElement } from "../../Utils";
import { updateRequiredCalifProg } from "../../../redux/Calificaciones/Programas/actions";

export const validProgramasField = async (
    formValues: AccCalifLatam,
    dispatch: Dispatch,
    fieldRequired: IsFieldReqCalifProg,
    requeridos: RefReqAccCalifLatam
) => {

    for (const field of fieldToValidateCalifProg) {
        if (isInvalidField(formValues[field.name], field.defaultValue)) {
            updateFieldAsInvalid(dispatch, fieldRequired, field.name);
            focusElement(field.name, requeridos[field.name]);
            return false;
        }
    }

    return true;
};

const isInvalidField = (fieldValue: any, defaultValue: string) => !fieldValue || fieldValue === defaultValue || fieldValue == 0;

const updateFieldAsInvalid = (dispatch: Dispatch, fieldRequired: IsFieldReqCalifProg, fieldName: string) => {
    dispatch(updateRequiredCalifProg({ ...fieldRequired, [fieldName]: true }));
};