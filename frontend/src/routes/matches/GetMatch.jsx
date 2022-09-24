import * as React from 'react';
import { Box } from '@mui/system';
import { BasicMatchForm } from './BasicMatchForm';
import { Form } from "react-router-dom";

/******************                   Main Component                       ******************/
export default function GetMatch() {
  return(
    <Box> 
      <Form method="post">
        <BasicMatchForm/>
      </Form>
    </Box>
  )
}

/******************                   Functions                       ******************/
