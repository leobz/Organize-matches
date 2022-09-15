import * as React from 'react';
import AbcIcon from '@mui/icons-material/Abc';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import AddIcon from '@mui/icons-material/Add';
import { Box } from '@mui/system';
import Button from '@mui/material/Button';
import dayjs from 'dayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import Grid from '@mui/material/Grid';
import InputAdornment from '@mui/material/InputAdornment';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import TextField from '@mui/material/TextField';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';

export default function CreateMatch() {
  const [dateTime, setDateTime] = React.useState(nowPlus30Min());
  const [minTime, setMinTime] = React.useState(nowPlus30Min());
  const today = dayjs();

  return(
    <Box component="form" autoComplete="off">
    <div>
      <FormSpace/>
      <RequiredTextField adornment={<AbcIcon/>} id={"name"} defaultValue={"Nombre del Partido"}/>
      <FormSpace/>
      <RequiredTextField adornment={<LocationOnIcon/>} id={"location"} defaultValue={"Ubicacion"}/>
      <FormSpace/>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker
          label="Fecha"
          value={dateTime}
          onChange={(newValue) => {
            setDateTime(newValue);

            var isAfterToday = dayjs(newValue).isAfter(dayjs(), 'day');
            if (isAfterToday) {setMinTime(dayjs().minute(0).hour(0).second(0));}
            else{setMinTime(nowPlus30Min());}
          }
        }
          renderInput={(params) => <TextField {...params} id={"date"} sx={{ width: 0.5}} />}
          inputFormat="YYYY-MM-DD"
          minDate={today}
        />

        <TimePicker
          label="Hora"
          value={dateTime}
          onChange={(newValue) => {
            setDateTime(newValue);
          }}
          inputFormat="HH:mm:00"
          renderInput={(params) => <TextField {...params} id={"time"} sx={{ width: 0.5}} />}
          minTime={minTime}
        />
      </LocalizationProvider>
      <FormSpace/>

      <Grid container justifyContent="flex-end">
        <Button variant="contained" startIcon={<AddIcon/>} onClick={() => {
          if(validateForm(dateTime)){postCreateMatch();}}}>
          Crear Partido
        </Button>
      </Grid>

    </div>
  </Box>
  )
}

class FormSpace extends React.Component{
  render(){return(<Box sx={{ height: 20}}/>)}
} 

class RequiredTextField extends React.Component{
  render(){
    return(       
    <TextField required fullWidth
    id = {this.props.id}
    placeholder= {this.props.defaultValue}
    InputProps={{
      startAdornment: (
        <InputAdornment position="start"> 
           {this.props.adornment}
        </InputAdornment>
      ),
    }}
  />)
  }
}

function nowPlus30Min() {
  return dayjs().add(30, 'minute');
}

function validateForm(dateTime) {
  if (document.getElementById("name").value.trim() == "") {
      alert("Campo nombre es requerido");
      return false;
  }
  if (document.getElementById("location").value.trim() == "") {
    alert("Campo Ubicacion es requerido");
    return false;
  }
  if (dateTime.isBefore(dayjs())) {
    alert("Fecha y hora deben ser posterior al momento actual");
    return false;
  }
  return true;
};

async function postCreateMatch() {
  // TODO: Externalizar a variables de configuracion
  const url_base = "http://localhost:8081"
  const path = "/matches"
  const url = url_base + path

  // TODO: Hardcodeado momentaneamente.
  // IMPORTANTE: Cuando se obtenga el userId desde el controller del backend, eliminar esta linea
  const userId = "00000000-0000-0000-0000-000000000000"

  var body = {
    name : document.getElementById("name").value,
    location: document.getElementById("location").value,
    date: document.getElementById("date").value,
    hour: document.getElementById("time").value,
    userId: userId
  }

  // TODO: Agregar manejo de errores (console.log + alert)
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    body: JSON.stringify(body),
    });

    response.json().then(data => {
      // TODO: Agregar redireccion al recurso creado + Opcional Mensaje flotante indicando exito en creacion
      alert(JSON.stringify(data));
    });
}
