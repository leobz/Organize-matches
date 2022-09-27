import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import { Link, useOutletContext } from "react-router-dom";
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {Alert} from "@mui/material";
import {useNavigate} from 'react-router-dom';
import { loginUser, buildUser } from "../../services/login";
import { useEffect, useState } from "react";

export default function SignIn() {

    const [userId, setUserId] = useOutletContext();
    const navigate = useNavigate();
    const [wrongUserOrPasswordAlert, setWrongUserOrPasswordAlert] = useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        loginUser(buildUser(data.get('email'), data.get('password')))
        .then((response) => {
            if (response.status >= 400){
                setWrongUserOrPasswordAlert(true);
            } else {
                response.json().then(data => {
                    setUserId(data.userId);
                });
                navigate('/home');
            }
        });


    };

    useEffect(() => {
        localStorage.setItem('userId', userId);
    }, [userId]);

    return (
    // TODO: Reutilizar componente de tema en todos las pantallas, para tener componentes homogeneos de manera sencilla
        <Container component="main" maxWidth="sm">
            <CssBaseline />
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign in
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                    />
                    {wrongUserOrPasswordAlert &&
                        <Alert
                            severity="error"
                            id="unknownUser">
                            Usuario o contraseña incorrecta.
                        </Alert>
                    }
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Sign In
                    </Button>
                    <Grid container>
                        <Grid item>
                            <Link to="/register">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
}
