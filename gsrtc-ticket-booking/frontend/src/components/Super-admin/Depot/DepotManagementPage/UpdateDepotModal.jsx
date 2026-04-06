import { useState, useEffect } from "react";

const UpdateDepotModal = ({ depot, onClose, onSubmit }) => {
  const [depotName, setDepotName] = useState("");
  const [address, setAddress] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    if (depot) {
      console.log(depot);

      setDepotName(depot.depotName || "");
      setAddress(depot.address || "");
      setPassword("");
    }
  }, [depot]);

  const handleSubmit = () => {
    if (depotName === "" && address === "" && password === "") {
      alert("Atleast one field is required.")
    }

    onSubmit(depot.depotId, depotName, address, password);
    onClose();
  };

  return (
    <div className="modal-overlay">
      <div className="modal-container">
        <h2>Update Depot</h2>

        <input
          type="text"
          className="modal-input"
          placeholder="Depot Name"
          value={depotName}
          onChange={(e) => setDepotName(e.target.value)}
        />

        <input
          type="text"
          className="modal-input"
          placeholder="Address"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />

        <input
          type="password"
          className="modal-input"
          placeholder="New Password (optional)"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <div className="modal-actions">
          <button className="update-btn" onClick={handleSubmit}>
            Update
          </button>
          <button className="cancel-btn" onClick={onClose}>
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};

export default UpdateDepotModal;