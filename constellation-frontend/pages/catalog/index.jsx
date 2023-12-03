import Button from "@/components/Button";
import CatalogTable from "@/components/CatalogTable";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import Title from "@/components/Title";
import UserPanel from "@/components/UserPanel";
import { getSession } from "next-auth/react";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";

const mockRows = [
  { id: 1, name: "Product 1", price: 100, active: true, type: "dutch" },
  { id: 2, name: "Product 2", price: 200, active: true, type: "dutch" },
  { id: 3, name: "Product 3", price: 300, active: true, type: "dutch" },
];

export default function Catalog({ user, mockData = [] }) {
  const router = useRouter();
  const headers = ["ID", "Name", "Price"];
  const rows = mockRows
    .filter((row) => row.name.toLowerCase().includes(router.query.filter || ""))
    .map((row) => [row.id, row.name, row.price]);

  const data = { headers, rows };

  const [filter, setFilter] = useState("");
  const handleSearch = () => {
    router.push({ to: "/catalog", query: { filter } });
  };
  useEffect(() => {
    setFilter(router.query.filter || "");
  }, [router.query.filter]);

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
            <Button className="w-32" onClick={handleSearch}>
              Search
            </Button>
          </div>
          <CatalogTable className="mt-4" data={data} />
        </Paper>
        <div className="flex space-x-4 mt-4">
          <UserPanel user={user} />
          <Paper className="pt-4 grow flex flex-col">
            <Title className="text-xl">Sell Item</Title>
            <form className="mt-4">
              <div className="flex justify-between space-x-4">
                <Input className="grow" label="Item Name" required />
                <Input className="grow" label="Description" required />
              </div>
              <div className="flex justify-between space-x-4 mt-2">
                <Input className="grow" label="Auction Type" required />
                <Input
                  className="grow"
                  type="number"
                  label="Start Price"
                  required
                />
                <Input
                  className="grow"
                  type="datetime-local"
                  label="End Date"
                  required
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

export async function getServerSideProps(context) {
  const session = await getSession(context);
  const user = session?.user;
  console.log("session at catalog has : " + JSON.stringify(session));
  if (!user) {
    // return {
    //   redirect: {
    //     destination: "/",
    //     permanent: false,
    //   },
    // };
  }

  return {
    props: { user, mockRows },
  };
}
