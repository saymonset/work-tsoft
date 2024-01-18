import { useState } from "react";
import { Cell, Pie, PieChart, Sector } from "recharts";
import { generateUUID, ColorsGraph } from "../../../../../../utils";
import { InstGraphics } from "../../../../../../model";

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
        {payload.empresa}
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
      <text x={ex + (cos >= 0 ? 1 : -1) * 10} y={ey} textAnchor={textAnchor} fill="#333">{`Solicitudes ${value}`}</text>
      <text x={ex + (cos >= 0 ? 1 : -1) * 10} y={ey} dy={18} textAnchor={textAnchor} fill="#999">
        {`(${(percent * 100).toFixed(0)} %)`}
      </text>
    </g>
  );
};

interface SimplePieChartProps{
    dataBody: InstGraphics[]
}

export const SimplePieChart: React.FC<SimplePieChartProps> = ({ dataBody }) => {

    const [activeIndex, setActiveIndex] = useState<number>(0)

    const onPieEnter = (_:any, index:any) => {
        setActiveIndex(index);
    };

    const [height, setHeight] = useState(window.innerHeight/2);

    window.addEventListener('resize', () => {
      setHeight(window.innerHeight / 2)
    });

    const radiusInterno = (height / 2.5);
    const radiusExterno = radiusInterno + 35;

    return (
      <div className="flex justify-between">
        <div className="flex justify-center items-center">
          <table className="table text-xs">
            <thead className="thead">
                <tr>
                    <th className="px-3"></th>
                    <th>Institución</th>
                    <th className="px-1">Solicitudes -  %</th>
                </tr>
            </thead>
            <tbody className="tbody">
              {dataBody.map((item, key) => (
                <tr key={key}>
                  <td style={{background: ColorsGraph[key]}}></td>
                  <td>{ item.empresa }</td>
                  <td>{ item.peticiones }</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div>
          <PieChart width={1000} height={height * 1.5}>
            <Pie
              activeIndex={activeIndex}
              activeShape={renderActiveShape}
              data={dataBody}
              cx="40%"
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
        </div>
      </div>
    )
}