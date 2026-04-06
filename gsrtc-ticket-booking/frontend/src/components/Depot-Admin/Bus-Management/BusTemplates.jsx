const templates = [
  { name: "Express", description: "Fast service with minimal stops." },
  { name: "Premium", description: "Luxury coaches with air conditioning." },
  { name: "Sleeper", description: "Night travel with sleeper berths." },
  { name: "Ordinary", description: "Affordable buses with multiple stops." },
];

const BusTemplates = ({ onSelect }) => {
  return (
    <div className="bus-templates">
      {/* <h3>Select a Bus Template</h3> */}
      <div className="template-grid">
        {templates.map((temp) => (
          <div key={temp.name} className="template-card" onClick={() => onSelect(temp)}>
            <h4>{temp.name}</h4>
            <p>{temp.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default BusTemplates;
