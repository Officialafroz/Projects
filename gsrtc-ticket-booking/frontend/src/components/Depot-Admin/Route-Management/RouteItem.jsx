import { useState } from "react";
import StopManagement from "./Stop-Management/StopManagement";

const RouteItem = ({ route, deleteRoute, updateRoute }) => {
  const [showStops, setShowStops] = useState(false);
  const convertMinutesToHours = (totalMinutes) => {
    if (!totalMinutes || isNaN(totalMinutes)) return "";

    const hours = Math.floor(totalMinutes / 60);
    const minutes = totalMinutes % 60;

    return `${hours}h ${minutes}m`;
  };

  const duration = convertMinutesToHours(route.duration);


  console.log(route);

  return (
    <div className="route-item">
      <div className="route-info">
        <div className="route-info-wrapper">
          <p>{route.routeName}</p>
          <p>{route.classType}</p>
          <p>{`${route.distance} Km`}</p>
          <p>{duration}</p>
          <div className="route-actions">
            <button onClick={() => setShowStops(!showStops)}>
              {showStops ? "Hide Stops" : "Manage Stops"}
            </button>
            <button onClick={() => deleteRoute(route.routeId)}>Delete</button>
          </div>
        </div>

      </div>

      {showStops && (
        <StopManagement route={route} updateRoute={updateRoute} />
      )}
    </div>
  );
};

export default RouteItem;
