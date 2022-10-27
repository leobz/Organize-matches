import { NavLink } from 'react-router-dom';
import {useNavigate} from 'react-router-dom';
import { logout } from "../services/login";
import MenuIcon from '@mui/icons-material/Menu';
import IconButton from '@mui/material/IconButton';

export default function Sidebar(props) {
  const navigate = useNavigate();

  const onClickLogout = (e) => {
    e.preventDefault()
    localStorage.clear()
    props.setUserId(undefined)
    logout().then((response) => {
        if (response.status >= 400){
            console.log("Error on logout")
        } else {
            navigate('/login');
        }
    })
  }

  return (
      <div id="sidebar">
        <h1>Organize Matches</h1>
        <nav>
          { (props.responsive) &&
            <IconButton 
              color="primary" aria-label="menu" component="label" onClick={props.closeMenu}
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
      </div>
  );
}