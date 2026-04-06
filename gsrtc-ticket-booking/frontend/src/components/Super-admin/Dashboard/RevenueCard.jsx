const RevenueCard = ({ revenue }) => {

  return (
    <div className="card big-card revenue-card">
      <h4>Total Revenue</h4>
      <h1>₹ {revenue}</h1>
    </div>
  )
}

export default RevenueCard;