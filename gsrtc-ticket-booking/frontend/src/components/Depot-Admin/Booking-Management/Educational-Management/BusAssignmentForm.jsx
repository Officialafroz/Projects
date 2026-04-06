import { useState } from "react";
import { busAssignment } from "../../../../api/EduManagementApi.jsx";

const BusAssignmentForm = ({ bookingId }) => {

  const [busNumber,setBusNumber]=useState("");
  const [driverName,setDriverName]=useState("");
  const [conductorName,setConductorName]=useState("");

  const assignBus = async () => {

    const res = await busAssignment(bookingId, busNumber, driverName, conductorName);
    alert(res.data);
  };

  return (
    <div className="panel-card">
      <h3>Assign Bus & Staff</h3>

      <input
        placeholder="Bus Number"
        value={busNumber}
        onChange={(e)=>setBusNumber(e.target.value)}
      />

      <input
        placeholder="Driver Name"
        value={driverName}
        onChange={(e)=>setDriverName(e.target.value)}
      />

      <input
        placeholder="Conductor Name"
        value={conductorName}
        onChange={(e)=>setConductorName(e.target.value)}
      />

      <button onClick={assignBus}>
        Assign
      </button>
    </div>
  );
};

export default BusAssignmentForm;