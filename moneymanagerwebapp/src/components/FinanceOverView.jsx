import { addThousandSeparator } from "../Util/util";
import CustomPieChart from "./CustomPieChart";

const FinanceOverView = ({totalBalance,totalIncome,totalExpense}) => {

    const COLORS = ['#8884d8', '#166534', '#ffbb28', '#ff8042', '#0088FE', '#00C49F'];
    const balanceData = [
        {name: "Total Balance",amount: totalBalance},
        {name: "Total Expenses",amount: totalExpense},
        {name: "Total Income",amount: totalIncome},
    ]
    return (
        <div className="card">
            <div className="flex items-center justify-between">
                <h5 className="text-lg">Financial Overview</h5>
            </div>

            <CustomPieChart 
                data={balanceData}
                label="Total Balance"
                totalAmount={` ₹${addThousandSeparator(totalBalance)}`}
                colors={COLORS}
                showTextAnchor
                />
        </div>
    )
}

export default FinanceOverView;