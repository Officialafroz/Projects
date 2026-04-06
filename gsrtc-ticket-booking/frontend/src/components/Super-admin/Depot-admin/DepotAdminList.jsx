import { useEffect, useState } from "react";
import { fetchDepotAdmins, deleteDepotAdmin, sendCredentials } from "../../../api/DepotAdminApi.jsx";
import DepotAdminTable from "./DepotAdminTable.jsx";
import SearchDepotAdmin from "./SearchDepotAdmin.jsx";

const DepotAdminList = () => {
  const [admins, setAdmins] = useState([]);
  const [page, setPage] = useState(0);

  const loadAdmins = () => {
    fetchDepotAdmins(page, 10).then((res) => {
      setAdmins(res.data.content);
    });
  };

  useEffect(() => {
    loadAdmins();
  }, [page]);

  const handleDelete = (id) => {
    deleteDepotAdmin(id).then(() => loadAdmins());
  };

  const handleSendMail = async (adminId, email, password) => {
    try {
      const res = await sendCredentials(adminId, email, password);
      alert(res.data);
    } catch (error) {
      alert(error?.response?.data || "Failed to send mail");
    }
  };

  return (
    <div className="list-container">
      <SearchDepotAdmin setAdmins={setAdmins} />
      <DepotAdminTable
        admins={admins}
        onDelete={handleDelete}
        onSendMail={handleSendMail}
      />

      <div className="pagination">
        <button onClick={() => setPage(page - 1)}>Prev</button>
        <button onClick={() => setPage(page + 1)}>Next</button>
      </div>

    </div>
  );
};

export default DepotAdminList;