import React, { useState } from 'react'
import { PieChart, Pie, Sector, ResponsiveContainer, Cell } from 'recharts';
import { ColorsGraph, generateUUID } from '../../../../../../utils';
import { AreaGraphics } from '../../../../../../model';


import  { PureComponent } from 'react';

const data = [
  { name: 'Group A', value: 400 },
  { name: 'Group B', value: 300 },
  { name: 'Group C', value: 300 },
  { name: 'Group D', value: 200 },
];

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const RADIAN = Math.PI / 180;
const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent, index }) => {
  const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
  const x = cx + radius * Math.cos(-midAngle * RADIAN);
  const y = cy + radius * Math.sin(-midAngle * RADIAN);

  return (
    <text x={x} y={y} fill="white" textAnchor={x > cx ? 'start' : 'end'} dominantBaseline="central">
      {`${(percent * 100).toFixed(0)}%`}
    </text>
  );
};
const renderActiveShape = (props: any) => {
  const RADIAN = Math.PI / 180;
  const { cx, cy, midAngle, innerRadius, outerRadius, startAngle, endAngle, fill, payload, percent, value } = props;
  const sin = Math.sin(-RADIAN * midAngle);
  const cos = Math.cos(-RADIAN * midAngle);
  const sx = cx + (outerRadius + 10) * cos;
  const sy = cy + (outerRadius + 10) * sin;
  const mx = cx + (outerRadius + 2) * cos;
  const my = cy + (outerRadius + 19) * sin;
  const ex = mx + (cos >= 0 ? 1 : -1) * 10;
  const ey = my;
  const textAnchor = cos >= 0 ? 'start' : 'end';

  function splitText(text: any, maxCharPerLine = 10) {
    const words = text.split(' ');
    const lines = [];
    let currentLine = '';
  
    words.forEach((word: any, index: any) => {
      if ((currentLine + ' ' + word).length <= maxCharPerLine) {
        currentLine += (index === 0 ? '' : ' ') + word;
      } else {
        lines.push(currentLine);
        currentLine = word;
      }
    });
  
    if (currentLine) {
      lines.push(currentLine);
    }
  
    return lines;
  }

  return (
    <g>
      <text x={cx} y="8em" dy={8} textAnchor="middle" fill={fill}>
      
        {payload.servicio && payload.servicio.length > 22 &&
            splitText(payload.servicio, 22).map((line, index) => (
            <tspan key={index} x={cx} dy="1.2em">{line}</tspan>
            ))
        }
        {payload.cliente && payload.cliente.length > 22 &&
            splitText(payload.cliente).map((line, index) => (
            <tspan key={index} x={cx} dy="1.2em">{line}</tspan>
            ))
        }
      </text>
      <Sector
        cx={cx}
        cy={cy}
        innerRadius={innerRadius}
        outerRadius={outerRadius}
        startAngle={startAngle}
        endAngle={endAngle}
        fill={fill}
      />
      <Sector
        cx={cx}
        cy={cy}
        startAngle={startAngle}
        endAngle={endAngle}
        innerRadius={outerRadius + 6}
        outerRadius={outerRadius + 10}
        fill={fill}
      />
      <path d={`M${sx},${sy}L${mx},${my}L${ex},${ey}`} stroke={fill} fill="none" />
      <circle cx={ex} cy={ey} r={2} fill={fill} stroke="none" />
      <text x={ex + (cos >= 0 ? 1 : -1) * 10} y={ey} textAnchor={textAnchor} fill="#333">{`Peticiones ${value}`}</text>
      <text x={ex + (cos >= 0 ? 1 : -1) * 10} y={ey} dy={18} textAnchor={textAnchor} fill="#999">
        {`(Rate ${(percent * 100).toFixed(0)}%)`}
      </text>
    </g>
  );
};

interface PieChartClientServProps{
    dataBody: AreaGraphics[]
}

export const PieChartClientServ: React.FC<PieChartClientServProps> = ({ dataBody }) => {

    const [activeIndex, setActiveIndex] = useState(0)

    const onPieEnter = (_:any, index:any) => {
        setActiveIndex(index);
    };

    const [height, setHeight] = useState(window.innerHeight/2);

    window.addEventListener('resize', () => {
      setHeight(window.innerHeight / 2)
    });

    const radiusInterno = (height / 5.5);
    const radiusExterno = radiusInterno + 14;

    return (
      
        <ResponsiveContainer width="100%" height={height} >
            <PieChart width={300} height={500}>
                <Pie
                    activeIndex={activeIndex}
                    activeShape={renderActiveShape}
                    data={dataBody}
                    cx="50%"
                    cy="50%"
                    innerRadius={radiusInterno}
                    outerRadius={radiusExterno}
                    fill="#8884d8"
                    dataKey="peticiones"
                    onMouseEnter={onPieEnter}
                  
                >
                    {dataBody.map((_entry, index) => (
                        <Cell key={generateUUID()} fill={ColorsGraph[index % ColorsGraph.length]} />
                    ))}
                    
                </Pie>
            </PieChart>
        </ResponsiveContainer>
    
    );
}