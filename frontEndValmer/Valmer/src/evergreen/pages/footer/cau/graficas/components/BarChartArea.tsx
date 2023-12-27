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

interface BarChartAreaProps {
    data: AreaGraphics[]
    pantalla: string
}

export const BarChartArea: React.FC<BarChartAreaProps> = ({ data, pantalla }) => {

    const setPantalla = (): string[] => {
        if (pantalla === "area") {
            return [pantalla, "#ED335F"]
        } else if (pantalla === "cliente") {
            return [pantalla, "#4B0082"]
        } else {
            return [pantalla, "#9932CC"]
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