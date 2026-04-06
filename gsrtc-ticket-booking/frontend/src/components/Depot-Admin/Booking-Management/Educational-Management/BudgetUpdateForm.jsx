import { useState } from "react";
import { updateBudget } from "../../../../api/EduManagementApi.jsx";

const BudgetUpdateForm = ({ tripRequestId }) => {

  const [budget, setBudget] = useState("");

  const updateBudgetFunc = async () => {

    const res = await updateBudget(tripRequestId, budget);
    alert(res.data);
  };

  return (
    <div className="panel-card">
      <h3>Update Budget</h3>

      <input
        type="number"
        placeholder="Enter Budget"
        value={budget}
        onChange={(e)=>setBudget(e.target.value)}
      />

      <button onClick={updateBudgetFunc}>
        Update
      </button>
    </div>
  );
};

export default BudgetUpdateForm;