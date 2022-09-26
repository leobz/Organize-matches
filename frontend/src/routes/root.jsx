import { Outlet, NavLink, useNavigation } from 'react-router-dom';

export default function Root() {
  const navigation = useNavigation();

  return (
    <>
      <div id="sidebar">
        <h1>React Router Contacts</h1>
        <nav>
          <ul>
            <li key='login'>
              <NavLink to='login'> Login </NavLink>
              <NavLink to='register'> Sign Up </NavLink>
              <NavLink to='users'> Users </NavLink>
              <NavLink to='create-match'> Create Match </NavLink>
            </li>
              <li key="sections">
                  <NavLink to="matches">Matches</NavLink>
              </li>
          </ul>
        </nav>
      </div>
      <div 
        id="detail"
        className={
          navigation.state === "loading" ? "loading" : ""
        }
      >
        <Outlet/>
      </div>
    </>
  );
}