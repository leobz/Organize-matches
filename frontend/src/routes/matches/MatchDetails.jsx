import React from "react";
import List from '@mui/material/List';
import EventIcon from '@mui/icons-material/Event';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import GroupsIcon from '@mui/icons-material/Groups';
import SubItem from "./SubItem.jsx";
import { useTheme } from "@mui/material/styles";
import { useMediaQuery } from "@mui/material";

const MatchDetails = ({match}) => {
    const {location, startingPlayers, substitutePlayers, dateAndTime} = match;
    let objTime = new Date(dateAndTime + "Z");
    let date = objTime.toLocaleString([], {
        year: "numeric", month: "2-digit", day: "2-digit", hour: "2-digit", minute: "2-digit"
    }).replace(",", "");
    const theme = useTheme();
    const responsive = useMediaQuery(theme.breakpoints.down('280'));
    let playersQuantity = `${startingPlayers.length + substitutePlayers.length} `;

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
                text={responsive ? (playersQuantity + "jug.") : (playersQuantity + "jugadores")}
            />
        </List>
    );
}

export default MatchDetails;