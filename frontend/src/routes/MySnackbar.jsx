import * as React from 'react';
import { Snackbar, Alert } from '@mui/material';
import Slide, { SlideProps } from '@mui/material/Slide';

export default function MySnackbar(props) {
  return (
    <Snackbar 
      open={props.open}
      anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
      TransitionComponent={() => <Slide {...SlideProps} direction="right"/>}  
    >
      <Alert severity={props.severity}>
        {props.message}
      </Alert>
    </Snackbar>
  );
}