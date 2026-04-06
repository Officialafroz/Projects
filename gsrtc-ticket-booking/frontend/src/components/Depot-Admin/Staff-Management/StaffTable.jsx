const StaffTable = ({ staffList }) => {
  if (!Array.isArray(staffList) || staffList.length === 0) {
    return <p className="no-data">No staff found</p>;
  }

  return (
    <table className="staff-table">
      <thead>
        <tr>
          <th>Full Name</th>
          <th>STF</th>
          <th>Mobile</th>
          <th>Role</th>
          <th>License No</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>

      <tbody>
        {staffList.map((staff, index) => (
          <tr key={index}>
            <td>{staff.fullName}</td>
            <td>{staff.stf}</td>
            <td>{staff.mobileNumber}</td>
            <td>{staff.role}</td>
            <td>{staff.licenseNumber || "-"}</td>
            <td className={`status `}>
              {staff.status}
            </td>
            <td><button>Update</button></td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default StaffTable;
