import {
  getDashboardData,
  getBookingTrends,
  getRevenueTrends,
  getSeatOccupancy,
  getUserGrowth
} from "../../../api/DashboardApi.jsx";
import RevenueCard from "./RevenueCard.jsx";
import BookingTrendChart from "./BookingTrendChart.jsx";
import UserGrowthChart from "./UserGrowthChart.jsx";
import SeatChart from "./SeatChart.jsx";
import FeedbackCard from "./FeedbackCard.jsx";
import { useEffect, useState } from "react";
import { StatsCard } from "./Statscard.jsx";

const Dashboard = () => {

  const [data, setData] = useState({});
  const [bookingData, setBookingData] = useState([]);
  const [revenue, setRevenue] = useState([]);
  const [userGrowth, setUserGrowth] = useState([]);
  const [seatData, setSeatData] = useState([]);

  useEffect(() => {

    getDashboardData(12, 2025).then(res => setData(res.data));

    getBookingTrends(2025).then(res => setBookingData(res.data));

    getRevenueTrends(2025).then(res => {
      const data = res.data;
      setRevenue(res.data[0].value);
      console.log(data[0].value);
    });

    getUserGrowth(2025).then(res => setUserGrowth(res.data));

    getSeatOccupancy(2025).then(res => setSeatData(res.data));

  }, [])

  return (
    <div className="dashboard-grid">
      <StatsCard title="Total Buses" value={data.totalBuses} />
      <StatsCard title="On trip buses" value={data.totalOnTripBuses} />
      <StatsCard title="Total Routes" value={data.totalRoutes} />
      <StatsCard title="Total Trips" value={data.totalTrips} />
      <StatsCard title="Registered Users" value={data.registeredUsers} />
      <StatsCard title="Total Bookings" value={data.totalBookings} />
      <StatsCard title="Seats booked" value={data.totalSeatsBooked} />
      <StatsCard title="Total Revenue" value={data.totalRevenue} />
      <StatsCard title="Total Depots" value={data.totalDepots} />
      <StatsCard title="Total Staff" value={data.totalStaff} />
      <StatsCard title="Educational Trips" value={data.totalEducationalTrips} />
      <StatsCard title="Educational Trips Revenue" value={data.educationalTripRevenue} />
      <StatsCard title="Avg Monthly Revenue" value={data.averageMonthlyRevenue} />
      <StatsCard title="Avg Monthly Booking" value={data.averageMonthlyBookingPerUser} />
      <StatsCard title="Feedback Ratings" value={data.feedbackRating} />

      <BookingTrendChart data={bookingData} />
      <RevenueCard revenue={revenue} />
      <UserGrowthChart data={userGrowth} />
      <SeatChart data={seatData} />
      <FeedbackCard rating={0} />
    </div>
  );
}

export default Dashboard;