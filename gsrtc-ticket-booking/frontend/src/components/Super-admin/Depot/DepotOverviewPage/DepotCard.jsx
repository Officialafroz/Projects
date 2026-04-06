import "../../../styles/Depot.css";
// import UpdateDepot from "../DepotManagementPage/UpdateDepot.jsx";

const DepotCard = ({ depots }) => {
  return (
    <table className="depot-table">
      <thead>
        <tr>
          <th>Depot</th>
          <th>Address</th>
          <th>Total Buses</th>
          <th>Total Staff</th>
          <th>Bookings</th>
          <th>Revenue</th>
          {/* <th>Actions</th> */}
        </tr>
      </thead>

      <tbody>
        {depots.map((depot, idx) => (
          <tr key={idx}>
            <td>{depot.depotName}</td>
            <td>{depot.address}</td>
            <td>{depot.totalBuses}</td>
            <td>{depot.totalStaff}</td>
            <td>{depot.totalBookings}</td>
            <td>₹{depot.revenue}</td>

            {/* <td>
              <UpdateDepot depot={depot} />
              <button onClick={() => onDelete(depot.depotId)}>
                Delete
              </button>
            </td> */}
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default DepotCard;