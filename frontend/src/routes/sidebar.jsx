import { NavLink } from 'react-router-dom';
import MenuIcon from '@mui/icons-material/Menu';
import IconButton from '@mui/material/IconButton';
import { styled } from '@mui/material/styles';

const MyDiv = styled('div')(({ theme }) => ({
  [theme.breakpoints.down('sm')]: {
      width: "100%",
  },
  [theme.breakpoints.up('sm')]: {
      width: "30%",
  },
  [theme.breakpoints.up('md')]: {
      width: "30%",
  },
  [theme.breakpoints.up('lg')]: {
      width: "30%",
  },
}));


export default function Sidebar(props) {
  const onClickLogout = (e) => {
    e.preventDefault()
    props.disconnect();
  }

  return (
      <MyDiv id='sidebar'>
        <h1>Organize Matches</h1>
        <nav>
          { (props.responsive) &&
            <IconButton 
              color="primary" aria-label="menu" component="label" onClick={props.closeMenu}
              style={{ 
                'paddingLeft': '0rem',
                'paddingTop': '0rem'
              }}   
            >
                <MenuIcon/>
            </IconButton>
          }
          <ul>
            {!props.userId &&
                <li key='login'>
                    <NavLink to='/login' onClick={props.closeMenu}> Sign In </NavLink>
                    <NavLink to='/register' onClick={props.closeMenu}> Sign Up </NavLink>
                </li>
            }
            {props.userId &&
              <li key="sections">
                <NavLink to='/home' onClick={props.closeMenu}> Home </NavLink>
                <NavLink to="/matches" onClick={props.closeMenu}> Matches </NavLink>
                <NavLink to="/create-match" onClick={props.closeMenu}> Create Match </NavLink>
                <NavLink to="/logout" onClick={(e) => {onClickLogout(e); props.closeMenu();}}> Log Out </NavLink>
              </li>
            }
          </ul>
        </nav>
      </MyDiv>
  );
}