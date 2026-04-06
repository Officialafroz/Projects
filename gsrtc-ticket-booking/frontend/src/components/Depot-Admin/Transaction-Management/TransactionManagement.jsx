import { useContext, useEffect, useState } from "react";
import TransactionFilters from "./TransactionFilters.jsx";
import TransactionTable from "./TransactionTable.jsx";
import { fetchTransactions } from "../../../api/TransactionApi.jsx";
import "../../styles/TransactionManagement.css";
import { AppContext } from "../../../store/AppContext.jsx";

const TransactionManagement = () => {
  const {authUser} = useContext(AppContext);
  const depotId = authUser.depotId;

  const [filters, setFilters] = useState({
    pnr: "",
    transactionRef: "",
    date: "",
    paymentMethod: "",
    status: ""
  });

  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);

  // Load ALL transactions initially (no filters)
  useEffect(() => {
    loadTransactions();
  }, []);

  const loadTransactions = async () => {
    try {
      setLoading(true);
      const res = await fetchTransactions({ depotId, ...filters });
      setTransactions(res.data);
      console.log(res.data);
    } catch (err) {
      console.error(err);
      setTransactions([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="transaction-page">
      <h2>Transaction Management</h2>

      <TransactionFilters
        filters={filters}
        setFilters={setFilters}
        onSearch={loadTransactions}
      />

      {loading ? <p>Loading...</p> : <TransactionTable transactions={transactions} />}
    </div>
  );
};

export default TransactionManagement;
