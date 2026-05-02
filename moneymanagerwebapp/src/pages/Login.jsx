import { useState } from "react";
import { Link,useNavigate } from "react-router-dom";
import { assests } from "../assets/assets";
import Input from "../components/input";

const Login = () => {
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");
    const [error,setError] = useState("");

    const navigate =  useNavigate();

    return (
        <div className="h-screen w-full  relative flex items-center justify-center overflow-hidden">
            {/* Background image  */}
            <img src={assests.login_bg} alt="Baclground" className="absolute inset-0 w-full h-full object-cover blur-sm" />
            <div className="relative z-10 w-full max-w-md px-6">
                <div className="bg-white bg-opacity-95 backdrop-blur-sm rounded-lg shadow-2xl p-6 max-h-[90vh] overflow-y-auto">
                    <h3 className="text-xl font-extrabold text-black text-center mb-2">
                        Welcome Back
                    </h3>
                    <p className="text-sm text-slate-700 font-semibold text-center mb-6">
                       Please enter your details to login in
                    </p>

                    <form className="space-y-3">
                            
                        <Input value={email} onChange={(e)=> setEmail(e.target.value)} label="Email Address" placeholder="name@example.com" type="text"/>

                       <div className="col-span-2">
                        
                            <Input value={password} onChange={(e)=> setPassword(e.target.value)} label="Password" placeholder="*******" type="password"/>
                        

                        </div>
                        {error && (
                            <p className="text-red-800 text-sm text-center bg-red-50 p-2 rounded">
                               {error} 
                            </p>
                        )} 
                        <button className="btn-primary w-full py-3 text-lg font-extrabold" type="submit">
                            LOGIN IN
                        </button>
                        <p className="text-sm text-slate-800 text-center mt-6">
                            Don't have an account?{" "}
                            <Link to="/signup" className=" font-bold text-primary underline hover:text-primary-dark transition-colors">
                            Sign up</Link>
                        </p>
                    </form>
                </div>

            </div>
        </div>
    )
}

export default Login;