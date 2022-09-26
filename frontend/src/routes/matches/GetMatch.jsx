import * as React from 'react';
import { BasicMatchForm, FormSpace} from './BasicMatchForm';
import { Form } from "react-router-dom";
import { useLoaderData } from "react-router-dom";
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { Typography, Container, CssBaseline, Box, createTheme, ThemeProvider , Avatar} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import SportsSoccerOutlinedIcon from '@mui/icons-material/SportsSoccerOutlined';
import Groups2OutlinedIcon from '@mui/icons-material/Groups2Outlined';


const theme = createTheme();

export async function loader() {
  // TODO: Eliminar id hardoced. Recibir el ID desde la URL del path.
  const id = "b32d97ca-0bc7-44be-bc31-19bab3637990"
  const match = await getMatch(id);
  return { match };
}

/******************                   Main Component                       ******************/
export default function GetMatch() {
  const { match } = useLoaderData();

  console.log(match)
  return(
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <SportsSoccerOutlinedIcon />
            </Avatar>

        </Box>
        <Box>
          <Form>
          <Typography component="h1" variant="h5">
            <Box sx={{ textAlign: 'center', m: 1}}>
              Datos de partido
            </Box>
          </Typography>
            <BasicMatchForm
              readOnly={true}
              location={match.location}
              name={match.name}
              date={match.dateAndTime}
              time={match.dateAndTime}
              />
          <FormSpace/>

          <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <Groups2OutlinedIcon />
            </Avatar>

        </Box>
          <Typography component="h1" variant="h5">
            <Box sx={{ textAlign: 'center', m: 1}}>
              Jugadores
            </Box>
          </Typography>
          <Grid container justifyContent="center">
            <Button variant="contained" fullWidth startIcon={<AddIcon/>}>
              ¡Anotarme!
            </Button>
          </Grid>
            <h4>Titulares</h4>
            <Box>
              {match.startingPlayers.map((player) => <BasicCard playerName={player.alias}/>)}
            </Box>

            <h4>Suplentes</h4>
            <Box>
              {match.substitutePlayers.map((player) => <BasicCard playerName={player.alias}/>)}
            </Box>
          </Form>
        </Box>
      </Container>
    </ThemeProvider>
  )
}

/******************                   Functions                       ******************/
export const getMatch = async (id) =>
  await fetch("/api/matches/" + id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    }
  }).then(function(response) {
    return response.json();
  }).then(function(data) {
    console.log(data);
    return data;
  }).catch(error => {
    throw new Response("", {
      status: response.status,
      statusText: response.statusText,
    });
  })
  .finally((data) => data);




export function BasicCard(props) {
  return (
    <>
    <Card sx={{ minWidth: 275 }}>
      <CardContent>
        <Typography variant="body1" component="div">
          {props.playerName}
        </Typography>
      </CardContent>
    </Card>
    <FormSpace/>
    </>
  );
}