import { useState, useEffect } from 'react';
import {ResponseConsultaDataCorp} from "../../../../../../../../model";

type CalculateFunction = (inputValue: string, data: ResponseConsultaDataCorp['body']) => Record<string, string>;
interface InputConfig {
  calculate: (inputValue: string, data: ResponseConsultaDataCorp['body']) => Record<string, string>;
}
const isEmpty = (value: string) => value.trim() === '';

const parseFloatOrDefault = (value: string, defaultValue = 0): number => {
  const number = parseFloat(value);
  return isNaN(number) ? defaultValue : number;
}

const calculatePercentage = (numerator: number, denominator: number): string => {
  return denominator === 0 ? '0' : ((numerator / denominator) * 100).toFixed(2);
}

const calculateFunctions: Record<string, CalculateFunction> = {
  n_der_cobro: (inputValue, data) => {
    const nFactor = parseFloatOrDefault(data.n_factor);
    const efectivo = parseFloatOrDefault(data.n_efectivo);
    const activoNoCirc = parseFloatOrDefault(data.n_activo_no_circ);
    const inputValueAsNumber = parseFloatOrDefault(inputValue);

    const newValueDer35 = (nFactor * inputValueAsNumber).toString();
    const newValueAc = (nFactor * inputValueAsNumber + efectivo).toString();
    const newValueAcRec = (nFactor * inputValueAsNumber + efectivo + activoNoCirc).toString();
    const newValuePorcenVna = calculatePercentage(parseFloat(newValueAcRec), parseFloatOrDefault(data.n_monto_circulacion));
    const nPrecioNuevo = parseFloat(inputValue) !== 0 && parseFloat(newValuePorcenVna) !== 0
        ? (parseFloat(newValuePorcenVna) * parseFloatOrDefault(data.n_valor_nominal_act)).toString()
        : '0';

    return {
      n_der_35_porcent: newValueDer35,
      n_ac: newValueAc,
      n_ac_rec: newValueAcRec,
      n_porc_vna: newValuePorcenVna,
      n_precio_nuevo: nPrecioNuevo,
    };
  },
  n_factor: (inputValue, data) => {
    const nDerCobro = parseFloatOrDefault(data.n_der_cobro);
    const inputValueAsNumber = parseFloatOrDefault(inputValue);
    const efectivo = parseFloatOrDefault(data.n_efectivo);
    const activoNoCirc = parseFloatOrDefault(data.n_activo_no_circ);
    const garantias = parseFloatOrDefault(data.n_garantias);

    if (isEmpty(inputValue) || isEmpty(data.n_activo_no_circ) || isEmpty(data.n_garantias)) {
      return {
        n_der_35_porcent: '0',
        n_ac: '0',
        n_re_anc: '0',
        n_re_garantias: '0',
        n_ac_rec: '0',
        n_porc_vna: '0',
        n_precio_nuevo: '0',
      };
    } else {
      const newValueDer35F = (nDerCobro * inputValueAsNumber).toString();
      const newValueAcF = (nDerCobro * inputValueAsNumber + efectivo).toString();
      const newValue35ActivoNoCirc = (activoNoCirc * inputValueAsNumber).toString();
      const newValue35Garantias = (inputValueAsNumber * garantias).toString();
      const newValueAcRec = (inputValueAsNumber * nDerCobro + efectivo + activoNoCirc).toString();
      const newValuePorcenVna = calculatePercentage(parseFloatOrDefault(data.n_ac_rec), parseFloatOrDefault(data.n_monto_circulacion));
      const nPrecioNuevo = inputValueAsNumber !== 0 ? '0' : (parseFloat(newValuePorcenVna) * parseFloatOrDefault(data.n_valor_nominal_act)).toString();

      return {
        n_der_35_porcent: newValueDer35F,
        n_ac: newValueAcF,
        n_re_anc: newValue35ActivoNoCirc,
        n_re_garantias: newValue35Garantias,
        n_ac_rec: newValueAcRec,
        n_porc_vna: newValuePorcenVna,
        n_precio_nuevo: nPrecioNuevo,
      };
    }
  },
  n_efectivo: (inputValue, data) => {
    const factor = parseFloatOrDefault(data.n_factor);
    const derCobro = parseFloatOrDefault(data.n_der_cobro);
    const efectivo = parseFloatOrDefault(inputValue);

    const newValueAcE = (factor * derCobro + efectivo).toString();
    const newValueAcRec = (factor * derCobro + efectivo + parseFloatOrDefault(data.n_activo_no_circ)).toString();
    const newValuePorcenVna = calculatePercentage(parseFloat(newValueAcRec), parseFloatOrDefault(data.n_monto_circulacion));
    const nPrecioNuevo = parseFloat(newValuePorcenVna) * parseFloatOrDefault(data.n_valor_nominal_act);
    const nPrecioNuevoString = nPrecioNuevo.toString();

    return {
      n_ac: newValueAcE,
      n_ac_rec: newValueAcRec,
      n_porc_vna: newValuePorcenVna,
      n_precio_nuevo: nPrecioNuevoString,
    };
  },
  n_garantias: (inputValue, data) => {
    if (isEmpty(inputValue)) {
      return {
        n_re_garantias: '0',
      };
    } else {
      const nFactor = parseFloatOrDefault(data.n_factor);
      const inputValueNumber = parseFloatOrDefault(inputValue);
      const newValue35Garantias = (nFactor * inputValueNumber).toString();
      return {
        n_re_garantias: newValue35Garantias,
      };
    }
  },
  n_activo_no_circ: (inputValue, data) => {
    if (isEmpty(inputValue)) {
      return {
        n_re_anc: '0',
        n_ac_rec: '0'
      };
    } else {
      const inputValueNumber = parseFloatOrDefault(inputValue);
      const nFactor = parseFloatOrDefault(data.n_factor);
      const derCobro = parseFloatOrDefault(data.n_der_cobro);
      const efectivo = parseFloatOrDefault(data.n_efectivo);

      const newValue35ActivoNoCirc = (inputValueNumber * nFactor).toString();
      const newValueAcRec = (nFactor * derCobro + efectivo + inputValueNumber).toString();

      return {
        n_re_anc: newValue35ActivoNoCirc,
        n_ac_rec: newValueAcRec
      };
    }
  },
  n_ac_rec: (inputValue, data) => {
    if (isEmpty(inputValue)) {
      return {
        n_porc_vna: '0',
      };
    } else {
      const inputValueNumber = parseFloatOrDefault(inputValue);
      const montoCirculacion = parseFloatOrDefault(data.n_monto_circulacion);

      const newValuePorcenVna = calculatePercentage(inputValueNumber, montoCirculacion);

      return {
        n_porc_vna: newValuePorcenVna,
      };
    }
  }
};

export const useInputConfig = (): Record<string, InputConfig> => {
  const [inputConfigs, setInputConfigs] = useState<Record<string, InputConfig>>({});

  useEffect(() => {
    const configs = Object.keys(calculateFunctions).reduce<Record<string, InputConfig>>((acc, key) => {
      acc[key] = { calculate: calculateFunctions[key] };
      return acc;
    }, {});

    setInputConfigs(configs);
  }, []);

  return inputConfigs;
};

