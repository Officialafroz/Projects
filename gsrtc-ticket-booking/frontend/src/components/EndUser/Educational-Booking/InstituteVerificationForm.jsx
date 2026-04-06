import { useState } from "react";
import { verifyInstitute } from "../../../api/InstituteApi.jsx";

const InstituteVerificationForm = ({ onVerified }) => {
  const [code, setCode]       = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError]     = useState("");

  const confirm = async () => {
    if (!code.trim()) { setError("Institute code is required."); return; }
    setLoading(true);
    setError("");
    try {
      const res = await verifyInstitute(code);
      onVerified(res.data);
    } catch {
      setError("Verification failed. Please check the code and try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="eb-card">
      <p className="eb-card__desc">
        Enter your institution's UDISE (schools) or AISHE (colleges) code to verify
        eligibility before submitting a trip request.
      </p>
      <div className="eb-field-row">
        <input
          className="eb-input eb-input--lg"
          type="text"
          placeholder="Enter UDISE or AISHE Code"
          value={code}
          onChange={(e) => { setCode(e.target.value); setError(""); }}
          onKeyDown={(e) => e.key === "Enter" && confirm()}
        />
        <button className="eb-btn eb-btn--primary" onClick={confirm} disabled={loading}>
          {loading ? <span className="eb-spinner" /> : "Verify Institute"}
        </button>
      </div>
      {error && <p className="eb-error">{error}</p>}
    </div>
  );
};

export default InstituteVerificationForm;