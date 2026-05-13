import React from "react";
import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

const CustomLineChart = ({ chartData = [] }) => {
  if (!chartData || chartData.length === 0) {
    return (
      <div className="flex h-[300px] w-full items-center justify-center text-gray-400">
        No data available for the chart
      </div>
    );
  }

  return (
    <div className="h-[300px] w-full mt-5">
      <ResponsiveContainer width="100%" height="100%">
        <AreaChart data={chartData}>
          <defs>
            {/* ✅ This defines the gradient color */}
            <linearGradient id="colorIncome" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="#22c55e" stopOpacity={0.3} />
              <stop offset="95%" stopColor="#22c55e" stopOpacity={0} />
            </linearGradient>
          </defs>
          
          <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f0f0f0" />
          <XAxis 
            dataKey="label" 
            axisLine={false} 
            tickLine={false} 
            tick={{ fill: '#94a3b8', fontSize: 12 }}
            dy={10}
          />
          <YAxis hide={true} domain={['auto', 'auto']} />
          <Tooltip 
            contentStyle={{ borderRadius: '8px', border: 'none', boxShadow: '0 4px 12px rgba(0,0,0,0.1)' }}
          />
          
          {/* ✅ The Area component creates the line + the gradient fill */}
          <Area
            type="monotone"
            dataKey="amount"
            stroke="#22c55e"       // Line Color
            strokeWidth={3}        // Line Thickness
            fillOpacity={1}
            fill="url(#colorIncome)" // References the gradient above
          />
        </AreaChart>
      </ResponsiveContainer>
    </div>
  );
};

export default CustomLineChart;

// 13:02:37