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
  } from 'recharts'
import React from "react"
import { HomeGraphics } from '../../../../../../model';
  
  interface SimpleBarChartProps {
    dataBody: HomeGraphics[]
  }
  
  export const SimpleBarChart: React.FC<SimpleBarChartProps> = ({ dataBody }) => {
    return (
    <ResponsiveContainer width="100%" aspect={3}>
        <BarChart
        
        data={dataBody}
        margin={{
            top: 20,
            right: 20,
            left: 0,
            bottom: 10,
        }}
        >
        <CartesianGrid strokeDasharray="3 2" />
        <XAxis dataKey="area"/>
        <YAxis />
        <Tooltip />
        <Legend />
        <ReferenceLine y={0} stroke="#000" />
        
        <Bar dataKey="peticiones" barSize={28} fill="#22d3ee" />
        
        
        </BarChart>
    </ResponsiveContainer>
    )
  }
  