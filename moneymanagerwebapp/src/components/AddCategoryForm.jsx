import { useEffect, useState } from "react";
import Input from "./input.jsx"
import EmojiPickerPopup from "./EmojiPickerPopup.jsx";
import { LoaderCircle } from "lucide-react";
const AddCategoryForm = ({onAddCategory,initialCategoryData,isEditing}) => {
    const [category,setCategory] = useState({
        name: "",
        type: "income",
        icon: ""
    })
    const [loading,setLoading] = useState(false);
    useEffect(()=> {
        if(isEditing && initialCategoryData){
            setCategory(initialCategoryData);
        }else{
            setCategory({name:"",type:"income",icon:""})
        }
    },[isEditing,initialCategoryData])
    const categoryTypesOptions = [
        {value: "income",label:"Income"},
        {value: "expense",label:"Expense"}
    ]
    const handleChange = (key,value) => {
        setCategory({...category,[key]: value})
    }
    const handleSubmit = async() => {
        setLoading(true);
        try{
            await onAddCategory(category);
        }finally{
            setLoading(false);
        }
    }
    return (
        <div className="p-4">
            <EmojiPickerPopup icon={category.icon} onSelect={(selectedIcon)=> handleChange("icon",selectedIcon)}/>
            <Input value={category.name} onChange={({target})=> handleChange("name",target.value)} label="Category Name" placeholder="e.g., Freelance, Salary,Groceries" type="text" />
            <Input label="Category Type" value={category.type} onChange={({target}) => handleChange("type",target.value)} isSelect={true} options={categoryTypesOptions} />
            <div className="flex justify-end mt-6">
                <button type="button" onClick={handleSubmit} disabled={loading}
                className="bg-purple-600 text-white px-6 py-2 rounded-lg font-semibold hover:bg-purple-700 transition-colors shadow-sml">
                    {loading? (
                        <>
                            <LoaderCircle className="w-4 h-4 animate-spin"/>
                            {isEditing? "Updating...": "Adding..."}
                        </>
                    ): (
                        <>
                            {isEditing? "Update Category": "Add Category"}
                        </>
                    )}
                </button>
            </div>
        </div>
    )
}

export default AddCategoryForm;