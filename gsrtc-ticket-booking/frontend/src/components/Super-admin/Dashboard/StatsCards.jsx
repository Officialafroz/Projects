import StatCard from "../../Dashboard/StatCard.jsx";

const StatsCards = ({ data }) => {
  const stats = [
    { title: "Total Buses", value: data.totalBuses },
    { title: "On Trip Buses", value: data.totalOnTripBuses },
    { title: "Total Routes", value: data.totalRoutes },
    { title: "Total Trips", value: data.totalTrips },
    { title: "Registered Users", value: data.registeredUsers },
    { title: "Total Bookings", value: data.totalBookings },
    { title: "Passengers", value: data.totalSeatsBooked },
    { title: "Total Revenue", value: data.totalRevenue },
    { title: "Total Depots", value: data.totalDepots },
    { title: "Total Staff", value: data.totalStaff },
    { title: "Educational Trips", value: data.totalEducationalTrips },
    { title: "Educational Trip Revenue", value: data.educationalTripRevenue },
    { title: "Average Monthly Revenue", value: data.averageMonthlyRevenue },
    { title: "Avg Monthly Booking/User", value: data.averageMonthlyBookingPerUser },
    { title: "Feedback Rating", value: data.feedbackRating }
  ];

  return (
    <>
      {stats.map((stat, index) => (
        <StatCard
          key={index}
          title={stat.title}
          value={stat.value}
        />
      ))}
    </>
  );
}

export default StatsCards;