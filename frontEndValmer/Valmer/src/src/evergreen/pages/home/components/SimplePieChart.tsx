import React, { useState } from 'react'
import { PieChart, Pie, Sector, ResponsiveContainer, Cell } from 'recharts';
import {generateUUID} from "../../../../utils";
import {GraphCau} from "../../../../model";

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const renderActiveShape = (props: any) => {
  const RADIAN = Math.PI / 180;
  const { cx, cy, midAngle, innerRadius, outerRadius, startAngle, endAngle, fill, payload, percent, value } = props;
  const sin = Math.sin(-RADIAN * midAngle);
  const cos = Math.cos(-RADIAN * midAngle);
  const sx = cx + (outerRadius + 10) * cos;
  const sy = cy + (outerRadius + 10) * sin;
  const mx = cx + (outerRadius + 30) * cos;
  const my = cy + (outerRadius + 30) * sin;
  const ex = mx + (cos >= 0 ? 1 : -1) * 10;
  const ey = my;
  const textAnchor = cos >= 0 ? 'start' : 'end';

  return (
    <g>
      <text x={cx} y={cy} dy={8} textAnchor="middle" fill={fill}>
        {payload.name}
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
      <text x={ex + (cos >= 0 ? 1 : -1) * 10} y={ey} textAnchor={textAnchor} fill="#333">{`NUM ${value}`}</text>
      <text x={ex + (cos >= 0 ? 1 : -1) * 10} y={ey} dy={18} textAnchor={textAnchor} fill="#999">
        {`(Rate ${(percent * 100).toFixed(2)}%)`}
      </text>
    </g>
  );
};

interface SimplePieChartProps{
    dataBody: GraphCau[]
}

export const SimplePieChart: React.FC<SimplePieChartProps> = ({ dataBody }) => {

    const [activeIndex, setState] = useState(0)

    const onPieEnter = (_:any, index:any) => {
        setState(index);
    };

    const [height, setHeight] = useState(window.innerHeight/2);

    window.addEventListener('resize', () => {
      setHeight(window.innerHeight / 2)
    });

    const radiusInterno = (height / 3.9);
    const radiusExterno = radiusInterno + 14;

    return (
      <ResponsiveContainer width="100%" height={height}>
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
            dataKey="value"
            onMouseEnter={onPieEnter}
          >
            {dataBody.map((entry, index) => (
              <Cell key={generateUUID()} fill={COLORS[index % COLORS.length]} />
            ))}
          </Pie>
        </PieChart>
      </ResponsiveContainer>
    );
}