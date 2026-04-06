import { useContext, useEffect, useState } from "react";
import axios from "axios";
import { AppContext } from "../../../store/AppContext";
import "../../styles/ScheduleManagement.css";

const ScheduleManagement = () => {
  const { routes, buses, schedules, setSchedules, authUser } = useContext(AppContext);
  const [selectedRoute, setSelectedRoute] = useState("");
  const [selectedBus, setSelectedBus] = useState("");
  const [date, setDate] = useState("");
  const [departureTime, setDepartureTime] = useState("");
  const [destinationTime, setDestinationTime] = useState("");
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchSchedules();
  }, [])

  const fetchSchedules = async () => {
    const res = await axios.get(`/api/depot/trip/list/${authUser.depotId}`);
    console.log(res.data);
    setSchedules(res.data);
  }

  // ✅ Add or update schedule
  const handleAddOrUpdate = async () => {
    if (!selectedRoute || !selectedBus || !date || !departureTime || !destinationTime)
      return alert("Please fill all fields.");

    // Extract routeId and bus details
    const route = routes.find((r) => r.routeName === selectedRoute);
    const bus = buses.find((b) => b.busNumber === selectedBus);

    if (!route || !bus) {
      alert("Invalid route or bus selected.");
      return;
    }

    const scheduleData = {
      routeId: route.routeId,
      busNumber: bus.busNumber,
      date,
      departureTime,
      destinationTime,
      status: "Active",
      depotId: authUser.depotId
    };

    try {
      if (editingId) {
        // 🔹 Update schedule locally (optional backend PUT)
        setSchedules((prev) =>
          prev.map((s) =>
            s.id === editingId
              ? { ...s, route: selectedRoute, bus: selectedBus, date, departureTime, destinationTime }
              : s
          )
        );
        setEditingId(null);
      } else {
        // 🔹 Save new schedule to backend
        await axios.post("/api/depot/trip", scheduleData);
        console.log(scheduleData);

        const newSchedule = {
          id: Date.now(),
          route: selectedRoute,
          bus: selectedBus,
          date,
          departureTime,
          destinationTime,
          status: "Active"
          // depotId: authUser.depotId
        };

        setSchedules([...schedules, newSchedule]);
        alert("Schedule added successfully!");
      }
    } catch (err) {
      console.error("Error saving schedule:", err);
      alert("Failed to save schedule. Check console for details.");
    }

    // Reset form fields
    setSelectedRoute("");
    setSelectedBus("");
    setDate("");
    setDepartureTime("");
    setDestinationTime("");
  };

  // ✅ Edit schedule locally
  const handleEdit = (schedule) => {
    setSelectedRoute(schedule.route);
    setSelectedBus(schedule.bus);
    setDate(schedule.date);
    setDepartureTime(schedule.departureTime);
    setDestinationTime(schedule.destinationTime);
    setEditingId(schedule.id);
  };

  // ✅ Cancel schedule (optional backend PATCH)
  const handleCancel = async (id) => {
    const schedule = schedules.find((s) => s.id === id);
    if (!schedule) return;

    try {
      // Optionally call backend to mark trip inactive
      // Backend API doesn't exist
      // await axios.patch(`/api/busTrip/status/${schedule.tripCode}`, { status: "Inactive" });

      setSchedules((prev) =>
        prev.map((s) => (s.id === id ? { ...s, status: "Inactive" } : s))
      );
    } catch (err) {
      console.error("Error canceling schedule:", err);
      alert("Failed to cancel schedule");
    }
  };

  // ✅ Delete schedule (optional backend DELETE)
  const handleDelete = async (id) => {
    if (confirm("Are you sure you want to delete this schedule?")) {
      const schedule = schedules.find((s) => s.id === id);
      try {
        // Backend API doesn't exist
        // await axios.delete(`/api/busTrip/delete/${schedule.tripCode}`);
        setSchedules(schedules.filter((s) => s.id !== id));
      } catch (err) {
        console.error("Error deleting schedule:", err);
        alert("Failed to delete schedule");
      }
    }
  };

  return (
    <div className="schedule-management">
      <h1>Schedule Management</h1>

      <div className="schedule-form">
        <div className="form-group">
          <label>Select route</label>
          <select value={selectedRoute} onChange={(e) => setSelectedRoute(e.target.value)}>
            <option value="">Select Route</option>
            {routes.map((r, index) => (
              <option key={index} value={r.routeName}>
                {r.routeName + " (" + r.classType + ")"}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Select Bus</label>
          <select value={selectedBus} onChange={(e) => setSelectedBus(e.target.value)}>
            <option value="">Select Bus</option>
            {buses.map((bus, index) => (
              <option key={index} value={bus.busNumber}>
                {bus.busNumber}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Journey date</label>
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
        </div>

        <div className="form-group">
          <label>Departure Time</label>
          <input
            type="datetime-local"
            className="time-input"
            value={departureTime}
            onChange={(e) => setDepartureTime(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Destination Time</label>
          <input
            type="datetime-local"
            className="time-input"
            value={destinationTime}
            onChange={(e) => setDestinationTime(e.target.value)}
          />
        </div>

        <div className="form-group">
          <button onClick={handleAddOrUpdate}>
            {editingId ? "Update Schedule" : "Add Schedule"}
          </button>
        </div>
      </div>

      <table className="schedule-table">
        <thead>
          <tr>
            <th>Route</th>
            <th>Bus</th>
            <th>Date</th>
            <th>Departure</th>
            <th>Destination</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {schedules.map((s, index) => (
            <tr key={index} className={s.status === "Inactive" ? "inactive" : ""}>
              <td>{s.routeName}</td>
              <td>{s.busNumber}</td>
              <td>{s.date}</td>
              <td>{s.departureTime}</td>
              <td>{s.destinationTime}</td>
              <td className={`status ${s.status.toLowerCase()}`}>{s.status}</td>
              <td className="actions">
                <button onClick={() => handleEdit(s)} className="btn btn-primary">Edit</button>
                <button onClick={() => handleCancel(s.id)} className="btn btn-primary">Cancel</button>
                <button onClick={() => handleDelete(s.id)} className="btn btn-primary">Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ScheduleManagement;
