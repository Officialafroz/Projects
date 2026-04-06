const PassengerTable = ({ passengers }) => {

  return (
    <table className="passenger-table">

      <thead>
        <tr>
          <th>Reference</th>
          <th>Name</th>
          <th>Age</th>
          <th>Gender</th>
          <th>Boarding</th>
          <th>Destination</th>
          <th>Seat</th>
          <th>Fare</th>
          <th>Booking Time</th>
        </tr>
      </thead>

      <tbody>

        {passengers.map((p, i) => (
          <tr key={i}>
            <td>{p.passRef}</td>
            <td>{p.name}</td>
            <td>{p.age}</td>
            <td>{p.gender}</td>
            <td>{p.boardingPoint}</td>
            <td>{p.destinationPoint}</td>
            <td>{p.seatNo}</td>
            <td>{p.individualFare}</td>
            <td>{p.bookingTime}</td>
          </tr>
        ))}

      </tbody>

    </table>
  );
};

export default PassengerTable;