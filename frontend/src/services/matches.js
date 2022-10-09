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