import React, { useState } from "react";

const ScheduleItem = ({
  schedule,
  routes,
  buses,
  editableId,
  setEditableId,
  onUpdateSchedule,
  onCancelSchedule,
}) => {
  const [editData, setEditData] = useState(schedule);

  const handleEdit = () => {
    if (editableId === schedule.id) {
      const selectedRoute = routes.find((r) => r.id === parseInt(editData.routeId));
      const selectedBus = buses.find((b) => b.id === parseInt(editData.busId));

      onUpdateSchedule({
        ...editData,
        routeName: `${selectedRoute.from} - ${selectedRoute.to}`,
        busName: `${selectedBus.name} (${selectedBus.number})`,
      });

      setEditableId(null);
    } else {
      setEditableId(schedule.id);
    }
  };

  return (
    <div className={`schedule-item ${schedule.status.toLowerCase()}`}>
      {editableId === schedule.id ? (
        <>
          <select
            value={editData.routeId}
            onChange={(e) =>
              setEditData({ ...editData, routeId: e.target.value })
            }
          >
            {routes.map((r) => (
              <option key={r.id} value={r.id}>
                {r.from} → {r.to}
              </option>
            ))}
          </select>

          <select
            value={editData.busId}
            onChange={(e) =>
              setEditData({ ...editData, busId: e.target.value })
            }
          >
            {buses.map((b) => (
              <option key={b.id} value={b.id}>
                {b.name} ({b.number})
              </option>
            ))}
          </select>

          <input
            type="date"
            value={editData.date}
            onChange={(e) =>
              setEditData({ ...editData, date: e.target.value })
            }
          />
          <input
            type="time"
            value={editData.departure}
            onChange={(e) =>
              setEditData({ ...editData, departure: e.target.value })
            }
          />
        </>
      ) : (
        <>
          <p><strong>Route:</strong> {schedule.routeName}</p>
          <p><strong>Bus:</strong> {schedule.busName}</p>
          <p><strong>Date:</strong> {schedule.date}</p>
          <p><strong>Departure:</strong> {schedule.departure}</p>
          <p><strong>Status:</strong> {schedule.status}</p>
        </>
      )}

      <div className="actions">
        <button onClick={handleEdit}>
          {editableId === schedule.id ? "Save" : "Edit"}
        </button>
        <button
          onClick={() => onCancelSchedule(schedule.id)}
          className="inactive-btn"
        >
          Cancel
        </button>
      </div>
    </div>
  );
};

export default ScheduleItem;
