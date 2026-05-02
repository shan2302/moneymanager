import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { assests } from "../assets/assets";
import Input from "../components/input";
import { validateEmail } from "../Util/validation";
import axiosConfig from "../Util/axiosConfig";
import { API_ENDPOINTS } from "../Util/apiEndpoints";
import toast from "react-hot-toast";
import { LoaderCircle } from "lucide-react";

const Signup = () => {
    const [fullName,setFullName] = useState("");
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");
    const [error,setError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const navigate =  useNavigate();
    const handleSubmit = async (e) => {
    e.preventDefault(); // This MUST be the first line to stop the redirect
    setIsLoading(true);
    setError("");

    try {
        // Validation check
        if (!validateEmail(email)) {
            setError("Please enter a valid email");
            setIsLoading(false);
            return;
        }

        const response = await axiosConfig.post(API_ENDPOINTS.REGISTER, {
            fullName,
            email,
            password
        });
        
        // Handle success...
    } catch (err) {
        // This keeps the error on the page instead of redirecting
        setError(err.response?.data?.message || "Registration failed");
    } finally {
        setIsLoading(false);
    }
};
    // const handleSubmit = async (e) => {
    //     e.preventDefault();
    //     setIsLoading(true);
    //     setError("");
        
    //     // basic validation
    //     if(!fullName.trim())
    //     {
    //         setError("Please enter your fullName");
    //         setIsLoading(false);
    //         return;
    //     }
    //     if(!validateEmail(email))
    //     {
    //         setError("Please enter valid email address");
    //         setIsLoading(false);
    //         return;
    //     }
    //      if(!password.trim())
    //     {
    //         setError("Please enter your password");
    //         setIsLoading(false);
    //         return;
    //     }
    //     setError("");
    //     // signup api call
    //     try{
    //         const response =  await axiosConfig.post(API_ENDPOINTS.REGISTER,{fullName,email,password})
    //         if(response.status === 201){
    //             toast.success("Profile created successfully.");
    //             navigate("/login")
    //         }

    //     }catch(err){
    //         console.error("Someething went wrong",err);
    //         setError(err.response?.data?.message||err.message || "An error occurred");
    //     } finally {
    //         setIsLoading(false);
    //     }
    // }

    return(
        <div className="h-screen w-full  relative flex items-center justify-center overflow-hidden">
            {/* Background image  */}
            <img src={assests.login_bg} alt="Baclground" className="absolute inset-0 w-full h-full object-cover blur-sm" />
            <div className="relative z-10 w-full max-w-md px-6">
                <div className="bg-white bg-opacity-95 backdrop-blur-sm rounded-lg shadow-2xl p-6 max-h-[90vh] overflow-y-auto">
                    <h3 className="text-xl font-extrabold text-black text-center mb-2">
                        Create An Account
                    </h3>
                    <p className="text-sm text-slate-700 font-semibold text-center mb-6">
                        Start tracking your spendings by joining with us
                    </p>

                    <form onSubmit={handleSubmit} className="space-y-3">

                        <div className="flex justify-center mb-6">
                        {/* Profile image */}

                        </div>
                        <div className="grid grid-cols-2 md:grid-cols-2 gap-4">

                        <Input value={fullName} onChange={(e)=> setFullName(e.target.value)} label="Full Name" placeholder="John Doe" type="text"/>


                        <Input value={email} onChange={(e)=> setEmail(e.target.value)} label="Email Address" placeholder="name@example.com" type="text"/>

                       <div className="col-span-2">
                        
                            <Input value={password} onChange={(e)=> setPassword(e.target.value)} label="Password" placeholder="*******" type="password"/>
                        </div>

                        </div>
                        {error && (
                            <p className="text-red-800 text-sm text-center bg-red-50 p-2 rounded">
                               {error} 
                            </p>
                        )} 
                        <button disabled={isLoading} className={`btn-primary w-full py-3 text-lg font-extrabold flex items-center justify-center gap-2 ${isLoading?'opacity-50 cursor-not-allowed': ''}`}type="submit">
                            {isLoading ? (
                                <>
                                    <LoaderCircle className="animate-spin w-5 h-5"/>
                                    Signing up...
                                </>
                            ): (
                                "SIGN UP"
                            )}
                        </button>
                        <p className="text-sm text-slate-800 text-center mt-6">
                            Already have an Account?{" "}
                            <Link to="/login" className=" font-bold text-primary underline hover:text-primary-dark transition-colors">
                            Login</Link>
                        </p>
                    </form>
                </div>

            </div>
        </div>
    )

}

export default Signup;

// 7:29:01