import React, { useEffect, useRef } from 'react'
import { PieChart, Pie, Sector, Cell } from 'recharts'; 

export default function MyChart() {

    const onPieClick = () => {

    }

    const data = [
        {name: "Alice", value: 100},
        {name: "Tom", value: 150},
        {name: "Kathy", value: 50},
    ];

    const COLORS = ["#c7c91c", "#7feb75", "#68b9d9"];
    return (
            <PieChart width={600} height={600} className="chart">
            <Pie
                dataKey="value"
                isAnimationActive={false}
                data={data}
                innerRadius={100}
                outerRadius={160}
                fill="#8884d8"
                onClick = {onPieClick}
                label
            >
                {
          	data.map((entry, index) => <Cell style={{outline: 'none'}} fill={COLORS[index % COLORS.length]}/>)
          }
            </Pie>
        </PieChart>
        
    );
}