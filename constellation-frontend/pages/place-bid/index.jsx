import Button from "@/components/Button";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import Title from "@/components/Title";
import { useSession } from "next-auth/react";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import useSWR from "swr";

const fetcher = (url) => fetch(url).then((res) => res.json());
const truncate = (num) => Math.floor(num * 100) / 100;

const PlaceBid = () => {
  const router = useRouter();
  const { data: session } = useSession();
  const user = session?.user;
  const { itemId } = router.query;
  const [bid, setBid] = useState(0);

  const {
    data: item,
    error,
    isLoading,
  } = useSWR(
    () => (itemId ? `${process.env.GATEWAY_URL}/items/${itemId}` : null),
    fetcher
  );

  useEffect(() => {
    if (item && !item.isDutch) {
      const minPrice =
        item.highestBid === -1 ? item.initialPrice : item.highestBid;
      setBid(truncate(minPrice + 0.01));
    }
  }, [item]);

  const handleSumbit = (e) => {
    e.preventDefault();
    const bidValue = item.isDutch ? item.initialPrice : bid;
    console.log("handles submit");
    fetch(`${process.env.GATEWAY_URL}/bids`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        price: bidValue,
        itemId: itemId,
        userId: user.id,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error();
        }

        if (item.isDutch) {
          alert("Bid placed successfully!");
          router.push("/auction-ended?itemId=" + itemId);
        } else {
          alert("Bid placed successfully!");
          router.reload();
        }
      })
      .catch((error) => {
        alert("Failed to place bid");
      });
  };

  return (
    <Shell>
      <Paper width="400px" className="mt-8 flex flex-col">
        <Title>Place Bid</Title>
        {isLoading && <p>Loading...</p>}
        {!isLoading && !item && <p>Item not found</p>}
        {!isLoading && item && (
          <>
            <div className="border border-black p-4 mt-4">
              <p className="text-gray-700 font-medium">
                <b>Item Name:</b> {item.name}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Item Description:</b> {item.description}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Shipping Price:</b> ${item.shippingPrice.toFixed(2)}
              </p>
            </div>
            <div className="border border-black p-4 mt-4">
              {item.isDutch && (
                <p className="text-gray-700 font-medium">
                  <b>Buy now price:</b> ${item.initialPrice}
                </p>
              )}
              {!item.isDutch && (
                <>
                  <p className="text-gray-700 font-medium">
                    <b>Current Price:</b> $
                    {item.highestBid !== -1
                      ? item.highestBid.toFixed(2)
                      : item.initialPrice.toFixed(2)}
                  </p>
                  <p className="text-gray-700 font-medium">
                    <b>Highest Bidder:</b> {item.highestBidderId}
                  </p>
                </>
              )}
            </div>
            {item.isDutch && (
              <Button className="mt-12" onClick={handleSumbit}>
                Buy Now
              </Button>
            )}
            {!item.isDutch && (
              <form onSubmit={handleSumbit}>
                <Input
                  className="mt-12"
                  placeholder="Bid Price"
                  required
                  value={bid}
                  type="number"
                  min={truncate(item.highestBid + 0.01)}
                  step={0.01}
                  onChange={(e) => setBid(e.target.value)}
                />
                <Button className="mt-4 w-full">Place Bid</Button>
              </form>
            )}
            <Button className="mt-2" onClick={() => router.back()}>
              Back
            </Button>
          </>
        )}
      </Paper>
    </Shell>
  );
};

export default PlaceBid;
