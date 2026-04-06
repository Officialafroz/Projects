const StaffFilters = ({ filters, setFilters, onSearch }) => {
  return (
    <div className="staff-filters">

      {/* Single Search */}
      <input
        type="text"
        placeholder="Search by STF or name or license number"
        value={filters.search}
        onChange={(e) =>
          setFilters({ ...filters, search: e.target.value })
        }
      />

      {/* Role */}
      <select
        value={filters.role}
        onChange={(e) =>
          setFilters({ ...filters, role: e.target.value })
        }
      >
        <option value="">All Roles</option>
        <option value="DRIVER">Driver</option>
        <option value="CONDUCTOR">Conductor</option>
        <option value="MANAGER">Manager</option>
      </select>

      {/* Status */}
      <select
        value={filters.status}
        onChange={(e) =>
          setFilters({ ...filters, status: e.target.value })
        }
      >
        <option value="">All Status</option>
        <option value="ACTIVE">Active</option>
        <option value="INACTIVE">Inactive</option>
        <option value="SUSPENDED">Suspended</option>
      </select>

      <button onClick={onSearch}>Search</button>
    </div>
  );
};

export default StaffFilters;
