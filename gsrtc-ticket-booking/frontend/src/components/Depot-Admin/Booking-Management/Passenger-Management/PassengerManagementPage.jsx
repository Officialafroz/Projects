import { useContext, useEffect, useState } from "react";
import PassengerFilters from "./PassengerFilters.jsx";
import PassengerTable from "./PassengerTable.jsx";
import { loadPassengerList } from "../../../../api/PassengerApi.jsx";
import { AppContext } from "../../../../store/AppContext.jsx";
import "../../../styles/PassengerManagement.css";

const PassengerManagementPage = () => {
  const { authUser } = useContext(AppContext);
  const [passengers, setPassengers] = useState([]);

  const loadPassengers = async () => {
    const res = await loadPassengerList(authUser.depotId);

    setPassengers(res.data);
    console.log(res.data);
  };

  useEffect(() => {
    loadPassengers();
  }, []);

  return (
    <div className="passenger-management">

      <h2>Passenger Management</h2>

      <PassengerFilters setPassengers={setPassengers} />

      <PassengerTable passengers={passengers} />

    </div>
  );
};

export default PassengerManagementPage;