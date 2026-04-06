import { useEffect, useState } from "react";
import CreateDepot from "./CreateDepot"
import DepotTable from "./DepotTable";
import "../../../styles/Depot.css"
import { createDepot, getDepotList, getDepots, update } from "../../../../api/BusDepotApi";
import UpdateDepotModal from "./UpdateDepotModal";
import DepotSearch from "../DepotOverviewPage/DepotSearch";

const DepotManagementPage = () => {
  const [depots, setDepots] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [selectedDepot, setSelectedDepot] = useState(null);


  const fetchDepots = async () => {
    try {
      const res = await getDepotList(page, 9);
      setDepots(res.data.content);
      setTotalPages(res.data.totalPages);

    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchDepots();
  }, [page]);

  const handleDelete = (id) => {
    deleteDepot(id).then(() => fetchDepots());
  };

  const handleCreate = async (form) => {
    try {
      console.log(form);
      const res = await createDepot(form);
      alert(res.data);
      fetchDepots(); // ✅ refresh list after create
    } catch (error) {
      console.error(error);
    }
  };

  const handleUpdate = async (depotId, depotName, address, password) => {
    try {
      const res = await update(depotId, depotName, address, password);
      alert(res.data);
    } catch (error) {
      alert(error);
    }
  }

  return (
    <div className="depot-container">
      <CreateDepot onCreate={handleCreate} />
      <DepotSearch setDepot={setDepots} />
      <DepotTable
        depots={depots}
        setDepot={setSelectedDepot}
        onDelete={handleDelete}
        onUpdate={handleUpdate}
      />
      {selectedDepot && (
        <UpdateDepotModal
          depot={selectedDepot}
          onClose={() => setSelectedDepot(null)}
          onSubmit={handleUpdate}
        />
      )}
      <div className="pagination">
        <button
          disabled={page === 0}
          onClick={() => setPage(page - 1)}
        >
          Prev
        </button>

        <span>Page {page + 1}</span>

        <button
          disabled={page === totalPages - 1}
          onClick={() => setPage(page + 1)}
        >
          Next
        </button>
      </div>
    </div>
  )
}

export default DepotManagementPage