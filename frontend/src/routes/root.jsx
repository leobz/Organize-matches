import {Outlet, useNavigation, useNavigate, useLocation} from 'react-router-dom';
import {SnackbarProvider} from 'notistack';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useEffect, useState} from "react";
import Sidebar from './sidebar';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';
import MenuIcon from '@mui/icons-material/Menu';
import IconButton from '@mui/material/IconButton';
import { styled } from '@mui/material/styles';
import {logout} from "../services/login.js";


const Detail = styled('div')(({ theme }) => ({
    id: 'detail',
    [theme.breakpoints.down('500')]: {
        padding: '1rem 1rem',
    },
    [theme.breakpoints.up('500')]: {
        padding: '1rem 5rem',
    },
    [theme.breakpoints.up('sm')]: {
        padding: '2rem 2rem',
    },
    [theme.breakpoints.up('md')]: {
        padding: '2rem 7rem',
    },
    [theme.breakpoints.up('lg')]: {
        padding: '2rem 20rem',
    },
  }));

export default function Root() {
    const [userId, setUserId] = useState(localStorage.getItem('userId') || undefined);
    const [hideSidebar, setHideSidebar] = useState(true);
    const navigation = useNavigation();
    const theme = useTheme();
    const responsive = useMediaQuery(theme.breakpoints.down('sm'));
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

    const onClickMenu = () => {
        setHideSidebar(!hideSidebar);
    }

    const closeMenu = () => {
        setHideSidebar(true);
    }

    useEffect(() => {
        let userId = localStorage.getItem('userId');
        if(userId) {
            setUserId(localStorage.getItem('userId'));
        }
    }, []);

    useEffect(() => {
        if(localStorage.getItem("tokenExpirationDate") && new Date(localStorage.getItem("tokenExpirationDate")) <= new Date()) {
            disconnect();
        }
    }, [location.key])

    return (
        <>
            <ThemeProvider theme={createTheme()}>
                <SnackbarProvider maxSnack={1}>
                    <>
                        { (!responsive || !hideSidebar) &&
                            <Sidebar disconnect={disconnect} userId={userId} setUserId={setUserId} responsive={responsive} closeMenu={closeMenu}/>
                        }
                        <div>
                        { (responsive && hideSidebar) &&
                            <IconButton
                                color="primary" aria-label="menu" component="label" onClick={onClickMenu}
                                style={{
                                    'padding-left': '2rem',
                                    'padding-top': '1rem'
                                }}
                            >
                                <MenuIcon/>
                            </IconButton>
                        }
                        { (!responsive || hideSidebar) &&
                        <Detail
                            className={
                                navigation.state === "loading" ? "loading" : ""
                            }
                        >
                            <Outlet context={[userId, setUserId]}/>
                        </Detail>
                        }
                        </div>
                    </>
                </SnackbarProvider>
            </ThemeProvider>
        </>
    );
}
