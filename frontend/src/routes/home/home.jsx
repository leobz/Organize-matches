import * as React from 'react';
import { getLastMetrics } from "../../services/last-metrics";
import { useLoaderData } from "react-router-dom";
import { Box, ThemeProvider, createTheme } from '@mui/system';
import Grid from '@mui/system/Unstable_Grid';
import styled from "@mui/system/styled";

export async function loader() {
  const metrics = await getLastMetrics();
  return { metrics };
}

const Item = styled("div")(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
  border: "2px solid",
  borderColor: theme.palette.mode === "dark" ? "#444d58" : "#ced7e0",
  padding: theme.spacing(1),
  borderRadius: 15,
  textAlign: "center"
}));

const theme = createTheme({
  palette: {
    background: {
      general: '#f0f0f0',
    },
    text: {
      regular: '#173A5E',
      special: '#46505A',
    },
    action: {
      active: '#e3e3e3',
    },
  },
});

export default function Metrics() {
  const { metrics } = useLoaderData();

  return (


    <ThemeProvider theme={theme}>
      <Box sx = {{textAlign: "center",}}>
          <img src="/img/OrganizeMatches.gif" alt={"logo"} width="100%"/>
      </Box>

      <Box
        display="flex" justifyContent="center" alignItems="center"
        sx={{
          bgcolor: 'background.general',
          boxShadow: 1,
          borderRadius: 5,
          p: 1,
          width: "100%",
          flexGrow: 1, 
          '&:hover': {
            bgcolor: 'action.active',
          }
        }}
      >
        <Grid container spacing={2}>

          <Grid xs={6}>
            <Item>
              <Box sx={{ color: 'text.special'}}> Last Matches </Box>
              <Box sx={{ color: 'text.regular', fontSize: 34, fontWeight: 'medium' }}> { JSON.parse(metrics)["matches"]} </Box>
            </Item>
          </Grid>
          <Grid xs={6}>
            <Item>
              <Box sx={{ color: 'text.special' }}> Last Players </Box>
              <Box sx={{ color: 'text.regular', fontSize: 34, fontWeight: 'medium' }}> { JSON.parse(metrics)["players"]} </Box>
            </Item>
          </Grid>
          
          <Grid xs={12} display="flex" justifyContent="center" alignItems="center">
            <Box sx = {{
              color: 'text.special', 
              textAlign: "center",
              textDecoration: 'underline',
              fontSize: 14,
            }}>
              Estas métricas son consideradas respecto a las últimas 2 horas.
            </Box>
          </Grid>
        </Grid>
      </Box>
    </ThemeProvider>
  );
}
