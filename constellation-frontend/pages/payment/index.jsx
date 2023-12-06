import Button from "@/components/Button";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import { useSession } from "next-auth/react";
import { useRouter } from "next/router";
import { useForm } from "react-hook-form";
import useSWR from "swr";

const fetcher = (url) => fetch(url).then((res) => res.json());

const Payment = () => {
  const router = useRouter();
  const { data: session } = useSession();
  const user = session?.user;
  const { itemId } = router.query;

  const {
    data: item,
    error,
    isLoading,
  } = useSWR(
    () => (itemId ? `${process.env.GATEWAY_URL}/items/${itemId}` : null),
    fetcher
  );

  const { register, handleSubmit } = useForm();
  const handleSellSubmit = (data) => {
    fetch(`${process.env.GATEWAY_URL}/payments`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        ...data,
        userId: user.id,
        itemId: parseInt(itemId),
        amount: (
          item?.highestBid +
          item?.shippingPrice +
          (router.query?.expedite === "yes" ? item?.expeditePrice : 0)
        ).toFixed(2),
      }),
    })
      .then(async (response) => {
        if (!response.ok) {
          throw new Error();
        }

        alert("Payment success!");
        const res = await response.json();
        router.push({ pathname: "/receipt", query: { paymentId: res } });
      })
      .catch((error) => {
        alert("Failed to make payment");
      });
  };

  return (
    <Shell>
      <Paper width="800px" className="mt-8 flex flex-col">
        <p className="mr-16 text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Payment
        </p>
        <div className="flex mt-8">
          <div className="grow pr-6 border-r-2 border-gray-300">
            <p className="border-b-2 border-gray-300 text-md text-center">
              Winning Bidder
            </p>
            <div className="flex flex-col mt-4">
              <p className="text-gray-700 font-medium">
                <b>First Name:</b>{" "}
                {user?.firstName || user?.email?.split("@")[0] || "N/a"}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Last Name</b> {user?.lastName || "N/a"}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Street Address:</b> {user?.streetAddress || "N/a"}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Province:</b> {user?.province || "N/a"}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Country:</b> {user?.country || "N/a"}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Postal Code:</b> {user?.postalCode || "N/a"}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Total Cost:</b> $
                {(
                  item?.highestBid +
                  item?.shippingPrice +
                  (router.query?.expedite === "yes" ? item?.expeditePrice : 0)
                ).toFixed(2)}
              </p>
            </div>
          </div>
          <form className="grow pl-6" onSubmit={handleSubmit(handleSellSubmit)}>
            <p className="border-b-2 border-gray-300 text-md text-center">
              Credit Card Details
            </p>
            <Input
              className="mt-4"
              label="Card Number"
              required
              type="number"
              {...register("cardNumber")}
            />
            <Input
              className="mt-4"
              label="Card Name"
              required
              {...register("cardName")}
            />
            <Input
              className="mt-4"
              label="Expiry Date"
              required
              {...register("expiryDate")}
            />

            <Input
              className="mt-4"
              label="CCV"
              required
              {...register("ccv")}
              type="number"
              min={0}
              max={999}
            />
            <Button className="mt-4 w-full">Submit</Button>
          </form>
        </div>
      </Paper>
    </Shell>
  );
};

export default Payment;
