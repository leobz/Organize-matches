import * as React from 'react';
import { getLastMetrics } from "../../services/last-metrics";
import { useLoaderData } from "react-router-dom";

export async function loader() {
  const metrics = await getLastMetrics();
  return { metrics };
}

export default function Metrics() {
  const { metrics } = useLoaderData();

  return (
    <div>{metrics}</div>
  );
}
