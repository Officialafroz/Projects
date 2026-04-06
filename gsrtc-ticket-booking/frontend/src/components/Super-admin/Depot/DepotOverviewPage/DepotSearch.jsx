import { useState } from "react";
import { getByDepotName } from "../../../../api/BusDepotApi.jsx";

const DepotSearch = ({ setDepot }) => {
  const [name, setName] = useState("");

  const searchDepot = async () => {
    try {
      const res = await getByDepotName(name);
      setDepot([res.data]);
    } catch (error) {
      alert(error);
    }
  };

  return (
    <div className="search-box">
      <input
        type="text"
        placeholder="Search depot by name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <button onClick={searchDepot}>Search</button>
    </div>
  );
};

export default DepotSearch;