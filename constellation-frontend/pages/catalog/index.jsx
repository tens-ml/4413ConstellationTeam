import Button from "@/components/Button";
import CatalogTable from "@/components/CatalogTable";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import Title from "@/components/Title";
import UserPanel from "@/components/UserPanel";
import useSearch from "@/lib/useSearch";
import { useSession } from "next-auth/react";
import { useRouter } from "next/router";
import { useForm } from "react-hook-form";
import useSWR from "swr";
const fetcher = (url) => fetch(url).then((res) => res.json());

export default function Catalog({ mockData = [] }) {
  const router = useRouter();

  // bounce if not authed
  const { data: session, status } = useSession();
  const user = session?.user;

  if (status === "unauthenticated") {
    router.push("/");
  }

  // fetch items on page load
  const { filter, setFilter, handleSearch } = useSearch();
  console.log(
    "request: " + `${process.env.GATEWAY_URL}/items?search=${filter}`
  );
  const { data, error, isLoading } = useSWR(
    () => `${process.env.GATEWAY_URL}/items?search=${filter}`,
    fetcher
  );

  // form handling
  const { register, handleSubmit } = useForm();
  const handleSellSubmit = (data) => {
    fetch(`${process.env.GATEWAY_URL}/items`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ ...data, sellerId: user.id }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error();
        }

        alert("Item put on sale!");
        router.push("/");
      })
      .catch((error) => {
        alert("Failed to sell item");
      });
  };

  return (
    <Shell>
      <div className="mt-8 flex flex-col w-4/5 max-w-screen-xl">
        <Paper className="flex flex-col w-full">
          <div className="flex items-center space-x-4">
            <Title>Catalog</Title>
            <Input
              className="h-full grow"
              type="text"
              placeholder="Search by item name..."
              value={filter}
              onChange={(e) => setFilter(e.target.value)}
            />
          </div>
          <CatalogTable className="mt-4" data={data} isLoading={isLoading} />
        </Paper>
        <div className="flex space-x-4 mt-4">
          <UserPanel user={user} />
          <Paper className="pt-4 grow flex flex-col">
            <Title className="text-xl">Sell Item</Title>
            <form className="mt-4" onSubmit={handleSubmit(handleSellSubmit)}>
              <div className="flex justify-between space-x-4">
                <Input
                  className="grow"
                  label="Item Name"
                  required
                  {...register("name")}
                />
                <Input
                  className="grow"
                  label="Description"
                  required
                  {...register("description")}
                />
              </div>
              <div className="flex justify-between space-x-4 mt-2">
                <Input
                  type="checkbox"
                  className="grow"
                  label="Dutch (Buy now)"
                  required
                  {...register("isDutch")}
                />
                <Input
                  className="grow"
                  type="number"
                  label="Start Price"
                  required
                  {...register("initialPrice")}
                />
                <Input
                  className="grow"
                  type="datetime-local"
                  label="End Date"
                  required
                  {...register("auctionEnd")}
                />
              </div>
              <Button className="mt-4 w-full">Sell Item</Button>
            </form>
          </Paper>
        </div>
      </div>
    </Shell>
  );
}
