import {Outlet, NavLink, useNavigation, useLocation} from 'react-router-dom';
import {SnackbarProvider} from 'notistack';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useEffect, useState} from "react";
import {useNavigate} from 'react-router-dom';
import { logout } from "../services/login";

const theme = createTheme();


export default function Root() {
    const [userId, setUserId] = useState(localStorage.getItem('userId') || null);
    const navigation = useNavigation();
    const navigate = useNavigate();

    const location = useLocation();

    const disconnect = () => {
        logout().then((response) => {
            if (response.status >= 400){
                console.log("Error on logout")
            } else {
                setUserId(null);
                localStorage.clear();
                navigate('/login');
            }
        })
    };

    useEffect(() => {
        if(localStorage.getItem("tokenExpirationDate") && new Date(localStorage.getItem("tokenExpirationDate")) <= new Date()) {
            disconnect();
        }
    }, [location.key])

    return (
        <>
            <ThemeProvider theme={theme}>
                <SnackbarProvider maxSnack={1}>
                    <div id="sidebar">
                        <h1>Organize Matches</h1>
                        <nav>
                            <ul>
                                {!userId &&
                                    <li key='login'>
                                        <NavLink to='login'> Sign In </NavLink>
                                        <NavLink to='register'> Sign Up </NavLink>
                                    </li>
                                }
                                {userId &&
                                    <li key="sections">
                                        <NavLink to='home'> Home </NavLink>
                                        <NavLink to="matches"> Matches </NavLink>
                                        <NavLink to="create-match"> Create Match </NavLink>
                                        <NavLink to="logout" onClick={(e) => { e.preventDefault(); disconnect(e); }}> Log Out </NavLink>
                                    </li>
                                }
                            </ul>
                        </nav>
                    </div>
                    <div
                        id="detail"
                        className={
                            navigation.state === "loading" ? "loading" : ""
                        }
                    >
                        <Outlet context={[userId, setUserId]}/>
                    </div>
                </SnackbarProvider>
            </ThemeProvider>
        </>
    );
}
