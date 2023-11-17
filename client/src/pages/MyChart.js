import React, { useEffect, useRef } from 'react'
import { PieChart, Pie, Sector, Cell } from 'recharts'; 

export default function MyChart(chartData) {

    const onPieClick = () => {

    }



    const data = [
        {name: "Alice", value: 100},
        {name: "Tom", value: 150},
        {name: "Kathy", value: 50},
    ];

    console.log(chartData.chartData)

    const COLORS = ["#c7c91c", "#7feb75", "#68b9d9", "#f6287f", "eeeeee"];
    return (
            <PieChart width={400} height={400} className="chart">
            <Pie
                dataKey="value"
                isAnimationActive={false}
                data={chartData.chartData}
                innerRadius={100}
                outerRadius={160}
                fill="#8884d8"
                onClick = {onPieClick}
                label
            >
                {
          	chartData.chartData.map((entry, index) => <Cell style={{outline: 'none'}} fill={COLORS[index % COLORS.length]}/>)
          }
            </Pie>
        </PieChart>
        
    );
}