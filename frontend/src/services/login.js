export async function loginUser(user) {
    const response = fetch("http://localhost:8080" + "/api/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: user,
    });

    if (response.status >= 400){
        throw new Response("", {
            status: response.status,
            statusText: response.statusText,
        });
    }

    return response.json().userId;
}

export function buildUser(data) {
    const user = {
        email : data.email,
        password : data.password
    };
    return JSON.stringify(user);
}
