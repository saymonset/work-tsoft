import {fieldsToValidateCorp, InstrumentosType, IsFieldModifiedFvDdGobIns, RequeridosCorp} from "../../../model";
import {Dispatch} from "redux";
import {
    updateFieldRequiredCorp,
    updateIsSelectPanelCorp,
} from "../../../redux";
import {focusElement} from "../../Utils";

export const validCorpField = async (
    formValues: InstrumentosType,
    dispatch: Dispatch,
    fieldRequiredCorp: IsFieldModifiedFvDdGobIns,
    requeridos: RequeridosCorp
) => {

    const updatedFieldRequiredCorp = initializeFieldRequiredCorp(fieldRequiredCorp);
    let isValid = true;
    const isReferenciaMercado54 = formValues.n_referencia_mercado === '54';
    let shouldDispatch = false;

    for (const field of fieldsToValidateCorp) {
        const fieldValue = formValues[field.name] || "";
        const fieldName = field.name;

        if (fieldName === 'n_frecuencia_cupon') {
            shouldDispatch = true;
        }

        if (shouldSkipFieldValidation(fieldName, formValues)) {
            continue;
        }

        const fieldValidationResult = handleFieldValidation(fieldName,
            formValues, updatedFieldRequiredCorp, requeridos, isReferenciaMercado54);
        if (fieldValidationResult !== null) {
            isValid = fieldValidationResult;
        } else if (isFieldEmptyOrDefault(fieldValue, field.defaultValue)) {
            if (shouldDispatch) {
                dispatch(updateIsSelectPanelCorp(true));
                setTimeout(() => dispatch(updateIsSelectPanelCorp(false)), 30);
            }
            updatedFieldRequiredCorp[field.name] = true;
            focusElement(fieldName, requeridos[fieldName]);
            isValid = false;
            break;
        }

        if (!isValid) {
            break;
        }
    }

    dispatch(updateFieldRequiredCorp(updatedFieldRequiredCorp));
    return isValid;
};

const handleFieldValidation = (fieldName: string,
                               formValues: InstrumentosType,
                               updatedFieldRequiredCorp: IsFieldModifiedFvDdGobIns,
                               requeridos: RequeridosCorp,
                               isReferenciaMercado54: boolean) => {
    switch (fieldName) {
        case 's_inscrito_bolsa':
            return validateInscritoBolsa(formValues, updatedFieldRequiredCorp, requeridos);
        case 'n_tasa_referencia':
            return validateTasaReferencia(formValues, updatedFieldRequiredCorp, requeridos);
        case 's_tv_ref_1':
        case 's_emisora_ref_1':
        case 's_serie_ref_1':
        case 's_tv_ref_2':
        case 's_emisora_ref_2':
        case 's_serie_ref_2':
            if (isReferenciaMercado54) {
                return validateReferenciaMercado(formValues, updatedFieldRequiredCorp, requeridos);
            }
            return true;
        default:
            return null;
    }
};


const initializeFieldRequiredCorp = (obj: IsFieldModifiedFvDdGobIns): IsFieldModifiedFvDdGobIns => {
    const result: IsFieldModifiedFvDdGobIns = {} as IsFieldModifiedFvDdGobIns;
    for (const key in obj) {
        if (Object.hasOwn(obj, key)) {
            result[key] = false;
        }
    }
    return result;
}

const isFieldEmptyOrDefault = (fieldValue: any, defaultValue: string) =>
    fieldValue === undefined || fieldValue === "" || fieldValue === defaultValue || fieldValue === "default";

const validateInscritoBolsa = (formValues: InstrumentosType,
                               updatedFieldRequiredCorp: IsFieldModifiedFvDdGobIns,
                               requeridos: RequeridosCorp) => {
    if (formValues.b_oferta_publica === '1' && isFieldEmptyOrDefault(formValues.s_inscrito_bolsa, 'default')) {
        updatedFieldRequiredCorp.s_inscrito_bolsa = true;
        focusElement('s_inscrito_bolsa', requeridos.s_inscrito_bolsa);
        return false;
    }
    return true;
};

const validateTasaReferencia = (formValues: InstrumentosType,
                                updatedFieldRequiredCorp: IsFieldModifiedFvDdGobIns,
                                requeridos: RequeridosCorp) => {
    if (formValues.n_tipo_instrumento !== '4' && isFieldEmptyOrDefault(formValues.n_tasa_referencia, 'default')) {
        updatedFieldRequiredCorp.n_tasa_referencia = true;
        focusElement('n_tasa_referencia', requeridos.n_tasa_referencia);
        return false;
    }
    return true;
};

const validateReferenciaMercado = (
    formValues: InstrumentosType,
    updatedFieldRequiredCorp: IsFieldModifiedFvDdGobIns,
    requeridos: RequeridosCorp
) => {

    const fieldsToCheck = [
        's_tv_ref_1', 's_emisora_ref_1', 's_serie_ref_1',
        's_tv_ref_2', 's_emisora_ref_2', 's_serie_ref_2'
    ];

    for (const fieldName of fieldsToCheck) {
        if (isFieldEmptyOrDefault(formValues[fieldName], 'default')) {
            updatedFieldRequiredCorp[fieldName] = true;
            focusElement(fieldName, requeridos[fieldName]);
            return false;
        }
    }

    return true;
};

const shouldSkipFieldValidation = (fieldName: string, formValues: InstrumentosType) => {
    return (fieldName === 's_inscrito_bolsa' && formValues.b_oferta_publica === '0') ||
        (fieldName === 'n_tasa_referencia' && formValues.n_tipo_instrumento === '4');
};
