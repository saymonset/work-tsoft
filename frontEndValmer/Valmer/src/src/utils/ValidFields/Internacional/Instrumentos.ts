import {fieldsToValidateIntern, InstrumentosType, IsFieldModifiedFvInterIns} from "../../../model";
import {Dispatch} from "redux";
import {updateFieldRequiredIntern} from "../../../redux";

export const validInternacionalField = async (formValues: InstrumentosType,
                                       dispatch: Dispatch,
                                       fieldRequired: IsFieldModifiedFvInterIns) => {
    let isValid = true;

    fieldsToValidateIntern.forEach(field => {
        const fieldValue = formValues[field.name] || "";
        if (fieldValue === "" || fieldValue === field.defaultValue) {
            dispatch(updateFieldRequiredIntern({...fieldRequired, [field.name]: true}));
            isValid = false;
        }
    });

    return isValid;
}