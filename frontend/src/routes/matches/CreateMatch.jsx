import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import { Box } from '@mui/system';
import dayjs from 'dayjs';
import BasicMatchForm from './BasicMatchForm';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import AddIcon from '@mui/icons-material/Add';
import {Container, ThemeProvider, CssBaseline, createTheme, Avatar, Typography} from '@mui/material';
import LibraryAddOutlinedIcon from '@mui/icons-material/LibraryAddOutlined';
import { postCreateMatch, validateDateTime } from '../../services/matches';
import { useSnackbar } from "notistack";

const theme = createTheme();

/******************                   Main Component                       ******************/
export default function CreateMatch() {
  const { enqueueSnackbar } = useSnackbar(); 
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    const formData = event.target.elements;
    event.preventDefault();
    const dateTime = dayjs(formData.date.value + " " + formData.time.value)
  
    const bodyMatch = {
      name: formData.name.value,
      location: formData.location.value,
      dateAndTime: dateTime
    };
  
    if(validateDateTime(dateTime)){
      try {
        const matchId = await postCreateMatch(bodyMatch)
        enqueueSnackbar("Match created", { variant: "success" });
        navigate("/matches/" + matchId );
      } catch(exception) {
        enqueueSnackbar("Error " + exception.status + ": You have entered invalid data. Please check your information and try again.", { variant: "error" });
      }
    }
    else
      enqueueSnackbar("Error: Fecha y hora deben ser posterior al momento actual", { variant: "error" });      
  }

  return(
    // TODO: Reutilizar componente de tema en todos las pantallas, para tener componentes homogeneos de manera sencilla
    <ThemeProvider theme={theme}>
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
              <LibraryAddOutlinedIcon />
            </Avatar>
        </Box>
        <form onSubmit={handleSubmit}>
          <Typography component="h1" variant="h5">
            <Box sx={{ textAlign: 'center', m: 1}}>
              Crear Partido
            </Box>
          </Typography>
          <BasicMatchForm/>
          <Grid container justifyContent="flex-end">
            <Button type="submit" variant="contained" startIcon={<AddIcon/>}>
              Crear Partido
            </Button>
          </Grid>
        </form>
      </Container>
    </ThemeProvider>
  )
}