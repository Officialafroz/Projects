import { useState } from "react";
import { createPortal } from "react-dom";
import { IoClose } from "react-icons/io5";

const BudgetModal = ({ onClose, onApproveCurrent, onUpdateApprove }) => {
  const [budget, setBudget] = useState("");

  const modalContent = (
    <div className="bm-overlay" onClick={onClose}>
      <div className="bm-box" onClick={(e) => e.stopPropagation()}>

        <button className="bm-close-btn" onClick={onClose} aria-label="Close">
          <IoClose size={20} />
        </button>

        <h3 className="bm-title">Approve Trip</h3>
        <p className="bm-subtitle">Optionally update the budget before approving.</p>

        <input
          type="number"
          className="bm-input"
          placeholder="Enter updated budget (optional)"
          value={budget}
          onChange={(e) => setBudget(e.target.value)}
        />

        <div className="bm-actions">
          <button className="bm-btn bm-btn--proceed" onClick={onApproveCurrent}>
            Proceed with Existing Budget
          </button>
          <button
            className="bm-btn bm-btn--update"
            onClick={() => onUpdateApprove(budget)}
            disabled={!budget}
          >
            Update &amp; Approve
          </button>
        </div>

      </div>
    </div>
  );

  return createPortal(modalContent, document.body);
};

export default BudgetModal;