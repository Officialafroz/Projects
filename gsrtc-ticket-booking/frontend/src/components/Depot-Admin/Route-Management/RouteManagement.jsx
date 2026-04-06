import { useContext, useEffect, useState } from "react";
import RouteForm from "./RouteForm";
import RouteList from "./RouteList";
import RouteFilters from "./RouteFilters";
import "../../styles/RouteManagement.css";
import { AppContext } from "../../../store/AppContext";
import { addRoute } from "../../../api/RouteApi";

const RouteManagement = () => {
  const { routes, setRoutes } = useContext(AppContext);
  const [searchTerm, setSearchTerm] = useState("");
  const [filterClass, setFilterClass] = useState("");

  const addRouteFunc = async (route, duration) => {
    const res = await addRoute(route, duration);

    setRoutes([...routes, route]);
    alert(res.data);
  };

  const deleteRoute = (id) => {
    setRoutes(routes.filter((r) => r.id !== id));
  };

  const updateRoute = (updatedRoute) => {
    setRoutes(routes.map((r) => (r.id === updatedRoute.id ? updatedRoute : r)));
  };

  const filteredRoutes = routes.filter((r) => {
    const matchesSearch = r.routeName.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesClass = filterClass ? r.classType === filterClass : true;
    return matchesSearch && matchesClass;
  });

  return (
    <div className="route-management">
      <div className="route-header">
        <h1>Route Management System</h1>
        <RouteForm addRoute={addRouteFunc} />
      </div>
      <RouteFilters setSearchTerm={setSearchTerm} setFilterClass={setFilterClass} />
      <RouteList
        routes={filteredRoutes}
        setRoutes={setRoutes}
        deleteRoute={deleteRoute}
        updateRoute={updateRoute}
      />
    </div>
  );
};

export default RouteManagement;
