import React, { useState } from "react";
import ScheduleItem from "./ScheduleItem";

const ScheduleList = ({ schedules, routes, buses, onUpdateSchedule, onCancelSchedule }) => {
  const [editableId, setEditableId] = useState(null);

  return (
    <div className="schedule-list">
      {schedules.length === 0 ? (
        <p className="empty">No schedules found</p>
      ) : (
        schedules.map((schedule) => (
          <ScheduleItem
            key={schedule.id}
            schedule={schedule}
            routes={routes}
            buses={buses}
            editableId={editableId}
            setEditableId={setEditableId}
            onUpdateSchedule={onUpdateSchedule}
            onCancelSchedule={onCancelSchedule}
          />
        ))
      )}
    </div>
  );
};

export default ScheduleList;
