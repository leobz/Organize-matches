
import * as React from 'react';
import { Grid, Button, TextField, Box } from "@mui/material";
import { NavLink, useNavigate } from "react-router-dom";
import { registerUser } from "../../services/users";
import { useSnackbar } from "notistack";

export default function SignUpForm ()
{
	const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();

	const showError = (statusCode, message) => {
		enqueueSnackbar("Error " + statusCode + ": " + message, { variant: "error" });
	}

	const showSuccess = (message) => {
		enqueueSnackbar(message, { variant: "success" });
	}

	const handleSubmit = async (event) => {
		const formData = event.target.elements;
		event.preventDefault();
	
		if(formData.password.value !== formData.repeatPassword.value) {
			showError(400, "Password mismatch");
			return;
		}
		const user = {
			fullName: formData.fullName.value,
			alias: formData.alias.value,
			phone: formData.phone.value,
			email: formData.email.value,
			password: formData.password.value,
		};
		try {
			await registerUser(user);
			showSuccess("User created successfully.");
			navigate('/login');
		}
		catch (error) {
			if(error.status === 409)
				showError(error.status, "Email address is taken. Try another one.");
			else if(error.status === 400)
				showError(error.status, "You have entered invalid data. Please check your information and try again.");
			else
				showError(error.status, "Internal error.");
		}
	}

	return (
		<Box noValidate sx={{ mt: 3 }}>
			<form onSubmit={handleSubmit}>
				<Grid container spacing={2}>
					<Grid item xs={12} sm={12}>
						<TextField
							autoComplete="given-name"
							name="fullName"
							required
							fullWidth
							id="fullName"
							label="Full Name"
							autoFocus
							inputProps={{ minLength: 5 }}
						/>
					</Grid>
					<Grid item xs={12} sm={12}>
						<TextField
							autoComplete="given-name"
							name="alias"
							required
							fullWidth
							id="alias"
							label="Alias"
							autoFocus
							inputProps={{ minLength: 5 }}
						/>
					</Grid>
					<Grid item xs={12} sm={12}>
						<TextField
							autoComplete="given-name"
							name="phone"
							type="tel"
							required
							fullWidth
							id="phone"
							label="Phone"
							autoFocus
							inputProps={{ minLength: 8, inputMode: 'numeric', pattern: '[0-9]*' }}
						/>
					</Grid>
					<Grid item xs={12} sm={12}>
						<TextField
							autoComplete="given-name"
							name="email"
							type="email"
							required
							fullWidth
							id="email"
							label="Email"
							autoFocus
						/>
					</Grid>
					<Grid item xs={12} sm={6}>
						<TextField
							autoComplete="given-name"
							name="password"
							required
							fullWidth
							type="password"
							id="password"
							label="Password"
							autoFocus
							inputProps={{ minLength: 6 }}
						/>
					</Grid>
					<Grid item xs={12} sm={6}>
						<TextField
							autoComplete="given-name"
							name="repeatPassword"
							required
							fullWidth
							type="password"
							id="repeatPassword"
							label="Repeat Password"
							autoFocus
							inputProps={{ minLength: 6 }}
						/>
					</Grid>
					<Grid item xs={12}>
						<Button
							type="submit"
							fullWidth
							variant="contained"
							sx={{ mt: 0, mb: 2 }}
						>
						Sign Up
						</Button>
					</Grid>
					<Grid container justifyContent="flex-end">
						<Grid item>
							<NavLink to='/login' variant="body2">
								Already have an account? Sign in
							</NavLink>
						</Grid>
					</Grid>
				</Grid>
			</form>
		</Box>
	);
}
