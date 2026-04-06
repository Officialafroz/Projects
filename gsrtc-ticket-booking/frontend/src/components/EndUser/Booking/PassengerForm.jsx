import { useContext, useState } from "react";
import "../styles/PassengerForm.css";
import axios from "axios";
import { AppContext } from "../../store/AppContext";

const PassengerForm = ({
  stops,
  selectedSeats,
  total,
  setTotal,
}) => {
  const { passengers, setPassengers, busSearch, tripCode } = useContext(AppContext);

  const [form, setForm] = useState({
    fullName: "",
    age: "",
    gender: "",
    boardingPoint: "",
  });

  const handleChange = (field, value) => {
    setForm({ ...form, [field]: value });
  };

  const calculateFare = async (boardingPoint) => {
    const res = await axios.get("/api/end-user/passenger/individual-fare", {
      params: {
        boardingPoint,
        destination: busSearch.destination,
        tripCode,
      },
    });

    return res.data;
  };

  const handleAddPassenger = async () => {
    if (!form.fullName || !form.age || !form.gender || !form.boardingPoint) {
      alert("Please complete all fields");
      return;
    }

    const seat = selectedSeats[passengers.length]; // seat for this passenger
    if (!seat) {
      alert("No more selected seats available");
      return;
    }
    console.log(seat);

    // Calculate fare
    const fare = await calculateFare(form.boardingPoint);

    const passenger = {
      seat,
      fullName: form.fullName,
      age: form.age,
      gender: form.gender,
      boardingPoint: form.boardingPoint,
      fare,
      destination: busSearch.destination,
      tripCode,
    };

    setPassengers((prev) => [...prev, passenger]);

    // Update total price
    setTotal((prev) => prev + fare);

    // Reset form fields
    setForm({
      fullName: "",
      age: "",
      gender: "",
      boardingPoint: "",
    });
  };

  const availableStops = stops.filter(
    (s) => s.stopName !== busSearch.destination
  );

  return (
    <div className="passenger-form">
      <h3>Fill Details</h3>

      <label>Passenger for Seat {selectedSeats[passengers.length]}</label>

      <input
        type="text"
        placeholder="Full Name"
        value={form.fullName}
        onChange={(e) => handleChange("fullName", e.target.value)}
      />

      <input
        type="number"
        placeholder="Age"
        value={form.age}
        onChange={(e) => handleChange("age", e.target.value)}
      />

      <select
        value={form.gender}
        onChange={(e) => handleChange("gender", e.target.value)}
      >
        <option value="">Gender</option>
        <option>Male</option>
        <option>Female</option>
      </select>

      <select
        value={form.boardingPoint}
        onChange={(e) => handleChange("boardingPoint", e.target.value)}
      >
        <option value="">Select Boarding Point</option>
        {availableStops.map((stop, idx) => (
          <option key={idx} value={stop.stopName}>
            {stop.stopName}
          </option>
        ))}
      </select>

      <button className="btn btn-primary" onClick={handleAddPassenger}>
        Add Passenger
      </button>
    </div>
  );
};

export default PassengerForm;
