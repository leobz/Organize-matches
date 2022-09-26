import * as React from 'react';
import { Typography, Container, CssBaseline, Avatar, Box, createTheme, ThemeProvider } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { redirect } from 'react-router-dom';
import SignUpForm from './SignUpForm';
import { registerUser } from "../../services/users";

const theme = createTheme();

export async function action({ request }) {
	const formData = await request.formData();
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
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
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
    </ThemeProvider>
  );
}
