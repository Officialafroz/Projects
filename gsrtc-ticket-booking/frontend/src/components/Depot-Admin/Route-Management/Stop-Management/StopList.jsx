import axios from "axios";
import { useEffect } from "react";

const StopList = ({ stops, setStops, deleteStop, routeId }) => {
  useEffect(() => {
    if (!routeId) return;
    axios.get(`/api/depot/route-stops/${routeId}`)
      .then((res) => setStops(res.data))
      .catch((err) => console.error("Error fetching stops:", err));
  }, [routeId]);

  if (stops.length === 0) return <p>No stops added yet.</p>;

  const convertMinutesToHours = (totalMinutes) => {
    if (!totalMinutes || isNaN(totalMinutes)) return "";

    const hours = Math.floor(totalMinutes / 60);
    const minutes = totalMinutes % 60;

    return `${hours}h ${minutes}m`;
  };

  return (
    <div className="stop-list-container">
      <h4>Stops List</h4>
      <table className="stop-table">
        <thead>
          <tr>
            <th>No</th>
            <th>Stop Name</th>
            <th>Order</th>
            <th>Distance (from start)</th>
            <th>Duration</th>
            <th>Fare (₹)</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {stops.map((stop, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{stop.stopName}</td>
              <td>{stop.stopOrder}</td>
              <td>{`${stop.distanceFromStart} km`}</td>
              <td>{convertMinutesToHours(stop.duration)}</td>
              <td>{`₹${stop.fare}`}</td>
              <td>
                <button className="remove-btn" onClick={() => deleteStop(stop.stopName)}>
                  Remove
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default StopList;
