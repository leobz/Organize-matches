import * as React from 'react';
import { BasicMatchForm, FormSpace} from './BasicMatchForm';
import { Form } from "react-router-dom";
import { useLoaderData } from "react-router-dom";
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { Typography, Container, CssBaseline, Box, createTheme, ThemeProvider } from '@mui/material';


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
        <Box> 
          <Form>
          <Typography component="div">
            <Box sx={{ textAlign: 'center', m: 1, fontWeight: 'bold'}}>
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
          <Typography component="div">
            <Box sx={{ textAlign: 'center', m: 1, fontWeight: 'bold'}}>
              Jugadores
            </Box>
          </Typography>
            <p>Titulares</p>
            <Box>
              {match.startingPlayers.map((player) => <BasicCard playerName={player.alias}/>)}
            </Box>

            <p>Suplentes</p>
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