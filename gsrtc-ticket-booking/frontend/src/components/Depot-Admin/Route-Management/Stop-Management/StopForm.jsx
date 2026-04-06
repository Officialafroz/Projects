import { useContext, useRef } from "react";
import { AppContext } from "../../../../store/AppContext.jsx";
import axios from "axios";

const StopForm = ({ addStop, routeId }) => {
  const nameRef = useRef();
  const stopOrderRef = useRef();
  const distanceRef = useRef();
  const fareRef = useRef();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const stopName = nameRef.current.value.trim();
    const stopOrder = stopOrderRef.current.value.trim();
    const distanceFromStart = distanceRef.current.value.trim();
    const fare = fareRef.current.value.trim();

    if (!stopName || !stopOrder || !distanceFromStart || !fare)
      return alert("Please enter stop name, stop order, time, and fare.");

    console.log(routeId);

    const newStop = {
      stopName,
      stopOrder,
      distanceFromStart,
      fare,
      routeId
    };
    console.log(newStop);

    addStop(newStop);

    // clear fields
    nameRef.current.value = "";
    stopOrderRef.current.value = "";
    distanceRef.current.value = "";
    fareRef.current.value = "";
  };

  return (
    <form className="stop-form" onSubmit={handleSubmit}>
      <input type="text" placeholder="Stop name" ref={nameRef} />
      <input type="number" placeholder="Stop order" ref={stopOrderRef} />
      <input type="number" placeholder="Distance from start" ref={distanceRef} />
      <input type="number" placeholder="Fare" ref={fareRef} />
      <button type="submit">Add Stop</button>
    </form>
  );
};

export default StopForm;
