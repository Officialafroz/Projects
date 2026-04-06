import { useContext, useState } from "react";
import { AppContext } from "../../../store/AppContext";

const RouteForm = ({ addRoute }) => {
  const { authUser } = useContext(AppContext);
  const [routeName, setRouteName] = useState("");
  const [startingPoint, setStartingPoint] = useState("");
  const [endingPoint, setEndingPoint] = useState("");
  const [classType, setClassType] = useState("");
  const [distance, setDistance] = useState();
  const [duration, setDuration] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    const newRouteName = `${startingPoint} to ${endingPoint}`;
    setRouteName(newRouteName);

    if (!newRouteName || !startingPoint || !endingPoint || !duration || !distance) {
      alert("Please fill all fields");
      return;
    }

    const newRoute = {
      routeName: newRouteName,
      startingPoint,
      endingPoint,
      classType,
      distance, 
      depotId: authUser.depotId
    };
    console.log(newRoute, duration);

    addRoute(newRoute, duration);

    setRouteName("");
    setStartingPoint("");
    setEndingPoint("");
    setClassType("");
  };


  return (
    <form className="route-form" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="source"
        value={startingPoint}
        onChange={(e) => setStartingPoint(e.target.value)}
      />
      <input
        type="text"
        placeholder="endingPoint"
        value={endingPoint}
        onChange={(e) => setEndingPoint(e.target.value)}
      />
      <input
        type="number"
        placeholder="Distance (e.g. 265 km)"
        value={distance}
        onChange={(e) => setDistance(e.target.value)}
      />
      <input
        type="text"
        placeholder="Duration (e.g. 5h 30m)"
        value={duration}
        onChange={(e) => setDuration(e.target.value)}
      />
      <select
        value={classType}
        onChange={(e) => setClassType(e.target.value)}
      >
        <option value="">Select Class Type</option>
        <option value="Express">Express</option>
        <option value="Premium">Premium</option>
        <option value="Sleeper">Sleeper</option>
        <option value="AC">AC</option>
      </select>
      <button type="submit">Add Route</button>
    </form>
  );
};

export default RouteForm;
