import React from "react";
import ListItemText from '@mui/material/ListItemText';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemButton from '@mui/material/ListItemButton';
import SportsSoccerIcon from '@mui/icons-material/SportsSoccer';
import MatchDetails from "./MatchDetails.jsx";
import Grid from '@mui/material/Grid';


const MatchItem = ({match}) => {
    return (
        <ListItemButton>
            <Grid container>
                <Grid item component={ListItem} md={6}>
                    <ListItemIcon><SportsSoccerIcon /></ListItemIcon>
                    <ListItemText>{match.name}</ListItemText>
                </Grid>
                <Grid md={6}>
                    <MatchDetails match={match} />
                </Grid>
            </Grid>
        </ListItemButton>
    );
}

export default MatchItem;