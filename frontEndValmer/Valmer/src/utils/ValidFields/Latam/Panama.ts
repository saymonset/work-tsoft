import { AccCalifLatam, IsFieldRequiredLatPanama, RefReqAccCalifLatam, fieldToValidateLatamPanama } from "../../../model";
import { focusElement } from "../../Utils";

export const validLatPamanaField = async (
    formValues: AccCalifLatam,
    dispatch: React.Dispatch<React.SetStateAction<IsFieldRequiredLatPanama>>,
    fieldRequired: IsFieldRequiredLatPanama,
    requeridos: RefReqAccCalifLatam
) => {
    console.log(formValues?.n_crv_index)
    if (formValues) {
        for (const field of fieldToValidateLatamPanama) {
            if (isInvalidField(field.name, formValues[field.name], field.defaultValue)) {
                updateFieldAsInvalid(dispatch, fieldRequired, field.name);
                focusElement(field.name, requeridos[field.name]);
                return false;
            }
        }
    } else {
        dispatch({...fieldRequired, "s_nemotecnico": true});
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
    dispatch: React.Dispatch<React.SetStateAction<IsFieldRequiredLatPanama>>, 
    fieldRequired: IsFieldRequiredLatPanama, 
    fieldName: string
) => {
    dispatch({ ...fieldRequired, [fieldName]: true });
};