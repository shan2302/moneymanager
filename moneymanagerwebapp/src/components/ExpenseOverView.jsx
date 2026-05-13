import { useEffect, useState } from "react";
import { prepareIncomeLineChartData } from "../Util/util.js"; // You can use the same logic if it groups by date
import CustomLineChart from "../Util/CustomLineChart.jsx";
import { Plus } from "lucide-react";

const ExpenseOverView = ({ transactions, onAddExpense }) => {
    const [chartData, setChartData] = useState([]);

    useEffect(() => {
        const result = prepareIncomeLineChartData(transactions);
        setChartData(result);
    }, [transactions]);

    return (
        <div className="card">
            <div className="flex items-center justify-between">
                <div>
                    <h5 className="text-lg font-bold">Expense Overview</h5>
                    <p className="text-xs text-gray-400 mt-0.5">Visualize your spending patterns and trends</p>
                </div>
                <button className="flex items-center gap-1.5 px-4 py-2 bg-[#ffebee] text-[#b71c1c] rounded-lg font-semibold hover:bg-[#ffcdd2] transition-colors cursor-pointer border-none"
                    onClick={onAddExpense}>
                    <Plus size={15} strokeWidth={3} /> Add Expense
                </button>
            </div>
            <div className="mt-10 font-bold text-xl text-black-900">
                <CustomLineChart chartData={chartData} />
            </div>
        </div>
    )
}

export default ExpenseOverView;