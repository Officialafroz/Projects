import "../../../styles/Depot.css";
import UpdateDepot from "./UpdateDepotModal";

const DepotTable = ({ depots, setDepot, onDelete, onUpdate }) => {

  return (
    <table className="depot-table">
      <thead>
        <tr>
          <th>Depot</th>
          <th>Address</th>
          <th>City</th>
          <th>Email</th>
          <th>Actions</th>

        </tr>
      </thead>

      <tbody>
        {depots.map((depot, idx) => (
          <tr key={idx}>
            <td>{depot.depotName}</td>
            <td>{depot.address}</td>
            <td>{depot.city}</td>
            <td>{depot.email}</td>
            <td>
              <button onClick={() => setDepot(depot)}>Update</button>
              <button onClick={() => onDelete(depot.depotId)}>
                Delete
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default DepotTable;