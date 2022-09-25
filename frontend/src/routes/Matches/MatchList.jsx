import React, {useEffect, useState} from "react";
import List from '@mui/material/List';
import MatchItem from "./MatchItem.jsx";
import { useLoaderData } from "react-router-dom";

export const loader = async () => {
    return fetch("http://localhost:8081/matches").then(resp => {
        // return resp.json()
        return {matches: [
            {
                name: "Torneo de los cinco papis",
                time: new Date("2022-09-21 20:00:00"),
                location: "Qatar 2022",
                players: 5
            },
            {
                name: "Fulbito de las 11",
                time: new Date("2022-09-20 23:00:00"),
                location: "Paysandú y Méndez de Andes",
                players: 10
            },
        ]};
    });
}

const MatchList = () => {
    const { matches } = useLoaderData();

    return (
        <List
            sx={{ width: '100%', bgcolor: 'background.paper' }}
            component="Grid"
            container
            aria-labelledby="nested-list-subheader"
        >
            {matches.map(doparti => <MatchItem container match={doparti} />)}
        </List>
    );
};

export default MatchList;