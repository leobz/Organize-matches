import * as React from 'react';
import { Box } from '@mui/system';
import { BasicMatchForm } from './BasicMatchForm';
import { Form } from "react-router-dom";
import { useLoaderData } from "react-router-dom";

export async function loader() {
  // TODO: Eliminar id hardoced. Recibir el ID desde la URL del path.
  const id = "4689421a-63d7-4877-be0a-7ed3980e55bb"
  const match = await getMatch(id);
  return { match };
}

/******************                   Main Component                       ******************/
export default function GetMatch() {
  const { match } = useLoaderData();

  console.log(match)
  return(
    <Box> 
      <Form method="post">
        <BasicMatchForm
          readOnly={true}
          location={match.location}
          name={match.name}
          date={match.dateAndTime}
          time={match.dateAndTime}
          />
      </Form>
    </Box>
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