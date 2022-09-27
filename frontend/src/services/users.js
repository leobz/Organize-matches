export async function registerUser(user) {
  const response = await fetch("/api/users", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    body: JSON.stringify(user),
  });
  
  if (response.status >= 300){
    throw new Response("", {
      status: response.status,
      statusText: response.statusText,
    });
  }
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
    return JSON.stringify(data);
  }).catch(() => {
    throw new Response("", {
      status: response.status,
      statusText: response.statusText,
    });
  })
  .finally((data) => data);
