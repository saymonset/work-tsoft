import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    ResponsiveContainer,
  } from 'recharts'
import React, { useEffect, useState } from "react"
import { HomeGraphics } from '../../../../../../model';
import { ColorsGraph } from '../../../../../../utils';
  
  interface SimpleBarChartProps {
    data: HomeGraphics[]
  }

  interface ChartDataItem {
    mes: string;
    [key: string]: number | string;
  }

  interface Ref {
    [key: number]: string
  }
  
export const SimpleBarChart: React.FC<SimpleBarChartProps> = ({ data }) => {

  const uniqueData: Record<string, ChartDataItem> = {};
  const meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio","Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"]

  data.forEach((item) => {
    const key = item.mes;
    const index = parseInt(item.mes)

    const isIquals = index - 1 == meses.indexOf(meses[index - 1])

    if (isIquals) {
      item.mes = meses[index - 1];
    }

    if (!uniqueData[key]) {
      uniqueData[key] = { mes: item.mes };
    }

    if (!uniqueData[key][item.area]) {
      uniqueData[key][item.area] = item.peticiones;
    } else if (typeof uniqueData[key][item.area] === 'number') {
      uniqueData[key][item.area] = (uniqueData[key][item.area] as number) + item.peticiones;
    }
  });

  const mesesOrdenados = Object.fromEntries(
    Object.entries(uniqueData).sort(([A], [B]) => meses.indexOf(A) - meses.indexOf(B))
  );

  const chartData = Object.values(mesesOrdenados);

  const [ref, setRef] = useState<Ref>({} as Ref)

  data.forEach((item, index) => {
    const value = item.area 
    const isValue = Object.values(ref).some((value) => value === item.area)

    if (!isValue) {
      const updated = {...ref, [index]: value}
      setRef(updated)
    }
  })



  const [height, setHeight] = useState(window.innerHeight / 1.6);

  useEffect(() => {
    const h = window.innerHeight

    if (h > 920) {
      setHeight(h / 1.6)
    }
  }, [])

    window.addEventListener('resize', () => {
      const h = window.innerHeight
      if (h > 920) {
        setHeight(h / 1.6)
        return
      }
      setHeight(window.innerHeight / 2)
    });

  return (
    <ResponsiveContainer width="100%" height={height}>
      <BarChart data={chartData} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="mes" />
        <YAxis />
        <Tooltip />
        <Legend />
        {Object.values(ref).map((item, index) => {
            console.log('----------------------item-------------inicio');
            console.log({item});
            console.log('----------------------ref-------------fin');
            return <Bar key={item} dataKey={item} fill={ColorsGraph[index]} />;
        })}
      </BarChart>
    </ResponsiveContainer>
  );
}
  