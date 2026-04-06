import { BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer } from "recharts";

const data = [
  { day: "Mon", bookings: 120 },
  { day: "Tue", bookings: 150 },
  { day: "Wed", bookings: 180 },
  { day: "Thu", bookings: 160 },
  { day: "Fri", bookings: 240 },
  { day: "Sat", bookings: 270 },
  { day: "Sun", bookings: 220 },
];

const BookingChart = () => {
  return (
    <div className="chart-card">
      <h3>Weekly Booking Trends</h3>
      <ResponsiveContainer width="100%" height={250}>
        <BarChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="day" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="bookings" fill="#ff9800" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};

export default BookingChart;
