const InstituteInfoCard = ({ institute }) => {
  const fields = [
    { label: "Code",      value: institute.instituteCode  },
    { label: "Type",      value: institute.instituteType  },
    { label: "Ownership", value: institute.ownership      },
    { label: "Location",  value: institute.location       },
    { label: "Phone",     value: institute.institutePhone },
    { label: "Email",     value: institute.instituteEmail },
  ];

  return (
    <div className="eb-inst-card">
      <div className="eb-inst-card__head">
        <span className="eb-inst-card__verified">✔ Verified</span>
        <h3 className="eb-inst-card__name">{institute.instituteName}</h3>
      </div>
      <div className="eb-inst-card__grid">
        {fields.filter(f => f.value).map(({ label, value }) => (
          <div className="eb-inst-card__item" key={label}>
            <span className="eb-inst-card__label">{label}</span>
            <span className="eb-inst-card__value">{value}</span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default InstituteInfoCard;