import DepotAdminList from "./DepotAdminList.jsx";
import "../../styles/DepotAdmin.css";
import InviteCard from "./InviteCard.jsx";

const DepotAdminPage = () => {
  return (
    <div className="da-container">
      {/* <h2>Depot Admin Management</h2> */}
      <InviteCard />
      <DepotAdminList />
    </div>
  );
};

export default DepotAdminPage;