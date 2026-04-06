const FeedbackCard = ({ rating }) => {

  return (
    <div className="card rating-card" >
      <h4>Average Rating</h4>
      <h2>{rating} ⭐</h2>
    </div >
  )
}

export default FeedbackCard;