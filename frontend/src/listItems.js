import * as React from 'react';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import HomeIcon from '@mui/icons-material/Dashboard';
import ListMatchesIcon from '@mui/icons-material/List';
import CreateMatchesIcon from '@mui/icons-material/Create';
import LogoutIcon from '@mui/icons-material/Logout';

export const mainListItems = (
    <React.Fragment>
        <ListItemButton>
            <ListItemIcon>
                <HomeIcon />
            </ListItemIcon>
            <ListItemText primary="Home" />
        </ListItemButton>
        <ListItemButton>
            <ListItemIcon>
                <ListMatchesIcon />
            </ListItemIcon>
            <ListItemText primary="Listar partidos" />
        </ListItemButton>
        <ListItemButton>
            <ListItemIcon>
                <CreateMatchesIcon />
            </ListItemIcon>
            <ListItemText primary="Crear partido" />
        </ListItemButton>
        <ListItemButton>
            <ListItemIcon>
                <LogoutIcon />
            </ListItemIcon>
            <ListItemText primary="Logout" />
        </ListItemButton>
    </React.Fragment>
);
