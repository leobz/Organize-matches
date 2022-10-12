import React from "react";
import ListItemText from '@mui/material/ListItemText';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemButton from '@mui/material/ListItemButton';
import SportsSoccerIcon from '@mui/icons-material/SportsSoccer';
import MatchDetails from "./MatchDetails.jsx";
import Grid from '@mui/material/Grid';
import { useNavigate } from 'react-router-dom';


const MatchItem = ({match}) => {
    const navigate = useNavigate();

    return (
        <ListItemButton onClick={() => navigate(`/matches/${match.id}`)}>
            <Grid container>
                <Grid item component={ListItem} md={6}>
                    <ListItemIcon><SportsSoccerIcon fontSize="medium" /></ListItemIcon>
                    <ListItemText primaryTypographyProps={{
                        variant: 'h5'
                    }}>{match.name}</ListItemText>
                </Grid>
                <Grid item md={6}>
                    <MatchDetails match={match} />
                </Grid>
            </Grid>
        </ListItemButton>
    );
}

export default MatchItem;