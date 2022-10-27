import {Outlet, useNavigation} from 'react-router-dom';
import {SnackbarProvider} from 'notistack';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useEffect, useState} from "react";
import Sidebar from './sidebar';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';
import MenuIcon from '@mui/icons-material/Menu';
import IconButton from '@mui/material/IconButton';

export default function Root() {
    const [userId, setUserId] = useState(localStorage.getItem('userId') || undefined);
    const [hideSidebar, setHideSidebar] = useState(true);
    const navigation = useNavigation();
    const theme = useTheme();
    const responsive = useMediaQuery(theme.breakpoints.down('md'));
    
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
                        { (responsive && hideSidebar) &&
                            <div>
                                <IconButton 
                                    color="primary" aria-label="menu" component="label" onClick={onClickMenu}
                                    style={{ 
                                        'padding-left': '2.5rem',
                                        'padding-top': '1.5rem'
                                    }}    
                                >
                                    <MenuIcon/>
                                </IconButton>
                            </div>
                        }
                        { (!responsive || !hideSidebar) && 
                            <Sidebar userId={userId} setUserId={setUserId} responsive={responsive} closeMenu={closeMenu}/>
                        }
                        { (!responsive || hideSidebar) && 
                        <div
                            id="detail"
                            className={
                                navigation.state === "loading" ? "loading" : ""
                            }
                        >
                            <Outlet context={[userId, setUserId]}/>
                        </div>
                        }
                    </>
                </SnackbarProvider>
            </ThemeProvider>
        </>
    );
}
