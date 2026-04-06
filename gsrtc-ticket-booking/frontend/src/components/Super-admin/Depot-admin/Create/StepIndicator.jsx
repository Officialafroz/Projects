const StepIndicator = ({ step }) => {

  return (
    <div className="step-indicator">
      <div className={step >= 1 ? "step active" : "step"}>
        1. Basic Info
      </div>

      <div className={step >= 2 ? "step active" : "step"}>
        2. Password
      </div>

      <div className={step >= 3 ? "step active" : "step"}>
        3. Complete
      </div>
    </div>
  );
};

export default StepIndicator;