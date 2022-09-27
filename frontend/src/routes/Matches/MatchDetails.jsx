import React from "react";
import List from '@mui/material/List';
import EventIcon from '@mui/icons-material/Event';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import GroupsIcon from '@mui/icons-material/Groups';
import SubItem from "./SubItem.jsx";

const MatchDetails = ({match}) => {
    const {location, startingPlayers, substitutePlayers, dateAndTime} = match;
    let objTime = new Date(dateAndTime);
    let date = objTime.toLocaleString([], {
        year: "numeric", month: "2-digit", day: "2-digit", hour: "2-digit", minute: "2-digit"
    }).replace(",", "");
    let playersText = `${startingPlayers.length + substitutePlayers.length} jugadores`;

    return (
        <List>
            <SubItem
                icon={<EventIcon />}
                text={date}
            />
            <SubItem
                icon={<LocationOnIcon />}
                text={location}
            />
            <SubItem
                icon={<GroupsIcon />}
                text={playersText}
            />
        </List>
    );
}

export default MatchDetails;