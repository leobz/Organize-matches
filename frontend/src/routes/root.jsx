import {Outlet, useNavigation} from 'react-router-dom';
import {SnackbarProvider} from 'notistack';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useEffect, useState} from "react";
import Sidebar from './sidebar';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';
import MenuIcon from '@mui/icons-material/Menu';
import IconButton from '@mui/material/IconButton';
import { styled } from '@mui/material/styles';


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
        padding: '2rem 16rem',
    },
  }));

export default function Root() {
    const [userId, setUserId] = useState(localStorage.getItem('userId') || undefined);
    const [hideSidebar, setHideSidebar] = useState(true);
    const navigation = useNavigation();
    const theme = useTheme();
    const responsive = useMediaQuery(theme.breakpoints.down('sm'));
    
    const onClickMenu = () => {
        setHideSidebar(!hideSidebar);
    }

    const closeMenu = () => {
        setHideSidebar(true);
    }

    useEffect(() => {
        setUserId(localStorage.getItem('userId'));
    }, []);

    return (
        <>
            <ThemeProvider theme={createTheme()}>
                <SnackbarProvider maxSnack={1}>
                    <>
                        { (!responsive || !hideSidebar) && 
                            <Sidebar userId={userId} setUserId={setUserId} responsive={responsive} closeMenu={closeMenu}/>
                        }
                        <div>
                        { (responsive && hideSidebar) &&
                            <IconButton 
                                color="primary" aria-label="menu" component="label" onClick={onClickMenu}
                                style={{ 
                                    'paddingLeft': '2rem',
                                    'paddingTop': '1rem'
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
