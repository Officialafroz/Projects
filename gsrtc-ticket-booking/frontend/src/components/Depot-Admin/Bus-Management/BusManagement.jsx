import React, { useState } from "react";
import BusForm from "./BusForm";
import BusTemplates from "./BusTemplates";
import BusList from "./BusList";
import "../../styles/BusManagement.css";

const BusManagement = () => {
  const [selectedTemplate, setSelectedTemplate] = useState(null);
  const [buses, setBuses] = useState([]);

  const handleTemplateSelect = (template) => {
    setSelectedTemplate(template);
  };

  const handleAddBus = (bus) => {
    setBuses([bus, ...buses]);
    setSelectedTemplate(null);
  };

  // const handleOnRemove = (index) => {
  //   if (window.confirm("Are you sure you want to remove this bus?")) {
  //     setBuses((prevBuses) => prevBuses.filter((_, i) => i !== index));
  //   }
  // };

  return (
    <div className="bus-management-container">
      <h2>Bus Management</h2>
      <BusTemplates onSelect={handleTemplateSelect} />
      <BusForm template={selectedTemplate} onAddBus={handleAddBus} />
      <BusList buses={buses} setBuses={setBuses} />
    </div>
  );
};

export default BusManagement;
