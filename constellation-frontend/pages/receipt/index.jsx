import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import { useRouter } from "next/router";
import useSWR from "swr";

const fetcher = (url) => fetch(url).then((res) => res.json());

const Receipt = () => {
  const router = useRouter();
  const { paymentId } = router.query;

  const {
    data: payment,
    error,
    isLoading,
  } = useSWR(
    () =>
      paymentId ? `${process.env.GATEWAY_URL}/payments/${paymentId}` : null,
    fetcher
  );

  return (
    <Shell>
      <Paper width="800px" className="mt-12 flex flex-col">
        <p className="mr-16 text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Payment
        </p>
        <form className="flex mt-8">
          <div className="grow pr-6 border-r-2 border-gray-300">
            <p className="border-b-2 border-gray-300 text-md text-center">
              Receipt
            </p>
            <div className="flex flex-col mt-4">
              <p className="text-gray-700 font-medium">
                <b>First Name:</b> {payment?.user?.firstName}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Last Name</b> {payment?.user?.lastName}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Street Address:</b> {payment?.user?.streetAddress}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Province:</b> {payment?.user?.province}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Country:</b> {payment?.user?.country}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Postal Code:</b> {payment?.user?.postalCode}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Total Paid:</b> {payment?.amount}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Item ID:</b> {payment?.itemId}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Item Name:</b> {payment?.item?.name}
              </p>
            </div>
          </div>
          <div className="grow pl-6 w-1/2">
            <p className="border-b-2 border-gray-300 text-md text-center">
              Shipping Details
            </p>
            <p className="mt-12">
              Your item should ship within the next{" "}
              <b>{payment?.item?.daysToShip}</b> business days.
            </p>
          </div>
        </form>
      </Paper>
    </Shell>
  );
};

export default Receipt;
