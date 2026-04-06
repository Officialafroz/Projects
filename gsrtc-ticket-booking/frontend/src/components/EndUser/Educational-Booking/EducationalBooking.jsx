import { useState, useContext } from "react";
import InstituteVerificationForm from "./InstituteVerificationForm";
import InstituteInfoCard from "./InstituteInfoCard";
import TripRequestForm from "./TripRequestForm";
import MyTrips from "./MyTrips";
import "../../styles/EducationalBooking.css";
import { AppContext } from "../../../store/AppContext";

const STEPS = [
  { id: "trips",  label: "My Bookings", icon: "🗂️" },
  { id: "new",    label: "New Request", icon: "＋"  },
];

const EducationalBooking = () => {
  const { authUser } = useContext(AppContext);

  const [activeStep, setActiveStep] = useState("trips");
  const [institute, setInstitute]   = useState(null);
  const [subStep, setSubStep]       = useState("verify"); // "verify" | "form"

  const handleVerified = (data) => {
    setInstitute(data);
    setSubStep("form");
  };

  const handleSubmitSuccess = () => {
    setInstitute(null);
    setSubStep("verify");
    setActiveStep("trips");
  };

  const handleNavClick = (id) => {
    if (id === "new") {
      setInstitute(null);
      setSubStep("verify");
    }
    setActiveStep(id);
  };

  const getTitle = () => {
    if (activeStep === "trips")                      return "My Bookings";
    if (activeStep === "new" && subStep === "verify") return "Verify Institute";
    return "Trip Request";
  };

  return (
    <div className="eb-root">

      {/* ── Floating sidebar ── */}
      <aside className="eb-sidebar">
        <div className="eb-sidebar__brand">
          <span className="eb-sidebar__logo">🚌</span>
          <span className="eb-sidebar__name">EduTrip</span>
        </div>

        <nav className="eb-sidebar__nav">
          {STEPS.map((step) => {
            const active = activeStep === step.id;
            return (
              <button
                key={step.id}
                className={`eb-nav-item${active ? " eb-nav-item--active" : ""}`}
                onClick={() => handleNavClick(step.id)}
              >
                <span className="eb-nav-item__icon">{step.icon}</span>
                <span className="eb-nav-item__label">{step.label}</span>
                {active && <span className="eb-nav-item__pip" />}
              </button>
            );
          })}
        </nav>

        {institute && activeStep === "new" && (
          <div className="eb-sidebar__institute">
            <p className="eb-sidebar__inst-label">✔ Verified</p>
            <p className="eb-sidebar__inst-name">{institute.instituteName}</p>
            <span className="eb-sidebar__inst-code">{institute.instituteCode}</span>
          </div>
        )}
      </aside>

      {/* ── Main content ── */}
      <main className="eb-main">
        <header className="eb-main__header">
          <p className="eb-main__eyebrow">GSRTC · Educational Trip Portal</p>
          <h1 className="eb-main__title">{getTitle()}</h1>
        </header>

        <div className="eb-main__body">

          {activeStep === "trips" && (
            <MyTrips userId={authUser.userId} />
          )}

          {activeStep === "new" && subStep === "verify" && (
            <InstituteVerificationForm onVerified={handleVerified} />
          )}

          {activeStep === "new" && subStep === "form" && institute && (
            <>
              <InstituteInfoCard institute={institute} />
              <TripRequestForm
                institute={institute}
                onSubmitSuccess={handleSubmitSuccess}
              />
            </>
          )}

        </div>
      </main>
    </div>
  );
};

export default EducationalBooking;