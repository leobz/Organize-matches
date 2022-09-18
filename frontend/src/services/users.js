export async function registerUser(user) {
  const response = fetch("/api/users", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    body: JSON.stringify(user),
  });
  
  if (response.status < 300){
    throw new Response("", {
      status: response.status,
      statusText: response.statusText,
    });
  }
  
  response.json().then(data => {
    const message = `Recurso creado exitosamente ID: ${data.id}`;
    alert(message);

  });
}
 
export const getUsers = async () =>
  await fetch("/api/users", {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    }
  }).then(function(response) {
    return response.json();
  }).then(function(data) {
    console.log(data);
    return JSON.stringify(data);
  }).catch(error => {
    throw new Response("", {
      status: response.status,
      statusText: response.statusText,
    });
  })
  .finally((data) => data);