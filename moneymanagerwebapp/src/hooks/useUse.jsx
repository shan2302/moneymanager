import { useContext, useEffect } from "react";
import { AppContext } from "../context/AppContext";
import { useNavigate } from "react-router-dom";
import { API_ENDPOINTS } from "../Util/apiEndpoints";
import {axiosConfig} from "../Util/axiosConfig"
export const useUser =() => {
    const {user,setUser,clearUser} = useContext(AppContext);
    const navigate = useNavigate();

    useEffect(()=>{
        if(user) {
            return;
        }
        let isMounted = true;
        
        const fetchUserInfo = async () => {
            try{
                const response = await axiosConfig.get(API_ENDPOINTS.GET_USER_INFO);
                if(isMounted && response.data){
                    setUser(response.data);
                }
            }catch(error)
            {
                   console.log("Failed to fetch the user info",error);
                   if(isMounted){
                    clearUser();
                    navigate("/login");
                   }
                    
            }
        };
        fetchUserInfo();
        return () => {
            isMounted = false;
        }
    },[user,setUser,clearUser,navigate])
};


// 9:38:46