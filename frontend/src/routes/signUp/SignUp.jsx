import * as React from 'react';
import { Typography, Container, CssBaseline, Avatar, Box } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { redirect, json } from 'react-router-dom';
import SignUpForm from './SignUpForm';
import { registerUser } from "../../services/users";

export async function action({ request }) {
	const formData = await request.formData();

  if(formData.get('password') !== formData.get('repeatPassword')) {
    throw json (
      { errorMessage: "Password mismatch" },
      { status: 400 }
    );
  }

  const user = {
    fullName: formData.get('fullName'),
    alias: formData.get('alias'),
    phone: formData.get('phone'),
    email: formData.get('phone'),
    password: formData.get('password')
	};
	await registerUser(user);
	return redirect('/login');
}

export default function SignUp() {
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
          Sign up
        </Typography>
        <SignUpForm/>
      </Box>
    </Container>
  );
}

