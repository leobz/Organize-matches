import {Outlet, NavLink, useNavigation} from 'react-router-dom';
import {SnackbarProvider} from 'notistack';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useEffect, useState} from "react";
import {useNavigate} from 'react-router-dom';
import { logout } from "../services/login";

const theme = createTheme();


export default function Root() {
    const [userId, setUserId] = useState(localStorage.getItem('userId') || undefined);
    useEffect(() => {
        setUserId(localStorage.getItem('userId'));
    }, []);
    const navigation = useNavigation();
    const navigate = useNavigate();

    const onClickLogout = (e) => {
        e.preventDefault()
        localStorage.clear()
        setUserId(undefined)
        logout().then((response) => {
            if (response.status >= 400){
                console.log("Error on logout")
            } else {
                navigate('/login');
            }
        })
    }

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
                                        <NavLink to="logout" onClick={(e) => {onClickLogout(e)}}> Log Out </NavLink>
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
