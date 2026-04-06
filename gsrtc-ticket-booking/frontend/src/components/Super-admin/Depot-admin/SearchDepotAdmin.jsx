import { useState } from "react";
import { searchDepotAdmins } from "../../../api/DepotAdminApi.jsx";

const SearchDepotAdmin = ({ setAdmins }) => {

  const [keyword, setKeyword] = useState("");

  const handleSearch = () => {

    searchDepotAdmins(keyword, 0, 10).then((res) => {
      setAdmins(res.data.content);
    });

  };

  return (
    <div className="search-box">

      <input
        placeholder="Search by Name / Email / Depot"
        onChange={(e) => setKeyword(e.target.value)}
      />

      <button onClick={handleSearch}>
        Search
      </button>

    </div>
  );
};

export default SearchDepotAdmin;