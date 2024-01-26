import React from 'react'
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

import { AreaGraphics } from "../../../../../../model"
import { ColorsGraph } from '../../../../../../utils';

interface BarChartAreaProps {
    data: AreaGraphics[]
    pantalla: string
}

export const BarChartArea: React.FC<BarChartAreaProps> = ({ data, pantalla }) => {

    const setPantalla = (): string[] => {
        if (pantalla === "area") {
            return [pantalla, ColorsGraph[0]]
        } else if (pantalla === "cliente") {
            return [pantalla,ColorsGraph[1]]
        } else {
            return [pantalla, ColorsGraph[2]]
        }
    }

    return (
        <ResponsiveContainer width="100%" aspect={3}>
            <BarChart
            
            data={data}
            margin={{
                top: 20,
                right: 20,
                left: 0,
                bottom: 10,
            }}
            >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey={setPantalla()[0]}/>
            <YAxis/>
            <Tooltip />
            <Legend />
            <ReferenceLine y={0} stroke="#000" />
            
            <Bar dataKey="peticiones" barSize={28} fill={setPantalla()[1]} />
            
            </BarChart>
        </ResponsiveContainer>
        )
}