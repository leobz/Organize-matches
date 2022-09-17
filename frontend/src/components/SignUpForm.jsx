import { Grid, Button, TextField, Box, Link } from "@mui/material";

export default function SignUpForm ()
{
    const handleSubmit = (event) => {
      event.preventDefault();
      const data = new FormData(event.currentTarget);
      console.log({
        fullName: data.get('fullName'),
        alias: data.get('alias'),
        phone: data.get('phone'),
        email: data.get('phone'),
        password: data.get('password'),
        repeatPassword: data.get('repeatPassword'),
      });
      
    };

    return (
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
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
                        <Link href="#" variant="body2">
                            Already have an account? Sign in
                        </Link>
                    </Grid>
                </Grid>
            </Grid>
        </Box>
    );
}