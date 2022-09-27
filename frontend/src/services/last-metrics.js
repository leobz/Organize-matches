 
export const getLastMetrics = async () =>
  await fetch("/api/matches/counter", {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    }
  }).then(function(response) {
    return response.json();
  }).then(function(data) {
    return JSON.stringify(data);
  }).catch(error => {
    throw new Response("", {
      status: response.status,
      statusText: response.statusText,
    });
  })
  .finally((data) => data);
