import { useEffect, useState } from "react";
import axios from "axios";
import StopForm from "./StopForm";
import StopList from "./StopList";

const StopManagement = ({ route, updateRoute }) => {
  const [stops, setStops] = useState([]);

  // Fetch all stops for the selected route
  useEffect(() => {
    if (!route.routeId) return;
    axios.get(`/api/depot/route-stops/${route.routeId}`)
      .then((res) => setStops(res.data))
      .catch((err) => console.error("Error fetching stops:", err));
  }, [route.routeId]);

  // Add stop via API
  const addStop = async (stop) => {
    try {
      const res = await axios.post(`/api/depot/route-stops`, stop);
      setStops((prev) => [...prev, stop]);
      updateRoute({ ...route, stops: [...stops, res.data] });
    } catch (err) {
      console.error("Error adding stop:", err);
      alert("Failed to add stop");
    }
  };

  // Delete stop via API
  const deleteStop = async (stopName) => {
    if (!window.confirm("Are you sure you want to remove this stop?")) return;

    try {
      await axios.delete(`/api/depot/route-stops/${stopName}`);
      const updatedStops = stops.filter((stop) => stop.stopName !== stopName);
      setStops(updatedStops);
      updateRoute({ ...route, stops: updatedStops });
    } catch (err) {
      console.error("Error deleting stop:", err);
      alert("Failed to delete stop");
    }
  };

  return (
    <div className="stop-management">
      {/* <h4>Stops for {}</h4> */}
      <StopForm addStop={addStop} routeId={route.routeId} />
      <StopList stops={stops} setStops={setStops} routeId={route.routeId} deleteStop={deleteStop} />
    </div>
  );
};

export default StopManagement;
