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

/******************                   Main Component                       ******************/
export function BasicMatchForm() {
  const [dateTime, setDateTime] = React.useState(nowPlus30Min());
  const [minTime, setMinTime] = React.useState(nowPlus30Min());
  const today = dayjs();

  return(
    <div>
      <FormSpace/>
      <RequiredTextField name="hola" adornment={<AbcIcon/>} id={"name"} defaultValue={"Nombre del Partido"}/>
      <RequiredTextField adornment={<LocationOnIcon/>} id={"location"} defaultValue={"Ubicacion"}/>
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
          renderInput={(params) => <TextField {...params} name={"date"} id={"date"} sx={{ width: 0.5}} />}
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
          renderInput={(params) => <TextField {...params} name={"time"} id={"time"} sx={{ width: 0.5}} />}
          minTime={minTime}
        />
      </LocalizationProvider>
      <FormSpace/>

      <Grid container justifyContent="flex-end">
        <Button type="submit" variant="contained" startIcon={<AddIcon/>}>
          Crear Partido
        </Button>
      </Grid>
    </div>
  )
}

/******************                   Sub Components                       ******************/
class FormSpace extends React.Component{
  render(){return(<Box sx={{ height: 20}}/>)}
} 

class RequiredTextField extends React.Component{
  render(){
    return(
    <>
      <TextField 
      id={this.props.id} placeholder={this.props.defaultValue}  name={this.props.id}
      required fullWidth
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            {this.props.adornment}
          </InputAdornment>
        ),
      }}
      />
      <FormSpace/>
    </>
  )
  }
}

/******************                   Functions                       ******************/

function nowPlus30Min() {
  return dayjs().add(30, 'minute');
}
