import { useEffect,useState } from "react";
import {prepareIncomeLineChartData} from "../Util/util.js"
import CustomLineChart from "../Util/CustomLineChart.jsx"
import { Plus } from "lucide-react";
const IncomeOverView = ({transactions,onAddIncome}) => {
    const [chartData, setChartData] = useState([]);
    useEffect(()=> {
        const result = prepareIncomeLineChartData(transactions);
        console.log(result);
        setChartData(result);
        return () => {};
    },[transactions]);
    return(
        <div className="card">
            <div className="flex items-center justify-between">
                <div>
                    <h5 className="text-lg font-bold">
                        Income Overview
                    </h5>
                    <p className="text-xs text-gray-400 mt-0.5">
                        Track your earnings over time and analyze your income trends
                    </p>
                </div>
                <button className="flex items-center gap-1.5 px-4 py-2 bg-[#e8f5e9] text-[#1b5e20] rounded-lg font-semibold hover:bg-[#c8e6c9] transition-colors cursor-pointer  border-none "  
                    onClick={onAddIncome}>
                                <Plus size={15}  strokeWidth={3} className="text-lg"/> Add Income
                            </button>
            </div>
            <div className="mt-10 font-bold text-xl  text-black-900">
                    {/* create line chart */}
                    <CustomLineChart chartData={chartData}/>
                    line chart
                </div>
        </div>
    )
}

export default IncomeOverView;

// 13:01:07