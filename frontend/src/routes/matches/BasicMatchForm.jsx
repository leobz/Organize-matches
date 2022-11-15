import * as React from 'react'; 
import AbcIcon from '@mui/icons-material/Abc';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { Box } from '@mui/system';
import dayjs from 'dayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import InputAdornment from '@mui/material/InputAdornment';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { TextField, Grid, Container } from '@mui/material';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';
import DoneIcon from '@mui/icons-material/Done';
import CancelIcon from '@mui/icons-material/Cancel';
import DeleteIcon from '@mui/icons-material/Delete';
import { Button } from '@mui/material'
import { deleteMatch } from '../../services/matches';
import { useNavigate } from 'react-router-dom';
import { useSnackbar } from "notistack";
import EditIcon from '@mui/icons-material/Edit';



/******************                   Main Component                       ******************/
function BasicMatchForm(props) {
  const [dateTime, setDateTime] = React.useState(props.dateTime);
  const [minTime, setMinTime] = React.useState(undefined);
  const [name, setName] = React.useState(props.name);
  const [location, setLocation] = React.useState(props.location);
  const today = dayjs();
  const readOnly =  props.readOnly || false

  const navigate = useNavigate()
  const { enqueueSnackbar } = useSnackbar()


  return(
    <div>
      <FormSpace/>
      <RequiredTextField
        onChange={(e) => { props.onChange(); setName(e.target.value); }}
        value={name}
        readOnly={props.readOnly} adornment={<AbcIcon/>} id={"name"}
        disabled={!props.isEditing && props.isEditable}/>
      <RequiredTextField
        onChange={(e) => { props.onChange(); setLocation(e.target.value); }}
        value={location}
        readOnly={props.readOnly} adornment={<LocationOnIcon/>} id={"location"}
        disabled={!props.isEditing && props.isEditable}/>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <DatePicker
              label="Fecha"
              value={dateTime||props.dateTime}
              defaultValue={props.dateTime||nowPlus30Min()}
              disabled={readOnly||!props.isEditing && props.isEditable}
              onChange={(newValue) => {
                setDateTime(newValue);
                var isAfterToday = dayjs(newValue).isAfter(dayjs(), 'day');
                if (isAfterToday) {setMinTime(dayjs().minute(0).hour(0).second(0));}
                else{setMinTime(nowPlus30Min());}
                props.onChange();
              }
            }
              renderInput={(params) => <TextField {...params} name={"date"} id={"date"} sx={{ width: 1}} />}
              inputFormat="YYYY-MM-DD"
              minDate={today}
              width="100%"
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TimePicker
              label="Hora"
              value={dateTime||props.dateTime}
              defaultValue={dateTime||nowPlus30Min()}
              onChange={(newValue) => {
                setDateTime(newValue);
                props.onChange();
              }}
              disabled={readOnly||!props.isEditing && props.isEditable}
              inputFormat="HH:mm:00"
              renderInput={(params) => <TextField {...params} name={"time"} id={"time"} sx={{ width: 1}} />}
              minTime={minTime}
              width="100%"
            />
          </Grid>
        </Grid>
      </LocalizationProvider>
      <FormSpace/>
      {
        (!props.readOnly && !props.isEditing && props.isEditable) && 
        <>
        <FormSpace/>
        <Button fullWidth variant="contained" startIcon={<EditIcon/>} onClick={() => props.onChange()}>
          Editar
        </Button>
        </>
      }
      { !props.readOnly && props.isEditing &&
        <>
          <FormSpace/>
          <Grid container spacing={1}>
            <Grid item xs={12} sm={4}>
              <Button fullWidth type="submit" variant="contained" startIcon={<DoneIcon/>}>
                Guardar
              </Button>
            </Grid>
            <Grid item xs={12} sm={4}>
              <Button fullWidth variant="outlined" startIcon={<CancelIcon/>} onClick={() => {
                  props.setIsEditing(false);
                  setLocation(props.location);
                  setName(props.name);
                  setDateTime(props.dateTime);
                }}>
                Cancelar
              </Button>
            </Grid>
            <Grid item xs={12} sm={4}>
              <Button fullWidth variant="outlined" color="error" startIcon={<DeleteIcon/>} onClick={() => {
                  deleteMatch(props.matchId, navigate, enqueueSnackbar);
                }}>
                Eliminar
              </Button>
            </Grid>
          </Grid>
        </>
      }
    </div>
  )
}

BasicMatchForm.defaultProps = {
  onChange: () => {},
  name: "",
  location: ""
}

export default BasicMatchForm

/******************                   Sub Components                       ******************/
export function FormSpace(){
  return(<Box sx={{ height: 20}}/>)
} 

export function RequiredTextField (props){
    return(
    <>
      <TextField 
        //variant={props.readOnly? "filled" : "outlined"}
        disabled={props.disabled}
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
