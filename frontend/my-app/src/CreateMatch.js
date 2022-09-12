

import * as React from 'react';
import AbcIcon from '@mui/icons-material/Abc';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import AddIcon from '@mui/icons-material/Add';
import { Box } from '@mui/system';
import Button from '@mui/material/Button';
import dayjs from 'dayjs';
import 'dayjs/locale/es';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import Grid from '@mui/material/Grid';
import InputAdornment from '@mui/material/InputAdornment';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import TextField from '@mui/material/TextField';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';


export default function CreateMatch() {

  const [value, setValue] = React.useState(dayjs());
  const [_locale, _setLocale] = React.useState('es');

  return(
    <Box component="form" noValidate autoComplete="off">
    <div>
      <FormSpace/>
      <RequiredTextField adornment={<AbcIcon/>} id={"name"} defaultValue={"Nombre del Partido"}/>
      <FormSpace/>
      <RequiredTextField adornment={<LocationOnIcon/>} id={"location"} defaultValue={"Ubicacion"}/>
      <FormSpace/>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker
          label="Fecha"
          value={value}
          onChange={(newValue) => {
            setValue(newValue);
          }}
          renderInput={(params) => <TextField {...params} sx={{ width: 0.5}} />}
          inputFormat="DD/MM/YYYY"
        />

        <TimePicker
          label="Hora"
          value={value}
          onChange={(newValue) => {
            setValue(newValue);
          }}
          renderInput={(params) => <TextField {...params} sx={{ width: 0.5}} />}
        />
      </LocalizationProvider>
      <FormSpace/>

      <Grid container justifyContent="flex-end">
        <Button variant="contained" startIcon={<AddIcon/>} onClick={() => {alert('clicked');}}>
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