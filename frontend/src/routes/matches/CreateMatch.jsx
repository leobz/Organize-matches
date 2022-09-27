import * as React from 'react';
import { Box } from '@mui/system';
import dayjs from 'dayjs';
import { BasicMatchForm } from './BasicMatchForm';
import { Form, redirect } from "react-router-dom";
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import AddIcon from '@mui/icons-material/Add';
import {Container, ThemeProvider, CssBaseline, createTheme, Avatar, Typography} from '@mui/material';
import LibraryAddOutlinedIcon from '@mui/icons-material/LibraryAddOutlined';

const theme = createTheme();

export async function action({ request }) {
  const formData = await request.formData();
  const dateTime = dayjs(formData.get('date') + " " + formData.get('time'))

  const match = {
    name: formData.get('name'),
    location: formData.get('location'),
    dateAndTime: dateTime
	};


  if(validateForm(dateTime)){
    //postCreateMatch(match)
    // TODO: Despues de crear partido, redireccion a partido creado
    const matchId = await postCreateMatch(match)
    return redirect("/matches/" + matchId )
  }
}

/******************                   Main Component                       ******************/
export default function CreateMatch() {
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
              <LibraryAddOutlinedIcon />
            </Avatar>
        </Box>
        <Form method="post">
          <Typography component="h1" variant="h5">
            <Box sx={{ textAlign: 'center', m: 1}}>
              Crear Partido
            </Box>
          </Typography>
          <BasicMatchForm/>
          <Grid container justifyContent="flex-end">
            <Button type="submit" variant="contained" startIcon={<AddIcon/>}>
              Crear Partido
            </Button>
          </Grid>
        </Form>
      </Container>
    </ThemeProvider>
  )
}

/******************                   Functions                       ******************/

function validateForm(dateTime) {
  if (dateTime.isBefore(dayjs())) {
    alert("Fecha y hora deben ser posterior al momento actual");
    return false;
  }
  return true;
};

async function postCreateMatch(body) {
  try {
    const response = await fetch("/api/matches", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(body),
      });

    if (!response.ok){
      alert("Ah ocurrido un error");
      const message = `An error has occured: ${response.status}`;
      throw new Error(message)
    }

    return (await response.json()).id;
  }
  catch(e){
    console.log(e)
  }
}
