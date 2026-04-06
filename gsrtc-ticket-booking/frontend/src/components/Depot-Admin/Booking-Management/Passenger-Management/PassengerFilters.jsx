import { useState } from "react";
import {
  filterByPnrOrPassengerReference,
  filterByBusNumberOrTripCodeOrTotalSeats,
  filterByRouteAndBusTypeAndRevenue
} from "../../../../api/PassengerApi";

const PassengerFilters = ({ setPassengers }) => {

  const [pnrOrRef, setPnrOrRef] = useState("");
  const [busOrTrip, setBusOrTrip] = useState("");
  const [totalSeats, setTotalSeats] = useState("");

  const [route, setRoute] = useState("");
  const [classType, setClassType] = useState("");
  const [revenue, setRevenue] = useState("");

  /* PNR OR Passenger Ref */
  const searchByPnrOrRef = async () => {

    if (!pnrOrRef) {
      alert("Enter PNR or Passenger Reference");
      return;
    }

    const res = await filterByPnrOrPassengerReference(
      pnrOrRef,
      pnrOrRef
    );

    setPassengers(res.data);
  };

  /* Bus Number OR Trip Code */
  const searchByBusOrTrip = async () => {

    if (!busOrTrip && !totalSeats) {
      alert("Enter Bus Number / Trip Code or Total Seats");
      return;
    }

    const res = await filterByBusNumberOrTripCodeOrTotalSeats(
      busOrTrip,
      busOrTrip,
      totalSeats
    );

    setPassengers(res.data);
  };

  /* Route + Class Type */
  const searchByRoute = async () => {

    if (!route || !classType) {
      alert("Route and Bus Class are required");
      return;
    }

    const res = await filterByRouteAndBusTypeAndRevenue(
      route,
      classType,
      revenue
    );

    setPassengers(res.data);
  };

  return (
    <div className="filters">

      {/* PNR / PASS REF */}
      <div className="filter-group">
        <input
          placeholder="PNR or Passenger Ref"
          value={pnrOrRef}
          onChange={(e)=>setPnrOrRef(e.target.value)}
        />

        <button onClick={searchByPnrOrRef}>Search</button>
      </div>

      {/* BUS / TRIP */}
      {/* <div className="filter-group">
        <input
          placeholder="Bus Number or Trip Code"
          value={busOrTrip}
          onChange={(e)=>setBusOrTrip(e.target.value)}
        />

        <input
          placeholder="Total Seats"
          value={totalSeats}
          onChange={(e)=>setTotalSeats(e.target.value)}
        />

        <button onClick={searchByBusOrTrip}>Search</button>
      </div> */}

      {/* ROUTE */}
      {/* <div className="filter-group">
        <input
          placeholder="Route"
          value={route}
          onChange={(e)=>setRoute(e.target.value)}
        />

        <input
          placeholder="Bus Class"
          value={classType}
          onChange={(e)=>setClassType(e.target.value)}
        />

        <input
          placeholder="Revenue"
          value={revenue}
          onChange={(e)=>setRevenue(e.target.value)}
        />

        <button onClick={searchByRoute}>Search</button>
      </div> */}

    </div>
  );
};

export default PassengerFilters;