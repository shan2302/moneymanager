import { Coins, Wallet, WalletCards } from "lucide-react";
import Dashboard from "../components/Dashboard";
import InfoCard from "../components/InfoCard";
import { useUser } from "../hooks/useUse";
import {addThousandSeparator} from "../Util/util.js";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axiosConfig from "../Util/axiosConfig.jsx"
import { API_ENDPOINTS } from "../Util/apiEndpoints.js";
import { toast } from "react-hot-toast";
import RecentTransactions from "../components/RecentTransactions.jsx";
import FinanceOverView from "../components/FinanceOverView.jsx";
import Transactions from "../components/Transactions.jsx";
const Home = () => {
    useUser();

    const navigate = useNavigate();
    const [dashboardData,setDashboardData] = useState(null);
    const [loading, setLoading] = useState(false);

    const fetchDashboardData = async () => {
        if(loading) return;

        setLoading(true);

        try {
            const response = await axiosConfig.get(API_ENDPOINTS.DASHBOARD_DATA)
            if(response.status === 200){
                console.log(response.data);
                
                setDashboardData(response.data);
            }
        } catch (error) {
            console.error("Something went wrong while fetching dashboard data:",error);
            toast.error("Something went wrong!");

        }finally{
            setLoading(false);
        }
    }

    useEffect(()=> {
        fetchDashboardData();
        return () => {};
    },[])
    return (
        <div>
            <Dashboard activeMenu = "Dashboard">
                <div className="my-5 mx-auto">
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                        {/* Display the cards */}
                        <InfoCard 
                            icon={<WalletCards/>}
                            label="Total Balance"
                            value={addThousandSeparator(dashboardData?.totalBalance || 0)}
                            color="bg-purple-800"
                        />
                        <InfoCard 
                            icon={<Wallet/>}
                            label="Total Income"
                            value={addThousandSeparator(dashboardData?.totalIncome || 0)}
                            color="bg-green-800"
                        />
                        <InfoCard 
                            icon={<Coins/>}
                            label="Total Expense"
                            value={addThousandSeparator(dashboardData?.totalExpense || 0)}
                            color="bg-red-800"
                        />
                    </div>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
                        {/* Recent Transactions */}
                        <RecentTransactions 
                            transactions={dashboardData?.RecentTransactions} 
                            onMore={()=> navigate("/Expense")}
                        />
                        {/* finance overview chart */}
                        <FinanceOverView
                            totalBalance={dashboardData?.totalBalance||0}
                            totalExpense={dashboardData?.totalExpense || 0}
                            totalIncome={dashboardData?.totalIncome || 0}
                        />

                        {/* Expense Transactions */}
                        <Transactions 
                            transactions={dashboardData?.Recent5Expenses || []}
                            onMore={()=> navigate("/expense")}
                            type="expense"
                            title="Recent Expenses"
                        />
                        {/* Income Transactions */}
                        <Transactions 
                            transactions={dashboardData?.Recent5Incomes || []}
                            onMore={()=> navigate("/income")}
                            type="income"
                            title="Recent Income"
                        />
                    </div>
                </div>
            </Dashboard>
        </div>
    )
}

export default Home;