import { Download, LoaderCircle, Mail } from "lucide-react";
import TransactionInfoCard from "./TransactionInfoCard";
import moment from "moment/moment";
import { useState } from "react";

const ExpenseList = ({ transactions, onDelete, onDownload, onEmail }) => {
    const [loading, setLoading] = useState(false);

    const handleAction = async (action) => {
        setLoading(true);
        try { await action(); } finally { setLoading(false); }
    }

    return (
        <div className="card">
            <div className="flex items-center justify-between">
                <h5 className="text-lg">Recent Expenses</h5>
                <div className="flex items-center justify-end gap-2">
                    <button disabled={loading} className="card-btn" onClick={() => handleAction(onEmail)}>
                        {loading ? <LoaderCircle className="w-4 h-4 animate-spin" /> : <><Mail size={15} className="text-red-500" /> Email</>}
                    </button>
                    <button disabled={loading} className="card-btn" onClick={() => handleAction(onDownload)}>
                        {loading ? <LoaderCircle className="w-4 h-4 animate-spin" /> : <><Download size={15} className="text-red-500" /> Download</>}
                    </button>
                </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {transactions?.map((expense) => (
                    <TransactionInfoCard
                        key={expense.id}
                        title={expense.name}
                        icon={expense.icon}
                        date={moment(expense.date).format("Do MMM YYYY")}
                        amount={expense.amount}
                        type="expense"
                        onDelete={() => onDelete(expense.id)}
                    />
                ))}
            </div>
        </div>
    )
}

export default ExpenseList;