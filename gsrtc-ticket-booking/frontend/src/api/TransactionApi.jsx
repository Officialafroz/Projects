import axios from "axios";

export const fetchTransactions = async ({
  depotId,
  pnr,
  transactionRef,
  date,
  paymentMethod,
  status
}) => {
  return axios.get(
    "/api/depot/transaction-management/search",
    {
      params: {
        depotId,
        pnr: pnr || null,
        transactionRef: transactionRef || null,
        paymentDate: date || null,
        paymentMethod: paymentMethod || null,
        status: status || null
      }
    }
  );
};
