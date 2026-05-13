import { useEffect, useState } from "react";
import EmojiPickerPopup from "./EmojiPickerPopup.jsx"
import Input from "./input.jsx"
import { LoaderCircle } from "lucide-react";

const AddExpenseForm = ({ onAddExpense, categories }) => {
    const todayDate = new Date().toISOString().split('T')[0];
    const [expense, setExpense] = useState({
        name: '', amount: '', date: todayDate, icon: '', categoryId: ''
    })
    const [loading, setLoading] = useState(false);

    const categoryOptions = categories.map(cat => ({ value: cat.id, label: cat.name }));

    const handleChange = (key, value) => setExpense(
        { ...expense, [key]: value }
    );

    const handleSubmit = async () => {
        setLoading(true);
        try { await onAddExpense(expense); } finally { setLoading(false); }
    }

    useEffect(() => {
        if (categories.length > 0 && !expense.categoryId) {
            setExpense((prev) => ({ ...prev, categoryId: categories[0].id }))
        }
    }, [categories]);

    return (
        <div>
            <EmojiPickerPopup icon={expense.icon} onSelect={(selectedIcon) => handleChange('icon', selectedIcon)} />
            <Input value={expense.name} onChange={({ target }) => handleChange('name', target.value)} label="Expense Description" placeholder="e.g., Rent, Groceries" type="text" />
            <Input label="Category" value={expense.categoryId} onChange={({ target }) => handleChange('categoryId', target.value)} isSelect={true} options={categoryOptions} />
            <Input value={expense.amount} onChange={({ target }) => handleChange('amount', target.value)} label="Amount" placeholder="0.00" type="number" />
            <Input value={expense.date} onChange={({ target }) => handleChange('date', target.value)} label="Date" type="date" />

            <div className="flex justify-end mt-6">
                <button onClick={handleSubmit} disabled={loading} className="add-btn add-btn-fill !bg-red-600">
                    {loading ? <><LoaderCircle className="w-4 h-4 animate-spin" /> Adding...</> : "Add Expense"}
                </button>
            </div>
        </div>
    )
}

export default AddExpenseForm;