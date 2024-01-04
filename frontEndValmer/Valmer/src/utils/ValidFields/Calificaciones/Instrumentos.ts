import { Dispatch } from "redux";
import { 
    AccCalifLatam, 
    IsFieldReqCalifInst, 
    RefReqAccCalifLatam, 
    fieldToValidateCalifInst } from "../../../model";
import { focusElement } from "../../Utils";
import { updateRequiredCalifInst } from "../../../redux";

export const validCalifInstField = async (
    formValues: AccCalifLatam,
    dispatch: Dispatch,
    fieldRequired: IsFieldReqCalifInst,
    requeridos: RefReqAccCalifLatam
) => {

    for (const field of fieldToValidateCalifInst) {
        if (isInvalidField(formValues[field.name], field.defaultValue)) {
            updateFieldAsInvalid(dispatch, fieldRequired, field.name);
            focusElement(field.name, requeridos[field.name]);
            return false;
        }
    }

    return true;
};

const isInvalidField = (fieldValue: any, defaultValue: string) => !fieldValue || fieldValue === defaultValue || fieldValue == 0;

const updateFieldAsInvalid = (dispatch: Dispatch, fieldRequired: IsFieldReqCalifInst, fieldName: string) => {
    dispatch(updateRequiredCalifInst({ ...fieldRequired, [fieldName]: true }));
};