import { useEffect, useState } from "react";
import { createDepot, deleteDepot, getDepots } from "../../../../api/BusDepotApi.jsx";
import DepotCard from "./DepotCard.jsx";
import DepotSearch from "./DepotSearch.jsx";
import "../../../styles/Depot.css";

const DepotListPage = () => {
  const [depots, setDepots] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchDepots = async () => {
    try {

      const res = await getDepots(page, 10);

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

  return (

    <div className="depot-container">

      {/* <CreateDepot onCreate={handleCreate} /> */}

      <DepotSearch setDepot={setDepots} />

      <DepotCard depots={depots} onDelete={handleDelete} />

      {/* <div className="depot-grid">
        {depots.map((depot, index) => (
          <DepotCard key={index} depot={depot} />
        ))}
      </div> */}

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
  );
};

export default DepotListPage;