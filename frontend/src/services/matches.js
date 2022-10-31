import dayjs from 'dayjs';

export async function patchMatch(match) {

  const fetchUrl = "/api/matches/" + match.id

  const response = await fetch(fetchUrl, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    body: JSON.stringify(match),
  });
  
  if (response.status >= 300){
    throw new Response("", {
      status: response.status,
      statusText: response.statusText,
    });
  }
}

export function validateDateTime(dateTime) {
  return !dateTime.isBefore(dayjs());
}

export async function postCreateMatch(body) {
  const response = await fetch("/api/matches", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    body: JSON.stringify(body),
  });

  if (response.status >= 300){
    const message = `An error has occured: ${response.status}`;
    throw new Response("", {
      status: response.status,
      statusText: message,
    });
  } else {
    return (await response.json()).id;
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

export async function registerPlayer(matchId, userId, navigate, enqueueSnackbar) {
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
        enqueueSnackbar("Error " + response.status + ": Ha ocurrido un error.", { variant: "error" });
        const message = `An error has occured: ${response.status}`;
        throw new Error(message)
      }

      response.json().then(data => {
        enqueueSnackbar("¡Te has anotado al partido!", { variant: "success" });
        navigate("/matches/"+ matchId)
      })
  }
  catch(e){
    console.log(e)
  }
}

export async function unregisterPlayer(matchId, userId, navigate, enqueueSnackbar) {
  try {
    const response = await fetch("/api/matches/" + matchId + "/players/" + userId, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: null,
      });

      if (!response.ok){
        enqueueSnackbar("Error " + response.status + ": Ha ocurrido un error.", { variant: "error" });
        const message = `An error has occured: ${response.status}`;
        throw new Error(message)
      }

      response.json().then(data => {
        enqueueSnackbar("Te has dado de baja del partido", { variant: "success" });
        navigate("/matches/"+ matchId)
      })
  }
  catch(e){
    console.log(e)
  }
}

export async function deleteMatch(matchId, navigate, enqueueSnackbar) {

  try {
    const response = await fetch("/api/matches/" + matchId, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: null,
      });

      if (!response.ok){
        enqueueSnackbar("Error " + response.status + ": Ha ocurrido un error.", { variant: "error" });
        const message = `An error has occured: ${response.status}`;
        throw new Error(message)
      }

      response.json().then(data => {
        enqueueSnackbar("El partido ha sido eliminado ", { variant: "success" });
        navigate("/matches/")
      })
  }
  catch(e){
    console.log(e)
  }
}
