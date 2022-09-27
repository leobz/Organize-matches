import { Outlet, NavLink, useNavigation } from 'react-router-dom';
import { SnackbarProvider } from 'notistack';
import { createTheme, ThemeProvider } from '@mui/material/styles';
const theme = createTheme();

export default function Root() {
  const navigation = useNavigation();

  return (
    <>

      <ThemeProvider theme={theme}>
        <SnackbarProvider maxSnack={1}>
        <div id="sidebar">
          <h1>React Router Contacts</h1>
          <nav>
            <ul>
              <li key='login'>
                <NavLink to='home'> Home </NavLink>
                <NavLink to='login'> Login </NavLink>
                <NavLink to='register'> Sign Up </NavLink>
                <NavLink to='users'> Users </NavLink>
                <NavLink to='create-match'> Create Match </NavLink>
              </li>
              <li key="sections">
                  <NavLink to="matches">Matches</NavLink>
              </li>
            </ul>
          </nav>
        </div>
        <div 
          id="detail"
          className={
            navigation.state === "loading" ? "loading" : ""
          }
        >
          <Outlet/>
        </div>
        </SnackbarProvider>
      </ThemeProvider>
    </>
  );
}