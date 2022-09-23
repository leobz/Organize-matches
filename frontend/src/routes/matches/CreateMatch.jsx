import * as React from 'react';
import { Box } from '@mui/system';
import dayjs from 'dayjs';
import { BasicMatchForm } from './BasicMatchForm';
import { Form } from "react-router-dom";

export async function action({ request }) {
	const formData = await request.formData();

  // TODO: Hardcodeado momentaneamente.
  // IMPORTANTE: Cuando se obtenga el userId desde el controller del backend, eliminar esta linea
  const userId = "00000000-0000-0000-0000-000000000000"

  const match = {
    name: formData.get('name'),
    location: formData.get('location'),
    date: formData.get('date'),
    hour: formData.get('time'),
    userId: userId
	};

  const dateTime = dayjs(match.date + " " + match.hour)

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
  // TODO: Externalizar url_base a variable de configuracion
  const url_base = "http://localhost:8081"
  const path = "/matches"
  const url = url_base + path

  try {
    const response = await fetch(url, {
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
