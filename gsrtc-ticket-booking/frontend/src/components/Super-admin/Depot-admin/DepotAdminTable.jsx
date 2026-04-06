import { useState } from "react";
import MailCredentialsModal from "./MailCredentialsModal.jsx";

const DepotAdminTable = ({ admins, onDelete, onSendMail }) => {
  const [selectedAdmin, setSelectedAdmin] = useState(null);

  return (
    <>
      <table className="admin-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Depot</th>
            <th>Address</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {admins.map((admin, idx) => (
            <tr key={idx}>
              <td>{admin.name}</td>
              <td>{admin.email}</td>
              <td>{admin.phoneNumber}</td>
              <td>{admin.depotName}</td>
              <td>{admin.address}</td>
              <td>{admin.accountStatus}</td>
              <td>

                {/* ✅ Show only when PENDING */}
                {admin.accountStatus === "PENDING" && (
                  <button onClick={() => setSelectedAdmin(admin)}>
                    Mail
                  </button>
                )}

                <button onClick={() => onDelete(admin.id)}>
                  Delete
                </button>

              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* ✅ Modal */}
      {selectedAdmin && (
        <MailCredentialsModal
          admin={selectedAdmin}
          onClose={() => setSelectedAdmin(null)}
          onSubmit={onSendMail}
        />
      )}
    </>
  );
};

export default DepotAdminTable;