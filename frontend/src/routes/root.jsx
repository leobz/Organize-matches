import {Outlet, NavLink, useNavigation} from 'react-router-dom';
import {SnackbarProvider} from 'notistack';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useEffect, useState} from "react";


const theme = createTheme();


export default function Root() {
    const [userId, setUserId] = useState('');
    useEffect(() => {
        const userId = localStorage.getItem('userId');
        if (userId) {
            setUserId(userId);
        }
    }, []);
    let onSession = userId;
    const navigation = useNavigation();


    return (
        <>
            <ThemeProvider theme={theme}>
                <SnackbarProvider maxSnack={1}>
                    <div id="sidebar">
                        <h1>Organize Matches</h1>
                        <nav>
                            <ul>
                                {! onSession &&
                                    <li key='login'>

                                        <NavLink to='login'> Sign In </NavLink>
                                        <NavLink to='register'> Sign Up </NavLink>

                                    </li>
                                }
                                {onSession &&
                                    <li key="sections">
                                        <NavLink to='home'> Home </NavLink>
                                        <NavLink to="matches">Matches</NavLink>
                                        <NavLink to='users'> Users </NavLink>
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
                        <Outlet/>
                    </div>
                </SnackbarProvider>
            </ThemeProvider>
        </>
    );
}
