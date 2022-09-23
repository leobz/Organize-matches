export async function loginUser(user) {
    return fetch("/api/login", {
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
