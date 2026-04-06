import DashboardHeader from "./DashboardHeader";
import StatCard from "./StatCard";
import RevenueChart from "./RevenueChart";
import BookingChart from "./BookingChart";
import "../../styles/Dashboard.css";

const Dashboard = () => {
  return (
    <div className="dashboard">
      <div className="dashboard-content">
        <DashboardHeader />

        <div className="stats-grid">
          <StatCard title="Total Revenue" value="₹12.5L" change="+12.5% from last month" />
          <StatCard title="Active Buses" value="142" change="+5 from last week" />
          <StatCard title="Total Bookings" value="1,834" change="+8.2% from last month" />
          <StatCard title="Registered Users" value="8,492" change="+156 this week" />
        </div>

        <div className="chart-grid">
          <RevenueChart />
          <BookingChart />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
