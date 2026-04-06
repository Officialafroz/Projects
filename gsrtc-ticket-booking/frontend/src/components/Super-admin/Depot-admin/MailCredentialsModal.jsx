import { useState, useEffect } from "react";
import "../../styles/depot.css"; // reuse modal styles

const MailCredentialsModal = ({ admin, onClose, onSubmit }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    if (admin) {
      setEmail("");
      setPassword("");
    }
  }, [admin]);

  const handleSubmit = () => {
    if (!email.trim() || !password.trim()) {
      alert("Email and Password are required");
      return;
    }

    console.log(admin);

    onSubmit(admin.depotAdminId, email, password);
    onClose();
  };

  return (
    <div className="modal-overlay">
      <div className="modal-container">
        <h2>Send Credentials</h2>

        <input
          type="email"
          className="modal-input"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          className="modal-input"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <div className="modal-actions">
          <button className="update-btn" onClick={handleSubmit}>
            Send Mail
          </button>
          <button className="cancel-btn" onClick={onClose}>
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};

export default MailCredentialsModal;