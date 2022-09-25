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

export function buildUser(email, password) {
    const user = {
        email : email,
        password : password
    };
    return JSON.stringify(user);
}