import * as React from 'react';
import { getUsers } from "../../services/users";
import { useLoaderData } from "react-router-dom";

export async function loader() {
  const users = await getUsers();
  return { users };
}

export default function Users() {
  const { users } = useLoaderData();

  return (
    <div>{users}</div>
  );
}