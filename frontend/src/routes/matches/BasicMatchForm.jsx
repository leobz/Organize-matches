import * as React from 'react';
import { useEffect } from 'react'; 
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
import DoneIcon from '@mui/icons-material/Done';
import CancelIcon from '@mui/icons-material/Cancel';
import { Button } from '@mui/material'

/******************                   Main Component                       ******************/
export function BasicMatchForm(props) {
  const [dateTime, setDateTime] = React.useState(props.dateTime);
  const [minTime, setMinTime] = React.useState(undefined);
  const [name, setName] = React.useState(props.name);
  const [location, setLocation] = React.useState(props.location);
  const today = dayjs();
  const readOnly =  props.readOnly || false

  return(
    <div>
      <FormSpace/>
      <RequiredTextField
        onChange={(e) => { props.onChange(); setName(e.target.value); }}
        value={name}
        readOnly={props.readOnly} adornment={<AbcIcon/>} id={"name"}/>
      <RequiredTextField
        onChange={(e) => { props.onChange(); setLocation(e.target.value); }}
        value={location}
        readOnly={props.readOnly} adornment={<LocationOnIcon/>} id={"location"}/>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker
          label="Fecha"
          value={dateTime||props.dateTime}
          defaultValue={props.dateTime||nowPlus30Min()}
          disabled={readOnly}
          onChange={(newValue) => {
            setDateTime(newValue);
            var isAfterToday = dayjs(newValue).isAfter(dayjs(), 'day');
            if (isAfterToday) {setMinTime(dayjs().minute(0).hour(0).second(0));}
            else{setMinTime(nowPlus30Min());}
            props.onChange();
          }
        }
          renderInput={(params) => <TextField {...params} name={"date"} id={"date"} sx={{ width: 0.5}} />}
          inputFormat="YYYY-MM-DD"
          minDate={today}
        />

        <TimePicker
          label="Hora"
          value={dateTime||props.dateTime}
          defaultValue={dateTime||nowPlus30Min()}
          onChange={(newValue) => {
            setDateTime(newValue);
            props.onChange();
          }}
          disabled={readOnly}
          inputFormat="HH:mm:00"
          renderInput={(params) => <TextField {...params} name={"time"} id={"time"} sx={{ width: 0.5}} />}
          minTime={minTime}
        />
      </LocalizationProvider>
      <FormSpace/>
      { props.isEditing &&
          <>
            <FormSpace/>
            <Button type="submit" variant="contained" startIcon={<DoneIcon/>}>
              Guardar
            </Button>
            <Button variant="outlined" startIcon={<CancelIcon/>} onClick={() => {
                props.setIsEditing(false);
                setLocation(props.location);
                setName(props.name);
                setDateTime(props.dateTime);
              }}>
              Cancelar
            </Button>
          </>
          }
    </div>
  )
}

/******************                   Sub Components                       ******************/
export function FormSpace(){
  return(<Box sx={{ height: 20}}/>)
} 

export function RequiredTextField (props){
    return(
    <>
      <TextField 
        //variant={props.readOnly? "filled" : "outlined"}
        id={props.id}
        name={props.id}
        value={props.value}
        label={props.id}
        defaultValue={props.defaultValue}
        required
        fullWidth
        autoFocus
        onChange={props.onChange}
      InputProps={{

        readOnly: props.readOnly  ,
        endAdornment: (
          <InputAdornment position="start" sx={{ml: 2}}>
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
