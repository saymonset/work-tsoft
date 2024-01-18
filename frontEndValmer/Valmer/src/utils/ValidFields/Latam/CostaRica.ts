import { AccCalifLatam, IsFieldRequiredLatCr, RefReqAccCalifLatam, fieldToValidateLatamCr } from "../../../model";
import { focusElement } from "../../Utils";

export const validLatCrField = async (
    formValues: AccCalifLatam,
    dispatch: React.Dispatch<React.SetStateAction<IsFieldRequiredLatCr>>,
    fieldRequired: IsFieldRequiredLatCr,
    requeridos: RefReqAccCalifLatam
) => {
    if (formValues) {
        for (const field of fieldToValidateLatamCr) {
            if (isInvalidField(field.name, formValues[field.name], field.defaultValue)) {
                updateFieldAsInvalid(dispatch, fieldRequired, field.name);
                focusElement(field.name, requeridos[field.name]);
                return false;
            }
        }
    } else {
        dispatch({...fieldRequired, "s_emisor": true});
        focusElement("s_emisor", requeridos["s_emisor"]);
        return false;
    }

    return true;
};

const isInvalidField = (field: string, fieldValue: any, defaultValue: string) => {
    const fieldName = field === "n_crv_index" || field === "n_odd_first_coupon" || field === "n_odd_last_coupon"
    if (fieldName) {
        return !fieldValue || fieldValue === defaultValue || fieldValue == "0";
    } else {
        return !fieldValue || fieldValue === defaultValue;
    }
}

const updateFieldAsInvalid = (
    dispatch: React.Dispatch<React.SetStateAction<IsFieldRequiredLatCr>>, 
    fieldRequired: IsFieldRequiredLatCr, 
    fieldName: string
) => {
    dispatch({ ...fieldRequired, [fieldName]: true });
};