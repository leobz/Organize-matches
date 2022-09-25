import * as React from 'react';
import AbcIcon from '@mui/icons-material/Abc';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { Box } from '@mui/system';
import dayjs from 'dayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import InputAdornment from '@mui/material/InputAdornment';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import TextField from '@mui/material/TextField';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';

/******************                   Main Component                       ******************/
export function BasicMatchForm(props) {
  const [dateTime, setDateTime] = React.useState(nowPlus30Min());
  const [minTime, setMinTime] = React.useState(nowPlus30Min());
  const today = dayjs();
  const readOnly =  props.readOnly || false

  return(
    <div>
      <FormSpace/>
      <RequiredTextField
        value={props.name}
        readOnly={props.readOnly} adornment={<AbcIcon/>} id={"name"} defaultValue={"Nombre del Partido"}/>
      <RequiredTextField
        value={props.location}
        adornment={<LocationOnIcon/>} id={"location"} defaultValue={"Ubicacion"}/>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker
          label="Fecha"
          value={props.date || dateTime}
          disabled={readOnly}
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
          value={props.time || dateTime}
          onChange={(newValue) => {
            setDateTime(newValue);
          }}
          disabled={readOnly}
          inputFormat="HH:mm:00"
          renderInput={(params) => <TextField {...params} name={"time"} id={"time"} sx={{ width: 0.5}} />}
          minTime={minTime}
        />
      </LocalizationProvider>
      <FormSpace/>
    </div>
  )
}

/******************                   Sub Components                       ******************/
class FormSpace extends React.Component{
  render(){return(<Box sx={{ height: 20}}/>)}
} 

export function RequiredTextField (props){
    return(
    <>
      <TextField 
      id={props.id} placeholder={props.defaultValue} name={props.id} value={props.value}
      required fullWidth
      InputProps={{
        readOnly: props.readOnly  ,
        startAdornment: (
          <InputAdornment position="start">
            {props.adornment}
          </InputAdornment>
        ),
      }}
      />
      <FormSpace/>
    </>
  )
}

/******************                   Functions                       ******************/

function nowPlus30Min() {
  return dayjs().add(30, 'minute');
}
