import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {
  createBrowserRouter,
  RouterProvider
} from "react-router-dom";

import ErrorPage from './error-page'

import Root from './routes/root'

import Index from './routes/index';

import SignUp, {
  action as signUpAction
} from './routes/signUp/SignUp';

import Users, { 
  loader as usersLoader
} from './routes/users/users';

import GetMatch, {
  loader as matchLoader,
  action as patchMatchAction
} from './routes/matches/GetMatch';

import CreateMatch, {
  action as matchAction
} from './routes/matches/CreateMatch';

import Metrics, {
  loader as metricsLoader
} from './routes/home/home';

import SignIn from './routes/signIn/SignIn.jsx';
import MatchList, {loader as getMatches} from "./routes/matches/MatchList";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root/>,
    errorElement: <ErrorPage/>,
    children: [
      {
        errorElement: <ErrorPage/>,
        children: [
          {
            path: "login",
            element: <SignIn/>
          },
          {
            path: "users",
            element: <Users/>,
            loader: usersLoader
          },
          {
            path: "matches/:matchId",
            element: <GetMatch/>,
            loader: matchLoader,
            action: patchMatchAction
          },
          {
            path: "create-match",
            element: <CreateMatch/>,
            action: matchAction,
          },
          {
            path: "register",
            element: <SignUp/>,
            action: signUpAction,
          },
          {
            path: "matches",
            element: <MatchList />,
            loader: getMatches
          },
          {
            path: "home",
            element: <Metrics/>,
            loader: metricsLoader,
          },  
          {
            index: true,
            element: <Index/>,
          },
        ]
      }
    ],
  },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
)
