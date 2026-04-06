import "../../styles/PaymentConfirmation.css";

const PaymentConfirmModal = ({ isOpen, onClose, onConfirm }) => {

  if (!isOpen) return null;

  return (
    <div className="modal-overlay confirm-overlay">
      <div className="confirm-modal">
        <h3>Confirm Payment</h3>
        <p>Are you sure you want to proceed with payment?</p>

        <div className="confirm-actions">
          <button className="cancel-btn" onClick={onClose}>Cancel</button>
          <button className="confirm-btn" onClick={onConfirm}>Confirm</button>
        </div>
      </div>
    </div>
  );
};

export default PaymentConfirmModal;
