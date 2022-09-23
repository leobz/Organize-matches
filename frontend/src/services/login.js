export async function loginUser(user) {
    return fetch("http://localhost:8080" + "/api/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: user,
    })
}

export function buildUser(data) {
    const user = {
        email : data.email,
        password : data.password
    };
    return JSON.stringify(user);
}
