import { useContext } from "react";
import "../../styles/Dashboard.css";
import { AppContext } from "../../../store/AppContext";

const DashboardHeader = () => {
  const {authUser} = useContext(AppContext)

  return (
    <div className="dashboard-header">
      <h1>Dashboard</h1>
      <p>Welcome back, {authUser.name.toUpperCase()}</p>
    </div>
  );
};

export default DashboardHeader;
