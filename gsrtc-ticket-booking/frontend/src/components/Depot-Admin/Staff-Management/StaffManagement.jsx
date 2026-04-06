import { useEffect, useState } from "react";
import StaffFilters from "./StaffFilters.jsx";
import StaffTable from "./StaffTable.jsx";
import { fetchStaff } from "../../../api/StaffApi.jsx";
import "../../styles/StaffManagement.css";

const StaffManagement = () => {
  const depotId = 1; // from auth/context later

  const [filters, setFilters] = useState({
    search: "",
    role: "",
    status: ""
  });

  const [staffList, setStaffList] = useState([]);
  const [loading, setLoading] = useState(false);

  // Load all staff initially
  useEffect(() => {
    loadStaff();
  }, []);

  const loadStaff = async () => {
    try {
      setLoading(true);
      const res = await fetchStaff({ depotId, ...filters });
      console.log(res.data);
      setStaffList(res.data);
    } catch (err) {
      console.error(err);
      setStaffList([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="staff-management">
      <h2>Staff Management</h2>

      <StaffFilters
        filters={filters}
        setFilters={setFilters}
        onSearch={loadStaff}
      />

      {loading ? <p className="loading">Loading...</p> : (
        <StaffTable staffList={staffList} />
      )}
    </div>
  );
};

export default StaffManagement;
