import { useContext, useEffect, useState } from "react";
import TripList from "../Educational-Management/TripList.jsx";
import TripDetailPage from "./TripDetialPage.jsx";
import BudgetModal from "../Educational-Management/BudgetModal.jsx";
import "../../../styles/EduBookingManagement.css";
import "../../../styles/BudgetModal.css"

import {
  loadTripOverviews,
  updateBudgetAndApprove,
  handleStatus,
  // rejectTrip,
  // cancelTrip,
} from "../../../../api/EduManagementApi";

import { AppContext } from "../../../../store/AppContext.jsx";

const EducationBookingPage = () => {
  const { authUser } = useContext(AppContext);

  const [trips, setTrips] = useState([]);
  const [selectedTrip, setSelectedTrip] = useState(null);
  const [detailTrip, setDetailTrip] = useState(null);
  const [showModal, setShowModal] = useState(false);

  const loadTrips = async () => {
    const res = await loadTripOverviews(authUser.depotId);
    setTrips(res.data);
  };

  useEffect(() => {
    loadTrips();
  }, []);

  // Open detail view (in-page navigation)
  const handleCardClick = (trip) => {
    setDetailTrip(trip);
  };

  const handleBackToList = () => {
    setDetailTrip(null);
  };

  // Actions
  const handleApproveClick = (trip) => {
    setSelectedTrip(trip);
    setShowModal(true);
  };

  const handleApproveCurrent = async () => {
    const res = await handleStatus(selectedTrip.tripRequestId, 'UNDER_PROCESS');

    alert(res.data);

    setShowModal(false);
    loadTrips();
  };

  const handleUpdateAndApprove = async (budget) => {
    const res = await updateBudgetAndApprove(selectedTrip.tripRequestId, budget);

    alert(res.data);

    setShowModal(false);
    loadTrips();
  };

  const handleReject = async (tripId) => {
    const res = await handleStatus(tripId, 'REJECTED');
    alert(res.data)

    loadTrips();
  };

  const handleCancel = async (tripId) => {
    await cancelTrip(tripId);
    loadTrips();
  };

  return (
    <div className="edu-booking-container">
      {detailTrip ? (
        <TripDetailPage trip={detailTrip} onBack={handleBackToList} />
      ) : (
        <TripList
          trips={trips}
          onCardClick={handleCardClick}
          onApprove={handleApproveClick}
          onReject={handleReject}
          onCancel={handleCancel}
        />
      )}

      {showModal && (
        <BudgetModal
          onClose={() => setShowModal(false)}
          onApproveCurrent={handleApproveCurrent}
          onUpdateApprove={handleUpdateAndApprove}
        />
      )}
    </div>
  );
};

export default EducationBookingPage;