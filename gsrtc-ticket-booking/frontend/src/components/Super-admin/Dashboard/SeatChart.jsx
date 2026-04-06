import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer } from "recharts";

const SeatChart = ({ data }) => {

  const colors = ["#3b82f6", "#22c55e", "#ef4444"]

  return (
    <div className="card seat-card">
      <h3>Seat Occupancy</h3>
      <ResponsiveContainer height={250}>
        <PieChart>
          <Pie
            data={data}
            dataKey="value"
            nameKey="label"
            outerRadius={90}
          >
            {data.map((entry, index) => (
              <Cell key={index} fill={colors[index % colors.length]} />
            ))}
          </Pie>
          <Tooltip />
        </PieChart>
      </ResponsiveContainer>
    </div>
  )
}

export default SeatChart;