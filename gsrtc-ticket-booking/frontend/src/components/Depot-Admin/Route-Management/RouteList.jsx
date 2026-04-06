import { useContext, useEffect } from "react";
import RouteItem from "./RouteItem";
import { AppContext } from "../../../store/AppContext";
import { fetchRouteList } from "../../../api/RouteApi";

const RouteList = ({ routes, setRoutes, deleteRoute, updateRoute }) => {
  const { authUser } = useContext(AppContext);

  useEffect(() => {
    fetchRouteList(authUser.depotId)
      .then(response => {
        setRoutes(response.data)
      })
      .catch(err => {
        console.log(err);
      })
  }, []);

  if (routes.length === 0) return <p className="route-filter-msg">No routes.</p>;



  return (
    <div className="route-list">
      {routes.map((route, index) => (
        <RouteItem
          key={index}
          route={route}
          deleteRoute={deleteRoute}
          updateRoute={updateRoute}
        />
      ))}
    </div>
  );
};

export default RouteList;
