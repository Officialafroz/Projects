import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";

const data = [
  { month: "Jan", revenue: 800000 },
  { month: "Feb", revenue: 900000 },
  { month: "Mar", revenue: 1050000 },
  { month: "Apr", revenue: 1000000 },
  { month: "May", revenue: 1200000 },
  { month: "Jun", revenue: 1300000 },
];

const RevenueChart = () => {
  return (
    <div className="chart-card">
      <h3>Revenue Trends</h3>
      <ResponsiveContainer width="100%" height={250}>
        <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="month" />
          <YAxis />
          <Tooltip />
          <Line type="monotone" dataKey="revenue" stroke="#0066cc" />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default RevenueChart;
