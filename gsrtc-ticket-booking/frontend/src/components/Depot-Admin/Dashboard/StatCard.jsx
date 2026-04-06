import "../../styles/StatCard.css";

const StatCard = ({ title, value, icon, change }) => {
  return (
    <div className="stat-card">
      <div className="stat-header">
        <h4>{title}</h4>
        <span className="icon">{icon}</span>
      </div>
      <h2>{value}</h2>
      <p className="change">{change}</p>
    </div>
  );
};

export default StatCard;
