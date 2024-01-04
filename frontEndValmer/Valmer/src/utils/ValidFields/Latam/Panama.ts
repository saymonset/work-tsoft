import { AccCalifLatam, IsFieldRequiredLatPanama, RefReqAccCalifLatam, fieldToValidateLatamPanama } from "../../../model";
import { focusElement } from "../../Utils";

export const validLatPamanaField = async (
    formValues: AccCalifLatam,
    dispatch: React.Dispatch<React.SetStateAction<IsFieldRequiredLatPanama>>,
    fieldRequired: IsFieldRequiredLatPanama,
    requeridos: RefReqAccCalifLatam
) => {
    console.log(formValues)
    if (formValues) {
        for (const field of fieldToValidateLatamPanama) {
            if (isInvalidField(formValues[field.name], field.defaultValue)) {
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

const isInvalidField = (fieldValue: any, defaultValue: string) => !fieldValue || fieldValue === defaultValue || fieldValue == 0;

const updateFieldAsInvalid = (
    dispatch: React.Dispatch<React.SetStateAction<IsFieldRequiredLatPanama>>, 
    fieldRequired: IsFieldRequiredLatPanama, 
    fieldName: string
) => {
    dispatch({ ...fieldRequired, [fieldName]: true });
};