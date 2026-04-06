import axios from "axios";
import { useState } from "react";

const ScheduleForm = ({ routes, buses, onAddSchedule }) => {
  const [form, setForm] = useState({
    routeId: "",
    routeName: "",
    busId: "",
    busName: "",
    date: "",
    departure: "",
    status: "Active",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(scheduleData);

    if (!form.routeId || !form.busId || !form.date || !form.departure) {
      alert("Please fill all fields");
      return;
    }
    console.log(scheduleData);


    const selectedRoute = routes.find((r) => r.id === parseInt(form.routeId));
    const selectedBus = buses.find((b) => b.id === parseInt(form.busId));

    const scheduleData = {
      routeId: selectedRoute.routeId,
      busNumber: selectedBus.busNumber, // required for backend method
      date: form.date,
      departureTime: form.departureTime,
      destinationTime: form.destinationTime, // optional, or you can add another input
      status: form.status,
    };

    try {
      await axios.post("/api/depot/trip", scheduleData);
      alert("Trip added successfully!");
      console.log(scheduleData);

      onAddSchedule({
        ...form,
        routeName: `${selectedRoute.from} - ${selectedRoute.to}`,
        busName: `${selectedBus.busName} (${selectedBus.busNumber})`,
      });

      // Reset form
      setForm({
        routeId: "",
        routeName: "",
        busId: "",
        busName: "",
        date: "",
        departure: "",
        status: "Active",
      });
    } catch (error) {
      console.error("Error adding trip:", error);
      alert("Failed to add trip. Please try again.");
    }
  };

  return (
    <form className="schedule-form" onSubmit={handleSubmit}>
      <select name="routeId" value={form.routeId} onChange={handleChange}>
        <option value="">Select Route</option>
        {routes.map((route) => (
          <option key={route.id} value={route.id}>
            {route.from} → {route.to} ({route.classType})
          </option>
        ))}
      </select>

      <select name="busId" value={form.busId} onChange={handleChange}>
        <option value="">Select Bus</option>
        {buses.map((bus) => (
          <option key={bus.id} value={bus.id}>
            {bus.busName} ({bus.busNumber})
          </option>
        ))}
      </select>

      <input name="date" type="date" value={form.date} onChange={handleChange} />
      <input name="departure" type="time" value={form.departure} onChange={handleChange} />

      <select name="status" value={form.status} onChange={handleChange}>
        <option>Active</option>
        <option>Inactive</option>
      </select>

      <button type="submit">Add Schedule</button>
    </form>
  );
};

export default ScheduleForm;
