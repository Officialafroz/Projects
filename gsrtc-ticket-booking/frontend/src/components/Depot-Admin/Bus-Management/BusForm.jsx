import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AppContext } from "../../../store/AppContext";
import { addNewBus } from "../../../api/BusManagementApi";

const BusForm = ({ template, onAddBus }) => {
  const { authUser } = useContext(AppContext);
  const [busNumber, setBusNumber] = useState("");
  const [seatLayout, setSeatLayout] = useState("");
  const [depots, setDepots] = useState([]);
  const [selectedDepot, setSelectedDepot] = useState("");

  // useEffect(() => {
  //   // Fetch depots for dropdown
  //   axios.get("/api/busDepot/depots")
  //     .then(res => setDepots(res.data))
  //     .catch(err => console.error("Error fetching depots:", err));
  // }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!busNumber || !seatLayout) {
      alert("Please fill all fields");
      return;
    }

    const newBus = {
      busNumber,
      busType: template.name,
      seatLayout,
      depotId: authUser.depotId
      // : parseInt(seatLayout)
      // depotId: parseInt(selectedDepot),
    };

    try {
      const res = await addNewBus(newBus);
      alert(res.data);

      onAddBus({
        ...newBus,
        totalSeats: seatLayout === "1" ? 52 : seatLayout === "2" ? 45 : 46,
      });

      setBusNumber("");
      setSeatLayout("");
      // setSelectedDepot("");
    } catch (error) {
      console.error("Error adding bus:", error);
      alert("Failed to add bus");
    }
  };

  return (
    <form className="bus-form" onSubmit={handleSubmit}>
      {/* <label>Bus Number (as per RTO):</label> */}
      <input
        type="text"
        placeholder="GJ05AB1234"
        value={busNumber}
        onChange={(e) => setBusNumber(e.target.value)}
      />

      {/* <label>Seat Layout:</label> */}
      <select value={seatLayout} onChange={(e) => setSeatLayout(e.target.value)}>
        <option value="">Select seat layout</option>
        <option value="1">52</option>
        <option value="2">45</option>
        <option value="3">46</option>
      </select>

      <button type="submit">Add Bus</button>
    </form>
  );
};

export default BusForm;
