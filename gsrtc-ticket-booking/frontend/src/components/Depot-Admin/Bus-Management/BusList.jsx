import { useContext, useEffect} from "react";
import axios from "axios";
import { AppContext } from "../../../store/AppContext";
import { deleteBus, fetchBusList } from "../../../api/BusManagementApi";

const BusList = () => {
  const { buses, setBuses, authUser } = useContext(AppContext);

  // Fetch buses from backend
  useEffect(() => {
    fetchBuses();
  }, []);

  const fetchBuses = async () => {
    try {
      const response = await fetchBusList(authUser.depotId);
      setBuses(response.data);
    } catch (error) {
      console.error("Error fetching bus data:", error);
    }
  };

  const handleRemove = async (busNumber) => {
    if (!window.confirm("Are you sure you want to remove this bus?")) return;

    try {
      await deleteBus(busNumber);
      setBuses((prevBuses) => prevBuses.filter((bus) => bus.busNumber !== busNumber));
      // if (onRemove) onRemove(busNumber);
    } catch (error) {
      console.error("Error deleting bus:", error);
      alert("Failed to delete bus. Please try again.");
    }
  }

  if (buses.length === 0) {
    return <p className="no-bus-msg">No buses added yet.</p>;
  }

  return (
    <div className="bus-list">
      {/* <h3>Added Buses</h3> */}
      <table>
        <thead>
          <tr>
            <th>Bus Number</th>
            <th>Type</th>
            <th>Total Seats</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {buses.map((bus, index) => (
            <tr key={index}>
              <td>{bus.busNumber}</td>
              <td>{bus.busType}</td>
              <td>{bus.totalSeats}</td>
              <td>
                <button className="remove-btn" onClick={() => handleRemove(bus.busNumber)}>
                  Remove
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default BusList;
