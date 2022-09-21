import { Grid, Button, TextField, Box } from "@mui/material";
import { Form, NavLink } from "react-router-dom";

export default function SignUpForm ()
{
	return (
		<Box noValidate sx={{ mt: 3 }}>
			<Form method="post">
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
						/>
					</Grid>
					<Grid item xs={12} sm={12}>
						<TextField
							autoComplete="given-name"
							name="phone"
							required
							fullWidth
							id="phone"
							label="Phone"
							autoFocus
						/>
					</Grid>
					<Grid item xs={12} sm={12}>
						<TextField
							autoComplete="given-name"
							name="email"
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
							id="password"
							label="Password"
							autoFocus
						/>
					</Grid>
					<Grid item xs={12} sm={6}>
						<TextField
							autoComplete="given-name"
							name="repeatPassword"
							required
							fullWidth
							id="repeatPassword"
							label="Repeat Password"
							autoFocus
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
			</Form>
		</Box>
	);
}