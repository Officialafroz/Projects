import { Navigate, Route, Routes } from 'react-router-dom'
import Sidebar from '../components/Depot-Admin/Sidebar.jsx'
import Dashboard from '../components/Depot-Admin/Dashboard/Dashboard.jsx'
import BusManagement from '../components/Depot-Admin/Bus-Management/BusManagement.jsx'
import RouteManagement from '../components/Depot-Admin/Route-Management/RouteManagement.jsx'
import ScheduleManagement from '../components/Depot-Admin/Schedule-Management/ScheduleManagement.jsx'
import BookingManagement from '../components/Depot-Admin/Booking-Management/BookingManagement.jsx'
import TransactionManagement from '../components/Depot-Admin/Transaction-Management/TransactionManagement.jsx'
import StaffManagement from '../components/Depot-Admin/Staff-Management/StaffManagement.jsx'
// import DepotList from '../components/Super-admin/Depot/DepotListPage.jsx'
import DepotListPage from '../components/Super-admin/Depot/DepotOverviewPage/DepotOverviewPage.jsx'
import SuperAdminDepotAdminPage from '../components/Super-admin/Depot-admin/DepotAdminPage.jsx'
import DepotAdminPage from '../components/Super-admin/Depot-admin/DepotAdminPage.jsx'
import DashboardPage from '../components/Super-admin/Dashboard/DashboardPage.jsx'
import PricingPage from '../components/Super-admin/Pricing/PricingPage.jsx'
import EducationBookingPage from '../components/Depot-Admin/Booking-Management/Educational-Management/EducationalBookingPage.jsx'
import PassengerManagementPage from '../components/Depot-Admin/Booking-Management/Passenger-Management/PassengerManagementPage.jsx'
import DepotManagementPage from '../components/Super-admin/Depot/DepotManagementPage/DepotManagementPage.jsx'
import ReservationPolicyPage from '../components/Super-admin/Policies/ReservationPolicyPage.jsx'
import CancellationPolicyPage from '../components/Super-admin/Policies/CancellationPolicyPage.jsx'



const AdminPages = ({ authUser }) => {
  return (
    <>
      <div className="admin-layout">
        <Sidebar />
        <div className="admin-content">
          {authUser.role === "SUPER_ADMIN" ? (
            <Routes>
              <Route path="/" element={<Navigate to={"/super-admin/dashboard"} />} />
              <Route path="/super-admin/dashboard" element={<DashboardPage />} />
              <Route path="/super-admin/bus-depot-management" element={<DepotManagementPage />} />
              <Route path="/super-admin/depot-overview" element={<DepotListPage />} />
              <Route path="/super-admin/depot-admin-management" element={<DepotAdminPage />} />
              <Route path="/super-admin/pricing" element={<PricingPage />} />
              <Route path="/super-admin/reservation-policies" element={<ReservationPolicyPage />} />
              <Route path="/super-admin/cancellation-policies" element={<CancellationPolicyPage />} />
              <Route path="/super-admin/reports" />

              <Route path="*" element={<Navigate to="/super-admin/dashboard" />} />
            </Routes>
          ) : (
            <Routes>
              <Route path="/" element={<Navigate to={"/admin/dashboard"} />} />
              <Route path="/admin/dashboard" element={<Dashboard />} />
              <Route path="/admin/bus-management" element={<BusManagement />} />
              <Route path="/admin/route-management" element={<RouteManagement />} />
              <Route path="/admin/schedule-management" element={<ScheduleManagement />} />
              <Route path="/admin/booking-management" element={<BookingManagement />} />
              <Route path="/admin/edu-booking-management" element={<EducationBookingPage />} />
              <Route path="/admin/passenger-management" element={<PassengerManagementPage />} />
              {/* <Route path="/admin/booking-management" element={<BookingManagement />} /> */}
              <Route path="/admin/transaction-management" element={<TransactionManagement />} />
              <Route path="/admin/staff-management" element={<StaffManagement />} />
              <Route path="/admin/user-management" />

              <Route path="*" element={<Navigate to="/admin/dashboard" />} />
            </Routes>
          )}
        </div>
      </div >
    </>
  )
}

export default AdminPages