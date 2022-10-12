import * as React from 'react';
import { BasicMatchForm, FormSpace } from './BasicMatchForm';
import { Form, useNavigate, useLoaderData } from "react-router-dom";
import { Card, CardContent, Button, Grid, Typography, Container, CssBaseline, Box, createTheme, ThemeProvider , Avatar} from '@mui/material';
import HighlightOffIcon from '@mui/icons-material/HighlightOff';
import AddIcon from '@mui/icons-material/Add';
import CheckCircleOutlineOutlinedIcon from '@mui/icons-material/CheckCircleOutlineOutlined';
import SportsSoccerOutlinedIcon from '@mui/icons-material/SportsSoccerOutlined';
import Groups2OutlinedIcon from '@mui/icons-material/Groups2Outlined';
import { useSnackbar } from "notistack";
import { getMatch, registerPlayer, unregisterPlayer } from '../../services/matches';

const theme = createTheme();

export async function loader(request) {
  const match = await getMatch(request.params.matchId);
  return { match };
}

/******************                   Main Component                       ******************/
export default function GetMatch() {
  const { match } = useLoaderData();
  const userId = localStorage.userId
  const inscriptedUserIds =
    match.startingPlayers.map(p => p.userId).concat(
      match.substitutePlayers.map(p => p.userId)
    )

  return(
    // TODO: Reutilizar componente de tema en todos las pantallas, para tener componentes homogeneos de manera sencilla
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="sm">
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
          <DinamicAddPlayerButton
            inscriptedUserIds={inscriptedUserIds}
            userId={userId}
            matchId={match.id}
          />
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

/******************                   Components                       ******************/

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

export function DinamicAddPlayerButton(props){
  const navigate = useNavigate()
  const { enqueueSnackbar } = useSnackbar()

  if (props.inscriptedUserIds.includes(props.userId)){
    return(
    <AddPlayerButton
      userId={props.userId}
      matchId={props.matchId}
      disabled = {false}
      color= {"error"}
      text={"Darme de baja"}
      icon={<HighlightOffIcon/>}
      onClick={() => unregisterPlayer(props.matchId, props.userId, navigate, enqueueSnackbar)}
    />)
  }
  else if (props.inscriptedUserIds.length >= 13){
    return(
      <AddPlayerButton
        userId={props.userId}
        matchId={props.matchId}
        disabled = {true}
        color= {"neutral"}
        text={"Partido Completo"}
        icon={<CheckCircleOutlineOutlinedIcon/>}
        />)
    }
  else {
    return(
      <AddPlayerButton
        userId={props.userId}
        matchId={props.matchId}
        disabled = {false}
        color= {"primary"}
        text={"¡Anotarme!"}
        icon={<AddIcon/>}
        onClick={() => registerPlayer(props.matchId, props.userId, navigate, enqueueSnackbar)}
      />)
    }
}

export function AddPlayerButton(props){
  return(
    <Grid container justifyContent="center">
    <Button
      disabled = {props.disabled}
      color= {props.color}
      startIcon={props.icon}
      variant="contained"
      fullWidth
      onClick={() => props.onClick()}
      >
      {props.text}
    </Button>
  </Grid>
  )
}