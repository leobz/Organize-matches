import React from "react";
import List from '@mui/material/List';
import MatchItem from "./MatchItem.jsx";
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';
import { useLoaderData } from "react-router-dom";
import ListItemText from '@mui/material/ListItemText';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';

export const loader = async () => {
    return fetch("http://localhost:8081/matches").then(resp => {
        return resp.json()
    });
}

const MatchList = () => {
    const { matches } = useLoaderData();

    const noMatches = (
        <ListItem>
            <ListItemIcon><SentimentVeryDissatisfiedIcon /></ListItemIcon>
            <ListItemText>Parece que no hay ningún partido programado.</ListItemText>
        </ListItem>
    );

    return (
        <List
            sx={{ width: '100%', bgcolor: 'background.paper' }}
            component="Grid"
            container
            aria-labelledby="nested-list-subheader"
        >
            {matches.length? matches.map(doparti => <MatchItem container match={doparti} />) : noMatches}
        </List>
    );
};

export default MatchList;
