import {Outlet, NavLink, useNavigation} from 'react-router-dom';
import {SnackbarProvider} from 'notistack';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useEffect, useState} from "react";


const theme = createTheme();


export default function Root() {
    const [userId, setUserId] = useState(localStorage.getItem('userId') || undefined);
    useEffect(() => {
        setUserId(localStorage.getItem('userId'));
    }, []);
    const navigation = useNavigation();

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
