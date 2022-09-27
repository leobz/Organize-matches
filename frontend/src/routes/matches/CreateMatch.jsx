import * as React from 'react';
import { Box } from '@mui/system';
import dayjs from 'dayjs';
import { BasicMatchForm } from './BasicMatchForm';
import { Form } from "react-router-dom";
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import AddIcon from '@mui/icons-material/Add';

export async function action({ request }) {
	const formData = await request.formData();
  const dateTime = dayjs(formData.get('date') + " " + formData.get('time'))

  const match = {
    name: formData.get('name'),
    location: formData.get('location'),
    dateAndTime: dateTime
	};


  if(validateForm(dateTime)){
    postCreateMatch(match)
  }
}

/******************                   Main Component                       ******************/
export default function CreateMatch() {
  return(
    <Box>
      <Form method="post">
        <BasicMatchForm/>
        <Grid container justifyContent="flex-end">
          <Button type="submit" variant="contained" startIcon={<AddIcon/>}>
            Crear Partido
          </Button>
        </Grid>
      </Form>
    </Box>
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

      response.json().then(data => {
        // TODO: Agregar redireccion al recurso creado + Opcional Mensaje flotante indicando exito en creacion
        const message = `Recurso creado exitosamente ID: ${data.id}`;
        alert(message);
      });
  }
  catch(e){
    console.log(e)
  }
}
