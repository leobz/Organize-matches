import * as React from 'react';
import { useState } from 'react';
import BasicMatchForm, {FormSpace} from './BasicMatchForm';
import { useParams, useNavigate, useLoaderData, redirect, Form } from "react-router-dom";
import { Grid, Button, Typography, Container, CssBaseline, Box, Card, CardContent, Avatar} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import CheckCircleOutlineOutlinedIcon from '@mui/icons-material/CheckCircleOutlineOutlined';
import SportsSoccerOutlinedIcon from '@mui/icons-material/SportsSoccerOutlined';
import Groups2OutlinedIcon from '@mui/icons-material/Groups2Outlined';
import { patchMatch, validateDateTime } from '../../services/matches';
import dayjs from 'dayjs';
import { useSnackbar } from "notistack";

export async function loader(request) {
  const match = await getMatch(request.params.matchId);
  match.dateAndTime = match.dateAndTime + "z"
  return { match };
}

/******************                   Main Component                       ******************/
export default function GetMatch() {
  const { enqueueSnackbar } = useSnackbar();
  const { match } = useLoaderData();
  const [isEditing, setIsEditing] = useState(false);
  const userId = localStorage.userId
  const { matchId } = useParams();
  const inscriptedUserIds =
    match.startingPlayers.map(p => p.userId).concat(
      match.substitutePlayers.map(p => p.userId)
    )

  const handleSubmit = async (event) => {
    const formData = event.target.elements;
    event.preventDefault();
    const dateTime = dayjs(formData.date.value + " " + formData.time.value)
  
    const bodyMatch = {
      id: matchId,
      name: formData.name.value,
      location: formData.location.value,
      dateAndTime: dateTime
    };
  
    if(validateDateTime(dateTime)){
      await patchMatch(bodyMatch)
      enqueueSnackbar("Match changes saved", { variant: "success" });
      setIsEditing(false);
    }
    else
      enqueueSnackbar("Error: Fecha y hora deben ser posterior al momento actual", { variant: "error" });      
  }

  return(
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
          <Typography component="h1" variant="h5">
            <Box sx={{ textAlign: 'center', m: 1}}>
              Datos de partido
            </Box>
          </Typography>
          <form onSubmit={handleSubmit}>
            <BasicMatchForm
              readOnly={match.userId != userId}
              location={match.location}
              name={match.name}
              dateTime={match.dateAndTime}
              onChange={() => setIsEditing(true)}
              isEditing={isEditing}
              setIsEditing={setIsEditing}
            />
          </form>
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
        </Box>
      </Container>
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
  if (props.inscriptedUserIds.includes(props.userId)){
    return(
    <AddPlayerButton
      userId={props.userId}
      matchId={props.matchId}
      disabled = {true}
      color= {"success"}
      text={"Inscripto"}
      icon={<CheckCircleOutlineOutlinedIcon/>}
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
        />)
    }
}

export function AddPlayerButton(props){
  const navigate = useNavigate()

  return(
    <Grid container justifyContent="center">
    <Button
      disabled = {props.disabled}
      color= {props.color}
      startIcon={props.icon}
      variant="contained"
      fullWidth
      onClick={() => registerPlayer(props.matchId, props.userId, navigate)}
      >
      {props.text}
    </Button>
  </Grid>
  )
}


/******************                   Functions                       ******************/
async function registerPlayer(matchId, userId, navigate) {
  try {
    const response = await fetch("/api/matches/" + matchId + "/players", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(userId),
      });

      if (!response.ok){
        alert("Ah ocurrido un error");
        const message = `An error has occured: ${response.status}`;
        throw new Error(message)
      }

      response.json().then(data => {
        alert("¡Te has anotado al partido!");
        navigate("/matches/"+ matchId)
      })
  }
  catch(e){
    console.log(e)
  }
}


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
    return data;
  }).catch(error => {
    throw new Response("", {
      status: response.status,
      statusText: response.statusText,
    });
  })
  .finally((data) => data);