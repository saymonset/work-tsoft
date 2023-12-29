import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ReferenceLine,
  ResponsiveContainer,
} from 'recharts';
import {HorarioBarra} from "../../../../model";
import React from "react";

interface SimpleBarChartProps {
  dataBody: HorarioBarra[];
}

export const SimpleBarChart: React.FC<SimpleBarChartProps> = ({ dataBody }) => {
  return (
    <>
      <ResponsiveContainer width="100%" aspect={2}>
        <BarChart
          
          data={dataBody}
          margin={{
            top: 10,
            right: 0,
            left: -25,
            bottom: 0,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name"/>
          <YAxis />
          <Tooltip />
          <Legend />
          <ReferenceLine y={0} stroke="#000" />
          <Bar dataKey="Gubernamental" barSize={28} fill="#22d3ee" />
          <Bar dataKey="Preliminar" barSize={30} fill="#a78bfa" />
          <Bar dataKey="Definitivo" barSize={30} fill="#3b82f6" />
          <Bar dataKey="Definitivo_Notas_PV" barSize={30} fill="#fdba74" />
          <Bar dataKey="Def_Notas_Estruct" barSize={30} fill="#f87171" />
        </BarChart>
      </ResponsiveContainer>
    </>
  )
}
