import React from "react";
import List from '@mui/material/List';
import MatchItem from "./MatchItem.jsx";
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';
import { useLoaderData } from "react-router-dom";
import ListItemText from '@mui/material/ListItemText';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import { Typography, Container, CssBaseline, Box, createTheme, ThemeProvider , Avatar} from '@mui/material';
import SportsSoccerOutlinedIcon from '@mui/icons-material/SportsSoccerOutlined';

export const loader = async () => {
    return fetch("/api/matches").then(resp => {
        return resp.json()
    });
}

const theme = createTheme();

const MatchList = () => {
    const { matches } = useLoaderData();

    const noMatches = (
        <ListItem>
            <ListItemIcon><SentimentVeryDissatisfiedIcon /></ListItemIcon>
            <ListItemText>Parece que no hay ningún partido programado.</ListItemText>
        </ListItem>
    );

    return (
    // TODO: Reutilizar componente de tema en todos las pantallas, para tener componentes homogeneos de manera sencilla
    <ThemeProvider theme={theme}>
        <Container component="main">
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            width: '100%'
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <SportsSoccerOutlinedIcon />
            </Avatar>
        <Typography component="h1" variant="h5">
          <Box sx={{ textAlign: 'center', m: 1}}>
            Partidos disponibles
          </Box>
        </Typography>
        <List
            sx={{ width: '100%', bgcolor: 'background.paper' }}
            aria-labelledby="nested-list-subheader"
        >
            {matches.length? matches.map(doparti => <MatchItem key={doparti.id} match={doparti} />) : noMatches}
        </List>
        </Box>
        </Container>
    </ThemeProvider>
    );
};

export default MatchList;
